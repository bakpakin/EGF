package bakpakin.egf;

import static org.lwjgl.opengl.GL11.GL_ALPHA_TEST;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_GREATER;
import static org.lwjgl.opengl.GL11.GL_LEQUAL;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glAlphaFunc;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glDepthFunc;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import bakpakin.egf.framework.*;
import bakpakin.egf.util.AssetManager;

/**
 * EGF provides simple to use static methods to update
 * a (@link World} in a timed loop, as well as switch the current world dynamically, 
 * allowing for multiple levels or scenes.
 * @author Calvin
 *
 */
public class EGF {
	
	//Initialize the default settings for the application display.
	static {
		EGF.setFullscreen(true);
		EGF.setVSync(true);
		EGF.setTitle("EGF");
	}

	//prevents instatiation
	private EGF() {}

	/**
	 * The World that is currently being updated.
	 */
	private static World scene;

	/**
	 * 'nough said
	 */
	private static boolean loopDone;
	
	private static int fps = 60;

	/**
	 * Starts a main game loop with the given {@link World}. Does
	 * not provide any timing mechanism, so the world will begin another
	 * update right after it finishes the previous update.
	 * @param scene - the {@link World} to begin the update loop with.
	 */
	public static void mainLoop(World scene) {
		EGF.scene = scene;
		loopDone = false;
		long t2 = System.currentTimeMillis();
		long t1;
		long td;
		while (!loopDone && EGF.scene != null) {
			t1 = t2;
			t2 = System.currentTimeMillis();
			td = t2 - t1; 
			EGF.scene.update(((double)td) / 1000.0);
			Display.sync(fps);
		}
		cleanUp();
	}

	/**
	 * Cleans up assets and LWJGL stuff.
	 */
	public static void cleanUp() {
		AssetManager.cleanUp();

		Display.destroy();
		Mouse.destroy();
		Keyboard.destroy();
		AL.destroy();
	}

	/**
	 * @return the current {@link World}
	 */
	public static World getScene() {
		return scene;
	}

	/**
	 * Sets the current {@link World} that's updated in every run through the main loop.
	 * @param scene
	 */
	public static void setScene(World scene) {
		EGF.scene = scene;
	}

	/**
	 * Ends the main loop. Does not end the game right away, but does not 
	 * update after the current update is finished.
	 */
	public static void endGame() {
		loopDone = true;
	}
	
	/**
	 * Sets whether or not the game should run in fullscreen. A simple
	 * wrapper for <code>Dispaly.setFullscreen(boolean fullscreen)</code>
	 * @param fullscreen
	 */
	public static void setFullscreen(boolean fullscreen) {
		try {
			Display.setFullscreen(fullscreen);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return if the current display is in fullscreen.
	 */
	public static boolean isFullscreen() {
		return Display.isFullscreen();
	}
	
	/**
	 * Sets the size of the display.
	 * @param width
	 * @param height
	 */
	public static void setDisplaySize(int width, int height) {
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @return the width in pixels of the display window.
	 */
	public static int getDisplayWidth() {
		return Display.getWidth();
	}
	
	/**
	 * 
	 * @return the height in pixels of the display window.
	 */
	public static int getDisplayHeight() {
		return Display.getHeight();
	}
	
	/**
	 * @param vSync
	 */
	public static void setVSync(boolean vSync) {
		Display.setVSyncEnabled(vSync);
	}
	
	/**
	 * Sets the title of the application window.
	 * @param title
	 */
	public static void setTitle(String title) {
		Display.setTitle(title);
	}
	
	/**
	 * @return the current title of the application window.
	 */
	public static String getTitle() {
		return Display.getTitle();
	}
	
	/**
	 * Call this method before running a game. This method initiates the openGL context,
	 * creates the display, cleans up old unused Assets, etc. This should be the the first 
	 * method invoked before setting up a game.
	 */
	public static void init() {
		try {
			Display.create();
			Mouse.create();
			Keyboard.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glMatrixMode(GL_MODELVIEW);   
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_DEPTH_TEST);
		glDepthFunc(GL_LEQUAL);
		glEnable(GL_ALPHA_TEST);
		glAlphaFunc(GL_GREATER, 0f);
		AssetManager.cleanUp();//clean up old assets that will no longer be useful.
	}

	public static int getFps() {
		return fps;
	}

	public static void setFps(int fps) {
		EGF.fps = fps;
	}

}

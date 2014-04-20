package bakpakin.egf.util;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;

import bakpakin.egf.framework.*;

/**
 * A Runner provides simple to use static methods to update
 * a (@link World} in a timed loop, as well as switch the current world dynamically, 
 * allowing for multiple levels or scenes.
 * @author Calvin
 *
 */
public class Runner {
	
	//prevents instatiation
	private Runner() {}
	
	/**
	 * The World that is currently being updated.
	 */
	private volatile static World scene;
	
	/**
	 * 'nough said
	 */
	private volatile static boolean loopDone;
	
	/**
	 * Starts a main game loop with the given {@link World}. Does
	 * not provide any timing mechanism, so the world will begin another
	 * update right after it finishes the previous update.
	 * @param scene - the {@link World} to begin the update loop with.
	 */
	public static void mainLoop(World scene) {
		Runner.scene = scene;
		loopDone = false;
		long t2 = System.currentTimeMillis();
		long t1;
		long td;
		while (!loopDone && Runner.scene != null) {
			t1 = t2;
			t2 = System.currentTimeMillis();
			td = t2 - t1; 
			Runner.scene.update(((double)td) / 1000.0);
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
		Runner.scene = scene;
	}
	
	/**
	 * Ends the main loop. Does not end the game right away, but does not 
	 * update after the current update is finished.
	 */
	public static void endGame() {
		loopDone = true;
	}

}

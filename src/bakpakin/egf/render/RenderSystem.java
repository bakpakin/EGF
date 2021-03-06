package bakpakin.egf.render;

import static org.lwjgl.opengl.EXTFramebufferObject.GL_COLOR_ATTACHMENT0_EXT;
import static org.lwjgl.opengl.EXTFramebufferObject.GL_DEPTH_ATTACHMENT_EXT;
import static org.lwjgl.opengl.EXTFramebufferObject.GL_FRAMEBUFFER_EXT;
import static org.lwjgl.opengl.EXTFramebufferObject.GL_RENDERBUFFER_EXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glBindFramebufferEXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glBindRenderbufferEXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glFramebufferRenderbufferEXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glFramebufferTexture2DEXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glGenFramebuffersEXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glGenRenderbuffersEXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glRenderbufferStorageEXT;
import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GLContext;
import org.newdawn.slick.Color;

import bakpakin.egf.EGF;
import bakpakin.egf.framework.Entity;
import bakpakin.egf.framework.Matcher;
import bakpakin.egf.geom.Transform;

/**
 * 
 * @author Calvin
 *
 */
public class RenderSystem extends bakpakin.egf.framework.EntitySystem {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3515894852405144960L;
	
	/**
	 *
	 */
	private static final Comparator<? super Entity> depthSorter = new Comparator<Entity>() {

		@Override
		public int compare(Entity o1, Entity o2) {
			final float d1 = o1.get(RenderComponent.class).getDepth();
			final float d2 = o2.get(RenderComponent.class).getDepth();
			if (d2 > d1) {
				return -1;
			}
			if (d2 < d1) {
				return 1;
			}
			return 0;
		}

	};

	private Camera camera;
	private Camera hudCamera;
	private ArrayList<Entity> drawList;

	private Color backgroundColor = Color.gray;
	
	private int shader;
	
	private boolean escToExit = true;

	int colorTextureID;
	int framebufferID;
	int depthRenderBufferID;
	int fboWidth;
	int fboHeight;

	public RenderSystem() {
		this.setBackgroundColor(this.getBackgroundColor());
		camera = new Camera();
		hudCamera = new Camera(new Transform(
				EGF.getDisplayWidth()/2,
				EGF.getDisplayHeight()/2,
				EGF.getDisplayWidth(),
				EGF.getDisplayHeight(),
				0
				));
		drawList = new ArrayList<Entity>(getEntities());
	}

	private void initFbo() {
		framebufferID = glGenFramebuffersEXT();											// create a new framebuffer
		colorTextureID = glGenTextures();												// and a new texture used as a color buffer
		depthRenderBufferID = glGenRenderbuffersEXT();									// And finally a new depthbuffer

		resizeFbo();
	}

	//This code from lwjgl.org/wiki
	private void resizeFbo() {			

		fboWidth = Display.getWidth();
		fboHeight = Display.getHeight();

		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, framebufferID); 						// switch to the new framebuffer

		// initialize color texture
		glBindTexture(GL_TEXTURE_2D, colorTextureID);									// Bind the colorbuffer texture
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);				// make it linear filtered
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, fboWidth, fboHeight, 0, GL_RGBA, GL_INT, (java.nio.ByteBuffer) null);	// Create the texture data
		glFramebufferTexture2DEXT(GL_FRAMEBUFFER_EXT,GL_COLOR_ATTACHMENT0_EXT,GL_TEXTURE_2D, colorTextureID, 0); // attach it to the framebuffer


		// initialize depth renderbuffer
		glBindRenderbufferEXT(GL_RENDERBUFFER_EXT, depthRenderBufferID);				// bind the depth renderbuffer
		glRenderbufferStorageEXT(GL_RENDERBUFFER_EXT, GL14.GL_DEPTH_COMPONENT24, fboWidth, fboHeight);	// get the data space for it
		glFramebufferRenderbufferEXT(GL_FRAMEBUFFER_EXT,GL_DEPTH_ATTACHMENT_EXT,GL_RENDERBUFFER_EXT, depthRenderBufferID); // bind it to the renderbuffer

		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);									// Switch back to normal framebuffer rendering

	}

	@Override
	protected Matcher initMatcher() {
		return new Matcher(RenderComponent.class);
	}

	@Override
	public void update() {
		if (this.escToExit && (Display.isCloseRequested() || Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)))
			EGF.endGame();
		if (shader > 0 && GLContext.getCapabilities().OpenGL20) {
			updateWithFbo();
		} else {
			updateNoFbo();
		}
	}

	private void updateWithFbo() {
		if (Display.getWidth() != fboWidth || Display.getHeight() != fboHeight) {
			resizeFbo();
		}

		//Render first pass to fbo
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, framebufferID);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glMatrixMode(GL_MODELVIEW);
		for (Entity e : drawList) {
			if (e.isActive()) {
				draw(e);
			}
		}

		//Render second pass to screen using shader
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);	
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, colorTextureID);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(
				0,
				1, 
				0,
				1,
				-10000000, 
				1000000
				);	
		GL20.glUseProgram(shader);
		glMatrixMode(GL_MODELVIEW);
		glBegin(GL_QUADS);
		glTexCoord2d(0f, 0f);
		glVertex3d(0f, 0f, 0f);

		glTexCoord2d(0f, 1f);
		glVertex3d(0f, 1f, 0f);

		glTexCoord2d(1f, 1f);
		glVertex3d(1f, 1f, 0f);

		glTexCoord2d(1f, 0f);
		glVertex3d(1f, 0f, 0f);
		glEnd();
		GL20.glUseProgram(0);

		Display.update();
	}

	private void updateNoFbo() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glMatrixMode(GL_MODELVIEW);
		for (Entity e : drawList) {
			if (e.isActive()) {
				draw(e);
			}
		}
		Display.update();
	}

	@Override
	public void changeNotify() {
		drawList = new ArrayList<Entity>(getEntities());
		Collections.sort(drawList, depthSorter);
	}

	public void draw(Entity e) {
		glLoadIdentity();
		RenderComponent rc = e.get(RenderComponent.class);
		if (rc.isDrawHud()) {
			hudCamera.apply();
		} else {
			camera.apply();
		}
		Transform t = e.get(Transform.class);
		if (t == null) {
			t = new Transform();
		}
		rc.draw(this, t);
	}

	public Camera getHudCamera() {
		return hudCamera;
	}

	public void setHudCamera(Camera hudCamera) {
		this.hudCamera = hudCamera;
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
		GL11.glClearColor(
				backgroundColor.r, 
				backgroundColor.g, 
				backgroundColor.b, 
				backgroundColor.a
				);
	}

	public int getShader() {
		return shader;
	}

	public void setShader(int shader) throws Exception {
		if (GLContext.getCapabilities().OpenGL20) {
			this.shader = shader;
			if (framebufferID == 0) {//fbo not yet created
				initFbo();
			}
		} else {
			throw new Exception("Shaders not supported.");
		}
	}

	public boolean isEscToExit() {
		return escToExit;
	}

	public void setEscToExit(boolean escToExit) {
		this.escToExit = escToExit;
	}

}

package bakpakin.ld29;

import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glVertex3d;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

import bakpakin.egf.geom.Transform;
import bakpakin.egf.render.Camera;
import bakpakin.egf.render.Drawable;
import bakpakin.egf.render.RenderSystem;

public class Backdrop implements Drawable {

	public Backdrop() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(RenderSystem renderSystem, float depth, Color color,
			Transform t) {
		Camera cam = renderSystem.getCamera().clone();
		cam.getTransform().add(t, -1);
		Vector2f v1 = cam.worldPt(0f, 0f);
		Vector2f v2 = cam.worldPt(0f, Display.getHeight());
		Vector2f v3 = cam.worldPt(Display.getWidth(), Display.getHeight());
		Vector2f v4 = cam.worldPt(Display.getWidth(), 0f);
		glPushMatrix();
		glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(
        		0,
        		Display.getWidth(), 
        		0,
        		Display.getHeight(),
        		-10000000, 
        		1000000
        		);	
		glMatrixMode(GL_MODELVIEW);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
		glBegin(GL_QUADS);
		{
			doGlColor(v1);
			glVertex3d(0f, 0f, depth);
			doGlColor(v2);
			glVertex3d(0f, Display.getHeight(), depth);
			doGlColor(v3);
			glVertex3d(Display.getWidth(), Display.getHeight(), depth);
			doGlColor(v4);
			glVertex3d(Display.getWidth(), 0f, depth);
		}
		glEnd();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		glMatrixMode(GL_PROJECTION);
		glPopMatrix();
		glMatrixMode(GL_MODELVIEW);
	}
	
	private void doGlColor(Vector2f vertex) {
		float y = vertex.y * 0.008f;
		double blue = Math.pow(.98, y);
		GL11.glColor3d(blue / 4, blue / 1.5, blue);
	}

}

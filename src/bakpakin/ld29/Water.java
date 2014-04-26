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
import static org.lwjgl.opengl.GL11.glTexCoord2d;
import static org.lwjgl.opengl.GL11.glVertex3d;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import bakpakin.egf.geom.Transform;
import bakpakin.egf.render.Camera;
import bakpakin.egf.render.RenderSystem;
import bakpakin.egf.render.Sprite;

public class Water extends Sprite {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3116677090498697327L;

	public Water() {
		super("res/water.png");
	}

	@Override
	public void draw(RenderSystem renderSystem, float depth, Color color,
			Transform t) {
		Camera cam = renderSystem.getCamera().clone();
		cam.getTransform().addInterpolate(t, -1);
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
		texture.setTextureFilter(smooth ? GL11.GL_LINEAR : GL11.GL_NEAREST);
		texture.bind();
		color.bind();
		glBegin(GL_QUADS);
		{
			glTexCoord2d((v1.x - ox) / width, (v1.y - oy) / height);
			glVertex3d(0f, 0f, depth);

			glTexCoord2d((v2.x - ox) / width, (v2.y - oy) / height);
			glVertex3d(0f, Display.getHeight(), depth);

			glTexCoord2d((v3.x - ox) / width, (v3.y - oy) / height);
			glVertex3d(Display.getWidth(), Display.getHeight(), depth);

			glTexCoord2d((v4.x - ox) / width, (v4.y - oy) / height);
			glVertex3d(Display.getWidth(), 0f, depth);
		}
		glEnd();
		glMatrixMode(GL_PROJECTION);
		glPopMatrix();
		glMatrixMode(GL_MODELVIEW);
	}

}

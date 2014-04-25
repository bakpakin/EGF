package bakpakin.egf.render;

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

import java.net.URL;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import bakpakin.egf.geom.Transform;

public class SimpleBackground extends Background {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5084249544403824769L;

	public SimpleBackground(String key) {
		super(key);
		// TODO Auto-generated constructor stub
	}
	
	public SimpleBackground(URL url) {
		super(url);
	}

	@Override
	public void draw(RenderSystem renderSystem, float depth, Color color, Transform t) {
		glPopMatrix();
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
			glTexCoord2d(0, 0);
			glVertex3d(0f, Display.getHeight(), depth);

			glTexCoord2d(0, texture.getHeight());
			glVertex3d(0f, 0f, depth);

			glTexCoord2d(texture.getWidth(), texture.getHeight());
			glVertex3d(Display.getWidth(), 0f, depth);

			glTexCoord2d(texture.getWidth(), 0);
			glVertex3d(Display.getWidth(), Display.getHeight(), depth);
		}
		glEnd();
		glMatrixMode(GL_PROJECTION);
		glPushMatrix();
		glMatrixMode(GL_MODELVIEW);
	}

}

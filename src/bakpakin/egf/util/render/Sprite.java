package bakpakin.egf.util.render;

import static org.lwjgl.opengl.GL11.*;

import java.io.Serializable;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import bakpakin.egf.util.AssetManager;
import bakpakin.egf.util.geom.Transform;

public class Sprite implements Drawable, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5969804839987012310L;
	protected transient Texture texture;

	protected Texture getTexture() {
		return texture;
	}

	protected void setTexture(Texture tex) {
		texture = tex;
	}

	protected int width;
	protected int height;

	protected double ox, oy;

	/**
	 * Creates a new sprite. The sprite is loaded from the
	 * resource folder according to the key
	 * @param key - path of image file
	 * @param ox - origin x
	 * @param oy - origin y
	 */
	public Sprite(String key, double ox, double oy) {
		this.ox = ox;
		this.oy = oy;
		texture = AssetManager.getTexture(key);
		width = texture.getImageWidth();
		height = texture.getImageHeight();
	}


	/**
	 * Creates a new sprite. The sprite is loaded from the
	 * resource folder according to the key
	 * @param key - path of image file
	 */
	public Sprite(String key) {
		this(key, 0, 0);
	}

	/**
	 * Centers the sprite origin
	 * @return this for convenience
	 */
	public Sprite center() {
		ox = ((double)width)/(double)2;
		oy = ((double)height)/(double)2;
		return this;
	}
	
	public Sprite scale(double factor) {
		width *= factor;
		height *= factor;
		ox *= factor;
		oy *= factor;
		return this;
	}
	
	public Sprite scale(double xScale, double yScale) {
		width *= xScale;
		height *= yScale;
		ox *= xScale;
		oy *= yScale;
		return this;
	}

	public double getOriginX() {
		return ox;
	}

	public void setOriginX(double ox) {
		this.ox = ox;
	}


	public double getOriginY() {
		return oy;
	}


	public void setOriginY(double oy) {
		this.oy = oy;
	}


	public void setHeight(int height) {
		this.height = height;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return The width of the sprite
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * @return The height of the sprite
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Draw the sprite
	 */
	public void draw(RenderSystem renderSystem, float depth, Color color, Transform t) {
		texture.bind();
		color.bind();
		t.apply();
		glTranslated(-ox, -oy, 0);
		glBegin(GL_QUADS);
		{
			glTexCoord2d(0, texture.getHeight());
			glVertex3d(0, height, depth);

			glTexCoord2d(texture.getWidth(), texture.getHeight());
			glVertex3d(width, height, depth);

			glTexCoord2d(texture.getWidth(), 0);
			glVertex3d(width, 0, depth);

			glTexCoord2d(0, 0);
			glVertex3d(0, 0, depth);
		}
		glEnd();
		glTranslated(ox, oy, 0);
		t.applyInverse();
	}

}

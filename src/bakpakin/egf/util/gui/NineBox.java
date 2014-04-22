package bakpakin.egf.util.gui;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

import bakpakin.egf.util.geom.AxisAlignedBox;

/**
 * The {@link NineBox} class represents a stretchable image that can
 * be used for gui programming. A NineBox object encapsulates an opengl
 * Texture that can be used for rendering gui components. It also contains
 * information on how to scale the texture for differently sized media.
 * @author Calvin
 *
 */
public class NineBox {

	/**
	 * The openGl texture data used for rendering.
	 */
	private Texture texture;

	/**
	 * The pixel boundaries that describe the stretchable
	 * regions of the {@link NineBox}. 
	 */
	private int x1, x2, y1, y2;
	
	/**
	 * The pixel boundaries that determine where content goes in the {@link NineBox}.
	 */
	private int contentX1, contentY1, contentX2, contentY2;

	/**
	 * The texture coordinates for drawings
	 */
	private float tx1, tx2, tx3, ty1, ty2, ty3;

	private boolean dirty = true;

	/**
	 * Create a new {@link NineBox} with default regions.
	 * @param texture
	 */
	public NineBox(Texture texture) {
		this(
				texture,
				texture.getImageWidth() / 2,
				texture.getImageHeight() / 2,
				1 + texture.getImageWidth() / 2,
				1 + texture.getImageHeight() / 2
				);
	}

	/**
	 * Creates a new {@link NineBox} the specified boundaries.
	 * @param texture
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public NineBox(Texture texture, int x1, int y1, int x2, int y2) {
		this(texture, x1, y1, x2, y2, x1, y2, x2, y2);
	}
	
	/**
	 * Creates a new {@link NineBox} the specified boundaries.
	 * @param texture
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param contentX1
	 * @param contentY1
	 * @param contentX2
	 * @param contentY2
	 */
	public NineBox(
			Texture texture, 
			int x1, 
			int y1, 
			int x2, 
			int y2,
			int contentX1,
			int contentY1,
			int contentX2,
			int contentY2) {
		this.texture = texture;
		setX2(x2);
		setY2(y2);
		setX1(x1);
		setY1(y1);
		recalc();
		setContentX2(contentX2);
		setContentY2(contentY2);
		setContentX1(contentX1);
		setContentY1(contentY1);
	}

	/**
	 * Recalculates opengl texture coordinates based on pixel boundaries.
	 */
	private void recalc() {
		final float w = texture.getWidth() / getWidth();
		final float h = texture.getHeight() / getHeight();
		tx1 = x1 * w;
		tx2 = x2 * w;
		tx3 = texture.getWidth();
		ty1 = y1 * h;
		ty2 = y2 * h;
		ty3 = texture.getHeight();
		dirty = false;
	}
	
	/**
	 * Draw the image around the specified content box.
	 * @param aab
	 */
	public void drawAroundContents(AxisAlignedBox aab) {
		drawAroundContents(aab.getXMin(), aab.getYMin(), aab.getXMax(), aab.getYMax());
	}
	
	/**
	 * Draw the image around the specified content box.
	 * @param xMin
	 * @param yMin
	 * @param xMax
	 * @param yMax
	 */
	public void drawAroundContents(float xMin, float yMin, float xMax, float yMax) {
		draw(
				xMin + x1 - contentX1,
				yMin + y1 - contentY1,
				xMax + x2 - contentX2,
				yMax + y2 - contentY2
				);
	}
	
	/**
	 * Draws the {@link NineBox} at the origin with default width and height.
	 */
	public void draw() {
		draw(0f, 0f);
	}
	
	/**
	 * Draws the {@link NineBox} at (x, y) with default width and height.
	 * @param x
	 * @param y
	 */
	public void draw(float x, float y) {
		draw(x, y, x + getWidth(), y + getHeight());
	}
	
	/**
	 * Draws the scaled image.
	 * @param aab
	 */
	public void draw(AxisAlignedBox aab) {
		draw(aab.getXMin(), aab.getYMin(), aab.getXMax(), aab.getYMax());
	}

	/**
	 * Draws the scaled image.
	 * @param xMin
	 * @param yMin
	 * @param xMax
	 * @param yMax
	 */
	public void draw(float xMin, float yMin, float xMax, float yMax) {
		if (dirty)
			recalc();
		texture.bind();
		final float x2 = xMin + x1;
		final float y2 = yMin + y1;
		final float x3 = xMax - getWidth() + this.x2;
		final float y3 = yMax - getHeight() + this.y2;
		GL11.glBegin(GL11.GL_QUADS);

		glbox(xMin, yMin, x2, y2, 0, 0, tx1, ty1);
		glbox(x2, yMin, x3, y2, tx1, 0, tx2, ty1);
		glbox(x3, yMin, xMax, y2, tx2, 0, tx3, ty1);

		glbox(xMin, y2, x2, y3, 0, ty1, tx1, ty2);
		glbox(x2, y2, x3, y3, tx1, ty1, tx2, ty2);
		glbox(x3, y2, xMax, y3, tx2, ty1, tx3, ty2);

		glbox(xMin, y3, x2, yMax, 0, ty2, tx1, ty3);
		glbox(x2, y3, x3, yMax, tx1, ty2, tx2, ty3);
		glbox(x3, y3, xMax, yMax, tx2, ty2, tx3, ty3);

		GL11.glEnd();
	}

	/*
	 * Draws one of the nine boxes.
	 */
	private void glbox(float x1, float y1, float x2, float y2, float tx1, float ty1, float tx2, float ty2) {
		GL11.glTexCoord2f(tx1, ty1); GL11.glVertex2f(x1, y1);
		GL11.glTexCoord2f(tx1, ty2); GL11.glVertex2f(x1, y2);			
		GL11.glTexCoord2f(tx2, ty2); GL11.glVertex2f(x2, y2);			
		GL11.glTexCoord2f(tx2, ty1); GL11.glVertex2f(x2, y1);			
	}

	/**
	 * @return the {@link Texture} used for rendering
	 */
	public Texture getTexture() {
		return texture;
	}

	/**
	 * @return the number of pixels wide this {@link NineBox} is
	 */
	public int getWidth() {
		return texture.getImageWidth();
	}

	/**
	 * @return the number of pixels high this {@link NineBox} is
	 */
	public int getHeight() {
		return texture.getImageHeight();
	}

	/**
	 * Sets the texture used to render this {@link NineBox}. 
	 * When changing the the texture, the nine regions of the
	 * {@link NineBox} will not necessarily line up right.
	 * @param texture
	 */
	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public int getX1() {
		return x1;
	}

	public void setX1(int x1) {
		if (x1 < 0 || x1 > x2)
			throw new IllegalArgumentException("x1 must be greater than or equal to zero and less than or equal to x2.");
		dirty = true;
		this.x1 = x1;
	}

	public int getX2() {
		return x2;
	}

	public void setX2(int x2) {
		if (x2 > getWidth() || x1 > x2)
			throw new IllegalArgumentException("x2 must be less than or equal to getWidth() and greater than or equal to x1.");
		dirty = true;
		this.x2 = x2;
	}

	public int getY1() {
		return y1;
	}

	public void setY1(int y1) {
		if (y1 < 0 || y1 > y2)
			throw new IllegalArgumentException("y1 must be greater than or equal to zero and less than or equal to y2.");
		dirty = true;
		this.y1 = y1;
	}

	public int getY2() {
		return y2;
	}

	public void setY2(int y2) {
		if (y2 > getHeight() || y1 > y2)
			throw new IllegalArgumentException("y2 must be less than or equal to getHeight() and greater than or equal to y1.");
		dirty = true;
		this.y2 = y2;
	}

	public int getContentX1() {
		return contentX1;
	}

	public void setContentX1(int contentX1) {
		if (contentX1 < 0 || contentX1 > contentX2)
			throw new IllegalArgumentException("x1 must be greater than or equal to zero and less than or equal to x2.");
		this.contentX1 = contentX1;
	}

	public int getContentY1() {
		return contentY1;
	}

	public void setContentY1(int contentY1) {
		if (contentY1 < 0 || contentY1 > contentY2)
			throw new IllegalArgumentException("y1 must be greater than or equal to zero and less than or equal to y2.");
		this.contentY1 = contentY1;
	}

	public int getContentX2() {
		return contentX2;
	}

	public void setContentX2(int contentX2) {
		if (contentX2 > getWidth() || contentX1 > contentX2)
			throw new IllegalArgumentException("x2 must be less than or equal to getWidth() and greater than or equal to x1.");
		this.contentX2 = contentX2;
	}

	public int getContentY2() {
		return contentY2;
	}

	public void setContentY2(int contentY2) {
		if (contentY2 > getHeight() || contentY1 > contentY2)
			throw new IllegalArgumentException("y2 must be less than or equal to getHeight() and greater than or equal to y1.");
		this.contentY2 = contentY2;
	}

}

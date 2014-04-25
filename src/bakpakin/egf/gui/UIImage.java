package bakpakin.egf.gui;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

public class UIImage extends UIElement {
	
	private Texture image;
	
	private float xScale = 1f, yScale = 1f;
		
	public UIImage(Texture image) {
		setImage(image);
	}

	@Override
	public int getWidth() {
		return (int)Math.ceil(image.getImageWidth()*xScale);
	}

	@Override
	public int getHeight() {
		return (int)Math.ceil(image.getImageHeight()*yScale);
	}

	@Override
	public void paint() {
		Color.white.bind();
		image.bind();
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0f, 0f);
		GL11.glVertex2f(0f, 0f);
		
		GL11.glTexCoord2f(image.getWidth(), 0f);
		GL11.glVertex2f(image.getImageWidth()*xScale, 0f);
		
		GL11.glTexCoord2f(image.getWidth(), image.getHeight());
		GL11.glVertex2f(image.getImageWidth()*xScale, image.getImageHeight()*yScale);
		
		GL11.glTexCoord2f(0f, image.getHeight());
		GL11.glVertex2f(0f, image.getImageHeight()*yScale);
		
		GL11.glEnd();
	}

	@Override
	public void update() {	
		//DO NOTHING
	}

	public Texture getImage() {
		return image;
	}

	public void setImage(Texture image) {
		this.image = image;
	}

	public float getyScale() {
		return yScale;
	}

	public void setyScale(float yScale) {
		this.yScale = yScale;
	}

	public float getxScale() {
		return xScale;
	}

	public void setxScale(float xScale) {
		this.xScale = xScale;
	}
	
	public void setScale(float scale) {
		setxScale(scale);
		setyScale(scale);
	}
	
	public void scale(float factor) {
		scale(factor, factor);
	}

	public void scale(float xFactor, float yFactor) {
		xScale *= xFactor;
		yScale *= yFactor;
	}

}

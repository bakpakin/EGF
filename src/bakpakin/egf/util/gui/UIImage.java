package bakpakin.egf.util.gui;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

public class UIImage extends UIElement {
	
	private Texture image;
	
	public UIImage(Texture image) {
		setImage(image);
	}

	@Override
	public int getWidth() {
		return image.getImageWidth();
	}

	@Override
	public int getHeight() {
		return image.getImageHeight();
	}

	@Override
	public void paint() {
		Color.white.bind();
		image.bind();
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0f, 0f);
		GL11.glVertex2f(0f, 0f);
		
		GL11.glTexCoord2f(image.getWidth(), 0f);
		GL11.glVertex2f(image.getImageWidth(), 0f);
		
		GL11.glTexCoord2f(image.getWidth(), image.getHeight());
		GL11.glVertex2f(image.getImageWidth(), image.getImageHeight());
		
		GL11.glTexCoord2f(0f, image.getHeight());
		GL11.glVertex2f(0f, image.getImageHeight());
		
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

}

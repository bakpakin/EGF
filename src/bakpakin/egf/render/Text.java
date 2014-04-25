package bakpakin.egf.render;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import bakpakin.egf.geom.Transform;
import bakpakin.egf.util.AssetManager;

public class Text implements Drawable {

	public static final String DEFAULT_FONT = "bakpakin/egf/render/default.ttf";
	public static final float DEFAULT_SIZE = 64;
	private String text;
	
	private float xoffset;
	private float yoffset;

	private TrueTypeFont font;

	public Text(String text, String fontPath, float size) {
		this.text = text;
		setFont(fontPath, size);
	}

	public Text(String text) {
		this(text, DEFAULT_FONT, DEFAULT_SIZE);
	}

	public TrueTypeFont getFont() {
		return font;
	}

	public void setFont(TrueTypeFont font) {
		this.font = font;
	}

	public void setFont(String path, float size) {
		font = AssetManager.getFont(path, size);
	}

	@Override
	public void draw(RenderSystem renderSystem, float depth, Color color, Transform t) {
		t.apply();
		font.drawString(xoffset, yoffset, text, color);
		t.applyInverse();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public float getYOffset() {
		return yoffset;
	}

	public void setYOffset(float yoffset) {
		this.yoffset = yoffset;
	}

	public float getXOffset() {
		return xoffset;
	}

	public void setXOffset(float xoffset) {
		this.xoffset = xoffset;
	}
	
	public Text center() {
		setXOffset(-font.getWidth(text)/2);
		setYOffset(-font.getHeight(text)/2);
		return this;
	}

}

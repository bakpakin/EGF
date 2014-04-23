package bakpakin.egf.util.gui;

import org.newdawn.slick.TrueTypeFont;

public class UILabel extends UIElement {
	
	private String text;
	
	public UILabel(String text) {
		this.setText(text);
	}

	@Override
	public void paint() {
		TrueTypeFont font = getTheme().getBodyFont();
		font.drawString(0, 0, text);
	}

	@Override
	public void update() {
		
	}
	
	public float getWidth() {
		return getTheme().getBodyFont().getWidth(text);
	}
	
	public float getHeight() {
		return getTheme().getBodyFont().getHeight(text);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}

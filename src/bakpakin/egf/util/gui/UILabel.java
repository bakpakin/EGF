package bakpakin.egf.util.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

public class UILabel extends UIElement {
	
	private String text;
	
	public UILabel(String text) {
		this.setText(text);
	}

	@Override
	public void paint() {
		TrueTypeFont font = getTheme().getBodyFont();
		Color c = getTheme().getBodyFontColor();
		font.drawString(0, 0, text, c);
	}

	@Override
	public void update() {
		
	}
	
	public int getWidth() {
		return getTheme().getBodyFont().getWidth(text);
	}
	
	public int getHeight() {
		return getTheme().getBodyFont().getHeight(text);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}

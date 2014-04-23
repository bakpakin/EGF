package bakpakin.egf.util.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

public class UIButton extends UIAbstractButton {
	
	private String text;
	
	private String action;
	
	public UIButton(String text) {
		setText(text);
		setEventTag(text);
	}
	
	@Override
	public void paint() {
		Color.white.bind();
		UITheme t = getTheme();
		Color c = t.getBodyFontColor();
		TrueTypeFont font = t.getBodyFont();
		NineBox nb = getNineBox();
		int fw = max(font.getWidth(text), nb.getMinimumContentWidth());
		int fh = max(font.getHeight(), nb.getMinimumContentHeight());
		int fx = nb.getContentX1();
		int fy = nb.getContentY1();
		nb.drawAroundContents(fx, fy, fx + fw, fy + fh);
		font.drawString(fx, fy, text, c);
	}
	
	private int max(int i1, int i2) {
		return i1 > i2 ? i1 : i2;
	}
	
	@Override
	public void buttonPressed() {
		getUi().addActionEvent(new UIActionEvent(action));
	}
	
	private NineBox getNineBox() {
		UITheme t = getTheme();
		switch (state) {
		case HOVER:
			return t.getButtonHover();
		case PRESSED:
			return t.getButtonPressed();
		case RELEASED:
			return t.getButton();
		default:
			return t.getButton();
		}
	}
	
	@Override
	public int getWidth() {
		UITheme t = getTheme();
		TrueTypeFont f = t.getBodyFont();
		NineBox nb = getNineBox();
		return nb.getWidth(f.getWidth(text));
	}
	
	@Override
	public int getHeight() {
		UITheme t = getTheme();
		TrueTypeFont f = t.getBodyFont();
		NineBox nb = getNineBox();
		return nb.getHeight(f.getHeight(text));	
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public State getState() {
		return state;
	}

	public String getEventTag() {
		return action;
	}

	public void setEventTag(String eventTag) {
		this.action = eventTag;
	}

}

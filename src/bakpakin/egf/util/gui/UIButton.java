package bakpakin.egf.util.gui;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.TrueTypeFont;

import bakpakin.egf.util.geom.AxisAlignedBox;

public class UIButton extends UIElement {
	
	public static enum State {
		PRESSED, HOVER, RELEASED
	}
	
	private String text;
	
	private String action;
	
	public UIButton(String text) {
		setText(text);
		setAction(text);
	}
	
	private State state;

	@Override
	public void paint() {
		UITheme t = getTheme();
		TrueTypeFont font = t.getBodyFont();
		int fw = font.getWidth(text);
		int fh = font.getHeight(text);
		NineBox nb = getNineBox();
		int fx = 0; 
		int fy = 0;
		nb.drawAroundContents(fx, fy, fx + fw, fy + fh);
		font.drawString(fx, fy, text);
	}

	@Override
	public void update() {
		AxisAlignedBox b = getBoundingBox();
		State previousState = state;
		if (b.contains(getUi().getMouse())) {
			if (Mouse.isButtonDown(0)) {
				state = State.PRESSED;
			} else {
				state = State.HOVER;
			}
		} else {
			state = State.RELEASED;
		}
		if (state == State.PRESSED && previousState != State.PRESSED) {
			getUi().addActionEvent(new UIActionEvent(action));
		}
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
	public float getWidth() {
		UITheme t = getTheme();
		TrueTypeFont f = t.getBodyFont();
		NineBox nb = getNineBox();
		return f.getWidth(text) + nb.getContentX1() - nb.getContentX2() + nb.getWidth();
	}
	
	@Override
	public float getHeight() {
		UITheme t = getTheme();
		TrueTypeFont f = t.getBodyFont();
		NineBox nb = getNineBox();
		return f.getHeight(text) + nb.getContentY1() - nb.getContentY2() + nb.getHeight();	
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

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}

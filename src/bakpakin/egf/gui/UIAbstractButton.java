package bakpakin.egf.gui;

import org.lwjgl.input.Mouse;

import bakpakin.egf.geom.AxisAlignedBox;

public abstract class UIAbstractButton extends UIElement {
	
	public static enum State {
		PRESSED, HOVER, RELEASED
	}
	
	protected State state;
	
	public UIAbstractButton() {
		state = State.RELEASED;
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
			buttonPressed();
		}
	}
	
	public State getState() {
		return state;
	}

	public abstract void buttonPressed();

}

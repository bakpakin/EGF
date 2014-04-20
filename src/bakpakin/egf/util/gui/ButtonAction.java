package bakpakin.egf.util.gui;

import bakpakin.egf.framework.Component;
import bakpakin.egf.util.VoidBehavior;

public class ButtonAction implements Component, VoidBehavior {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4276477861784860588L;
	
	private VoidBehavior behavior;
	
	public ButtonAction(VoidBehavior behavior) {
		setBehavior(behavior);
	}

	public VoidBehavior getBehavior() {
		return behavior;
	}

	public void setBehavior(VoidBehavior behavior) {
		this.behavior = behavior;
	}

	@Override
	public void behave() {
		behavior.behave();
	}
}

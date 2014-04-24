package bakpakin.egf.util;

import bakpakin.egf.framework.Component;

public class BehaviorComponent implements Component {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6602401074156512154L;

	private Behavior behavior;
	
	boolean started;
	
	public BehaviorComponent(Behavior behavior) {
		setBehavior(behavior);
	}

	public Behavior getBehavior() {
		return behavior;
	}

	public void setBehavior(Behavior behavior) {
		this.behavior = behavior;
	}

}

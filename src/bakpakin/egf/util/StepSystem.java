package bakpakin.egf.util;

import bakpakin.egf.framework.Entity;
import bakpakin.egf.framework.Matcher;
import bakpakin.egf.framework.ProcessingSystem;

public class StepSystem extends ProcessingSystem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7432402321584641204L;

	public StepSystem() {
		super();
	}
	
	public StepSystem(int priority) {
		super(priority);
	}

	@Override
	public void update(Entity e) {
		StepBehavior sb = e.get(StepBehavior.class);
		if (!sb.started) {
			VoidBehavior vb = sb.getStart();
			if (vb != null) vb.behave();
			sb.started = true;
		}
		VoidBehavior vb = sb.getStep();
		if (vb != null) vb.behave();
	}

	@Override
	protected Matcher initMatcher() {
		return new Matcher(StepBehavior.class);
	}

}

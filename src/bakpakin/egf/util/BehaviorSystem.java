package bakpakin.egf.util;

import bakpakin.egf.framework.Entity;
import bakpakin.egf.framework.Matcher;
import bakpakin.egf.framework.ProcessingSystem;

public class BehaviorSystem extends ProcessingSystem {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2488284044143353620L;

	@Override
	public void update(Entity e) {
		BehaviorComponent bc = e.get(BehaviorComponent.class);
		Behavior b = bc.getBehavior();
		if (!bc.started) {
			bc.started = true;
			if (b != null)
				b.start(getWorld(), e);
		} else {
			if (b != null)
				b.step(getWorld(), e);
		}
	}

	@Override
	protected Matcher initMatcher() {
		return new Matcher(BehaviorComponent.class);
	}

}

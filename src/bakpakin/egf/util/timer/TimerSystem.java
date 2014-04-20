package bakpakin.egf.util.timer;

import bakpakin.egf.framework.Entity;
import bakpakin.egf.framework.Matcher;
import bakpakin.egf.framework.ProcessingSystem;
import bakpakin.egf.util.VoidBehavior;

public class TimerSystem extends ProcessingSystem {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2274058399698423445L;

	public TimerSystem() {
		super();
	}

	public TimerSystem(int priority) {
		super(priority);
	}

	@Override
	public void update(Entity e) {
		float dt = (float)getWorld().getDelta();
		TimerComponent tc = e.get(TimerComponent.class);
		if (!tc.isPaused()) {
			tc.setTimeLeft(tc.getTimeLeft() - dt);
			if (tc.getTimeLeft() <= 0f) {
				VoidBehavior b = tc.getBehavior();
				if (b != null) {
					b.behave();
					if (tc.getTimeRepeat() > 0f)
						if (tc.getTimeLeft() < -tc.getTimeRepeat())
							tc.setTimeLeft(0f);
						else
							tc.setTimeLeft(tc.getTimeRepeat() + tc.getTimeLeft());
					else
						tc.setPaused(true);
				}
			}
		}
	}

	@Override
	protected Matcher initMatcher() {
		return new Matcher(TimerComponent.class);
	}

}

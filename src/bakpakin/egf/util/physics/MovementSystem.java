package bakpakin.egf.util.physics;

import bakpakin.egf.framework.Entity;
import bakpakin.egf.framework.Matcher;
import bakpakin.egf.framework.ProcessingSystem;
import bakpakin.egf.util.geom.Transform;

public class MovementSystem extends ProcessingSystem {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8818802341858594235L;

	public MovementSystem() {
		super();
	}
	
	public MovementSystem(int priority) {
		super(priority);
	}

	@Override
	protected Matcher initMatcher() {
		return new Matcher().require(Transform.class).require(DeltaTransform.class);
	}

	@Override
	public void update(Entity e) {
		Transform t = e.get(Transform.class);
		DeltaTransform dt = e.get(DeltaTransform.class);
		t.addInterpolate(dt, getWorld().getDeltaf());
		Friction f = e.get(Friction.class);
		if (f != null) {
			f.apply(dt, getWorld().getDelta());
		}
	}

}

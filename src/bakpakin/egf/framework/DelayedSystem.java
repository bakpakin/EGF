package bakpakin.egf.framework;

public abstract class DelayedSystem extends EntitySystem implements Delayed {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5384559063639222102L;
	
	private float delay = 1f;
	private float timer;
	
	public DelayedSystem() {
		super();
	}
	
	public DelayedSystem(int priority) {
		super(priority);
	}

	@Override
	public void tryUpdate() {
		timer += world.getDelta();
		if (timer >= delay) {
			update();
			if (delay == 0)
				timer = 0;
			else
				timer %= delay;
		}
	}
	
	@Override
	public float getDelay() {
		return delay;
	}

	@Override
	public void setDelay(float delay) {
		if (!(delay > 0))
			throw new IllegalArgumentException("delay must be greater than 0.");
		this.delay = delay;
	}

}

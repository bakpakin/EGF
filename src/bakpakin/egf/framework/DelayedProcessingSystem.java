package bakpakin.egf.framework;

/**
 * A {@Link ProcessingSystem} that implements {@link Delayed}.
 * @author Calvin
 *
 */
public abstract class DelayedProcessingSystem extends ProcessingSystem implements Delayed {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6694164668410612688L;
	
	private float delay = 1f;
	private float timer;
	
	/**
	 * 
	 */
	public DelayedProcessingSystem() {
		super();
	}

	/**
	 * 
	 * @param priority
	 */
	public DelayedProcessingSystem(int priority) {
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
		this.delay = delay;
	}

}

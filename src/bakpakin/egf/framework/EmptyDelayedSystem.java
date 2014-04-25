package bakpakin.egf.framework;

/**
 * A {@link DelayedSystem} that does not process entities, An EmptyDelayedSystem
 * only executes logic periodically.
 * @author Calvin
 *
 */
public abstract class EmptyDelayedSystem extends DelayedSystem {

	/**
	 * 
	 */
	private static final long serialVersionUID = -722931940949954760L;

	/**
	 * 
	 */
	public EmptyDelayedSystem() {
		super();
	}

	/**
	 * @param priority
	 */
	public EmptyDelayedSystem(int priority) {
		super(priority);
	}
	
	@Override
	protected Matcher initMatcher() {
		return null;
	}
	
	@Override
	protected void tryAddEntity(Entity e) {
		//do nothing
	}
	
	@Override
	protected void removeEntity(Entity e) {
		//do nothing
	}

}

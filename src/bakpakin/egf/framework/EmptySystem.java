package bakpakin.egf.framework;

/**
 * An EmptySystem does process components. Its only function is to
 * execute logic every frame.
 * @author Calvin
 *
 */
public abstract class EmptySystem extends EntitySystem {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5606014591083488149L;

	public EmptySystem() {
		super();
	}
	
	public EmptySystem(int priority) {
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

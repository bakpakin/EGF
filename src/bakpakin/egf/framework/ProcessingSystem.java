package bakpakin.egf.framework;

public abstract class ProcessingSystem extends EntitySystem {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1052708397217551652L;

	public ProcessingSystem() {
		super();
	}
	
	public ProcessingSystem(int priority) {
		super(priority);
	}

	@Override
	public void update() {
		for (Entity e : getEntities()) {
			if (e.isActive())
				update(e);
		}		
	}
	
	public abstract void update(Entity e);

}

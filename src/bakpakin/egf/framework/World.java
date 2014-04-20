package bakpakin.egf.framework;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class World implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5848377902209650632L;

	/**
	 * 
	 */
	private static final Comparator<EntitySystem> systemComparator = new Comparator<EntitySystem>() {

		@Override
		public int compare(EntitySystem o1, EntitySystem o2) {
			return o2.getPriority() - o1.getPriority();
		}
		
	};
	
	/**
	 * 
	 */
	private Map<Integer, Entity> entities;
	
	/**
	 * 
	 */
	private List<EntitySystem> systems;

	/**
	 * 
	 */
	private double delta;
	
	/**
	 * 
	 */
	private double timeScale = 1.0;
	
	/**
	 * 
	 */
	public World() {
		entities = new HashMap<Integer, Entity>();
		systems = new LinkedList<EntitySystem>();
	}
	
	/**
	 * 
	 * @param deltaSeconds
	 */
	public void update(double deltaSeconds) {
		delta = deltaSeconds;
		for (EntitySystem system : systems) {
			if (system.isActive())
				system.updateAndManageEntities();
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public Entity createEntity() {
		Entity e = new Entity();
		entities.put(e.getId(), e);
		return e;
	}
	
	/**
	 * 
	 * @param components
	 * @return
	 */
	public Entity createEntity(Component... components) {
		Entity e = createEntity();
		for (Component component : components) {
			e.add(component);
		}
		revalidate(e);
		return e;
	}
	
	/**
	 * 
	 * @param entities
	 */
	public void add(Entity... entities) {
		for (Entity e : entities) {
			add(e);
		}
	}
	
	/**
	 * 
	 * @param e
	 * @return
	 */
	public Entity add(Entity e) {
		entities.put(e.getId(), e);
		revalidate(e);
		return e;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Entity removeId(int id) {
		for (EntitySystem system : systems) {
			system.removeId(id);
		}
		return entities.remove(id);
	}
	
	public void removeEntity(Entity e) {
		for (EntitySystem system : systems) {
			system.removeEntity(e);
		}
		entities.remove(e.getId());
	}

	/**
	 * 
	 * @param e
	 */
	public void revalidate(Entity e) {
		for (EntitySystem system : systems) {
			system.removeEntity(e);
			system.tryAddEntity(e);
		}
	}
	
	/**
	 * 
	 * @param system
	 * @return
	 */
	public boolean addSystem(EntitySystem system) {
		if (system.world != this && system.world != null) {
			return false;
		}
		system.world = this;
		systems.add(system);
		sortSystems();
		for (Entity e : entities.values()) {
			system.tryAddEntity(e);
		}
		return true;
	}
	
	/**
	 * 
	 * @param systems
	 */
	public void addSystems(EntitySystem... systems) {
		for (EntitySystem es : systems) {
			addSystem(es);
		}
	}
	
	/**
	 * 
	 * @param system
	 * @param priority
	 * @return
	 */
	public boolean addSystem(EntitySystem system, int priority) {
		system.setPriority(priority);
		return addSystem(system);
	}
	
	/**
	 * 
	 */
	void sortSystems() {
		Collections.sort(systems, systemComparator);
	}
	
	/**
	 * 
	 * @param system
	 * @return
	 */
	public boolean removeSystem(EntitySystem system) {
		system.removeNotify();
		return systems.remove(system);
	}
	
	/**
	 * 
	 * @return
	 */
	public double getDelta() {
		return delta * timeScale;
	}
	
	/**
	 * 
	 * @return
	 */
	public float getDeltaf() {
		return (float) (delta * timeScale);
	}

	/**
	 * @return the timeScale
	 */
	public double getTimeScale() {
		return timeScale;
	}

	/**
	 * @param timeScale the timeScale to set
	 */
	public void setTimeScale(double timeScale) {
		this.timeScale = timeScale;
	}
	
	public int getNumEntites() {
		return entities.size();
	}
	
	public int getNumSystems() {
		return systems.size();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("World [");
		if (entities != null) {
			builder.append("entities=");
			builder.append(entities);
			builder.append(", ");
		}
		if (systems != null) {
			builder.append("systems=");
			builder.append(systems);
		}
		builder.append("]");
		return builder.toString();
	}
}

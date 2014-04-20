package bakpakin.egf.framework;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class EntitySystem implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6070152385323539563L;

	private int priority;
	
	private static int nextId;
	
	private int id;
	
	private boolean active = true;
	
	World world;
		
	Matcher matcher;
		
	protected Map<Integer, Entity> entities;
	protected List<Integer> toRemove;
	protected List<Entity> toAdd;
	
	public EntitySystem() {
		this(0);
	}
	
	public EntitySystem(int priority) {
		this.id = nextId++;
		this.priority = priority;
		setMatcher(initMatcher());
		toAdd = new LinkedList<Entity>();
		toRemove = new LinkedList<Integer>();
		entities = new HashMap<Integer, Entity>();
	}
	
	protected void tryAddEntity(Entity e) {
		toAdd.add(e);
	}
	
	protected void removeEntity(Entity e) {
		toRemove.add(e.getId());
	}
	
	protected void removeId(int id) {
		toRemove.add(id);
	}
	
	public Collection<Entity> getEntities() {
		return entities.values();
	} 

	public Matcher getMatcher() {
		return matcher;
	}

	public void setMatcher(Matcher matcher) {
		this.matcher = matcher;
		if (entities != null) {
			for (Entity e : entities.values()) {
				entities.remove(e.getId());
				if (matcher != null && matcher.match(e)) {
					entities.put(e.getId(), e);
				}
			}
			changeNotify();
		}
	}

	protected abstract Matcher initMatcher();
	
	protected void updateAndManageEntities() {
		boolean notify = (toAdd.size() > 0 || toRemove.size() > 0);
		for (Integer i : toRemove) {
			entities.remove(i);
		}
		for (Entity e : toAdd) {
			if (matcher != null && matcher.match(e))
				entities.put(e.getId(), e);
		}
		toAdd.clear();
		toRemove.clear();
		if (notify)
			changeNotify();
		tryUpdate();
	}
	
	public void tryUpdate() {
		update();
	}

	public abstract void update();
	
	public World getWorld() {
		return world;
	}

	public void changeNotify() {
		
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
		world.sortSystems();
	}

	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		return this == obj;
	}

	public void removeNotify() {
		
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}

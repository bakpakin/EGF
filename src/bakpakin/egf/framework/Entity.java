package bakpakin.egf.framework;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class Entity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5907881869749095453L;

	private static int nextId = 0;
	
	private final int id;
	
	private Map<String, Component> components;
	
	private Map<String, Object> properties;
	
	private Set<String> tags;
	
	boolean active;
		
	public Entity() {
		this.active = true;
		this.id = nextId++;
		this.components = new HashMap<String, Component>();
		this.tags = new HashSet<String>();
		this.properties = new HashMap<String, Object>();
	}
	
	public Entity(Component... cs) {
		this();
		for (Component c : cs) {
			add(c);
		}
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getId() {
		return id;
	}
	
	public boolean addTag(String tag) {
		return tags.add(tag);
	}
	
	public boolean removeTag(String tag) {
		return tags.remove(tag);
	}
	
	public boolean hasTag(String tag) {
		return tags.contains(tag);
	}
	
	public void add(Component c) {
		components.put(c.getClass().getName(), c);
		//c.entity = this;
	}
	
	public void remove(Class<? extends Component> c) {
		components.remove(c.toString());//.entity = null;
	}
	
	@SuppressWarnings("unchecked")
	public <C extends Component> C get(Class<C> c) {
		return (C) components.get(c.getName());
	}
	
	public Component get(String c) {
		return components.get(c);
	}
	
	public boolean containsComponent(Class<? extends Component> c) {
		return components.containsKey(c.getName());
	}
	
	public boolean containsComponent(String c) {
		return components.containsKey(c);
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer(30);
		sb.append("Entity[");
		sb.append(id);
		for (Component c : components.values()) {
			sb.append(c);
			sb.append(", ");
		}
		sb.append("]");
		return sb.toString();
	}

	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		return this == obj;
	}
	
	public void setProperty(String name, Object value) {
		properties.put(name, value);
	}
	
	public void removeProperty(String name) {
		properties.remove(name);
	}
	
	public Object getProperty(String name) {
		return properties.get(name);
	}
	
}

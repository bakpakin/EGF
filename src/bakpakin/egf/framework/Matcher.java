package bakpakin.egf.framework;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A <code>Matcher</code> is used to select which {@link Component}s are compatible with
 * certain {@link EntitySystem}s.
 * @author Calvin
 *
 */
public class Matcher implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6599064164882118622L;
	
	private Set<String> excludedComponents;
	private Set<String> requiredComponents;
	private Set<String> oneRequiredComponents;

	private Set<String> excludedTags;
	private Set<String> requiredTags;
	private Set<String> oneRequiredTags;

	public Matcher(Class<? extends Component> clazz) {
		this();
		this.require(clazz);
	}

	public Matcher() {
		this.excludedComponents = new HashSet<String>();
		this.requiredComponents = new HashSet<String>();
		this.oneRequiredComponents = new HashSet<String>();

		this.excludedTags = new HashSet<String>();
		this.requiredTags = new HashSet<String>();
		this.oneRequiredTags = new HashSet<String>();
	}

	public Matcher exclude(Class<? extends Component> clazz) {
		this.excludedComponents.add(clazz.getName());
		return this;
	}

	public Matcher require(Class<? extends Component> clazz) {
		this.requiredComponents.add(clazz.getName());
		return this;
	}

	public Matcher requireOne(Class<? extends Component> clazz) {
		this.oneRequiredComponents.add(clazz.getName());
		return this;
	}

	public Matcher excludeTag(String tag) {
		this.excludedTags.add(tag);
		return this;
	}

	public Matcher requireTag(String tag) {
		this.requiredTags.add(tag);
		return this;
	}

	public Matcher requireOneTag(String tag) {
		this.oneRequiredTags.add(tag);
		return this;
	}

	public boolean matchTags(Entity e) {
		for (String t : excludedTags) {
			if (e.hasTag(t)) {
				return false;
			}
		}

		for (String t : requiredTags) {
			if (!e.hasTag(t)) {
				return false;
			}
		}

		if (oneRequiredTags.size() == 0)
			return true;

		for (String t : oneRequiredTags) {
			if (e.hasTag(t)) {
				return true;
			}
		}
		return false;

	}

	public boolean matchComponents(Entity e) {
		for (String c : excludedComponents) {
			if (e.containsComponent(c)) {
				return false;
			}
		}

		for (String c : requiredComponents) {
			if (!e.containsComponent(c)) {
				return false;
			}
		}

		if (oneRequiredComponents.size() == 0)
			return true;

		for (String c : oneRequiredComponents) {
			if (e.containsComponent(c)) {
				return true;
			}
		}
		return false;


	}

	public boolean match(Entity e) {
		return matchTags(e) && matchComponents(e);
	}

}

package bakpakin.egf.util.physics;

import java.util.Collection;

import bakpakin.egf.framework.Entity;
import bakpakin.egf.framework.EntitySystem;
import bakpakin.egf.framework.Matcher;
import bakpakin.egf.util.geom.Transform;

public class CircleCollisionSystem extends EntitySystem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3680108701680117128L;
	
	private int repeats;
	
	public static final String COLLISION_BEHAVIOR = "Collision Behavior";
	
	public CircleCollisionSystem() {
		this(1);
	}
	
	public CircleCollisionSystem(int repeats) {
		super();
		this.repeats = repeats;
	}

	@Override
	protected Matcher initMatcher() {
		return new Matcher().require(CircleCollider.class).require(Transform.class).require(DeltaTransform.class);
	}
	
	@Override
	public void update() {
		Collection<Entity> entities = this.getEntities();
		for (int i = 0; i < repeats; i++) {
			for (Entity e1 : entities) {
				if (!e1.isActive()) continue;
				for (Entity e2 : entities) {
					if (!e2.isActive()) continue;
					if (e1 == e2) continue;
					if (e1.getId() > e2.getId()) continue; //compare each pair of entities only once.
					if (resolve(e1, e2)) {
						CollisionResponse b1 = ((CollisionResponse)e1.getProperty(COLLISION_BEHAVIOR));
						CollisionResponse b2 = ((CollisionResponse)e2.getProperty(COLLISION_BEHAVIOR));
						if (b1 != null)
							b1.response(e1, e2);
						if (b2 != null) {
							b2.response(e2, e1);
						}
					}
				}
			}
		}
	}
	
	private boolean resolve(Entity e1, Entity e2) {
		final Transform t1, t2;
		final CircleCollider c1, c2;
		t1 = e1.get(Transform.class);
		t2 = e2.get(Transform.class);
		c1 = e1.get(CircleCollider.class);
		c2 = e2.get(CircleCollider.class);
		float xd, yd, dist, factor, r;
		xd = t2.getX() - t1.getX();
		yd = t2.getY() - t1.getY();
		dist = (float) Math.sqrt(xd*xd + yd*yd);
		r = c1.getRadius() + c2.getRadius();
		if (r < dist) {
			return false;
		}
		if ((c1.getInvMass() == 0 && c2.getInvMass() == 0) || 
				c1.getInvMass() == Double.POSITIVE_INFINITY || 
				c2.getInvMass() == Double.POSITIVE_INFINITY) {
			return true;
		}
		factor = (dist - r) / (c1.getInvMass() + c2.getInvMass());
		if (dist > 0) {
			xd /= dist;
			yd /= dist;
		} else {
			yd = 1;
			xd = 0;
		}
		t1.translate(factor*xd*c1.getInvMass(), factor*yd*c1.getInvMass());
		t2.translate(-factor*xd*c2.getInvMass(), -factor*yd*c2.getInvMass());
		e1.get(DeltaTransform.class).translate(factor*xd*c1.getInvMass(), factor*yd*c1.getInvMass());
		e2.get(DeltaTransform.class).translate(-factor*xd*c2.getInvMass(), -factor*yd*c2.getInvMass());
		return true;
	}
	
	public int getRepeats() {
		return repeats;
	}

	public void setRepeats(int repeats) {
		this.repeats = repeats;
	}

}

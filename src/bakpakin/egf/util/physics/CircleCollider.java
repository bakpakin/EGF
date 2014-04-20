package bakpakin.egf.util.physics;

import bakpakin.egf.framework.Component;
import bakpakin.egf.util.geom.AxisAlignedBox;
import bakpakin.egf.util.geom.Transform;

public class CircleCollider implements Component {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6417425144681874247L;
	private float radius;
	private float invMass;
	private final static float PI = (float)Math.PI;
	
	public CircleCollider(float radius, float invMass) {
		this.setRadius(radius);
		this.setInvMass(invMass);
	}
	
	public CircleCollider(float radius) {
		this(radius, 1f);
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public float getInvMass() {
		return invMass;
	}

	public void setInvMass(float invMass) {
		this.invMass = invMass;
	}

	public float getArea() {
		return PI*radius*radius;
	}

	public AxisAlignedBox getBoundingBox(Transform t) {
		return new AxisAlignedBox(
				t.getX() - radius*t.getXScale(), 
				t.getY() - radius*t.getYScale(),
				t.getX() + radius*t.getXScale(),
				t.getY() + radius*t.getYScale());
	}

}

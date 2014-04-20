
package bakpakin.egf.util.physics;

import bakpakin.egf.framework.Component;
import bakpakin.egf.util.geom.Transform;

public class Friction implements Component {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6717769599094735877L;
	private float friction;
	private float angularFriction;
	
	public Friction(float friction) {
		this(friction, 0);
	}
	
	public Friction(float friction, float angularFriction) {
		this.friction = friction;
		this.angularFriction = angularFriction;
	}
	
	public float getFriction() {
		return friction;
	}

	public void setFriction(float friction) {
		this.friction = friction;
	}

	public float getAngularFriction() {
		return angularFriction;
	}

	public void setAngularFriction(float angularFriction) {
		this.angularFriction = angularFriction;
	}
	
	public void apply(Transform velocity, double deltaSeconds) {
		float dt = (float)deltaSeconds;
		float x = velocity.getX();
		float y = velocity.getY();
		float angle = velocity.getAngle();
		
		float speed = (float) Math.sqrt(x*x + y*y);
		float speed2 = speed > friction*dt ? speed - friction*dt : 0;
		float factor = speed == 0 ? 0 : speed2 / speed;
		
		if (angle > angularFriction*dt) {
			angle -= angularFriction*dt;
		} else if (angle < -angularFriction*dt) {
			angle += angularFriction*dt;
		} else {
			angle = 0;
		}
		
		velocity.setX(x*factor);
		velocity.setY(y*factor);
		velocity.setAngle(angle);
	}
	
}

package bakpakin.egf.physics;

import bakpakin.egf.geom.Transform;


public class DeltaTransform extends Transform {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8264969815021793465L;

	public DeltaTransform(float x, float y, float xScale, float yScale,
			float angle) {
		super(x, y, xScale, yScale, angle);
	}

	public DeltaTransform() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DeltaTransform(float x, float y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	public DeltaTransform(float angle) {
		super(angle);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param xform
	 */
	public DeltaTransform(Transform xform) {
		super(xform);
	}
	
	@Override
	public DeltaTransform clone() {
		return new DeltaTransform(getX(), getY(), getXScale(), getYScale(), getAngle());
	}
	
	public float getDirection() {
		return (float)(180 / Math.PI * Math.atan2(y, x));
	}
	
	public float getSpeed() {
		return (float)Math.sqrt((x*x) + (y*y));
	}
	
	public void setSpeed(float speed) {
		final double factor = (double)speed / getSpeed();
		x *= factor;
		y *= factor;
	}
	
	public void setDirection(float direction) {
		final double rad = Math.toRadians(direction);
		final float speed = getSpeed();
		x = (float)(speed*Math.cos(rad));
		y = (float)(speed*Math.sin(rad));
	}

}

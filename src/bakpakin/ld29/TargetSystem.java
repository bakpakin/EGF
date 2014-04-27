package bakpakin.ld29;

import bakpakin.egf.framework.Entity;
import bakpakin.egf.framework.Matcher;
import bakpakin.egf.framework.ProcessingSystem;
import bakpakin.egf.geom.Transform;
import bakpakin.egf.physics.DeltaTransform;
import bakpakin.egf.physics.Friction;

public class TargetSystem extends ProcessingSystem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private float xMax;
	private float yMax;
	private float xMin;
	private float yMin;

	public TargetSystem() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(Entity e) {
		DeltaTransform dt = e.get(DeltaTransform.class);
		Transform t = e.get(Transform.class);
		Target trgt = e.get(Target.class);
		float accel = (Integer) e.getProperty("Accel");
		float delta = getWorld().getDeltaf();
		float dist = (float)Math.sqrt(Math.pow(trgt.x - t.getX(), 2) + Math.pow(trgt.y - t.getY(), 2));
		float factor = 1/dist * accel;
		dt.add(new Transform(factor*(trgt.x - t.getX()), factor*(trgt.y - t.getY())), delta);
		float ang = t.getAngle();
		float dir = dt.getDirection();
		float diff = angDiff(dir, ang);
		if (dt.getSpeed() > 0)
			t.rotate(-diff * delta * 10f);
		float maxSpeed = (Integer) e.getProperty("MaxSpeed");
		if (dt.getSpeed() > maxSpeed)
			dt.setSpeed(maxSpeed);
		if (t.getX() > xMax)
		{t.setX(xMax); dt.setX(0);}
		if (t.getX() < xMin)
		{t.setX(xMin); dt.setX(0);}
		if (t.getY() > yMax)
		{t.setY(yMax); dt.setY(0);}
		if (t.getY() < yMin)//don't stop the player, just add gravity so they fall back in the water
		{dt.translate(0, 20 * (float)Math.pow(.5, 1 - delta));}
	}

	private float angDiff(float a1, float a2) {
		return ((((a2 - a1) % 360) + 540) % 360) - 180;  
	}

	@Override
	protected Matcher initMatcher() {
		return new Matcher(Target.class)
		.require(Transform.class)
		.require(DeltaTransform.class);
	}

	public float getxMax() {
		return xMax;
	}

	public void setxMax(float xMax) {
		this.xMax = xMax;
	}

	public float getyMax() {
		return yMax;
	}

	public void setyMax(float yMax) {
		this.yMax = yMax;
	}

	public float getxMin() {
		return xMin;
	}

	public void setxMin(float xMin) {
		this.xMin = xMin;
	}

	public float getyMin() {
		return yMin;
	}

	public void setyMin(float yMin) {
		this.yMin = yMin;
	}

}

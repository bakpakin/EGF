package bakpakin.ld29;

import static org.lwjgl.input.Keyboard.*;
import bakpakin.egf.framework.Entity;
import bakpakin.egf.framework.Matcher;
import bakpakin.egf.framework.ProcessingSystem;
import bakpakin.egf.geom.Transform;
import bakpakin.egf.physics.DeltaTransform;
import bakpakin.egf.physics.Friction;
import bakpakin.egf.render.Camera;

public class SwimmerControlSystem extends ProcessingSystem {
	
	private Camera camera;
	
	private float xMax;
	private float yMax;
	private float xMin;
	private float yMin;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1709545108854124424L;
	
	public SwimmerControlSystem(Camera camera) {
		this.setCamera(camera);
	}

	@Override
	public void update(Entity e) {
		DeltaTransform dt = e.get(DeltaTransform.class);
		Transform t = e.get(Transform.class);
		float delta = getWorld().getDeltaf();
		float accel = (Integer) e.getProperty("Accel");
		if (t.getY() >= yMin) {
			e.get(Friction.class).setFriction(100);
			if (isKeyDown(KEY_LEFT)) {
				dt.translate(-accel * delta, 0);
			}
			if (isKeyDown(KEY_RIGHT)) {
				dt.translate(accel * delta, 0);
			}
			if (isKeyDown(KEY_UP)) {
				dt.translate(0, -accel * delta);
			}
			if (isKeyDown(KEY_DOWN)) {
				dt.translate(0, accel * delta);
			}
		} else {
			e.get(Friction.class).setFriction(0);
		}
		float ang = t.getAngle();
		float dir = dt.getDirection();
		float diff = ((((ang - dir) % 360) + 540) % 360) - 180;  
		if (dt.getSpeed() > 0)
			t.rotate(-diff * delta * 10f);
		float maxSpeed = (Integer) e.getProperty("MaxSpeed");
		if (dt.getSpeed() > maxSpeed)
			dt.setSpeed(maxSpeed);
		camera.setTransform(Transform.interpolateNoScale(camera.getTransform(), new Transform(t.getX(), t.getY()), (float)Math.pow(.2, 1 - delta)));
		Transform ct = camera.getTransform();
		if (ct.getX() < xMin + ct.getXScale()/2)
			ct.setX(xMin + ct.getXScale()/2);
		if (ct.getX() > xMax - ct.getXScale()/2)
			ct.setX(xMax - ct.getXScale()/2);
		if (ct.getY() > yMax - ct.getYScale()/2)
			ct.setY(yMax - ct.getYScale()/2);
		
		if (t.getX() > xMax)
			{t.setX(xMax); dt.setX(0);}
		if (t.getX() < xMin)
			{t.setX(xMin); dt.setX(0);}
		if (t.getY() > yMax)
			{t.setY(yMax); dt.setY(0);}
		if (t.getY() < yMin)//don't stop the player, just add gravity so they fall back in the water
			{dt.translate(0, 20 * (float)Math.pow(.5, 1 - delta));}
	}

	@Override
	protected Matcher initMatcher() {
		return new Matcher().requireTag(EntityFactory.PLAYER_TAG);
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
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

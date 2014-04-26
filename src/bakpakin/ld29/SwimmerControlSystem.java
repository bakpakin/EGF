package bakpakin.ld29;

import static org.lwjgl.input.Keyboard.*;
import bakpakin.egf.framework.Entity;
import bakpakin.egf.framework.Matcher;
import bakpakin.egf.framework.ProcessingSystem;
import bakpakin.egf.geom.Transform;
import bakpakin.egf.physics.DeltaTransform;
import bakpakin.egf.render.Camera;

public class SwimmerControlSystem extends ProcessingSystem {
	
	private Camera camera;
	
	private float defaultCameraWidth;
	private float defaultCameraHeight;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1709545108854124424L;
	
	public SwimmerControlSystem(Camera camera) {
		this.setCamera(camera);
		defaultCameraWidth = camera.getTransform().getXScale();
		defaultCameraHeight = camera.getTransform().getYScale();
	}

	@Override
	public void update(Entity e) {
		DeltaTransform dt = e.get(DeltaTransform.class);
		Transform t = e.get(Transform.class);
		float delta = getWorld().getDeltaf();
		float accel = (Integer) e.getProperty("Accel");
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
		float ang = t.getAngle();
		float dir = dt.getDirection();
		float diff = (ang + 180 - dir) % 360 - 180;
		if (dt.getSpeed() > 0)
			t.rotate(-diff * delta * 10f);
		camera.setTransform(Transform.interpolateNoScale(camera.getTransform(), new Transform(t.getX(), t.getY()), (float)Math.pow(.2, 1 - delta)));
		camera.getTransform().setXScale(defaultCameraWidth * (1 + (dt.getSpeed()*0.001f)));
		camera.getTransform().setYScale(defaultCameraHeight * (1 + (dt.getSpeed()*0.001f)));
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

}

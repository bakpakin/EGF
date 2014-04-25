package bakpakin.egf.render;

import bakpakin.egf.framework.Entity;
import bakpakin.egf.framework.Matcher;
import bakpakin.egf.framework.ProcessingSystem;
import bakpakin.egf.geom.Transform;

public class ParallaxSystem extends ProcessingSystem {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 894958944956048242L;
	
	private Camera camera;

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public ParallaxSystem(Camera camera) {
		super();
		this.camera = camera;
	}

	@Override
	public void update(Entity e) {
		Transform t = e.get(Transform.class);
		Transform cam = camera.getTransform();
		float factor = e.get(ParallaxComponent.class).getFactor();
		t.setX(cam.getX()*factor);
		t.setY(cam.getY()*factor);
	}

	@Override
	protected Matcher initMatcher() {
		return new Matcher(Transform.class).require(ParallaxComponent.class);
	}

}

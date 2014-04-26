package bakpakin.ld29;

import bakpakin.egf.framework.EmptyDelayedSystem;
import bakpakin.egf.geom.AxisAlignedBox;
import bakpakin.egf.render.Camera;

public class CloudGenerator extends EmptyDelayedSystem {

	/**
	 * 
	 */
	private static final long serialVersionUID = -704850226486957879L;

	private Camera camera;
	
	public CloudGenerator(Camera camera) {
		this.setDelay(3);
		this.setCamera(camera);
	}

	@Override
	public void update() {
		AxisAlignedBox aab = camera.getHitbox();
		this.getWorld().add(EntityFactory.cloud(
				rand(aab.getXMin() - 200, aab.getXMin() - 100), 
				rand(-600, -100), 
				rand(20, 85), 
				(int)(Math.random() * 4)));
	}
	
	private static float rand(float min, float max) {
		return (float)(min + (Math.random() * (max - min)));
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

}

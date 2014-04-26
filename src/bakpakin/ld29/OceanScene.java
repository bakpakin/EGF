package bakpakin.ld29;

import static bakpakin.ld29.EntityFactory.*;

import org.newdawn.slick.Color;

import bakpakin.egf.render.RenderComponent;

public class OceanScene extends Scene {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private SwimmerControlSystem swimmerControlSystem;
	private CloudGenerator cloudGenerator;

	public OceanScene() {
		super();
		cloudGenerator = new CloudGenerator(this.getRenderSystem().getCamera());
		swimmerControlSystem = new SwimmerControlSystem(this.getRenderSystem().getCamera());
		this.addSystem(cloudGenerator);
		this.addSystem(swimmerControlSystem);
		this.add(boat(400, -43));
		this.add(sun(300, -240));
		this.add(swimmer(400, 200));
		this.createEntity(new RenderComponent(new Water(), new Color(1, 1, 1, 0.5f)));
	}

	public SwimmerControlSystem getSwimmerControlSystem() {
		return swimmerControlSystem;
	}

	public void setSwimmerControlSystem(SwimmerControlSystem swimmerControlSystem) {
		this.swimmerControlSystem = swimmerControlSystem;
	}

	public CloudGenerator getCloudGenerator() {
		return cloudGenerator;
	}

	public void setCloudGenerator(CloudGenerator cloudGenerator) {
		this.cloudGenerator = cloudGenerator;
	}

}

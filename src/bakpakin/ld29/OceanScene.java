package bakpakin.ld29;

import static bakpakin.ld29.EntityFactory.*;

import java.io.IOException;

import org.newdawn.slick.Color;

import bakpakin.egf.framework.Entity;
import bakpakin.egf.particles.ParticleSystem;
import bakpakin.egf.particles.ParticleSystemDrawer;
import bakpakin.egf.physics.CircleCollisionSystem;
import bakpakin.egf.render.ParallaxSystem;
import bakpakin.egf.render.RenderComponent;
import bakpakin.egf.tilemap.JSONLoader;
import bakpakin.egf.tilemap.ObjectLayer;
import bakpakin.egf.tilemap.PlatformingSystem;
import bakpakin.egf.tilemap.TileMap;
import bakpakin.egf.tilemap.TileMapRenderer;

public class OceanScene extends Scene {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private SwimmerControlSystem swimmerControlSystem;
	private CloudGenerator cloudGenerator;
	private PlatformingSystem platformingSystem;
	private ParticleSystem particleSystem;
	private CircleCollisionSystem circleCollisionSystem;
	private TargetSystem targetSystem;
	
	private TileMap tileMap;
	private Entity tileMapRenderer;

	public OceanScene(String tileMapUrl) {
		super();
		getRenderSystem().setBackgroundColor(new Color(.8f, 1f, 1f, 1f));
		cloudGenerator = new CloudGenerator(this.getRenderSystem().getCamera());
		swimmerControlSystem = new SwimmerControlSystem(this.getRenderSystem().getCamera());
		particleSystem = new ParticleSystem();
		circleCollisionSystem = new CircleCollisionSystem();
		targetSystem = new TargetSystem();
		this.addSystem(particleSystem);
		this.addSystem(cloudGenerator);
		this.addSystem(swimmerControlSystem);
		this.addSystem(circleCollisionSystem, -10);
		this.addSystem(targetSystem);
		this.createEntity(new RenderComponent(new ParticleSystemDrawer(particleSystem)));
		try {
			this.tileMap = TileMap.load(tileMapUrl, new JSONLoader());
		} catch (IOException e) {
			e.printStackTrace();
		}
		((ObjectLayer)tileMap.getLayer("Object Layer 1")).addObjectsAsEntities(this, new OceanComponentAdder());
		tileMapRenderer = this.createEntity(new RenderComponent(new TileMapRenderer(this.tileMap), -10));
		platformingSystem = new PlatformingSystem(this.tileMap, "tiles");
		this.addSystem(this.platformingSystem, 1000);
		this.addSystem(new ParallaxSystem(this.getRenderSystem().getCamera()));
		float w = this.tileMap.getTileWidth() * this.tileMap.getLayer("tiles").getWidth() + 30;
		float h = this.tileMap.getTileHeight() * this.tileMap.getLayer("tiles").getHeight() + 40;
		this.add(boat(w/2 - 250, -43));
		this.add(sun(w/2 + 100, -240));
		Entity swimmer;
		this.add(swimmer = swimmer(w/2, 200, particleSystem));
		this.add(healthBar(swimmer));
		this.add(airBar(swimmer));
		this.createEntity(new RenderComponent(new WaterDrawer(), new Color(1, 1, 1, .5f)));
		swimmerControlSystem.setxMin(-30);
		swimmerControlSystem.setxMax(w);
		swimmerControlSystem.setyMax(h);
		targetSystem.setxMin(-30);
		targetSystem.setxMax(w);
		targetSystem.setyMax(h);
		this.createEntity(
				new RenderComponent(new Backdrop(), -2000)
				);
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

	public TileMap getTileMap() {
		return tileMap;
	}

	public void setTileMap(TileMap tileMap) {
		this.tileMap = tileMap;
	}

	public Entity getTileMapRenderer() {
		return tileMapRenderer;
	}

	public void setTileMapRenderer(Entity tileMapRenderer) {
		this.tileMapRenderer = tileMapRenderer;
	}

	public PlatformingSystem getPlatformingSystem() {
		return platformingSystem;
	}

	public void setPlatformingSystem(PlatformingSystem platformingSystem) {
		this.platformingSystem = platformingSystem;
	}

	public ParticleSystem getParticleSystem() {
		return particleSystem;
	}

	public void setParticleSystem(ParticleSystem particleSystem) {
		this.particleSystem = particleSystem;
	}

	public CircleCollisionSystem getCircleCollisionSystem() {
		return circleCollisionSystem;
	}

	public void setCircleCollisionSystem(CircleCollisionSystem circleCollisionSystem) {
		this.circleCollisionSystem = circleCollisionSystem;
	}

	public TargetSystem getTargetSystem() {
		return targetSystem;
	}

	public void setTargetSystem(TargetSystem targetSystem) {
		this.targetSystem = targetSystem;
	}

}

package bakpakin.ld29;

import static bakpakin.ld29.EntityFactory.*;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

import bakpakin.egf.EGF;
import bakpakin.egf.framework.Entity;
import bakpakin.egf.framework.EntitySystem;
import bakpakin.egf.geom.Transform;
import bakpakin.egf.input.InputListener;
import bakpakin.egf.particles.ParticleSystem;
import bakpakin.egf.particles.ParticleSystemDrawer;
import bakpakin.egf.physics.CircleCollisionSystem;
import bakpakin.egf.render.ParallaxSystem;
import bakpakin.egf.render.RenderComponent;
import bakpakin.egf.render.Text;
import bakpakin.egf.tilemap.JSONLoader;
import bakpakin.egf.tilemap.ObjectLayer;
import bakpakin.egf.tilemap.PlatformingSystem;
import bakpakin.egf.tilemap.TileMap;
import bakpakin.egf.tilemap.TileMapRenderer;
import bakpakin.egf.util.Routine;

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
	private MusicSystem musicSystem;
	private AirSystem airSystem;
	private AirWarningSystem airWarningSystem;
	private Text coinText;
	
	private Entity swimmer;
	
	private Text directionsText;
	
	public int coins;
	
	private PauseUI pauseUi;
	private DeathUI deathUi;
	private WinUI winUi;
	private StoreUI storeUi;
	
	private TileMap tileMap;
	private Entity tileMapRenderer;

	private boolean paused;

	public OceanScene(String tileMapUrl) {
		super();
		getRenderSystem().setBackgroundColor(new Color(.8f, 1f, 1f, 1f));
		cloudGenerator = new CloudGenerator(this.getRenderSystem().getCamera());
		swimmerControlSystem = new SwimmerControlSystem(this.getRenderSystem().getCamera());
		particleSystem = new ParticleSystem();
		circleCollisionSystem = new CircleCollisionSystem();
		targetSystem = new TargetSystem();
		musicSystem = new MusicSystem();
		this.addSystem(particleSystem);
		this.addSystem(cloudGenerator);
		this.addSystem(swimmerControlSystem);
		this.addSystem(circleCollisionSystem, -10);
		this.addSystem(targetSystem);
		this.addSystem(musicSystem);
		this.createEntity(new RenderComponent(new ParticleSystemDrawer(particleSystem)));
		try {
			this.tileMap = TileMap.load(tileMapUrl, new JSONLoader());
		} catch (IOException e) {
			e.printStackTrace();
		}
		platformingSystem = new PlatformingSystem(this.tileMap, "tiles");
		this.addSystem(this.platformingSystem, 1000);
		this.addSystem(new ParallaxSystem(this.getRenderSystem().getCamera()));
		float w = this.tileMap.getTileWidth() * this.tileMap.getLayer("tiles").getWidth();
		float h = this.tileMap.getTileHeight() * this.tileMap.getLayer("tiles").getHeight();
		Entity boat;
		this.add(boat = boat(w/2 - 250, -43));
		this.add(sun(w/2 + 100, -240));
		this.add(swimmer = swimmer(w/2, 200, particleSystem));
		this.add(healthBar(swimmer));
		this.add(airBar(swimmer));
		this.addSystem(setAirWarningSystem(new AirWarningSystem(this, swimmer)));
		this.addSystem(new BoatCollisionSystem(this, swimmer, boat));
		
		airSystem = new AirSystem(swimmer);
		this.addSystem(airSystem);
		
		((ObjectLayer)tileMap.getLayer("Object Layer 1")).addObjectsAsEntities(this, new OceanComponentAdder(swimmer));
		tileMapRenderer = this.createEntity(new RenderComponent(new TileMapRenderer(this.tileMap), 1));
		
		this.createEntity(new RenderComponent(new WaterDrawer(), 25, new Color(1, 1, 1, .5f)));
		this.createEntity(new RenderComponent(new Backdrop(), -2000));
		
		swimmerControlSystem.setxMax(w);
		swimmerControlSystem.setyMax(h);
		targetSystem.setxMax(w);
		targetSystem.setyMax(h);
		
		pauseUi = PauseUI.makeUI();
		pauseUi.addToRenderSystem(this.getRenderSystem(), 10000);
		pauseUi.setActive(false);
		
		deathUi = DeathUI.makeUI();
		deathUi.addToRenderSystem(this.getRenderSystem(), 10000);
		deathUi.setActive(false);
		
		winUi = WinUI.makeUI();
		winUi.addToRenderSystem(this.getRenderSystem(), 10000);
		winUi.setActive(false);
		
		storeUi = StoreUI.makeUI();
		storeUi.addToRenderSystem(this.getRenderSystem(), 10000);
		storeUi.setActive(false);
		
		Entity coins = coinCounter();
		this.add(coins);
		this.setCoinText((Text) coins.get(RenderComponent.class).getDrawable());
		
		directionsText = new Text("Find the Sunken Treasure.", "res/gilligansisland.ttf", 32);
		this.createEntity(
				new Transform(5, 185),
				new RenderComponent(directionsText, 10000).drawHud());
		
		this.getRenderSystem().getCamera().getTransform().setX(swimmer.get(Transform.class).getX());
		this.getRenderSystem().getCamera().getTransform().setY(swimmer.get(Transform.class).getY());
		
		this.createEntity(InputListener.keyListener(new Routine() {
			@Override
			public void doRoutine() {
				((OceanScene)EGF.getScene()).pause(true);
			}
		}, Keyboard.KEY_P, InputListener.PRESS));
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

	public MusicSystem getMusicSystem() {
		return musicSystem;
	}

	public void setMusicSystem(MusicSystem musicSystem) {
		this.musicSystem = musicSystem;
	}

	public AirSystem getAirSystem() {
		return airSystem;
	}

	public void setAirSystem(AirSystem airSystem) {
		this.airSystem = airSystem;
	}
	
	public void youDied() {
		setActive(false);
		deathUi.setActive(true);
	}
	
	private void setActive(boolean active) {
		for (EntitySystem sys : this.getSystems())
			sys.setActive(active);
		this.getRenderSystem().setActive(true);
		this.getInputSystem().setActive(true);

		paused = !active;
	}
	
	public void findTreasure() {
		directionsText.setText("Take the Treasure back to the Boat.");
	}
	
	public void win() {
		setActive(false);
		winUi.setActive(true);
	}

	public void pause(boolean pause) {
		setActive(!pause);
		pauseUi.setActive(pause);
	}
	
	public boolean isPaused() {
		return paused;
	}

	public Text getCoinText() {
		return coinText;
	}

	public void setCoinText(Text coinText) {
		this.coinText = coinText;
	}

	public void showStore() {
		setActive(false);
		storeUi.setActive(true);		
	}

	public void hideStore() {
		setActive(true);
		storeUi.setActive(false);		
	}

	public Entity getSwimmer() {
		return swimmer;
	}

	public AirWarningSystem getAirWarningSystem() {
		return airWarningSystem;
	}

	public AirWarningSystem setAirWarningSystem(AirWarningSystem airWarningSystem) {
		this.airWarningSystem = airWarningSystem;
		return airWarningSystem;
	}

}

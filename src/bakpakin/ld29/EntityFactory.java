package bakpakin.ld29;

import org.newdawn.slick.Color;

import bakpakin.egf.framework.Entity;
import bakpakin.egf.geom.Transform;
import bakpakin.egf.physics.BoxCollider;
import bakpakin.egf.physics.DeltaTransform;
import bakpakin.egf.physics.Friction;
import bakpakin.egf.render.Drawable;
import bakpakin.egf.render.RenderComponent;
import bakpakin.egf.render.RenderSystem;
import bakpakin.egf.render.Sprite;

public class EntityFactory {
	
	public static final String PLAYER_TAG = "Swimmer";
	public static final String ENEMY_TAG = "Enemy";

	private EntityFactory() {
	}
	
	public static Entity swimmer(float x, float y) {
		Entity swimmer = new Entity(
				new Transform(x, y),
				new DeltaTransform(),
				new Friction(100),
				new RenderComponent(new Sprite("res/swimmer.png").center()),
				new BoxCollider(20, 20)
				);
		swimmer.addTag(PLAYER_TAG);
		swimmer.setProperty("Accel", 500);
		swimmer.setProperty("MaxSpeed", 400);
		swimmer.setProperty("Air", 100);
		swimmer.setProperty("Health", 100);
		return swimmer;
	}
	
	public static Entity fish(float x, float y, Color color) {
		Entity fish = new Entity(
				new Transform(x, y),
				new DeltaTransform(),
				new RenderComponent(new Sprite("res/fish.png").center(), color)
				);
		fish.addTag(ENEMY_TAG);
		return fish;
	}
	
	public static Entity jellyFish(float x, float y, Color color) {
		Entity fish = new Entity(
				new Transform(x, y),
				new DeltaTransform(),
				new RenderComponent(new Sprite("res/jellyfish.png").center(), color)
				);
		fish.addTag(ENEMY_TAG);
		return fish;
	}
	
	public static Entity sun(float x, float y) {
		return new Entity(
				new Transform(x, y),
				new RenderComponent(new Sprite("res/sun.png").center(), -1000));
	}
	
	public static Entity boat(float x, float y) {
		return new Entity(
				new Transform(x, y), 
				new RenderComponent(new Sprite("res/boat.png").center()));
	}
	
	public static String[] clouds = {
		"res/cloud1.png",
		"res/cloud2.png",
		"res/cloud3.png",
		"res/cloud4.png"
	};
	
	public static Entity cloud(float x, float y, float hspeed, int zeroToThree) {
		Entity cloud = new Entity(
				new Transform(x, y),
				new DeltaTransform(hspeed, 0),
				new RenderComponent(new Sprite(clouds[zeroToThree]).center(), -50));
		
		return cloud;
	}
	
	public static Entity makeHealthBar(final Entity player) {
		Entity bar = new Entity(
				new Transform(5, 5),
				new RenderComponent(new Drawable() {
					@Override
					public void draw(RenderSystem renderSystem, float depth,
							Color color, Transform t) {
						
					}
				}));
		return bar;
	}

}

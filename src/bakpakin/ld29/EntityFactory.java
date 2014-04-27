package bakpakin.ld29;

import org.newdawn.slick.Color;

import bakpakin.egf.framework.Entity;
import bakpakin.egf.geom.Transform;
import bakpakin.egf.particles.ParticleDef;
import bakpakin.egf.particles.ParticleSystem;
import bakpakin.egf.physics.BoxCollider;
import bakpakin.egf.physics.CircleCollider;
import bakpakin.egf.physics.CircleCollisionSystem;
import bakpakin.egf.physics.DeltaTransform;
import bakpakin.egf.physics.Friction;
import bakpakin.egf.render.Drawable;
import bakpakin.egf.render.RenderComponent;
import bakpakin.egf.render.RenderSystem;
import bakpakin.egf.render.Sprite;
import bakpakin.egf.timer.TimerComponent;
import bakpakin.egf.util.Routine;

public class EntityFactory {

	public static final String PLAYER_TAG = "Swimmer";
	public static final String ENEMY_TAG = "Enemy";
	public static final String COLLECTIBLE_TAG = "Collectible";

	public static ParticleDef BUBBLE;
	
	public static ParticleDef BLOOD;

	private EntityFactory() {
	}

	public static Entity swimmer(float x, float y, final ParticleSystem ps) {
		makeBubble();
		makeBlood();
		Entity swimmer = new Entity(
				new Transform(x, y),
				new DeltaTransform(),
				new Friction(100),
				new RenderComponent(new Sprite("res/swimmer.png").center()),
				new BoxCollider(20, 20),
				new CircleCollider(20)
				);
		swimmer.add(new TimerComponent(.3f, .3f, new Bubbler(swimmer, ps)));
		swimmer.addTag(PLAYER_TAG);
		swimmer.setProperty("Accel", 500);
		//swimmer.setProperty(PlatformingSystem.COLLISION_PROPERTY, new Bumper());
		swimmer.setProperty("MaxSpeed", 400);
		swimmer.setProperty("Air", 10);
		swimmer.setProperty("Health", 10);
		swimmer.setProperty(CircleCollisionSystem.COLLISION_BEHAVIOR, new SwimmerCollisionResponse(ps));
		return swimmer;
	}

	private static void makeBlood() {
		BLOOD = new ParticleDef();
		BLOOD.setSprite(new Sprite("res/blood.png").center());
		BLOOD.setAlpha(1, 1, -.5f, 0, 0);
		BLOOD.setDirection(0, 360, 0, 0, 0);
		BLOOD.setOrientation(0, 360, 0, 0, 0);
		BLOOD.setLife(8, 8);
		BLOOD.setSpeed(50, 130, 1, 0, 3);
		BLOOD.setSize(.5f, 1.5f, -.2f, 0, 0);
	}

	public static class Bubbler implements Routine {

		private ParticleSystem ps;
		private Entity player;

		public Bubbler(Entity player, ParticleSystem ps) {
			this.player = player;
			this.ps = ps;
		}

		@Override
		public void doRoutine() {
			Transform t = player.get(Transform.class);
			float x = t.getX();
			float y = t.getY();
			int max = (int)(Math.random() * 5);
			if (t.getY() >= 0)
				for (int i = 0; i < max; i++)
					ps.createParticle(x, y, BUBBLE);
		}


	}

	private static void makeBubble() {
		BUBBLE = new ParticleDef();
		BUBBLE.setSprite(new Sprite("res/bubble.png").center());
		BUBBLE.setAlpha(1, 1, -.5f, 0, 0);
		BUBBLE.setDirection(250, 290, 0, 0, 100);
		BUBBLE.setLife(4, 4);
		BUBBLE.setSpeed(20, 90, 1, 0, 3);
		BUBBLE.setSize(.5f, 2, -1, 0, 0);
	}

	public static Entity sun(float x, float y) {
		return new Entity(
				new Transform(x, y),
				new RenderComponent(new Sprite("res/sun.png").center(), -3000));
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

	public static Entity healthBar(final Entity player) {
		final Sprite redBubble = new Sprite("res/redbubble.png");
		Entity bar = new Entity(
				new Transform(5, 5),
				new RenderComponent(new Drawable() {
					@Override
					public void draw(RenderSystem renderSystem, float depth,
							Color color, Transform t) {
						int health = (Integer) player.getProperty("Health");
						t = t.clone();
						for (int i = 0; i < health; i++) {
							redBubble.draw(renderSystem, depth, color, t);
							t.translate(t.getXScale()*(redBubble.getWidth() + 5), 0);
						}
					}
				}, 1000).drawHud());
		return bar;
	}

	public static Entity airBar(final Entity player) {
		final Sprite blueBubble = new Sprite("res/bluebubble.png");
		Entity bar = new Entity(
				new Transform(5, 50),
				new RenderComponent(new Drawable() {
					@Override
					public void draw(RenderSystem renderSystem, float depth,
							Color color, Transform t) {
						int health = (Integer) player.getProperty("Air");
						t = t.clone();
						for (int i = 0; i < health; i++) {
							blueBubble.draw(renderSystem, depth, color, t);
							t.translate(t.getXScale()*(blueBubble.getWidth() + 5), 0);
						}
					}
				}, 1000).drawHud());
		return bar;
	}

}

package bakpakin.egf.util.test;

import org.lwjgl.input.Mouse;

import org.lwjgl.util.vector.Vector2f;

import org.newdawn.slick.Color;

import bakpakin.egf.framework.Entity;
import bakpakin.egf.framework.World;
import bakpakin.egf.util.Runner;
import bakpakin.egf.util.StepBehavior;
import bakpakin.egf.util.StepSystem;
import bakpakin.egf.util.VoidBehavior;
import bakpakin.egf.util.geom.Circle;
import bakpakin.egf.util.geom.Transform;
import bakpakin.egf.util.input.InputListener;
import bakpakin.egf.util.input.InputSystem;
import bakpakin.egf.util.particles.ParticleDef;
import bakpakin.egf.util.particles.ParticleEmitter;
import bakpakin.egf.util.particles.ParticleSystem;
import bakpakin.egf.util.particles.ParticleSystemDrawer;
import bakpakin.egf.util.render.Background;
import bakpakin.egf.util.render.RenderComponent;
import bakpakin.egf.util.render.RenderSystem;
import bakpakin.egf.util.render.Sprite;
import bakpakin.egf.util.render.Text;

public class ParticleTest {

	public static void test() {
		final World world = new World();
		final RenderSystem renderSystem = new RenderSystem();
		final ParticleSystem particleSystem = new ParticleSystem();
		final InputSystem inputSystem = new InputSystem();
		final StepSystem stepSystem = new StepSystem();
		final SimpleNavigationSystem navigationSystem = new SimpleNavigationSystem(renderSystem);
	
		world.addSystem(renderSystem);
		world.addSystem(particleSystem);
		world.addSystem(inputSystem);
		world.addSystem(stepSystem);
		world.addSystem(navigationSystem);

		final ParticleDef def = new ParticleDef();
		def.setSprite(new Sprite("bakpakin/egf/util/test/testparticle.png").center());
		def.setSize(.5f, .5f, -.1f, 0, 0);
		def.setColor(Color.white);
		def.setSpeed(4, 40, 0, 0, 0);
		def.setAlpha(0, 0, 1f, -1f, .1f);
		def.setDirection(0, 360, 0, 0, 1000);
		def.setOrientation(0, 360, 0, 0, 1000);
		def.setBlending(true);
		
		VoidBehavior click = new VoidBehavior() {
			@Override
			public void behave() {
				Vector2f worldPoint = renderSystem.getCamera().worldPt(Mouse.getX(), Mouse.getY());
				for(int i = 0; i < 6; i++)
					particleSystem.createParticle(worldPoint.x, worldPoint.y, def);
			}
		};
		
		final Text hud = new Text("");
		
		VoidBehavior updateHud = new VoidBehavior() {
			@Override
			public void behave() {
				hud.setText("Particle Count: " + particleSystem.getParticleCount());
			}
		};
		
		world.createEntity(
				new RenderComponent(new ParticleSystemDrawer(particleSystem)),
				InputListener.mouseListener(click, 0, InputListener.HOLD)
				);
		
		world.createEntity(
				new RenderComponent(new Background("bakpakin/egf/util/test/testbackground.png"), -100)
				);
		
		world.createEntity(
				new RenderComponent(hud),
				new StepBehavior(updateHud)
				).addTag("Hud");
		
		Entity emitter = world.createEntity(
				new Transform(),
				new ParticleEmitter(new Circle(0, 0, 100)));
		emitter.addTag(particleSystem.getTag());
		
		emitter.get(ParticleEmitter.class).stream(def, 100);
		
		Runner.mainLoop(world);
	}

}

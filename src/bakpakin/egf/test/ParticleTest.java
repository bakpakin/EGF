package bakpakin.egf.test;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

import bakpakin.egf.EGF;
import bakpakin.egf.framework.Entity;
import bakpakin.egf.framework.World;
import bakpakin.egf.geom.Circle;
import bakpakin.egf.geom.Transform;
import bakpakin.egf.input.InputListener;
import bakpakin.egf.input.InputSystem;
import bakpakin.egf.particles.ParticleDef;
import bakpakin.egf.particles.ParticleEmitter;
import bakpakin.egf.particles.ParticleSystem;
import bakpakin.egf.particles.ParticleSystemDrawer;
import bakpakin.egf.render.Background;
import bakpakin.egf.render.RenderComponent;
import bakpakin.egf.render.RenderSystem;
import bakpakin.egf.render.Sprite;
import bakpakin.egf.render.Text;
import bakpakin.egf.util.StepBehavior;
import bakpakin.egf.util.StepSystem;
import bakpakin.egf.util.Routine;

public class ParticleTest {

	public static void test() {
		EGF.init();

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
		def.setSprite(new Sprite("bakpakin/egf/test/testparticle.png").center());
		def.setSize(.5f, .5f, -.1f, 0, 0);
		def.setColor(Color.white);
		def.setSpeed(4, 40, 0, 0, 0);
		def.setAlpha(0, 0, 1f, -1f, .1f);
		def.setDirection(0, 360, 0, 0, 1000);
		def.setOrientation(0, 360, 0, 0, 1000);
		def.setBlending(true);
		
		Routine click = new Routine() {
			@Override
			public void doRoutine() {
				Vector2f worldPoint = renderSystem.getCamera().worldPt(Mouse.getX(), Mouse.getY());
				for(int i = 0; i < 6; i++)
					particleSystem.createParticle(worldPoint.x, worldPoint.y, def);
			}
		};
		
		final Text hud = new Text("");
		
		Routine updateHud = new Routine() {
			@Override
			public void doRoutine() {
				hud.setText("Particle Count: " + particleSystem.getParticleCount());
			}
		};
		
		world.createEntity(
				new RenderComponent(new ParticleSystemDrawer(particleSystem)),
				InputListener.mouseListener(click, 0, InputListener.HOLD)
				);
		
		world.createEntity(
				new RenderComponent(new Background("bakpakin/egf/test/testbackground.png"), -100)
				);
		
		world.createEntity(
				new RenderComponent(hud).drawHud(),
				new StepBehavior(updateHud)
				);
		
		Entity emitter = world.createEntity(
				new Transform(),
				new ParticleEmitter(new Circle(0, 0, 100)));
		emitter.addTag(particleSystem.getTag());
		
		emitter.get(ParticleEmitter.class).stream(def, 100);
		
		EGF.mainLoop(world);
		
		EGF.cleanUp();
	}

}

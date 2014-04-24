package bakpakin.egf.util.test;

import bakpakin.egf.framework.World;
import bakpakin.egf.util.Runner;
import bakpakin.egf.util.StepSystem;
import bakpakin.egf.util.geom.AxisAlignedBox;
import bakpakin.egf.util.geom.Polygon;
import bakpakin.egf.util.geom.ShapeDrawer;
import bakpakin.egf.util.geom.Transform;
import bakpakin.egf.util.input.InputSystem;
import bakpakin.egf.util.particles.ParticleSystem;
import bakpakin.egf.util.physics.DeltaTransform;
import bakpakin.egf.util.physics.MovementSystem;
import bakpakin.egf.util.render.RenderComponent;
import bakpakin.egf.util.render.RenderSystem;

public class GeometryTest {
	
	public static void test() throws Exception {
		final World world = new World();
		final RenderSystem renderSystem = new RenderSystem();
		final ParticleSystem particleSystem = new ParticleSystem();
		final InputSystem inputSystem = new InputSystem();
		final StepSystem stepSystem = new StepSystem();
		final SimpleNavigationSystem navigationSystem = new SimpleNavigationSystem(renderSystem);
		final MovementSystem movementSystem  = new MovementSystem();

		world.addSystem(renderSystem);
		world.addSystem(particleSystem);
		world.addSystem(inputSystem);
		world.addSystem(stepSystem);
		world.addSystem(navigationSystem);
		world.addSystem(movementSystem);
		
		Polygon poly = new Polygon(new float[] 
				{-100f, -100,
				-200f, 100f, 
				100f, 100f, 
				100f, -100f});
		
		world.createEntity(
				new Transform(),
				new DeltaTransform().rotate(30),
				new RenderComponent(new ShapeDrawer(poly))
				);
		
		world.createEntity(
				new Transform(-200, -200),
				new DeltaTransform().rotate(-90),
				new RenderComponent(new ShapeDrawer(new AxisAlignedBox(-10, 40, -70, 80)))
				);
		
		Runner.mainLoop(world);
	} 

}

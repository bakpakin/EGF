package bakpakin.egf.test;

import org.newdawn.slick.Color;

import bakpakin.egf.EGF;
import bakpakin.egf.framework.World;
import bakpakin.egf.geom.AxisAlignedBox;
import bakpakin.egf.geom.Circle;
import bakpakin.egf.geom.OrientedBox;
import bakpakin.egf.geom.Polygon;
import bakpakin.egf.geom.ShapeDrawer;
import bakpakin.egf.geom.Transform;
import bakpakin.egf.input.InputSystem;
import bakpakin.egf.particles.ParticleSystem;
import bakpakin.egf.physics.DeltaTransform;
import bakpakin.egf.physics.MovementSystem;
import bakpakin.egf.render.RenderComponent;
import bakpakin.egf.render.RenderSystem;
import bakpakin.egf.util.StepSystem;

public class GeometryTest {
	
	public static void test() throws Exception {
		EGF.init();

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
				200f, -200f,
				130f, -90f});
		
		AxisAlignedBox aab = new AxisAlignedBox(-10, 40, -70, 80);
		
		Circle c = new Circle(200, 20, 50);
		
		OrientedBox ob = new OrientedBox(70, -80, 130, 95, 34);
		
		//Ellipse e = new Ellipse(-30, 300, 70, 160, -78);
		
		world.createEntity(
				new Transform(300, 100),
				new DeltaTransform().rotate(30),
				new RenderComponent(new ShapeDrawer(poly), Color.green)
				);
		
		world.createEntity(
				new Transform(-200, -200),
				new DeltaTransform().rotate(-90),
				new RenderComponent(new ShapeDrawer(aab), Color.blue)
				);
		
		world.createEntity(
				new Transform(200, -200),
				new DeltaTransform().rotate(-90),
				new RenderComponent(new ShapeDrawer(ob), Color.red)
				);
		
		world.createEntity(
				new Transform(),
				new DeltaTransform().rotate(-40),
				new RenderComponent(new ShapeDrawer(c), Color.yellow)
				);
		
		EGF.mainLoop(world);
		
		EGF.cleanUp();
	} 

}

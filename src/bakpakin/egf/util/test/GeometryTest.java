package bakpakin.egf.util.test;

import org.lwjgl.util.vector.Vector2f;

import bakpakin.egf.framework.World;
import bakpakin.egf.util.Runner;
import bakpakin.egf.util.StepSystem;
import bakpakin.egf.util.geom.AxisAlignedBox;
import bakpakin.egf.util.geom.Ellipse;
import bakpakin.egf.util.geom.Polygon;
import bakpakin.egf.util.geom.Shape;
import bakpakin.egf.util.geom.Transform;
import bakpakin.egf.util.input.InputSystem;
import bakpakin.egf.util.particles.ParticleSystem;
import bakpakin.egf.util.render.RenderComponent;
import bakpakin.egf.util.render.RenderSystem;
import bakpakin.egf.util.render.Sprite;

public class GeometryTest {
	
	public static void test() throws Exception {
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
		
		Polygon poly = new Polygon(new float[] 
				{0f, 35f,
				-60f, 100f, 
				200f, 200f, 
				100f, 0f});
		
		for (int i = 0; i < poly.getNumPoints(); i++) {
			Vector2f point = poly.getPoint(i);
			world.createEntity(
					new Transform(point.x, point.y),
					new RenderComponent(new Sprite("bakpakin/egf/util/test/testparticle.png"), 100));
		}
		
		addShape(world, poly);
		addShape(world, new Ellipse(0, 0, 320, 185, 45));//.transformLocal(Transform.rotation(20)));
		
		Runner.mainLoop(world);
		Runner.cleanUp();
	}
	
	private static void addShape(World world, Shape shape) {
		AxisAlignedBox box = shape.getAxisAlignedBox();
		RenderComponent red = new RenderComponent(new Sprite("bakpakin/egf/util/test/testred.png").center());
		RenderComponent blue = new RenderComponent(new Sprite("bakpakin/egf/util/test/testblue.png").center());
		for (float x = box.getXMin(); x <= box.getXMax(); x += 8) {
			for (float y = box.getYMin(); y <= box.getYMax(); y += 8) {
				if (shape.contains(x, y)) {
					world.createEntity(
							new Transform(x, y).scale(.25f, .25f),
							red
							);
				} else {
					world.createEntity(
							new Transform(x, y).scale(.25f, .25f),
							blue
							);
				}
			}
		}
	}

}

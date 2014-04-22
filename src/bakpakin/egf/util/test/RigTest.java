package bakpakin.egf.util.test;

import org.newdawn.slick.Color;

import bakpakin.egf.framework.World;
import bakpakin.egf.util.Runner;
import bakpakin.egf.util.StepSystem;
import bakpakin.egf.util.geom.Transform;
import bakpakin.egf.util.physics.DeltaTransform;
import bakpakin.egf.util.physics.MovementSystem;
import bakpakin.egf.util.render.RenderComponent;
import bakpakin.egf.util.render.RenderSystem;
import bakpakin.egf.util.render.Sprite;
import bakpakin.egf.util.rig.Bone;
import bakpakin.egf.util.rig.RigDrawer;

public class RigTest {
	
	public static void test() {
		final World world = new World();
		final RenderSystem renderSystem = new RenderSystem();
		final SimpleNavigationSystem navigationSystem = new SimpleNavigationSystem(renderSystem);
		final StepSystem stepSystem = new StepSystem();
		final MovementSystem movementSystem = new MovementSystem();

		world.addSystem(renderSystem);
		world.addSystem(navigationSystem);
		world.addSystem(stepSystem);
		world.addSystem(movementSystem);
		
		renderSystem.setBackgroundColor(Color.orange);
		
		Sprite ogre = new Sprite("bakpakin/egf/util/test/testbone.png").center();
		
		Bone[] chain = new Bone[10];
		for (int i = 0; i < chain.length; i++) {
			chain[i] = new Bone(i == 0 ? null : chain[i-1], 40, 10, ogre);
			chain[i].setDepth(i);
		}
		
		world.createEntity(
				new Transform(),
				new DeltaTransform().rotate(10),
				new RenderComponent(new RigDrawer(chain[0])));
		
		Runner.mainLoop(world);
	}

}

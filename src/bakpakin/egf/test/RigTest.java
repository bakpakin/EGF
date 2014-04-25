package bakpakin.egf.test;

import org.newdawn.slick.Color;

import bakpakin.egf.EGF;
import bakpakin.egf.framework.World;
import bakpakin.egf.geom.Transform;
import bakpakin.egf.physics.DeltaTransform;
import bakpakin.egf.physics.MovementSystem;
import bakpakin.egf.render.RenderComponent;
import bakpakin.egf.render.RenderSystem;
import bakpakin.egf.render.Sprite;
import bakpakin.egf.rig.Bone;
import bakpakin.egf.rig.RigDrawer;
import bakpakin.egf.util.StepSystem;

public class RigTest {
	
	public static void test() {
		EGF.init();

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
		
		Sprite ogre = new Sprite("bakpakin/egf/test/testbone.png").center();
		
		Bone[] chain = new Bone[10];
		for (int i = 0; i < chain.length; i++) {
			chain[i] = new Bone(i == 0 ? null : chain[i-1], 40, 10, ogre);
			chain[i].setDepth(i);
		}
		
		world.createEntity(
				new Transform(),
				new DeltaTransform().rotate(10),
				new RenderComponent(new RigDrawer(chain[0])));
		
		EGF.mainLoop(world);
		
		EGF.cleanUp();
	}

}

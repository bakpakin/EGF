/**
 * 
 */
package bakpakin.egf.util.test;

import bakpakin.egf.framework.World;
import bakpakin.egf.util.AssetManager;
import bakpakin.egf.util.Runner;
import bakpakin.egf.util.VoidBehavior;
import bakpakin.egf.util.input.InputListener;
import bakpakin.egf.util.input.InputSystem;
import bakpakin.egf.util.render.Background;
import bakpakin.egf.util.render.RenderComponent;
import bakpakin.egf.util.render.RenderSystem;
import bakpakin.egf.util.render.Sprite;

/**
 * @author Calvin
 *
 */
public class ShaderTest {
	
	public static void test() throws Exception {
		final World world = new World();
		final RenderSystem renderSystem = new RenderSystem();
		renderSystem.setLinearSampling(true);
		final SimpleNavigationSystem navigationSystem = new SimpleNavigationSystem(renderSystem);
		final InputSystem inputSystem = new InputSystem();
		
		world.addSystem(renderSystem);
		world.addSystem(navigationSystem);
		world.addSystem(inputSystem);
				
		final int shader = AssetManager.getShader("bakpakin/egf/util/test/fisheye");
		
		world.createEntity(
				new RenderComponent(new Sprite("bakpakin/egf/util/test/testgrid.png").center())
				);
		
		world.createEntity(
				new RenderComponent(new Background("bakpakin/egf/util/test/testblue.png"), -10000)
				);
		
		VoidBehavior click = new VoidBehavior() {
			@Override
			public void behave() {
				try {
					renderSystem.setShader(renderSystem.getShader() == 0 ? shader : 0);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		
		world.createEntity(
				InputListener.mouseListener(click, 0, InputListener.PRESS)
				);
		
		Runner.mainLoop(world);
	}

}

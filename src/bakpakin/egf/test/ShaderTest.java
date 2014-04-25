/**
 * 
 */
package bakpakin.egf.test;

import bakpakin.egf.EGF;
import bakpakin.egf.framework.World;
import bakpakin.egf.input.InputListener;
import bakpakin.egf.input.InputSystem;
import bakpakin.egf.render.Background;
import bakpakin.egf.render.RenderComponent;
import bakpakin.egf.render.RenderSystem;
import bakpakin.egf.render.Sprite;
import bakpakin.egf.util.AssetManager;
import bakpakin.egf.util.Routine;

/**
 * @author Calvin
 *
 */
public class ShaderTest {
	
	public static void test() throws Exception {
		EGF.init();

		final World world = new World();
		final RenderSystem renderSystem = new RenderSystem();
		renderSystem.setLinearSampling(true);
		final SimpleNavigationSystem navigationSystem = new SimpleNavigationSystem(renderSystem);
		final InputSystem inputSystem = new InputSystem();
		
		world.addSystem(renderSystem);
		world.addSystem(navigationSystem);
		world.addSystem(inputSystem);
				
		final int shader = AssetManager.getShader("bakpakin/egf/test/fisheye");
		
		world.createEntity(
				new RenderComponent(new Sprite("bakpakin/egf/test/testgrid.png").center())
				);
		
		world.createEntity(
				new RenderComponent(new Background("bakpakin/egf/test/testblue.png"), -10000)
				);
		
		Routine click = new Routine() {
			@Override
			public void doRoutine() {
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
		
		EGF.mainLoop(world);
		
		EGF.cleanUp();
	}

}

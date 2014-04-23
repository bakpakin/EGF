package bakpakin.egf.util.test;

import bakpakin.egf.framework.World;
import bakpakin.egf.util.Runner;
import bakpakin.egf.util.gui.UI;
import bakpakin.egf.util.gui.UIDrawer;
import bakpakin.egf.util.gui.UITheme;
import bakpakin.egf.util.render.RenderComponent;
import bakpakin.egf.util.render.RenderSystem;

public class UITest {

	public static void test() throws Exception {
		final World world = new World();
		final RenderSystem renderSystem = new RenderSystem();
		final SimpleNavigationSystem navigationSystem = new SimpleNavigationSystem(renderSystem);

		world.addSystem(renderSystem);
		world.addSystem(navigationSystem);
		
		UITheme theme = new UITheme();
				
		UI ui = new UI(theme, null, renderSystem.getCamera());
		
		world.createEntity(new RenderComponent(new UIDrawer(ui)));
		
		Runner.mainLoop(world);
	}
	
}

package bakpakin.egf.util.test;

import org.newdawn.slick.opengl.Texture;

import bakpakin.egf.framework.World;
import bakpakin.egf.util.AssetManager;
import bakpakin.egf.util.Runner;
import bakpakin.egf.util.gui.NineBox;
import bakpakin.egf.util.gui.UI;
import bakpakin.egf.util.gui.UIButton;
import bakpakin.egf.util.gui.UIDrawer;
import bakpakin.egf.util.gui.UITheme;
import bakpakin.egf.util.render.RenderComponent;
import bakpakin.egf.util.render.RenderSystem;
import bakpakin.egf.util.render.Text;

public class UITest {

	public static void test() throws Exception {
		final World world = new World();
		final RenderSystem renderSystem = new RenderSystem();
		final SimpleNavigationSystem navigationSystem = new SimpleNavigationSystem(renderSystem);

		world.addSystem(renderSystem);
		world.addSystem(navigationSystem);
		
		UITheme theme = new UITheme();
		theme.setBodyFont(AssetManager.getFont(Text.DEFAULT_FONT, Text.DEFAULT_SIZE));
		theme.setTitleFont(AssetManager.getFont(Text.DEFAULT_FONT, Text.DEFAULT_SIZE));
		Texture b = AssetManager.getTexture("bakpakin/egf/util/test/button.png");
		Texture bp = AssetManager.getTexture("bakpakin/egf/util/test/buttonpressed.png");
		Texture bh = AssetManager.getTexture("bakpakin/egf/util/test/buttonhover.png");
		NineBox button = new NineBox(b);
		NineBox buttonHover = new NineBox(bh);
		NineBox buttonPressed = new NineBox(bp);
		theme.setButton(button);
		theme.setButtonHover(buttonHover);
		theme.setButtonPressed(buttonPressed);
		
		UIButton butn = new UIButton("Hello World!");
				
		UI ui = new UI(theme, butn, renderSystem.getCamera());
		
		world.createEntity(new RenderComponent(new UIDrawer(ui)));
		
		Runner.mainLoop(world);
	}
	
}

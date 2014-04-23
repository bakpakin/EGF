package bakpakin.egf.util.test;

import org.newdawn.slick.opengl.Texture;

import bakpakin.egf.framework.World;
import bakpakin.egf.util.AssetManager;
import bakpakin.egf.util.Runner;
import bakpakin.egf.util.geom.Transform;
import bakpakin.egf.util.gui.NineBox;
import bakpakin.egf.util.gui.UI;
import bakpakin.egf.util.gui.UIButton;
import bakpakin.egf.util.gui.UIDrawer;
import bakpakin.egf.util.gui.UILinearLayout;
import bakpakin.egf.util.gui.UIPanel;
import bakpakin.egf.util.gui.UIRadioButton;
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
		Texture p = AssetManager.getTexture("bakpakin/egf/util/test/parchment.png");
		Texture b = AssetManager.getTexture("bakpakin/egf/util/test/button.png");
		Texture bp = AssetManager.getTexture("bakpakin/egf/util/test/buttonpressed.png");
		Texture bh = AssetManager.getTexture("bakpakin/egf/util/test/buttonhover.png");
		Texture rb = AssetManager.getTexture("bakpakin/egf/util/test/radiobutton.png");
		Texture rbs = AssetManager.getTexture("bakpakin/egf/util/test/radiobuttonselected.png");
		NineBox button = new NineBox(b);
		NineBox buttonHover = new NineBox(bh);
		NineBox buttonPressed = new NineBox(bp);
		NineBox c = new NineBox(p);
		theme.setButton(button);
		theme.setButtonHover(buttonHover);
		theme.setButtonPressed(buttonPressed);
		theme.setRadioButtonSelected(rbs);
		theme.setRadioButtonUnselected(rb);
		theme.setContainer(c);
		
		UIButton btn = new UIButton("Hello World!");
		UIRadioButton rdbtn = new UIRadioButton("Hello!");
		
		UILinearLayout ll = new UILinearLayout(UILinearLayout.LAYOUT_VERTICAL);
		ll.add(btn);
		ll.add(rdbtn);
		
		UIPanel panel = new UIPanel(ll);
				
		UI ui = new UI(theme, panel, renderSystem.getCamera());
		
		world.createEntity(new RenderComponent(new UIDrawer(ui)));
		
		Runner.mainLoop(world);
	}
	
}

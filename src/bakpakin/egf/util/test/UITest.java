package bakpakin.egf.util.test;

import org.newdawn.slick.opengl.Texture;

import bakpakin.egf.framework.World;
import bakpakin.egf.util.AssetManager;
import bakpakin.egf.util.Runner;
import bakpakin.egf.util.gui.NineBox;
import bakpakin.egf.util.gui.UI;
import bakpakin.egf.util.gui.UIButton;
import bakpakin.egf.util.gui.UIInvisiContainer;
import bakpakin.egf.util.gui.UILinearLayout;
import bakpakin.egf.util.gui.UIPanel;
import bakpakin.egf.util.gui.UIRadioButton;
import bakpakin.egf.util.gui.UITheme;
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
		NineBox c = new NineBox(p, 128, 128, 129, 129, 50, 50, 206, 206);
		theme.setButton(button);
		theme.setButtonHover(buttonHover);
		theme.setButtonPressed(buttonPressed);
		theme.setRadioButtonSelected(rbs);
		theme.setRadioButtonUnselected(rb);
		theme.setContainer(c);
		
		UIButton btn = new UIButton("Hello World!");
		UIRadioButton rdbtn1 = new UIRadioButton("Hello!");
		UIRadioButton rdbtn2 = new UIRadioButton("Hello!");
		UIRadioButton rdbtn3 = new UIRadioButton("Hello!");
		
		UIPanel p1 = new UIPanel(new UILinearLayout(UILinearLayout.LAYOUT_HORIZONTAL));
		p1.add(rdbtn1);
		p1.add(rdbtn2);
		p1.add(rdbtn3);
		
		UIPanel panel = new UIPanel();
		panel.add(btn);
		panel.add(p1);
		
		UI ui = new UI(theme, panel);
		ui.addToRenderSystem(renderSystem);
		
		Runner.mainLoop(world);
	}
	
}

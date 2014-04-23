package bakpakin.egf.util.test;

import org.lwjgl.opengl.Display;

import bakpakin.egf.framework.World;
import bakpakin.egf.util.Runner;
import bakpakin.egf.util.gui.UI;
import bakpakin.egf.util.gui.UIButton;
import bakpakin.egf.util.gui.UIContainer;
import bakpakin.egf.util.gui.UIInvisiContainer;
import bakpakin.egf.util.gui.UILinearLayout;
import bakpakin.egf.util.gui.UIPanel;
import bakpakin.egf.util.gui.UIRadioButton;
import bakpakin.egf.util.gui.UITheme;
import bakpakin.egf.util.render.RenderSystem;

public class UITest {

	public static void test() throws Exception {
		final World world = new World();
		final RenderSystem renderSystem = new RenderSystem();
		final SimpleNavigationSystem navigationSystem = new SimpleNavigationSystem(renderSystem);

		world.addSystem(renderSystem);
		world.addSystem(navigationSystem);
		
		UITheme theme = UITheme.getDefaultTheme();
		
		UIButton btn = new UIButton("Hello World!");
		UIRadioButton rdbtn1 = new UIRadioButton("Hello!");
		UIRadioButton rdbtn2 = new UIRadioButton("Hello!");
		UIRadioButton rdbtn3 = new UIRadioButton("Hello!");
		
		UIContainer p1 = new UIInvisiContainer(new UILinearLayout(UILinearLayout.LAYOUT_HORIZONTAL));
		p1.add(rdbtn1);
		p1.add(rdbtn2);
		p1.add(rdbtn3);
		
		UIContainer panel = new UIPanel();
		panel.add(btn);
		panel.add(p1);
		panel.setX(Display.getWidth()/2);
		panel.setY(Display.getHeight()/2);
		
		UI ui = new UI(theme, panel);
		ui.addToRenderSystem(renderSystem);
		
		Runner.mainLoop(world);
	}
	
}

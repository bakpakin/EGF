package bakpakin.egf.util.test;

import org.newdawn.slick.Color;

import bakpakin.egf.framework.World;
import bakpakin.egf.util.AssetManager;
import bakpakin.egf.util.Runner;
import bakpakin.egf.util.gui.UI;
import bakpakin.egf.util.gui.UIActionEvent;
import bakpakin.egf.util.gui.UIActionListener;
import bakpakin.egf.util.gui.UIButton;
import bakpakin.egf.util.gui.UIContainer;
import bakpakin.egf.util.gui.UIImage;
import bakpakin.egf.util.gui.UIInvisiContainer;
import bakpakin.egf.util.gui.UILabel;
import bakpakin.egf.util.gui.UILinearLayout;
import bakpakin.egf.util.gui.UIPanel;
import bakpakin.egf.util.gui.UIRadioButton;
import bakpakin.egf.util.gui.UIStateChangedEvent;
import bakpakin.egf.util.gui.UIStateListener;
import bakpakin.egf.util.gui.UITheme;
import bakpakin.egf.util.render.RenderSystem;

public class UITest {

	public static void test() throws Exception {
		final World world = new World();
		final RenderSystem renderSystem = new RenderSystem();
		final SimpleNavigationSystem navigationSystem = new SimpleNavigationSystem(renderSystem);

		world.addSystem(renderSystem);
		world.addSystem(navigationSystem);
		
		final UITheme theme = UITheme.getDefaultTheme();
		
		final UIButton btn = new UIButton("I'm a Button!");
		UIRadioButton rdbtn1 = new UIRadioButton("Hello 1!");
		UIRadioButton rdbtn2 = new UIRadioButton("Hello 1!");
		UIRadioButton rdbtn3 = new UIRadioButton("Hello 1!");
		
		UIContainer radioButtonPanel = new UIInvisiContainer(new UILinearLayout(UILinearLayout.LAYOUT_HORIZONTAL));
		radioButtonPanel.add(rdbtn1);
		radioButtonPanel.add(rdbtn2);
		radioButtonPanel.add(rdbtn3);
		
		UILabel label = new UILabel("I'm a Label!");
		UIImage image = new UIImage(AssetManager.getTexture("bakpakin/egf/util/test/testplayer.png"));
		UIContainer p3 = new UIInvisiContainer(new UILinearLayout(UILinearLayout.LAYOUT_HORIZONTAL)); 
		p3.add(btn);
		p3.add(label);
		p3.add(image);
		
		UIContainer panel = new UIPanel();
		panel.add(p3);
		UIContainer p2 = new UIInvisiContainer(new UILinearLayout(UILinearLayout.LAYOUT_HORIZONTAL));
		for (int i = 0; i < 10; i++) {
			p2.add(new UIButton(Integer.toString(i)));
		}
		panel.add(p2);
		panel.add(radioButtonPanel);
		
		UI ui = new UI(theme, panel);
		ui.setHud(false);
		
		ui.addActionListener(btn.getEventTag(), new UIActionListener() {

			@Override
			public void action(UIActionEvent e) {
				btn.setText(btn.getText() + "!");
			}
			
		});
		
		ui.addStateListener(rdbtn1.getEventTag(), new UIStateListener(){

			@Override
			public void stateChanged(UIStateChangedEvent e) {
				if ((Boolean)e.newState) {
					AssetManager.getSound("bakpakin/egf/util/test/beep1.wav").playAsSoundEffect(1f, 1f, false);
					theme.setBodyFontColor(Color.blue);
				} else {
					AssetManager.getSound("bakpakin/egf/util/test/beep2.wav").playAsSoundEffect(1f, 1f, false);
					theme.setBodyFontColor(Color.black);
				}
			}
			
		});
		
		ui.addToRenderSystem(renderSystem);
		
		Runner.mainLoop(world);
	}
	
}

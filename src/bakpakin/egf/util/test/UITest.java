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
import bakpakin.egf.util.render.Background;
import bakpakin.egf.util.render.RenderComponent;
import bakpakin.egf.util.render.RenderSystem;
import bakpakin.egf.util.render.Text;

public class UITest {

	public static void test() throws Exception {
		final World world = new World();
		final RenderSystem renderSystem = new RenderSystem();

		world.addSystem(renderSystem);
		world.addSystem(new SimpleNavigationSystem(renderSystem));
		
		final UITheme theme = UITheme.getDefaultTheme();
		
		final UIButton btn = new UIButton("Press Me!");
		UIRadioButton rdbtn1 = new UIRadioButton("Hello 1!");
		UIRadioButton rdbtn2 = new UIRadioButton("Hello 1!");
		UIRadioButton rdbtn3 = new UIRadioButton("Hello 1!");
		
		UIContainer radioButtonPanel = new UIInvisiContainer(new UILinearLayout(UILinearLayout.LAYOUT_HORIZONTAL));
		radioButtonPanel.add(new UILabel("These are Radio Buttons -->"));
		radioButtonPanel.add(rdbtn1);
		radioButtonPanel.add(rdbtn2);
		radioButtonPanel.add(rdbtn3);
		
		UILabel label = new UILabel("I'm a Label!");
		UIImage image = new UIImage(AssetManager.getTexture("bakpakin/egf/util/test/testplayer.png"));
		image.scale(2);
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
		
		panel.add(new UIButton("Here's some text."));
		
		UIContainer bigPanel = new UIInvisiContainer(new UILinearLayout(UILinearLayout.LAYOUT_HORIZONTAL));
		UIPanel littlePanel = new UIPanel();
		bigPanel.add(panel);
		bigPanel.add(littlePanel);
		bigPanel.setDragable(true);
		
		littlePanel.add(new UILabel("Hi."));
		littlePanel.add(new UILabel("New Line."));
		littlePanel.add(new UILabel("More Text"));
		littlePanel.add(new UILabel("Even More."));
		
		UILabel specialLabel = new UILabel("This is a cool, overriding font.");
		UITheme theme2 = new UITheme(theme);
		theme2.setBodyFontColor(Color.green);
		theme2.setBodyFont(new Text("").getFont());
		specialLabel.setOverrideTheme(theme2);
		littlePanel.add(specialLabel);
				
		UI ui = new UI(theme, bigPanel);
		
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
					theme.setBodyFontColor(Color.white);
				}
			}
			
		});
		
		ui.addToRenderSystem(renderSystem);
		
		world.createEntity(new RenderComponent(new Background("bakpakin/egf/util/test/testgrid.png"), -1000));
		
		Runner.mainLoop(world);
	}
	
}

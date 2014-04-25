package bakpakin.egf.test;

import org.newdawn.slick.Color;

import bakpakin.egf.EGF;
import bakpakin.egf.framework.World;
import bakpakin.egf.gui.UI;
import bakpakin.egf.gui.UIActionEvent;
import bakpakin.egf.gui.UIActionListener;
import bakpakin.egf.gui.UIButton;
import bakpakin.egf.gui.UIContainer;
import bakpakin.egf.gui.UIImage;
import bakpakin.egf.gui.UIInvisiContainer;
import bakpakin.egf.gui.UILabel;
import bakpakin.egf.gui.UILinearLayout;
import bakpakin.egf.gui.UIPanel;
import bakpakin.egf.gui.UIRadioButton;
import bakpakin.egf.gui.UIStateChangedEvent;
import bakpakin.egf.gui.UIStateListener;
import bakpakin.egf.gui.UITheme;
import bakpakin.egf.render.Background;
import bakpakin.egf.render.RenderComponent;
import bakpakin.egf.render.RenderSystem;
import bakpakin.egf.render.Text;
import bakpakin.egf.util.AssetManager;

public class UITest {

	public static void test() throws Exception {
		EGF.init();
		
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
		UIImage image = new UIImage(AssetManager.getTexture("bakpakin/egf/test/testplayer.png"));
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
					AssetManager.getSound("bakpakin/egf/test/beep1.wav").playAsSoundEffect(1f, 1f, false);
					theme.setBodyFontColor(Color.blue);
				} else {
					AssetManager.getSound("bakpakin/egf/test/beep2.wav").playAsSoundEffect(1f, 1f, false);
					theme.setBodyFontColor(Color.white);
				}
			}
			
		});
		
		ui.addToRenderSystem(renderSystem);
		
		world.createEntity(new RenderComponent(new Background("bakpakin/egf/test/testgrid.png"), -1000));
		
		EGF.mainLoop(world);
		
		EGF.cleanUp();
	}
	
}

package bakpakin.ld29;

import bakpakin.egf.EGF;
import bakpakin.egf.gui.*;
import bakpakin.egf.util.AssetManager;

public class MenuUI extends UI {
	
	public static MenuUI makeUI() {
		UITheme theme = new MyTheme();
		UIPanel panel = new UIPanel();
		panel.add(new UIButton("Start"));
		panel.add(new UIButton("How to Play"));
		panel.add(new UIButton("Quit"));		
		UIInvisiContainer cont1 = new UIInvisiContainer();
		cont1.add(new UIImage(AssetManager.getTexture("res/title.png")));
		cont1.add(panel);
		UIInvisiContainer cont = new UIInvisiContainer(new UISingleElementLayout(cont1));
		cont.setWidth(EGF.getDisplayWidth());
		cont.setHeight(EGF.getDisplayHeight());
		MenuUI ui = new MenuUI(theme, cont);
		ui.addActionListener("Start", new StartListener());
		ui.addActionListener("How to Play", new InstructionsListener());
		ui.addActionListener("Quit", new QuitListener());
		return ui;
	}

	private MenuUI(UITheme theme, UIElement root) {
		super(theme, root);
	}
	
	private static class StartListener implements UIActionListener {

		@Override
		public void action(UIActionEvent e) {
			AssetManager.getSound("res/blip.wav").playAsSoundEffect(1f, 1f, false);
			EGF.setScene(new OceanScene("res/lvl1.json"));
		}
		
	}
	
	private static class QuitListener implements UIActionListener {

		@Override
		public void action(UIActionEvent e) {
			AssetManager.getSound("res/blip.wav").playAsSoundEffect(1f, 1f, false);
			EGF.endGame();
		}
		
	}
	
	private static class InstructionsListener implements UIActionListener {

		@Override
		public void action(UIActionEvent e) {
			AssetManager.getSound("res/blip.wav").playAsSoundEffect(1f, 1f, false);
			EGF.setScene(new InstructionsScene());
		}
		
	}


}

package bakpakin.ld29;

import bakpakin.egf.EGF;
import bakpakin.egf.gui.*;
import bakpakin.egf.util.AssetManager;

public class DeathUI extends UI {
	
	public static DeathUI makeUI() {
		UITheme theme = new MyTheme();
		UIPanel panel = new UIPanel();
		panel.add(new UILabel("You Died. Tragic."));
		panel.add(new UIButton("Play Again?"));
		panel.add(new UIButton("Menu"));
		panel.add(new UIButton("Quit"));
		UIInvisiContainer cont = new UIInvisiContainer(new UISingleElementLayout(panel));
		cont.setWidth(EGF.getDisplayWidth());
		cont.setHeight(EGF.getDisplayHeight());
		DeathUI ui = new DeathUI(theme, cont);
		ui.addActionListener("Play Again?", new ResumeListener());
		ui.addActionListener("Menu", new MenuListener());
		ui.addActionListener("Quit", new QuitListener());
		return ui;
	}

	private DeathUI(UITheme theme, UIElement root) {
		super(theme, root);
	}
	
	private static class ResumeListener implements UIActionListener {

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

}

package bakpakin.ld29;

import bakpakin.egf.EGF;
import bakpakin.egf.gui.*;
import bakpakin.egf.util.AssetManager;

public class PauseUI extends UI {
	
	public static PauseUI makeUI() {
		UITheme theme = new MyTheme();
		UIPanel panel = new UIPanel();
		panel.add(new UILabel("Paused"));
		panel.add(new UIButton("Resume"));
		panel.add(new UIButton("Quit"));
		UIInvisiContainer cont = new UIInvisiContainer(new UISingleElementLayout(panel));
		cont.setWidth(EGF.getDisplayWidth());
		cont.setHeight(EGF.getDisplayHeight());
		PauseUI ui = new PauseUI(theme, cont);
		ui.addActionListener("Resume", new ResumeListener());
		ui.addActionListener("Quit", new QuitListener());
		return ui;
	}

	private PauseUI(UITheme theme, UIElement root) {
		super(theme, root);
	}
	
	private static class ResumeListener implements UIActionListener {

		@Override
		public void action(UIActionEvent e) {
			AssetManager.getSound("res/blip.wav").playAsSoundEffect(1f, 1f, false);
			((OceanScene)EGF.getScene()).pause(false);
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

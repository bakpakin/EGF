package bakpakin.ld29;

import bakpakin.egf.EGF;
import bakpakin.egf.gui.*;
import bakpakin.egf.util.AssetManager;

public class InstructionsUI extends UI {
	
	public static InstructionsUI makeUI() {
		UITheme theme = new MyTheme();
		UIPanel panel = new UIPanel();
		panel.add(new UILabel("***HOW TO PLAY***"));
		panel.add(new UILabel(""));		
		panel.add(new UILabel("Goal:"));
		panel.add(new UILabel("Find the sunken treasure at the bottom of the ocean."));		
		panel.add(new UILabel(""));		
		panel.add(new UILabel("Controls:"));		
		panel.add(new UILabel("WASD or arrow keys to move, press Space near the boat to buy things."));		
		panel.add(new UILabel(""));		
		panel.add(new UILabel("Avoid fish and jelly fish. They hurt you."));		
		panel.add(new UILabel("Collect coins to buy things at the store."));
		panel.add(new UILabel(""));		
		panel.add(new UILabel("Store:"));		
		panel.add(new UILabel("First Aid packs give you back health."));		
		panel.add(new UILabel("Scuba Tanks allow you to stay under water longer. Buy lots of them."));		
		panel.add(new UILabel(""));		
		panel.add(new UILabel("Don't drown. If you oxygen's low, go back to the surface ASAP."));	
		panel.add(new UILabel(""));		
		panel.add(new UIButton("Back"));		
		UIInvisiContainer cont = new UIInvisiContainer(new UISingleElementLayout(panel));
		cont.setWidth(EGF.getDisplayWidth());
		cont.setHeight(EGF.getDisplayHeight());
		InstructionsUI ui = new InstructionsUI(theme, cont);
		ui.addActionListener("Back", new BackListener());
		return ui;
	}

	private InstructionsUI(UITheme theme, UIElement root) {
		super(theme, root);
	}
	
	private static class BackListener implements UIActionListener {

		@Override
		public void action(UIActionEvent e) {
			AssetManager.getSound("res/blip.wav").playAsSoundEffect(1f, 1f, false);
			EGF.setScene(new MenuScene());
			try {
				Thread.sleep(500);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		
	}

}

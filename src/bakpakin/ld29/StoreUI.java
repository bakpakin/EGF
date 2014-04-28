package bakpakin.ld29;

import bakpakin.egf.EGF;
import bakpakin.egf.framework.Entity;
import bakpakin.egf.gui.*;
import bakpakin.egf.util.AssetManager;

public class StoreUI extends UI {

	public static StoreUI makeUI() {
		UITheme theme = new MyTheme();
		UIPanel panel = new UIPanel();
		panel.add(new UILabel("Boat Shop"));

		String bt1 = "First Aid: 5 Coins";
		String bt2 = "Scuba Tank: 10 Coins";

		UIContainer firstAidContainer = new UIInvisiContainer(new UILinearLayout(UILinearLayout.LAYOUT_HORIZONTAL));
		firstAidContainer.add(new UIImage(AssetManager.getTexture("res/firstaid.png")));
		firstAidContainer.add(new UIButton(bt1));

		UIContainer tankContainer = new UIInvisiContainer(new UILinearLayout(UILinearLayout.LAYOUT_HORIZONTAL));
		tankContainer.add(new UIImage(AssetManager.getTexture("res/tank.png")));
		tankContainer.add(new UIButton(bt2));

		panel.add(firstAidContainer);
		panel.add(tankContainer);
		panel.add(new UIButton("Exit"));

		UIInvisiContainer cont = new UIInvisiContainer(new UISingleElementLayout(panel));
		cont.setWidth(EGF.getDisplayWidth());
		cont.setHeight(EGF.getDisplayHeight());
		StoreUI ui = new StoreUI(theme, cont);
		ui.addActionListener(bt1, new FAListener());
		ui.addActionListener(bt2, new TAListener());
		ui.addActionListener("Exit", new UIActionListener() {

			@Override
			public void action(UIActionEvent e) {
				AssetManager.getSound("res/blip.wav").playAsSoundEffect(1f, 1f, false);
				((OceanScene) EGF.getScene()).hideStore();
			}

		});
		return ui;
	}

	private StoreUI(UITheme theme, UIElement root) {
		super(theme, root);
	}

	private static class FAListener implements UIActionListener {

		@Override
		public void action(UIActionEvent e) {
			OceanScene os = ((OceanScene) EGF.getScene());
			Entity swimmer = os.getSwimmer();
			if (os.coins >= 5) {
				AssetManager.getSound("res/blip.wav").playAsSoundEffect(1f, 1f, false);
				int health = (Integer)swimmer.getProperty("Health");
				swimmer.setProperty("Health", health + 1);
				os.coins -= 5;
				os.getCoinText().setText("Coins: " + os.coins);			
			} else {
				AssetManager.getSound("res/no.wav").playAsSoundEffect(1f, 1f, false);
			}
		}

	}

	private static class TAListener implements UIActionListener {

		@Override
		public void action(UIActionEvent e) {
			OceanScene os = ((OceanScene) EGF.getScene());
			Entity swimmer = os.getSwimmer();
			if (os.coins >= 10) {
				AssetManager.getSound("res/blip.wav").playAsSoundEffect(1f, 1f, false);
				int tpa = (Integer)swimmer.getProperty("TimePerAirBubble");
				swimmer.setProperty("TimePerAirBubble", tpa + 2);
				os.coins -= 10;
				os.getCoinText().setText("Coins: " + os.coins);			
			} else {
				AssetManager.getSound("res/no.wav").playAsSoundEffect(1f, 1f, false);
			}

		}

	}

}

package bakpakin.ld29;

import bakpakin.egf.EGF;
import bakpakin.egf.gui.UIActionEvent;
import bakpakin.egf.gui.UIActionListener;
import bakpakin.egf.util.AssetManager;

public class MenuListener implements UIActionListener {

	@Override
	public void action(UIActionEvent e) {
		AssetManager.getSound("res/blip.wav").playAsSoundEffect(1f, 1f, false);
		EGF.setScene(new MenuScene());
	}

}

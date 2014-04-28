package bakpakin.ld29;

import bakpakin.egf.gui.NineBox;
import bakpakin.egf.gui.UITheme;
import bakpakin.egf.util.AssetManager;

public class MyTheme extends UITheme {

	public MyTheme() {
		this.setParent(UITheme.getDefaultTheme());
		this.setBodyFont(AssetManager.getFont("res/gilligansisland.ttf", 22));
		this.setButton(new NineBox(AssetManager.getTexture("res/button.png")));
		this.setButtonHover(new NineBox(AssetManager.getTexture("res/buttonhover.png")));
		this.setButtonPressed(new NineBox(AssetManager.getTexture("res/buttonhover.png")));
		this.setContainer(new NineBox(AssetManager.getTexture("res/panel.png")));
	}

}

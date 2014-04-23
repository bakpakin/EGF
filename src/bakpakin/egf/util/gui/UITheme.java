
package bakpakin.egf.util.gui;

import org.newdawn.slick.Color;
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;

import bakpakin.egf.util.AssetManager;

public class UITheme {

	private static final String defaultLoc = "bakpakin/egf/util/gui/defaulttheme/";

	public static UITheme getDefaultTheme() {
		UITheme defaultTheme = new UITheme();
		defaultTheme.setBodyFont(AssetManager.getFont(defaultLoc + "Helvetica.ttf", 26f));
		defaultTheme.setTitleFont(AssetManager.getFont(defaultLoc + "Helvetica.ttf", 32f));
		Texture ct = AssetManager.getTexture(defaultLoc + "container.png");
		Texture b = AssetManager.getTexture(defaultLoc + "button.png");
		Texture bp = AssetManager.getTexture(defaultLoc + "buttonpressed.png");
		Texture bh = AssetManager.getTexture(defaultLoc + "buttonhover.png");
		Texture rb = AssetManager.getTexture(defaultLoc + "radiobuttonunselected.png");
		Texture rbs = AssetManager.getTexture(defaultLoc + "radiobuttonselected.png");
		NineBox button = new NineBox(b);
		NineBox buttonHover = new NineBox(bh);
		NineBox buttonPressed = new NineBox(bp);
		NineBox c = new NineBox(ct);
		defaultTheme.setButton(button);
		defaultTheme.setButtonHover(buttonHover);
		defaultTheme.setButtonPressed(buttonPressed);
		defaultTheme.setRadioButtonSelected(rbs);
		defaultTheme.setRadioButtonUnselected(rb);
		defaultTheme.setContainer(c);
		return defaultTheme;
	}

	private NineBox container;
	private NineBox button;
	private NineBox buttonHover;
	private NineBox buttonPressed;
	private NineBox slider;
	private Texture sliderHandle;
	private Texture radioButtonUnselected;
	private Texture radioButtonSelected;
	private TrueTypeFont bodyFont;
	private TrueTypeFont titleFont;
	
	private Color bodyFontColor = Color.black;
	private Color titleFontColor = Color.black;

	private Map<String, Texture> icons;

	public UITheme() {
		this.icons = new HashMap<String, Texture>();
	}

	public NineBox getContainer() {
		return container;
	}

	public void setContainer(NineBox container) {
		this.container = container;
	}

	public NineBox getButton() {
		return button;
	}

	public void setButton(NineBox button) {
		this.button = button;
	}

	public NineBox getButtonHover() {
		return buttonHover;
	}

	public void setButtonHover(NineBox buttonHover) {
		this.buttonHover = buttonHover;
	}

	public NineBox getButtonPressed() {
		return buttonPressed;
	}

	public void setButtonPressed(NineBox buttonPressed) {
		this.buttonPressed = buttonPressed;
	}

	public NineBox getSlider() {
		return slider;
	}

	public void setSlider(NineBox slider) {
		this.slider = slider;
	}

	public Texture getSliderHandle() {
		return sliderHandle;
	}

	public void setSliderHandle(Texture sliderHandle) {
		this.sliderHandle = sliderHandle;
	}

	public Texture getRadioButtonUnselected() {
		return radioButtonUnselected;
	}

	public void setRadioButtonUnselected(Texture radioButtonUnselected) {
		this.radioButtonUnselected = radioButtonUnselected;
	}

	public Texture getRadioButtonSelected() {
		return radioButtonSelected;
	}

	public void setRadioButtonSelected(Texture radioButtonSelected) {
		this.radioButtonSelected = radioButtonSelected;
	}

	public TrueTypeFont getBodyFont() {
		return bodyFont;
	}

	public void setBodyFont(TrueTypeFont bodyFont) {
		this.bodyFont = bodyFont;
	}

	public TrueTypeFont getTitleFont() {
		return titleFont;
	}

	public void setTitleFont(TrueTypeFont titleFont) {
		this.titleFont = titleFont;
	}

	public Map<String, Texture> getIcons() {
		return icons;
	}

	public void setIcons(Map<String, Texture> icons) {
		this.icons = icons;
	}

	public Color getTitleFontColor() {
		return titleFontColor;
	}

	public void setTitleFontColor(Color titleFontColor) {
		this.titleFontColor = titleFontColor;
	}

	public Color getBodyFontColor() {
		return bodyFontColor;
	}

	public void setBodyFontColor(Color bodyFontColor) {
		this.bodyFontColor = bodyFontColor;
	}

}

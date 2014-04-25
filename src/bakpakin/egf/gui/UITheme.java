
package bakpakin.egf.gui;

import org.newdawn.slick.Color;
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;

import bakpakin.egf.util.AssetManager;

public class UITheme {

	private static final String defaultLoc = "bakpakin/egf/gui/defaulttheme/";

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
		c.setContentX1(30);
		c.setContentX2(98);
		c.setContentY1(30);
		c.setContentY2(98);
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
	
	private Color bodyFontColor = Color.white;
	private Color titleFontColor = Color.lightGray;
	
	private UITheme parent;

	private Map<String, Texture> icons;

	public UITheme() {
		this.icons = new HashMap<String, Texture>();
	}
	
	public UITheme(UITheme parent) {
		this();
		setParent(parent);
	}

	public NineBox getContainer() {
		if (parent != null && container == null)
			return parent.getContainer();
		return container;
	}

	public void setContainer(NineBox container) {
		this.container = container;
	}

	public NineBox getButton() {
		if (parent != null && button == null)
			return parent.getButton();
		return button;
	}

	public void setButton(NineBox button) {
		this.button = button;
	}

	public NineBox getButtonHover() {
		if (parent != null && buttonHover == null)
			return parent.getButtonHover();
		return buttonHover;
	}

	public void setButtonHover(NineBox buttonHover) {
		this.buttonHover = buttonHover;
	}

	public NineBox getButtonPressed() {
		if (parent != null && buttonPressed == null)
			return parent.getButtonPressed();
		return buttonPressed;
	}

	public void setButtonPressed(NineBox buttonPressed) {
		this.buttonPressed = buttonPressed;
	}

	public NineBox getSlider() {
		if (parent != null && slider == null)
			return parent.getSlider();
		return slider;
	}

	public void setSlider(NineBox slider) {
		this.slider = slider;
	}

	public Texture getSliderHandle() {
		if (parent != null && sliderHandle == null)
			return parent.getSliderHandle();
		return sliderHandle;
	}

	public void setSliderHandle(Texture sliderHandle) {
		this.sliderHandle = sliderHandle;
	}

	public Texture getRadioButtonUnselected() {
		if (parent != null && radioButtonUnselected == null)
			return parent.getRadioButtonUnselected();
		return radioButtonUnselected;
	}

	public void setRadioButtonUnselected(Texture radioButtonUnselected) {
		this.radioButtonUnselected = radioButtonUnselected;
	}

	public Texture getRadioButtonSelected() {
		if (parent != null && radioButtonSelected == null)
			return parent.getRadioButtonSelected();
		return radioButtonSelected;
	}

	public void setRadioButtonSelected(Texture radioButtonSelected) {
		this.radioButtonSelected = radioButtonSelected;
	}

	public TrueTypeFont getBodyFont() {
		if (parent != null && bodyFont == null)
			return parent.getBodyFont();
		return bodyFont;
	}

	public void setBodyFont(TrueTypeFont bodyFont) {
		this.bodyFont = bodyFont;
	}

	public TrueTypeFont getTitleFont() {
		if (parent != null && titleFont == null)
			return parent.getTitleFont();
		return titleFont;
	}

	public void setTitleFont(TrueTypeFont titleFont) {
		this.titleFont = titleFont;
	}

	public Map<String, Texture> getIcons() {
		if (parent != null && icons == null)
			return parent.getIcons();
		return icons;
	}

	public void setIcons(Map<String, Texture> icons) {
		this.icons = icons;
	}

	public Color getTitleFontColor() {
		if (parent != null && titleFontColor == null)
			return parent.getTitleFontColor();
		return titleFontColor;
	}

	public void setTitleFontColor(Color titleFontColor) {
		this.titleFontColor = titleFontColor;
	}

	public Color getBodyFontColor() {
		if (parent != null && bodyFontColor == null)
			return parent.getBodyFontColor();
		return bodyFontColor;
	}

	public void setBodyFontColor(Color bodyFontColor) {
		this.bodyFontColor = bodyFontColor;
	}

	public UITheme getParent() {
		return parent;
	}

	public void setParent(UITheme parent) {
		this.parent = parent;
	}

}

package bakpakin.egf.util.gui;

import bakpakin.egf.util.geom.AxisAlignedBox;

public abstract class UIElement {

	float x, y;
		
	float localX, localY;
	
	private boolean inheritTheme;
	
	UIContainer parent;
	
	UI ui;
	
	public UIElement() {
	}
	
	public UIElement(UIContainer parent) {
		this.parent = parent;
		this.ui = parent.ui;
	}
	
	public UIContainer getParent() {
		return parent;
	}
	
	public abstract float getWidth();
	
	public abstract float getHeight();
	
	public UI getUi() {
		return ui;
	}

	public void setUi(UI ui) {
		this.ui = ui;
	}

	/**
	 * All {E+@link UIElement}s should implement this method assuming
	 * that their top left-hand corner has been positioned at the origin
	 * of the openGl context. 
	 */
	public abstract void paint();
	
	public abstract void update();

	public boolean isInheritTheme() {
		return inheritTheme;
	}

	public void setInheritTheme(boolean inheritTheme) {
		this.inheritTheme = inheritTheme;
	}
	
	public UITheme getTheme() {
		if (ui != null)
			return ui.getTheme();
		return null;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getLocalX() {
		return localX;
	}

	public float getLocalY() {
		return localY;
	}
	
	public AxisAlignedBox getBoundingBox() {
		return new AxisAlignedBox(getX(), getY(), getX() + getWidth(), getY() + getHeight());
	}
}

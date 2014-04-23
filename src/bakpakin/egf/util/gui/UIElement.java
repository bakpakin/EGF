package bakpakin.egf.util.gui;

import bakpakin.egf.util.geom.AxisAlignedBox;

public abstract class UIElement {

	int x, y;
		
	private boolean inheritTheme = true;
	
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
	
	public abstract int getWidth();
	
	public abstract int getHeight();
	
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

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public AxisAlignedBox getBoundingBox() {
		return new AxisAlignedBox(getX(), getY(), getX() + getWidth(), getY() + getHeight());
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}
}

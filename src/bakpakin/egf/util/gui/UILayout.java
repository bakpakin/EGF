package bakpakin.egf.util.gui;

import java.util.Collection;

public abstract class UILayout {
	
	UIContainer container;
	
	protected int width;
	
	protected int height;
	
	protected UIContainer root;
	
	public void layout() {
		for (UIElement e : getElements()) {
			if (e instanceof UIContainer)
				((UIContainer)e).update();
		}
	}

	public abstract void myLayout();
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public UIContainer getRoot() {
		return root;
	}
	
	public abstract void add(UIElement e, Object... args);

	public abstract Collection<UIElement> getElements();

}

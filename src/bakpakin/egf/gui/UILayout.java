package bakpakin.egf.gui;

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
		myLayout();
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
	
	public void setRoot(UIContainer root) {
		if (this.root != null && this.root != root) {
			reset();
		}
 		this.root = root;
		root.setLayout(this);
	}
	
	public abstract void reset();
	
	public abstract void add(UIElement e, Object... args);
	
	public void add(UIElement e) {
		add(e, new Object[0]);
	}

	public abstract Collection<UIElement> getElements();

}

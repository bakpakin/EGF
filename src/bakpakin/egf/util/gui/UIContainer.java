package bakpakin.egf.util.gui;

import org.newdawn.slick.Color;
import java.util.Collection;

import org.lwjgl.opengl.GL11;

public abstract class UIContainer extends UIElement {
		
	private UILayout layout;
	
	int width, height;
	
	public UIContainer() {
		this(new UILinearLayout(UILinearLayout.LAYOUT_VERTICAL));
	}
	
	public UIContainer(UILayout layout) {
		setLayout(layout);
	}
	
	@Override
	public void paint() {
		paintSelf();
		for (UIElement e : layout.getElements()) {
			GL11.glTranslatef(e.getX()-getX(), e.getY()-getY(), 0);
			e.paint();			
			GL11.glTranslatef(-e.getX()+getX(), -e.getY()+getY(), 0);
			Color.white.bind();
		}
	}
	
	public Collection<UIElement> getChildren() {
		return layout.getElements();
	}
	
	public void update() {
		for (UIElement e : getChildren())
			e.update();
		layout.layout();
	}
	
	public void add(UIElement e) {
		layout.add(e);
	}
	
	@Override
	public void setUi(UI ui) {
		super.setUi(ui);
		for (UIElement e : getChildren()) {
			if (e.isInheritTheme())
				e.setUi(ui);
		}
	}
	
	public abstract int getContentXOffset();
	
	public abstract int getContentYOffset();
	
	public abstract void setContentWidth(int width);
	
	public abstract void setContentHeight(int height);
	
	@Override
	public int getWidth() {
		return width;
	}
	
	@Override
	public int getHeight() {
		return height;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public abstract void paintSelf();

	/**
	 * @return the layout
	 */
	public UILayout getLayout() {
		return layout;
	}

	/**
	 * @param layout the layout to set
	 */
	public void setLayout(UILayout layout) {
		this.layout = layout;
		if (layout.getRoot() != this)
			layout.setRoot(this);
	}

}

package bakpakin.egf.util.gui;

import org.newdawn.slick.Color;

import java.util.Collection;
import java.util.Collections;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

public abstract class UIContainer extends UIElement {
		
	private UILayout layout;
	
	private boolean wasMouseDown;
	private boolean dragable;
	private float mouseXOffset;
	private float mouseYOffset;
	
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
		if (layout == null)
			return Collections.emptySet();
		return layout.getElements();
	}
	
	public void update() {
		if (dragable)
			handleDragging();
		for (UIElement e : getChildren())
			e.update();
		layout.layout();
	}
	
	private void handleDragging() {
		if (Mouse.isButtonDown(0)) {
			Vector2f m = getMouse();
			if (!wasMouseDown) {
				mouseXOffset = getX() - m.x;
				mouseYOffset = getY() - m.y;
			}
			setX((int) (m.x + mouseXOffset));
			setY((int) (m.y + mouseYOffset));
		}
		this.wasMouseDown = Mouse.isButtonDown(0);
	}

	public void add(UIElement e) {
		layout.add(e);
	}
	
	@Override
	public void setUi(UI ui) {
		super.setUi(ui);
		for (UIElement e : getChildren()) {
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

	public boolean isDragable() {
		return dragable;
	}

	public void setDragable(boolean dragable) {
		this.dragable = dragable;
	}

}

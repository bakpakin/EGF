package bakpakin.egf.gui;

import java.util.Collection;
import java.util.LinkedList;

public class UISingleElementLayout extends UILayout {
	
	private UIElement element;
	
	private float alignX, alignY;

	public UISingleElementLayout(UIElement element) {
		this(element, .5f, .5f);
	}
	
	public UISingleElementLayout(UIElement element, float alignX, float alignY) {
		setElement(element);
		setAlignX(alignX);
		setAlignY(alignY);
	}

	@Override
	public void myLayout() {
		if (element.getWidth() > root.getContentWidth()) {
			root.setContentWidth(element.getWidth());
		}
		if (element.getHeight() > root.getContentHeight()) {
			root.setContentHeight(element.getHeight());
		}
		float minX = root.getX() + root.getContentXOffset();
		float maxX = minX + root.getContentWidth() - element.getWidth();
		element.setX((int) (maxX*alignX + minX*(1-alignX)));
		float minY = root.getY() + root.getContentYOffset();
		float maxY = minY + root.getContentHeight() - element.getHeight();
		element.setY((int) (maxY*alignY + minY*(1-alignY)));
	}

	@Override
	public void reset() {
		element = null;
	}

	@Override
	public void add(UIElement e, Object... args) {
		setElement(e);
	}

	@Override
	public Collection<UIElement> getElements() {
		Collection<UIElement> ret = new LinkedList<UIElement>();
		ret.add(element);
		return ret;
	}

	public UIElement getElement() {
		return element;
	}

	public void setElement(UIElement element) {
		this.element = element;
	}

	public float getAlignX() {
		return alignX;
	}

	public void setAlignX(float alignX) {
		this.alignX = alignX;
	}

	public float getAlignY() {
		return alignY;
	}

	public void setAlignY(float alignY) {
		this.alignY = alignY;
	}

}

package bakpakin.egf.util.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UILinearLayout extends UILayout {

	public static final int LAYOUT_VERTICAL = 0;
	public static final int LAYOUT_HORIZONTAL = 1;
	
	public static final int ALIGN_TOP = 2;
	public static final int ALIGN_CENTER = 3;
	public static final int ALIGN_BOTTOM = 4;
	public static final int ALIGN_LEFT = 2;
	public static final int ALIGN_RIGHT = 4; 

	private int orientation;
	
	private int alignment = ALIGN_CENTER;

	private ArrayList<UIElement> elements;

	private int xSeperation = 5, ySeperation = 5;

	public UILinearLayout(int orientation) {
		this.setOrientation(orientation);
		this.elements = new ArrayList<UIElement>();
	}

	@Override
	public void myLayout() {
		int x1 = root.getX() + root.getContentXOffset();
		int y1 = root.getY() + root.getContentYOffset();
		int xpos = x1 + xSeperation;
		int ypos = y1 + ySeperation;
		//calculate size of container
		for (UIElement e : elements) {
			if (orientation == LAYOUT_VERTICAL) {
				e.setY(ypos);
				ypos += e.getHeight() + ySeperation;
				xpos = xpos > x1 + e.getWidth() ? xpos : x1 + e.getWidth();
			} else {
				e.setX(xpos);
				xpos += e.getWidth() + xSeperation;
				ypos = ypos > y1 + e.getHeight() ? ypos : y1 + e.getHeight();
			}
		}
		
		//align elements
		for (UIElement e : elements) {
			if (orientation == LAYOUT_VERTICAL) {
				e.setX(calcAlignment(x1, xpos, e.getWidth()));
			} else {
				e.setY(calcAlignment(y1, ypos, e.getHeight()));
			}
		}
		
		if (orientation == LAYOUT_VERTICAL) {
			root.setContentWidth(2 * xSeperation + xpos - x1);
			root.setContentHeight(ypos - y1);
		} else {
			root.setContentWidth(xpos - x1);
			root.setContentHeight(2 * ySeperation + ypos - y1);
		}
	}

	private int calcAlignment(int xMin, int xMax, int width) {
		switch (alignment) {
		case ALIGN_TOP:
			return xMin;
		case ALIGN_BOTTOM:
			return xMax - width;
		case ALIGN_CENTER:
		default:
			return (xMin + xMax - width) / 2;
		}
	}

	@Override
	public void add(UIElement e, Object... args) {
		elements.add(e);
	}

	public int getOrientation() {
		return orientation;
	}

	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}

	public int getxSeperation() {
		return xSeperation;
	}

	public void setxSeperation(int xSeperation) {
		this.xSeperation = xSeperation;
	}

	public int getySeperation() {
		return ySeperation;
	}

	public void setySeperation(int ySeperation) {
		this.ySeperation = ySeperation;
	}

	@Override
	public List<UIElement> getElements() {
		return Collections.unmodifiableList(elements);
	}

	@Override
	public void reset() {
		elements.clear();
	}

	public int getAlignment() {
		return alignment;
	}

	public void setAlignment(int alignment) {
		this.alignment = alignment;
	}

}

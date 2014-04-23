package bakpakin.egf.util.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UILinearLayout extends UILayout {

	public static final int LAYOUT_VERTICAL = 0;
	public static final int LAYOUT_HORIZONTAL = 1;

	private int orientation;

	private ArrayList<UIElement> elements;

	private int xSeperation = 5, ySeperation = 5;

	public UILinearLayout(int orientation) {
		this.setOrientation(orientation);
		this.elements = new ArrayList<UIElement>();
	}

	@Override
	public void myLayout() {
		int x1 = root.getX();
		int y1 = root.getY();
		int xpos = x1 + xSeperation;
		int ypos = y1 + ySeperation;
		int max = 0;
		for (UIElement e : elements) {
			e.setX(xpos);
			e.setY(ypos);
			if (orientation == LAYOUT_VERTICAL) {
				ypos += e.getHeight() + ySeperation;
				max = max > e.getWidth() ? max : e.getWidth();
			} else {
				xpos += e.getWidth() + xSeperation;
				max = max > e.getHeight() ? max : e.getHeight();
			}
		}
		if (orientation == LAYOUT_VERTICAL) {
			root.setContentWidth(2 * xSeperation + max);
			root.setContentHeight(ypos - y1);
		} else {
			root.setContentWidth(xpos - x1);
			root.setContentHeight(2 * ySeperation + max);
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

}

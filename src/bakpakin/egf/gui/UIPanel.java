package bakpakin.egf.gui;

import org.newdawn.slick.Color;

public class UIPanel extends UIContainer {

	public UIPanel() {
		super();
	}
	
	public UIPanel(UILayout layout) {
		super(layout);
	}

	@Override
	public void paintSelf() {
		Color.white.bind();
		NineBox nb = getTheme().getContainer();
		nb.draw(0, 0, getWidth(), getHeight());
	}

	@Override
	public void setContentWidth(int width) {
		NineBox nb = getTheme().getContainer();
		setWidth(nb.getWidth(width));
	}

	@Override
	public void setContentHeight(int height) {
		NineBox nb = getTheme().getContainer();
		setHeight(nb.getHeight(height));
	}

	@Override
	public int getContentXOffset() {
		NineBox nb = getTheme().getContainer();
		return nb.getContentX1();
	}

	@Override
	public int getContentYOffset() {
		NineBox nb = getTheme().getContainer();
		return nb.getContentY1();
	}
	
}

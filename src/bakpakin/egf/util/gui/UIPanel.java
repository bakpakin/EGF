package bakpakin.egf.util.gui;

public class UIPanel extends UIContainer {

	public UIPanel(UILayout layout) {
		super(layout);
	}

	@Override
	public void paintSelf() {
		NineBox nb = getTheme().getContainer();
		nb.draw(getX(), getY(), getX() + getWidth(), getY() + getHeight());
	}

	@Override
	void setContentWidth(int width) {
		
	}

	@Override
	void setContentHeight(int height) {
		// TODO Auto-generated method stub
		
	}
	
}

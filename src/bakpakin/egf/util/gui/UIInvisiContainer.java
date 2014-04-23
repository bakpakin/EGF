package bakpakin.egf.util.gui;

public class UIInvisiContainer extends UIContainer {
		
	private int xBorder, yBorder;
	
	public UIInvisiContainer() {
		this(new UILinearLayout(UILinearLayout.LAYOUT_VERTICAL));
	}
	
	public UIInvisiContainer(UILayout layout) {
		this(layout, 0, 0);
	}
	
	public UIInvisiContainer(UILayout layout, int xBorder, int yBorder) {
		super(layout);
		this.xBorder = xBorder;
		this.yBorder = yBorder;
	}

	@Override
	public int getContentXOffset() {
		return xBorder;
	}

	@Override
	public int getContentYOffset() {
		return yBorder;
	}

	@Override
	public void setContentWidth(int width) {
		this.setWidth(width + (2 * xBorder));
	}

	@Override
	public void setContentHeight(int height) {
		this.setWidth(height + (2 * yBorder));		
	}

	@Override
	public void paintSelf() {
		//DO NOTHING
	}

	public int getxBorder() {
		return xBorder;
	}

	public void setxBorder(int xBorder) {
		this.xBorder = xBorder;
	}

	public int getyBorder() {
		return yBorder;
	}

	public void setyBorder(int yBorder) {
		this.yBorder = yBorder;
	}

}

package bakpakin.egf.util.gui;

public class UISpacer extends UIElement {
	
	private int width, height;
	
	public UISpacer(int width, int height) {
		super();
		this.setWidth(width);
		this.setHeight(height);
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public void paint() {
		//DO NOTHING
	}

	@Override
	public void update() {
		//DO NOTHING
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}

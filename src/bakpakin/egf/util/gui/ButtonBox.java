package bakpakin.egf.util.gui;

import bakpakin.egf.framework.Component;

public class ButtonBox implements Component {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4212805487904547512L;
	
	private float width;
	private float height;
	
	private boolean mouseOver;
	
	public ButtonBox(float width, float height) {
		this.width = width;
		this.height = height;
	}
	
	public float getHeight() {
		return height;
	}
	
	public void setHeight(float height) {
		this.height = height;
	}
	
	public float getWidth() {
		return width;
	}
	
	public void setWidth(float width) {
		this.width = width;
	}
	
	public bakpakin.egf.util.geom.AxisAlignedBox getHitbox(bakpakin.egf.util.geom.Transform t) {
		float hh = height * .5f;
		float hw = width * .5f;
		return new bakpakin.egf.util.geom.AxisAlignedBox(t.getX() - hw, t.getY() - hh, t.getX() + hw, t.getY() + hh);
	}

	public boolean isMouseOver() {
		return mouseOver;
	}

	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}
	
}

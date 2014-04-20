/**
 * 
 */
package bakpakin.egf.util.physics;

import bakpakin.egf.framework.Component;
import bakpakin.egf.util.geom.AxisAlignedBox;
import bakpakin.egf.util.geom.Transform;

/**
 * @author Calvin
 *
 */
public class BoxCollider implements Component {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2155213930922198585L;
	private float width, height;

	/**
	 * 
	 */
	public BoxCollider(float width, float height) {
		setWidth(width);
		setHeight(height);
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}
	
	public AxisAlignedBox getHitbox(Transform t) {
		final double angle = t.getAngle()/180*Math.PI;
		final float hw = (float) (width*t.getXScale()*Math.cos(angle) + (height*t.getYScale()*Math.sin(angle))) * .5f;
		final float hh = (float) (width*t.getXScale()*Math.sin(angle) + (height*t.getYScale()*Math.cos(angle))) * .5f;
		final float xx = t.getX();
		final float yy = t.getY();
		return new AxisAlignedBox(xx - hw, yy - hh, xx + hw, yy + hh);
	}

}

package bakpakin.egf.util.geom;

import org.lwjgl.util.vector.Vector2f;

public class Ellipse implements Shape, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2359961034696244896L;
	
	protected final float x, y, xradius, yradius, angle;
	protected final float cos, sin;

	/**
	 * 
	 * @param x
	 * @param y
	 * @param xradius
	 * @param yradius
	 * @param angle
	 */
	public Ellipse(float x, float y, float xradius, float yradius, float angle) {
		this.x = x;
		this.y = y;
		this.xradius = xradius;
		this.yradius = yradius;
		this.angle = angle;
		final double ang = (Math.PI / 180) * angle;
		cos = (float)Math.cos(ang);
		sin = (float)Math.sin(ang);
	}

	@Override
	public float getOriginX() {
		return x;
	}

	@Override
	public float getOriginY() {
		return y;
	}

	/**
	 * 
	 * @return
	 */
	public float getXradius() {
		return xradius;
	}
	
	/**
	 * 
	 * @return
	 */
	public float getYradius() {
		return yradius;
	}

	@Override
	public float getRotation() {
		return angle;
	}

	@Override
	public AxisAlignedBox getAxisAlignedBox() {
		final float xx = xradius * cos;
		final float xy = xradius * sin;
		final float yx = yradius * sin;
		final float yy = yradius * cos;
		float hw = (float)Math.sqrt(xx*xx + yx*yx);
		float hh = (float)Math.sqrt(xy*xy + yy*yy);
		return new AxisAlignedBox(x - hw, y - hh, x + hw, y + hh);
	}

	@Override
	public AxisAlignedBox getAxisAlignedBox(Transform t) {
		return transformLocal(t).getAxisAlignedBox();
	}

	@Override
	public Shape transformLocal(Transform t) {
		return new Ellipse(
				x + t.x,
				y + t.y,
				xradius * t.xScale,
				yradius * t.yScale,
				angle + t.angle
				);
	}

	@Override
	public boolean contains(float x, float y) {
		final float dx = (this.x - x);
		final float dy = (this.y - y);
		final float localX = dx*cos + dy*sin;
		final float localY = dy*cos - dx*sin;
		return ((localX*localX)/(xradius*xradius)) + ((localY*localY)/(yradius*yradius)) <= 1f;
	}
	
	@Override
	public Ellipse clone() {
		return new Ellipse(x, y, xradius, yradius, angle);	
	}

	@Override
	public Vector2f randomPoint() {
		Vector2f ret = null, tmp;
		AxisAlignedBox box = getAxisAlignedBox();
		while (ret == null) {
			tmp = box.randomPoint();
			if (box.contains(tmp))
				ret = tmp;
		}
		return ret;
	}

}

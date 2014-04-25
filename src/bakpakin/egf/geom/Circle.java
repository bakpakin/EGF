package bakpakin.egf.geom;

import org.lwjgl.util.vector.Vector2f;

public class Circle  extends AbstractShape implements Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5103541183138773858L;
	
	protected final float x, y, radius;

	/**
	 * 
	 * @param x
	 * @param y
	 * @param radius
	 */
	public Circle(float x, float y, float radius) {
		this.x = x;
		this.y = y;
		this.radius = radius;
	}

	@Override
	public AxisAlignedBox getAxisAlignedBox() {
		return new AxisAlignedBox(x-radius, y-radius, x+radius, y+radius);
	}

	@Override
	public Shape transformLocal(Transform t) {
		if (t.xScale == t.yScale) {//return circle
			return new Circle(x + t.x, y + t.y, radius * t.xScale);
		} else {//return ellipse
			return new Ellipse(
					x + t.x, 
					y + t.y,
					radius * t.xScale,
					radius * t.yScale,
					t.angle
					);
		}
	}

	@Override
	public boolean contains(float x, float y) {
		final float dx = (x - this.x);
		final float dy = (y - this.y);
		return radius*radius > dx*dx + dy*dy;
	}

	/**
	 * 
	 * @return
	 */
	public float getRadius() {
		return radius;
	}

	@Override
	public float getOriginY() {
		return y;
	}

	@Override
	public float getOriginX() {
		return x;
	}
		
	@Override
	public Circle clone() {
		return new Circle(x, y, radius);
	}

	@Override
	public float getRotation() {
		return 0;
	}

	@Override
	public Vector2f randomPoint() {
		double t = 2.0*Math.PI*Math.random();
		double u = 2.0*Math.random();
		double r = (u > 1.0 ? 2 - u : u)*radius;
		return new Vector2f((float)(r*Math.cos(t)), (float)(r*Math.sin(t)));
	}

}

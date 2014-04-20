package bakpakin.egf.util.geom;

import org.lwjgl.util.vector.Vector2f;

public class AxisAlignedBox implements Cloneable, Shape {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4744031102335348979L;
	protected final float x1, x2, y1, y2;
	protected int hash;
	
	/**
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public AxisAlignedBox(float x1, float y1, float x2, float y2) {
		if (x1 > x2) {
			this.x1 = x2;
			this.x2 = x1;
		} else {
			this.x1 = x1;
			this.x2 = x2;
		}
		if (y1 > y2) {
			this.y1 = y2;
			this.y2 = y1;
		} else {
			this.y1 = y1;
			this.y2 = y2;
		}
	}
	
	/**
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param dud
	 */
	private AxisAlignedBox(float x1, float y1, float x2, float y2, boolean dud) {
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
	}

	/**
	 * 
	 * @return
	 */
	public float getXMin() {
		return x1;
	}

	/**
	 * 
	 * @return
	 */
	public float getXMax() {
		return x2;
	}

	/**
	 * 
	 * @return
	 */
	public float getYMin() {
		return y1;
	}

	/**
	 * 
	 * @return
	 */
	public float getYMax() {
		return y2;
	}
	
	/**
	 * 
	 * @return
	 */
	public float getHeight() {
		return y2 - y1;
	}
	
	/**
	 * 
	 * @return
	 */
	public float getWidth() {
		return x2 - x1;
	}
	
	@Override
	public float getOriginX() {
		return (x1 + x2)*.5f;
	}
	
	@Override
	public float getOriginY() {
		return (y1 + y2)*.5f;
	}
	
	/**
	 * 
	 * @param other
	 * @return
	 */
	public boolean contains(AxisAlignedBox other) {
		if (x2 < other.x2) return false;
		if (x1 > other.x1) return false;		
		if (y2 < other.y2) return false;
		if (y1 > other.y1) return false;
		return true;
	}
	
	/**
	 * 
	 * @param point
	 * @return
	 */
	public boolean contains(Vector2f point) {
		return contains(point.x, point.y);
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean contains(double x, double y) {
		return contains((float)x, (float)y);
	}
	
	@Override
	public boolean contains(float x, float y) {
		return (x >= x1 && x <= x2 && y >= y1 && y <= y2);
	}
	
	/**
	 * 
	 * @param other
	 * @return
	 */
	public boolean intersects(AxisAlignedBox other) {
		if (x1 > other.x2) return false;
		if (x2 < other.x1) return false;		
		if (y1 > other.y2) return false;
		if (y2 > other.y1) return false;
		return true;
	}
	
	/**
	 * 
	 * @param other
	 * @return
	 */
	public Vector2f overlap(AxisAlignedBox other) {
		if (!intersects(other)) {
			return new Vector2f(0f, 0f);
		}
		
		float xOverlap1, xOverlap2, yOverlap1, yOverlap2;
		xOverlap1 = x2 - other.x1;
		yOverlap1 = y2 - other.y1;
		xOverlap2 = x1 - other.x2;
		yOverlap2 = y1 - other.y2;
		
		float xOverlap = Math.abs(xOverlap1) > Math.abs(xOverlap2) ? xOverlap2 : xOverlap1;
		float yOverlap = Math.abs(yOverlap1) > Math.abs(yOverlap2) ? yOverlap2 : yOverlap1;
		
		return Math.abs(xOverlap) > Math.abs(yOverlap) ? new Vector2f(0f, yOverlap) : new Vector2f(xOverlap, 0f);

	}

	@Override
	public int hashCode() {
		if (hash == 0) {
			final int prime = 3;
			int result = prime + Float.floatToIntBits(x1);
			result = prime * result + Float.floatToIntBits(x2);
			result = prime * result + Float.floatToIntBits(y1);
			result = prime * result + Float.floatToIntBits(y2);
			hash = result;
		}
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof AxisAlignedBox))
			return false;
		AxisAlignedBox other = (AxisAlignedBox) obj;
		if (Float.floatToIntBits(x1) != Float.floatToIntBits(other.x1))
			return false;
		if (Float.floatToIntBits(x2) != Float.floatToIntBits(other.x2))
			return false;
		if (Float.floatToIntBits(y1) != Float.floatToIntBits(other.y1))
			return false;
		if (Float.floatToIntBits(y2) != Float.floatToIntBits(other.y2))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Hitbox [x1=");
		builder.append(x1);
		builder.append(", x2=");
		builder.append(x2);
		builder.append(", y1=");
		builder.append(y1);
		builder.append(", y2=");
		builder.append(y2);
		builder.append("]");
		return builder.toString();
	}
	
	@Override
	public AxisAlignedBox clone() {
		return new AxisAlignedBox(x1, y1, x2, y2, true);
	}

	@Override
	public AxisAlignedBox getAxisAlignedBox() {
		return this;
	}

	@Override
	public AxisAlignedBox getAxisAlignedBox(Transform t) {
		return transformLocal(t).getAxisAlignedBox();
	}

	@Override
	public Shape transformLocal(Transform t) {
		return new OrientedBox(
				getOriginX() + t.x,
				getOriginY() + t.y,
				getWidth() * t.xScale,
				getHeight() * t.yScale,
				t.angle
				);
	}

	@Override
	public float getRotation() {
		return 0;
	}

	@Override
	public Vector2f randomPoint() {
		final float a1 = (float)(Math.random());
		final float a2 = (float)(Math.random());
		return new Vector2f(
				(a1*x1) + ((1-a1)*x2),
				(a1*y2) + ((1-a2)*y2)
				);
	}

}

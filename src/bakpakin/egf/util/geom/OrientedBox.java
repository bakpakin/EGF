package bakpakin.egf.util.geom;

import org.lwjgl.util.vector.Vector2f;

public class OrientedBox extends AbstractShape implements Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6328278380098463068L;
	
	protected final float angle, x, y, width, height;
	protected final float cos, sin;
	private int hash;
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param angle
	 */
	public OrientedBox(float x, float y, float width, float height, float angle) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.angle = angle;
		final double ang = (Math.PI / 180) * angle;
		cos = (float)Math.cos(ang);
		sin = (float)Math.sin(ang);
	}

	@Override
	public AxisAlignedBox getAxisAlignedBox() {
		final float hw = (cos*width + sin*height)*.5f;
		final float hh = (sin*width + cos*height)*.5f;
		return new AxisAlignedBox(x - hw, y - hh, x + hw, y + hh);
	}

	@Override
	public Shape transformLocal(Transform t) {
		return new OrientedBox(
				x + t.x,
				y + t.y,
				width * t.xScale,
				height * t.yScale,
				angle + t.angle
				);
	}

	@Override
	public boolean contains(float x, float y) {
		final float dx = this.x - x;
		final float dy = this.y - y;
		return Math.abs(dx*cos + dy*sin) < width/2 && Math.abs(dx*sin - dy*cos) < height/2;
	}

	@Override
	public float getRotation() {
		return angle;
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
	public float getWidth() {
		return width;
	}

	/**
	 * 
	 * @return
	 */
	public float getHeight() {
		return height;
	}

	@Override
	public int hashCode() {
		if (hash == 0) {
			final int prime = 31;
			int result = 1;
			result = prime * result + Float.floatToIntBits(angle);
			result = prime * result + Float.floatToIntBits(height);
			result = prime * result + Float.floatToIntBits(width);
			result = prime * result + Float.floatToIntBits(x);
			result = prime * result + Float.floatToIntBits(y);
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
		if (!(obj instanceof OrientedBox))
			return false;
		OrientedBox other = (OrientedBox) obj;
		if (hashCode() != other.hashCode())
			return false;
		if (Float.floatToIntBits(angle) != Float.floatToIntBits(other.angle))
			return false;
		if (Float.floatToIntBits(height) != Float.floatToIntBits(other.height))
			return false;
		if (Float.floatToIntBits(width) != Float.floatToIntBits(other.width))
			return false;
		if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
			return false;
		if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OrientedBox [angle=");
		builder.append(angle);
		builder.append(", x=");
		builder.append(x);
		builder.append(", y=");
		builder.append(y);
		builder.append(", width=");
		builder.append(width);
		builder.append(", height=");
		builder.append(height);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public Vector2f randomPoint() {
		final float r1 = 2f*(float)(Math.random()) - 1;
		final float r2 = 2f*(float)(Math.random()) - 1;
		final float xx = width*r1;
		final float yy = height*r2;
		return new Vector2f(x + cos*xx + sin*yy, y + cos*yy + sin*xx);		
	}
	
	@Override
	public OrientedBox clone() {
		return new OrientedBox(x, y, width, height, angle);
	}

}

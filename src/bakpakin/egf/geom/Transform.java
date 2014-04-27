package bakpakin.egf.geom;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.util.vector.Vector2f;

import bakpakin.egf.framework.Component;

/**
 * This class represents a 2D transformation, comprised of scaling, rotation, and translation. 
 * @author Calvin
 *
 */
public class Transform implements Component, Cloneable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4423742453211546765L;

	/**
	 * The identity Transform.  Represents no transformation.
	 */
	private final static Transform identity = new Transform();
	
	/**
	 * The x and y displacements of the Transform
	 */
	protected float x, y;
	
	/**
	 * The scaling for this Transform.
	 */
	protected float xScale, yScale;
	
	/**
	 * The rotation represented by this Transform.
	 */
	protected float angle;
	
	/**
	 * @param x
	 * @param y
	 * @return a translated copy
	 */
	public static Transform translation(float x, float y) {
		return new Transform(x, y, 1, 1, 0);
	}
	
	/**
	 * @param angle
	 * @return a rotated copy
	 */
	public static Transform rotation(float angle) {
		return new Transform(0, 0, 1, 1, angle);
	}
	
	/**
	 * @param xScale
	 * @param yScale
	 * @return a scaled copy
	 */
	public static Transform scaling(float xScale, float yScale) {
		return new Transform(0, 0, xScale, yScale, 0);
	}
	
	/**
	 * @param factor
	 * @return a scaled copy;
	 */
	public static Transform scaling(float factor) {
		return scaling(factor, factor);
	}
	
	/**
	 * Adds the components of two Transforms together.
	 * @param a
	 * @param b
	 * @return a new Transform
	 */
	public static Transform add(Transform a, Transform b) {
		return new Transform(
				a.x + b.x,
				a.y + b.y,
				a.xScale * b.xScale, 
				a.yScale * b.yScale, 
				a.angle + b.angle
				);
	}
	
	/**
	 * Interpolates between two Transforms based on alpha. An alpha
	 * value of 0f returns a, and an alpha value of 1f returns b.
	 * @param a
	 * @param b
	 * @param alpha
	 * @return a new Transform
	 */
	public static Transform interpolate(Transform a, Transform b, float alpha) {
		final float invAlpha = 1 - alpha;
		return new Transform(
				a.x*invAlpha + b.x*alpha,
				a.y*invAlpha + b.y*alpha,
				a.xScale*invAlpha + b.xScale*alpha,
				a.yScale*invAlpha + b.yScale*alpha, 
				a.angle*invAlpha + b.angle*alpha
				);
	}
	
	/**
	 * Interpolates between two Transforms based on alpha. An alpha
	 * value of 0f returns a, and an alpha value of 1f returns b. The 
	 * returned Transform has the same scaling as a.
	 * @param a
	 * @param b
	 * @param alpha
	 * @return a new Transform
	 */
	public static Transform interpolateNoScale(Transform a, Transform b, float alpha) {
		final float invAlpha = 1 - alpha;
		return new Transform(
				a.x*invAlpha + b.x*alpha,
				a.y*invAlpha + b.y*alpha,
				a.xScale,
				a.yScale, 
				a.angle*invAlpha + b.angle*alpha
				);
	}
	
	/**
	 * Creates a new Transform that represents the identity transformation.
	 */
	public Transform() {
		this(0, 0, 1f, 1f, 0);
	}
	
	/**
	 * Create a new translation
	 * @param x
	 * @param y
	 */
	public Transform(float x, float y) {
		this(x, y, 1f, 1f, 0);
	}
	
	/**
	 * Create a new rotation
	 * @param angle
	 */
	public Transform(float angle) {
		this(0, 0, 1f, 1f, angle);
	}
	
	/**
	 * Copy Constructor.
	 * @param xform
	 */
	public Transform(Transform xform) {
		this(xform.x, xform.y, xform.xScale, xform.yScale, xform.angle);
	}
	
	/**
	 * Creates a new general Transform
	 * @param x
	 * @param y
	 * @param xScale
	 * @param yScale
	 * @param angle
	 */
	public Transform(float x, float y, float xScale, float yScale, float angle) {
		this.x = x;
		this.y = y;
		this.xScale = xScale;
		this.yScale = yScale;
		this.angle = angle;
	}
	
	/**
	 * Translates this Transform.
	 * @param x
	 * @param y
	 * @return this for convenience
	 */
	public Transform translate(float x, float y) {
		this.x += x;
		this.y += y;
		return this;
	}
	
	/**
	 * Rotates this Transform
	 * @param angle
	 * @return this for convenience
	 */
	public Transform rotate(float angle) {
		this.angle += angle;
		return this;
	}
	
	/**
	 * Scales this Transform
	 * @param xScale
	 * @param yScale
	 * @return this for convenience
	 */
	public Transform scale(float xScale, float yScale) {
		this.xScale *= xScale;
		this.yScale *= yScale;
		return this;
	}
	
	/**
	 * Scales this Transform
	 * @param f
	 * @return this for convenience
	 */
	public Transform scale(float f) {
		return scale(f, f);
	}
	
	/**
	 * Adds another Transform's components to this
	 * @param other
	 * @return this for convenience
	 */
	public Transform add(Transform other) {
		x += other.x;
		y += other.y;
		xScale *= other.xScale;
		yScale *= other.yScale;
		angle += other.angle;
		return this;
	}
	
	/**
	 * Interpolate this with another Transform based on alpha. An 
	 * alpha of 0f changes nothing, while an alpha of 1f changes this
	 * to other.
	 * @param other
	 * @param factor
	 * @return
	 */
	public Transform add(Transform other, float factor) {
		x += other.x*factor;
		y += other.y*factor;
		xScale *= Math.pow(other.xScale, factor);
		yScale *= Math.pow(other.yScale, factor);
		angle += other.angle*factor;
		return this;
	}
	
	/**
	 * @return the Transform that undoes this transform when added.
	 */
	public Transform getOpposite() {
		return new Transform().add(this, -1);
	}
	
	/**
	 * Applies this Transform to the GL context.
	 */
	public void apply() {
		glTranslatef(x, y, 0);
		glScalef(xScale, yScale, 1);
		glRotatef(angle, 0, 0, 1);
	}
	
	/**
	 * Undoes the application of this Transform to the GL context.
	 */
	public void applyInverse() {
		glRotatef(-angle, 0, 0, 1);
		glScalef(1/xScale, 1/yScale, 1);
		glTranslatef(-x, -y, 0);
	}
	
	/**
	 * @return if this represents the identity transformation.
	 */
	public boolean isIdentity() {
		return this.equals(identity);
	}
	
	@Override
	public Transform clone() {
		return new Transform(x, y, xScale, yScale, angle);
	}
		
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Transform [x=");
		builder.append(x);
		builder.append(", y=");
		builder.append(y);
		builder.append(", xScale=");
		builder.append(xScale);
		builder.append(", yScale=");
		builder.append(yScale);
		builder.append(", angle=");
		builder.append(angle);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(angle);
		result = prime * result + Float.floatToIntBits(x);
		result = prime * result + Float.floatToIntBits(xScale);
		result = prime * result + Float.floatToIntBits(y);
		result = prime * result + Float.floatToIntBits(yScale);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Transform))
			return false;
		Transform other = (Transform) obj;
		if (Float.floatToIntBits(angle) != Float.floatToIntBits(other.angle))
			return false;
		if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
			return false;
		if (Float.floatToIntBits(xScale) != Float.floatToIntBits(other.xScale))
			return false;
		if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
			return false;
		if (Float.floatToIntBits(yScale) != Float.floatToIntBits(other.yScale))
			return false;
		return true;
	}
	
	//Getters and Setters

	/**
	 * @return x
	 */
	public float getX() {
		return x;
	}
	
	/**
	 * Sets x
	 * @param x
	 */
	public void setX(float x) {
		this.x = x;
	}
	
	/**
	 * @return y
	 */
	public float getY() {
		return y;
	}
	
	/**
	 * Sets y
	 * @param y
	 */
	public void setY(float y) {
		this.y = y;
	}
	
	/**
	 * @return x-scale
	 */
	public float getXScale() {
		return xScale;
	}
	
	/**
	 * Sets x-scale
	 * @param xScale
	 */
	public void setXScale(float xScale) {
		this.xScale = xScale;
	}
	
	/**
	 * @return y-scale
	 */
	public float getYScale() {
		return yScale;
	}
	
	/**
	 * Sets y-scale
	 * @param yScale
	 */
	public void setYScale(float yScale) {
		this.yScale = yScale;
	}
	
	/**
	 * @return rotation angle
	 */
	public float getAngle() {
		return angle;
	}
	
	/**
	 * Sets the angle of rotation
	 * @param angle
	 */
	public void setAngle(float angle) {
		this.angle = angle;
	}
	
	/**
	 * @return position
	 */
	public Vector2f getPosition() {
		return new Vector2f(x, y);
	}

}

package bakpakin.egf.util.rig;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;

import bakpakin.egf.framework.Component;
import bakpakin.egf.util.geom.Transform;
import bakpakin.egf.util.render.Drawable;

/**
 * 
 * @author Calvin
 *
 */
public class Bone implements Component {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5522315175061522278L;
	
	/**
	 *
	 */
	private static int nextId = 0;

	/**
	 * 
	 */
	private Bone parent;

	/**
	 * 
	 */
	private Transform transform;

	/**
	 * 
	 */
	private Drawable drawable;

	/**
	 * 
	 */
	private Color color = Color.white;

	/**
	 * 
	 */
	private float depth;

	/**
	 * 
	 */
	private float length;

	/**
	 * 
	 */
	private List<Bone> children;
	
	/**
	 * 
	 */
	private int id;

	/**
	 * 
	 */
	private Bone() {
		id = nextId++;
		this.children = new ArrayList<Bone>();
		this.transform = new Transform();
		this.length = 1;
	}

	/**
	 * 
	 * @param parent
	 * @param length
	 * @param angle
	 */
	public Bone(Bone parent, float length, float angle) {
		this();
		setParent(parent);
		setLength(length);
		transform.setAngle(angle);
		if (parent != null)
			transform.setX(parent.getLength());
	}

	/**
	 * 
	 * @param parent
	 * @param length
	 * @param angle
	 * @param drawable
	 */
	public Bone(Bone parent, float length, float angle, Drawable drawable) {
		this(parent, length, angle);
		setDrawable(drawable);
	}

	/**
	 * 
	 * @return
	 */
	public Bone getParent() {
		return parent;
	}

	/**
	 * 
	 * @param parent
	 */
	public void setParent(Bone parent) {
		this.parent = parent;
		if (parent != null && (!parent.getChildren().contains(this))) {
			parent.addChild(this);
		}
	}

	/**
	 * 
	 * @return
	 */
	public Transform getTransform() {
		return transform;
	}

	/**
	 * 
	 * @param transform
	 */
	public void setTransform(Transform transform) {
		this.transform = transform;
	}

	/**
	 * 
	 * @return
	 */
	public Drawable getDrawable() {
		return drawable;
	}

	/**
	 * 
	 * @param drawable
	 */
	public void setDrawable(Drawable drawable) {
		this.drawable = drawable;
	}

	/**
	 * 
	 * @return
	 */
	public float getDepth() {
		return depth;
	}

	/**
	 * 
	 * @param depth
	 */
	public void setDepth(float depth) {
		this.depth = depth;
	}

	/**
	 * 
	 * @return
	 */
	public float getLength() {
		return length;
	}

	/**
	 * 
	 * @param length
	 */
	public void setLength(float length) {
		this.length = length;
	}

	/**
	 * 
	 * @return
	 */
	public List<Bone> getChildren() {
		return java.util.Collections.unmodifiableList(children);
	}

	/**
	 * 
	 * @param child
	 */
	public void addChild(Bone child) {
		children.add(child);
		if (child.getParent() != this) {
			child.setParent(this);
		}
	}

	/**
	 * 
	 * @param child
	 */
	public void removeChild(Bone child) {
		if (children.remove(child)) {
			child.setParent(null);
		}
	}

	/**
	 * 
	 * @return
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * 
	 * @param color
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + Float.floatToIntBits(depth);
		result = prime * result + Float.floatToIntBits(length);
		result = prime * result + id;
		result = prime * result
				+ ((transform == null) ? 0 : transform.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		return this == obj;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Bone [");
		if (transform != null) {
			builder.append("transform=");
			builder.append(transform);
			builder.append(", ");
		}
		if (color != null) {
			builder.append("color=");
			builder.append(color);
			builder.append(", ");
		}
		builder.append("depth=");
		builder.append(depth);
		builder.append(", length=");
		builder.append(length);
		builder.append("]");
		return builder.toString();
	}

}

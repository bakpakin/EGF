package bakpakin.egf.tilemap;

import java.util.HashMap;
import java.util.Map;

import bakpakin.egf.tilemap.TileMapBean.LayerBean;

/**
 * A layer of a {@link TileMap}. Used to hold information about a {@link TileMap}
 * associated with a certain depth.
 * 
 * @author Calvin
 *
 */
public abstract class Layer extends TileMapComponent {

	/**
	 * The depth of the {@link Layer}.
	 */
	protected float depth;

	/**
	 * The name of the {@link Layer}.
	 */
	protected String name = "";

	/**
	 * The x offset of the {@link Layer}.
	 */
	protected int x;
	
	/**
	 * The y offset of the {@link Layer}.
	 */
	protected int y;
	
	/**
	 * Whether or not the {@link Layer} should be rendered.
	 */
	protected boolean visible = true;
	
	/**
	 * The opacity of the {@link Layer}, between 0 and 1. An opacity
	 * of 0 is completely transparent, while 1 is completely opaque.
	 */
	protected float opacity = 1f;
	
	/**
	 * The width of this {@link Layer}.
	 */
	protected int width;
	
	/**
	 * The height of this {@link Layer}.
	 */
	protected int height;

	/**
	 * A flag indicated of this has been modified
	 */
	boolean dirty = true;
	
	/**
	 * The {@link TileMap} this belongs to.
	 */
	protected final TileMap map;

	/**
	 * Creates a new {@link Layer} with a specified depth.
	 * @param depth
	 * @param map
	 */
	public Layer(float depth, TileMap map) {
		setDepth(depth);
		this.map = map;
	}

	/**
	 * @return the depth of this {@link Layer}.
	 */
	public float getDepth() {
		return depth;
	}

	/**
	 * @return the x offset of this {@link Layer}.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the x offset of this {@link Layer}.
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y offset of this {@link Layer}.
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the y offset of this {@link Layer}.
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return <code>true</code> if this {@link Layer} is to be rendered, otherwise <code>false</code>.
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * Sets whether or not the layer should be rendered.
	 * @param visible
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	/**
	 * @return the opacity of this {@link Layer}, between 0 for transparent and 1 for opaque.
	 */
	public float getOpacity() {
		return opacity;
	}

	/**
	 * Sets the opacity of this {@link Layer}. Values greater than 1 or less than 0 are clamped.
	 * @param opacity
	 */
	public void setOpacity(float opacity) {
		this.opacity = opacity > 1f ? 1f : opacity < 0f ? 0f : opacity;
	}

	/**
	 * @return the width of this {@link Layer}.
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Sets the width.
	 * @param width
	 */
	public void setWidth(int width) {
		if (!(width >= 0f)) {
			throw new IllegalArgumentException("Width must be greater than or equal to 0");
		}
		this.width = width;
	}

	/**
	 * @return the height of this {@link Layer}.
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Sets the height.
	 * @param height
	 */
	public void setHeight(int height) {
		if (!(width >= 0f)) {
			throw new IllegalArgumentException("Height must be greater than or equal to 0");
		}
		this.height = height;
	}

	/**
	 * Sets the depth of this {@link Layer}.
	 * @param depth
	 */
	public void setDepth(float depth) {
		this.depth = depth;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of this {@link Layer}.
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the {@link TileMap} that this belongs to
	 */
	public TileMap getMap() {
		return map;
	}
	
	/**
	 * @return a template that can be used to reconstruct this {@link Layer}
	 */
	public LayerBean writeTemplate() {
		LayerBean lb = new LayerBean();
		lb.setWidth(width);
		lb.setHeight(height);
		lb.setName(name);
		lb.setOpacity(opacity);
		lb.setX(x);
		lb.setY(y);
		lb.setVisible(visible);
		Map<String, String> properties = new HashMap<String, String>();
		properties.putAll(getProperties());
		lb.setProperties(properties);
		return lb;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Layer [depth=");
		builder.append(depth);
		builder.append(", name=");
		builder.append(name);
		builder.append(", x=");
		builder.append(x);
		builder.append(", y=");
		builder.append(y);
		builder.append(", visible=");
		builder.append(visible);
		builder.append(", opacity=");
		builder.append(opacity);
		builder.append(", width=");
		builder.append(width);
		builder.append(", height=");
		builder.append(height);
		builder.append(", getProperties()=");
		builder.append(getProperties());
		builder.append("]");
		return builder.toString();
	}

}

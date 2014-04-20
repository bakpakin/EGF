package bakpakin.egf.util.tilemap;

import bakpakin.egf.util.tilemap.TileMapBean.LayerBean;

/**
 * A subclass of {@link Layer} that displays arbitrarily sized and placed images.
 * For placing tiles in a grid, use {@link TileMap}
 * 
 * @author Calvin
 *
 */
public class ImageLayer extends Layer {

	/**
	 * Creates a new {@link ImageLayer}.
	 * @param depth
	 * @param map
	 */
	public ImageLayer(float depth, TileMap map) {
		super(depth, map);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ImageLayer [depth=");
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
		builder.append("]");
		return builder.toString();
	}

	@Override
	public LayerBean writeTemplate() {
		LayerBean lb = super.writeTemplate();
		lb.setType("imagelayer");
		return lb;
	}

}

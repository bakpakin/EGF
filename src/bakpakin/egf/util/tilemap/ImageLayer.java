package bakpakin.egf.util.tilemap;

import java.net.URL;

import bakpakin.egf.util.render.Drawable;
import bakpakin.egf.util.render.Sprite;
import bakpakin.egf.util.tilemap.TileMapBean.LayerBean;

/**
 * A subclass of {@link Layer} that displays a background image.
 * For placing tiles in a grid, use {@link TileMap}
 * 
 * @author Calvin
 *
 */
public class ImageLayer extends Layer {
	
	private Drawable image;
	
	private String imagePath;

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
		lb.setImage(imagePath);
		lb.setType("imagelayer");
		return lb;
	}

	/**
	 * @return the image
	 */
	public Drawable getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(String imagePath, URL mapUrl) {
		this.imagePath = imagePath;
		this.image = new Sprite(TileMapComponent.getResource(imagePath, mapUrl));
	}

}

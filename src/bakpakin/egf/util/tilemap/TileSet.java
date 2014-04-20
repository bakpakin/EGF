package bakpakin.egf.util.tilemap;

import java.awt.Color;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.opengl.Texture;

import bakpakin.egf.util.AssetManager;
import bakpakin.egf.util.tilemap.TileMapBean.TileSetBean;

/**
 * A <code>TileSet</code> holds tile and texture data for a {@link TileMap}. Lazily creates
 * {@link Tile} objects as needed.
 * @author Calvin
 *
 */
public class TileSet extends TileMapComponent {
	
	/**
	 * The texture that holds image data
	 */
	private Texture texture;
	
	/**
	 * The number of tiles wide this TileSet is
	 */
	private final int width;
	
	/**
	 * The number of tiles high this TileSet is
	 */
	private final int height;
	
	/**
	 * The width of each tile in pixels
	 */
	private final int tileWidth;
	
	/**
	 * The height of each tile in pixels
	 */
	private final int tileHeight;
	
	/**
	 * The x drawing offset for each tile in pixels
	 */
	private int xOffset;
	
	/**
	 * The y drawing offset for each tile in pixels
	 */
	private int yOffset;
	
	/**
	 * The x separation between tiles in pixels
	 */
	private int xSeperation;
	
	/**
	 * The y separation between tiles in pixels
	 */
	private int ySeperation;
	
	/**
	 * The pixel margin around the image, separating the image border an tiles
	 */
	private int margin;
	
	/**
	 * The name of the this {@link TileSet}.
	 */
	private String name;
	
	/**
	 * An array holding all of the tiles for this tile set.
	 */
	private Tile[] tiles;
	
	/**
	 * The color that is used as transparency, set to -1 if no such color.
	 */
	private int transparentColor = -1;
	
	/**
	 * Creates a new TileSet
	 * @param key - String with a path to the image
	 * @param name - name of this tile set
	 * @param tileWidth - the width of each tile in pixels
	 * @param tileHeight - the height of each tile in pixels
	 */
	public TileSet(String key, String name, int tileWidth, int tileHeight) {
		texture = AssetManager.getTexture(key);
		setName(name);
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.width = (texture.getImageWidth() / tileWidth);
		this.height = (texture.getImageHeight() / tileHeight);
		tiles = new Tile[width*height];
	}
	
	/**
	 * Creates a new TileSet
	 * @param url - URL to the image
	 * @param name - name of this tile set
	 * @param tileWidth - the width of each tile in pixels
	 * @param tileHeight - the height of each tile in pixels
	 * @param firstTileId - the 
	 */
	public TileSet(URL url, String name, int tileWidth, int tileHeight) {
		texture = AssetManager.getTexture(url);
		setName(name);
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.width = (texture.getImageWidth() / tileWidth);
		this.height = (texture.getImageHeight() / tileHeight);
		tiles = new Tile[width*height];
	}
	
	/**
	 * @param row
	 * @param col
	 * @return the {@link Tile} specified by the row and column
	 */
	public Tile getTile(int row, int col) {
		return getTile(col + width*row);
	}
	
	/**
	 * @param index
	 * @return the {@link Tile} specified by the index
	 */
	public Tile getTile(int index) {
		Tile ret;
		if ((ret = tiles[index]) == null) {
			tiles[index] = new Tile(index, this);
			return tiles[index];
		}
		return ret;
	}
	
	/**
	 * Replace a tile ID with a new tile.
	 * @param index - the index/ID of the tile
	 * @param tile - the new tile
	 */
	public void replaceTile(int index, Tile tile) {
		tiles[index] = tile;
	}
	
	/**
	 * Replace a tile ID with a new tile.
	 * @param row - the row of the tile
	 * @param col - the column of the tile
	 * @param tile - the new tile
	 */
	public void replaceTile(int row, int col, Tile tile) {
		replaceTile(col + width*row, tile);
	}
	
	/**
	 * @return the texture used for rendering tiles
	 */
	public Texture getTexture() {
		return texture;
	}
	
	/**
	 * Sets the texture
	 * @param texture
	 */
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	
	/**
	 * @return the width in tiles
	 */
	public int getWidth() {
		return width;
	}
	
	
	/**
	 * @return the height in tiles
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * @return the x drawing offset in pixels
	 */
	public int getxOffset() {
		return xOffset;
	}
	
	/**
	 * Sets the x drawing offset in pixels
	 * @param xOffset
	 */
	public void setxOffset(int xOffset) {
		this.xOffset = xOffset;
	}
	
	/**
	 * @return the y drawing offset in pixels
	 */
	public int getyOffset() {
		return yOffset;
	}
	
	/**
	 * Sets the y drawing offset in pixels
	 * @param yOffset
	 */
	public void setyOffset(int yOffset) {
		this.yOffset = yOffset;
	}
	
	/**
	 * @return the x separation between tiles in pixels
	 */
	public int getxSeperation() {
		return xSeperation;
	}
	
	/**
	 * Sets the x separation between tiles in pixels
	 * @param xSeperation
	 */
	public void setxSeperation(int xSeperation) {
		this.xSeperation = xSeperation;
	}
	
	/**
	 * @return the y separation between tiles in pixels
	 */
	public int getySeperation() {
		return ySeperation;
	}
	
	/**
	 * Sets the y separation between tiles in pixels
	 * @param ySeperation
	 */
	public void setySeperation(int ySeperation) {
		this.ySeperation = ySeperation;
	}

	/**
	 * @return the height of each tile in pixels
	 */
	public int getTileHeight() {
		return tileHeight;
	}

	/**
	 * @return the width of each tile in pixels
	 */
	public int getTileWidth() {
		return tileWidth;
	}

	/**
	 * @return the name of this {@link TileSet}
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of this {@link TileSet}
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the margin in pixels between the border of the image and the tiles
	 */
	public int getMargin() {
		return margin;
	}

	/**
	 * Sets the margin in pixels between the border of the image and the tiles
	 * @param margin
	 */
	public void setMargin(int margin) {
		this.margin = margin;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TileSet [texture=");
		builder.append(texture);
		builder.append(", width=");
		builder.append(width);
		builder.append(", height=");
		builder.append(height);
		builder.append(", tileWidth=");
		builder.append(tileWidth);
		builder.append(", tileHeight=");
		builder.append(tileHeight);
		builder.append(", xOffset=");
		builder.append(xOffset);
		builder.append(", yOffset=");
		builder.append(yOffset);
		builder.append(", xSeperation=");
		builder.append(xSeperation);
		builder.append(", ySeperation=");
		builder.append(ySeperation);
		builder.append(", margin=");
		builder.append(margin);
		builder.append(", name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}

	/**
	 * @return
	 */
	public TileSetBean writeTemplate() {
		TileSetBean tsb = new TileSetBean();
		tsb.setImage(texture.getTextureRef());
		tsb.setImageheight(texture.getImageHeight());
		tsb.setImagewidth(texture.getImageWidth());
		tsb.setMargin(margin);
		tsb.setName(name);
		Map<String, String> properties = new HashMap<String, String>();
		properties.putAll(getProperties());
		tsb.setProperties(properties);
		tsb.setTilewidth(tileWidth);
		tsb.setTileheight(tileHeight);
		tsb.setSpacing(xSeperation);
		tsb.setTransparentcolor(Integer.toHexString(transparentColor).substring(0, 7));
		return tsb;
	}

	public int getTransparentColor() {
		return transparentColor;
	}

	public void setTransparentColor(int transparentColor) {
		this.transparentColor = transparentColor;
	}
	
	public float[] getColor() {
		Color color = new Color(transparentColor);
		float[] ret = new float[3];
		ret[0] = color.getRed() / 255f;
		ret[1] = color.getGreen() / 255f;
		ret[2] = color.getBlue() / 255f;
		return ret;
	}

}

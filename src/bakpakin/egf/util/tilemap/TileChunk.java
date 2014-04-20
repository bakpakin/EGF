package bakpakin.egf.util.tilemap;

import java.util.Arrays;

/**
 * A wrapper class for a square 2D Array of {@link Tile}s.
 * @author Calvin
 *
 */
public class TileChunk {
	
	/**
	 * Whether or not this {@link TileChunk} has been modified
	 */
	private boolean dirty = true;
	
	private final int[] tiles;
	
	/**
	 * The size of this chunk.
	 */
	public final int size;
	
	private TileLayer tileLayer;
	
	/**
	 * Creates a new {@link TileChunk} filled with the specified tile.
	 * @param size
	 * @param tileId
	 */
	public TileChunk(TileLayer tileLayer, int size, int tileId) {
		this(tileLayer, size);
		for (int i = 0; i < size*size; i++) {
			tiles[i] = tileId;
		}
	}
	
	/**
	 * Creates a new empty {@link TileChunk}.
	 * @param size
	 */
	public TileChunk(TileLayer tileLayer, int size) {
		this.size = size;
		this.tileLayer = tileLayer;
		tiles = new int[size*size];
	}
	
	/**
	 * @param row
	 * @param col
	 * @return the index of that row and column in the internal array
	 */
	public int coordToIndex(int row, int col) {
		return size*row + col;
	}
	
	/**
	 * @param row
	 * @param col
	 * @return the {@link Tile} at the specified row and column
	 */
	public Tile get(int row, int col) {
		return tileLayer.map.getTile(tiles[coordToIndex(row, col)]);
	}
	
	/**
	 * @param row
	 * @param col
	 * @return the tile ID at the specified row and column
	 */
	public int getId(int row, int col) {
		return tiles[coordToIndex(row, col)];
	}
	
	/**
	 * @param index
	 * @return the {@link Tile} at the specified index
	 */
	public Tile get(int index) {
		return tileLayer.map.getTile(tiles[index]);
	}
	
	/**
	 * @param index
	 * @return the tile ID at the specified index
	 */
	public int getId(int index) {
		return tiles[index];
	}
	
	/**
	 * Sets the {@link Tile} at the specified index 
	 * @param index
	 * @param tile
	 */
	public void set(int index, int tileId) {
		tiles[index] = tileId;
		dirty = true;
	}
	
	/**
	 * Sets the {@link Tile} at the specified row and column
	 * @param row
	 * @param col
	 * @param tile
	 */
	public void set(int row, int col, int tileId) {
		tiles[coordToIndex(row, col)] = tileId;
		dirty = true;
	}

	/**
	 * @return whether or not the {@link TileChunk} has been modified since
	 * dirty was last set to false.
	 */
	public boolean isDirty() {
		return dirty;
	}

	/**
	 * Sets the dirty flag for this chunk
	 * @param dirty
	 */
	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}

	/**
	 * @return the {@link TileLayer} that this chunk belongs to
	 */
	public TileLayer getTileLayer() {
		return tileLayer;
	}

	/**
	 * Sets the {@link TileLayer} that this Chunk belongs to.
	 * @param tileLayer
	 */
	public void setTileLayer(TileLayer tileLayer) {
		this.tileLayer = tileLayer;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TileChunk [");
		if (tiles != null) {
			builder.append("tiles=");
			builder.append(Arrays.toString(tiles));
			builder.append(", ");
		}
		builder.append("size=");
		builder.append(size);
		builder.append("]");
		return builder.toString();
	}

}

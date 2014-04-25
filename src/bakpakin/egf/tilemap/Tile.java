package bakpakin.egf.tilemap;

/**
 * A <code>Tile</code> represents a tile reference in a {@link TileMap}. Ideally, identical tiles
 * within a {@link TileMap} point to the same <code>Tile</code> instance in order to preserve memory. 
 * <Code>Tile</code>s keep track of data such as what {@link TileSet} they are from and from what positions
 * their images come from.
 * @author Calvin
 *
 */
public class Tile extends TileMapComponent {
	
	/**
	 * the index of this tile in its tile set
	 */
	public final int localIndex;
	
	/**
	 * the tile set this tile belongs to
	 */
	public final TileSet tileSet;
	
	/**
	 * Creates a {@link Tile} that is in the specified row and column of the {@link TileSet}.
	 * Equivalent to { <code>Tile(col + row*tileSet.getWidth(), tileSet)</code> }.
	 * @param row
	 * @param col
	 * @param tileSet
	 */
	public Tile(int row, int col, TileSet tileSet) {
		this(col + row*tileSet.getWidth(), tileSet);
	}
	
	/**
	 * Creates a new {@link Tile} with the specified index and {@link TileSet}.
	 * if tileSet is <code>null</code>, resulting tile is the empty tile.
	 * @param localIndex
	 * @param tileSet
	 */
	public Tile(int localIndex, TileSet tileSet) {
		this.tileSet = tileSet;
		if (tileSet != null && (localIndex < 0 || localIndex >= tileSet.getHeight()*tileSet.getHeight())) {
			throw new IllegalArgumentException("Tile index is not in TileSet");
		}
		this.localIndex = localIndex;
	}

	@Override
	public int hashCode() {
		return localIndex + 53 * tileSet.getName().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Tile))
			return false;
		Tile other = (Tile) obj;
		if (localIndex != other.localIndex)
			return false;
		if (tileSet != other.tileSet)
			return false;
		return true;
	}

}

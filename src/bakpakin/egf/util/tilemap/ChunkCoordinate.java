package bakpakin.egf.util.tilemap;

/**
 * Represents the integer coordinate of a {@link TileChunk} on an infinite grid. Meant to be used
 * as a key to a <code>Map</code> of {@link TileChunk}s.
 * @author Calvin
 *
 */
public class ChunkCoordinate {

	/**
	 * The x coordinate
	 */
	public final int x;
	
	/**
	 * The y coordinate
	 */
	public final int y;
	
	/**
	 * Caches the hashCode for quick map lookups.
	 */
	private final int hashCode;
	
	/**
	 * Creates a new {@link ChunkCoordinate} at (x, y).
	 * @param x
	 * @param y
	 */
	public ChunkCoordinate(int x, int y) {
		this.x = x;
		this.y = y;
		hashCode = (23 * (23 + x)) + y;
	}

	@Override
	public int hashCode() {
		return hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ChunkCoordinate))
			return false;
		ChunkCoordinate other = (ChunkCoordinate) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(30);
		builder.append("ChunkCoordinate [x=");
		builder.append(x);
		builder.append(", y=");
		builder.append(y);
		builder.append("]");
		return builder.toString();
	}
	
}

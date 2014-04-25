package bakpakin.egf.tilemap;

import java.util.HashMap;
import java.util.Map;

import bakpakin.egf.tilemap.TileMapBean.LayerBean;

/**
 * A subclass of {@link Layer} that stores a grid of tiles represented by the {@link Tile} class. Each {@link Tile}
 * stores references that provide information for drawing. The <code>TileLayer</code> class does not draw tiles, it only provides
 * the means to store them in an infinite grid. <code>TileLayer</code>s can support tiles that are of different sizes and from different
 * <code>TileSet</code>s, but it most efficient and recommended to keep all tiles on a layer from the same <code>TileSet</code>.
 * 
 * @author Calvin
 * 
 * @see TileMap
 * @see Tile
 * @see TileRenderer
 * @see TileChunk
 */
public class TileLayer extends Layer {
	
	/**
	 * The default size of chunks. This is appropriate for medium-sized
	 * {@link TileLayer}s, but for other sizes it is recommended
	 * to change the chunk size accordingly. {@link TileLayer}s should only
	 * be split up into as few chunks as necessary for speed. However, larger chunks means
	 * potentially more wasted memory.
	 */
	public static final int DEFAULT_CHUNK_SIZE = 500;
	
	/**
	 * A map of {@link TileChunks}
	 */
	private Map<ChunkCoordinate, TileChunk> chunks;
	
	/**
	 * The height and width in tiles of each {@link TileChunk} in chunks.
	 */
	private final int chunkSize;

	/**
	 * Creates a new {@link TileLayer} at the specified depth with the default chunk-size of 500.
	 * @param depth
	 * @param map
	 */
	public TileLayer(float depth, TileMap map) {
		this(depth, map, DEFAULT_CHUNK_SIZE);
	}
	
	/**
	 * Creates a new {@link TileLayer} at the specified depth with the specified chunk-size.
	 * @param depth
	 * @param map
	 * @param chunkSize
	 */
	public TileLayer(float depth, TileMap map, int chunkSize) {
		super(depth, map);
		chunks = new HashMap<ChunkCoordinate, TileChunk>();
		this.chunkSize = chunkSize;
	}
	
	/**
	 * {@link Tiles} in a {@link TileLayer} are not stored directly in an array, but instead in
	 * a map of chunks, where {@link ChunkCoordinate}s are mapped to {@link TileChunk}s. Chunk 
	 * size is the number of tiles high and wide each {@link TileChunk} is.
	 * @return the size of chunks
	 * @see TileChunk
	 */
	public int getChunkSize() {
		return chunkSize;
	}
	
	/**
	 * @return the map containing all of {@link TileChunk}s.
	 */
	public Map<ChunkCoordinate, TileChunk> getChunks() {
		return chunks;
	}
	
	/**
	 * @param coordinate
	 * @return the {@link TileChunk} at the specified coordinate
	 */
	public TileChunk getChunk(ChunkCoordinate coordinate) {
		return chunks.get(coordinate);
	}
	
	/**
	 * 
	 * @param coordinate
	 * @param chunk
	 */
	public void setChunk(ChunkCoordinate coordinate, TileChunk chunk) {
		chunks.put(coordinate, chunk);
	}
	
	/**
	 * @param x
	 * @param y
	 * @return the {@link Tile} at the specified tile coordinates.
	 */
	public Tile get(final int x, final int y) {
		ChunkCoordinate coordinate;
		TileChunk chunk = chunks.get(coordinate = getCoordinate(x, y));
		if (chunk == null) return null;
		return chunk.get(x - coordinate.x*chunkSize, y - coordinate.y*chunkSize);
	}
	
	/**
	 * Sets the tile at the specified coordinates of this {@link TileLayer}.
	 * @param tileX
	 * @param tileY
	 * @param tileId
	 */
	public void set(final int tileX, final int tileY, int tileId) {
		ChunkCoordinate coordinate = getCoordinate(tileX, tileY);
		TileChunk chunk = chunks.get(coordinate);
		if (chunk == null) {
			setChunk(coordinate, chunk = new TileChunk(this, chunkSize));
		}
		chunk.set(tileX - coordinate.x*chunkSize, tileY - coordinate.y*chunkSize, tileId);
	}
	
	/**
	 * Sets the tile at the specified coordinates of this {@link TileLayer}.
	 * @param tileX
	 * @param tileY
	 * @param tileSet
	 * @param localIndex
	 */
	public void set(final int tileX, final int tileY, TileSet tileSet, int localIndex) {
		if (this.getMap().getTileSet(tileSet.getName()) == null) {
			this.getMap().addTileSet(tileSet);
		}
		set(tileX, tileY, this.getMap().getGlobalIndex(tileSet, localIndex));
	}
	
	/**
	 * @param tileX
	 * @param tileY
	 * @return The {@link ChunkCoordinate} associated with the specified tile coordinate
	 */
	public ChunkCoordinate getCoordinate(final int tileX, final int tileY) {
		int xchunk, ychunk;
		xchunk = tileX >= 0 ? tileX / chunkSize : ((tileX + 1) / chunkSize) - 1;
		ychunk = tileY >= 0 ? tileY / chunkSize : ((tileY + 1) / chunkSize) - 1;
		return new ChunkCoordinate(xchunk, ychunk);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TileLayer [chunks=");
		builder.append(chunks);
		builder.append(", depth=");
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
		lb.setType("tilelayer");
		lb.getProperties().put("chunksize", Integer.toString(chunkSize));
				
		if (chunks.size() == 0) {
			
			lb.setWidth(0);
			lb.setHeight(0);
			lb.setData(new int[0]);
			
		} else {
			
			ChunkCoordinate first = chunks.keySet().iterator().next();
			int x1 = first.x;
			int x2 = first.x;
			int y1 = first.y;
			int y2 = first.y;
			
			for (ChunkCoordinate cc : chunks.keySet()) {
				x1 = cc.x < x1 ? cc.x : x1;
				x2 = cc.x > x2 ? cc.x : x2;
				y1 = cc.y < y1 ? cc.y : y1;
				y2 = cc.y > y2 ? cc.y : y2;
			}
			
			lb.setWidth((x2 - x1 + 1)*chunkSize);
			lb.setHeight((y2 - y1 + 1)*chunkSize);
			
			int[] data = new int[lb.getWidth()*lb.getHeight()];
			for (Map.Entry<ChunkCoordinate, TileChunk> entry : chunks.entrySet()) {
				ChunkCoordinate cc = entry.getKey();
				TileChunk  tc = entry.getValue();
				int offset = ((cc.x - x1)*chunkSize) + ((x2 - x1 + 1)*chunkSize)*((cc.y - y1)*chunkSize);
				for (int i = 0; i < tc.size*tc.size; i++) {
					data[i+offset] = tc.getId(i);
				}
			}
			lb.setData(data);
			
		}
		
		return lb;
	}


}

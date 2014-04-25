package bakpakin.egf.tilemap;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import bakpakin.egf.tilemap.TileMapBean.LayerBean;
import bakpakin.egf.tilemap.TileMapBean.ObjectBean;
import bakpakin.egf.tilemap.TileMapBean.TileSetBean;
import bakpakin.egf.util.AssetManager;

/**
 * A general purpose implementation of a tile map, with easy saving and loading via a {@link TileMapBean} class.
 * Supports multiple layers, infinite tile grids, multiple tile sets and level data. <code>TileMap</code>s can be
 * created and modified in code, or loaded from external files.
 * 
 * @author Calvin
 * 
 * @see TileMapBean
 *
 */
public class TileMap extends TileMapComponent {

	/**
	 * How much extra padding the tile look up table gets when it gets resized.
	 */
	private static final float LOOKUP_OVERSIZE_FACTOR = .75f;

	/**
	 * A static method that creates a {@link TileMap} from
	 * a file.
	 * @param file - the file with the map data
	 * @param loader - the {@link TileMapLoader} to read the data with
	 * @return a new {@link tileMap}
	 * @throws IOException
	 */
	public static TileMap load(File file, TileMapLoader loader) throws IOException {
		AssetManager.initProtocols();
		return load(file.toURI().toURL(), loader);
	}

	/**
	 * A static method that creates a {@link TileMap} from
	 * a url. Prefix the URL with the protocol
	 * "classpath:" to load a class resource. For example,
	 * <p>
	 * <code>TileMap.load("classpath:org/example/tilemaps/foo.json", new JSONLoader())</code>
	 * </p>
	 * @param url - the location of the map data
	 * @param loader - the {@link TileMapLoader} to read the data with
	 * @return a new {@link tileMap}
	 * @throws IOException
	 */
	public static TileMap load(String url, TileMapLoader loader) throws IOException {
		return load(AssetManager.stringToURL(url), loader);
	}

	/**
	 * A static method that creates a {@link TileMap} from
	 * a {@link URL}.
	 * @param url - the location of the map data
	 * @param loader - the {@link TileMapLoader} to read the data with
	 * @return a new {@link tileMap}
	 * @throws IOException
	 */
	public static TileMap load(URL url, TileMapLoader loader) throws IOException {
		TileMapBean template = loader.load(url);
		return readTemplate(template, url);
	}

	/**
	 * Creates a new {@link TileMap} from a {@link TileMapBean}.
	 * @param t - template
	 * @return the new <code>TileMap</code>.
	 */
	public static TileMap readTemplate(TileMapBean t, URL mapFileUrl) {
		TileMap tileMap = new TileMap(t.getTilewidth(), t.getTileheight());
		tileMap.getProperties().putAll(t.getProperties());

		for (TileSetBean tt : t.getTilesets()) {
			TileSet ts = readTemplate(tt, t.getURL());
			tileMap.addTileSet(ts);
		}

		float depth = -1;
		for (LayerBean lt : t.getLayers()) {		

			if (t.getProperties().containsKey("depth")) {
				depth = Float.parseFloat(t.getProperties().get("depth"));
			} else {
				//increment the depth to the next free depth
				while (tileMap.layers.containsKey(++depth));
			}
			Layer l = readTemplate(lt, tileMap, depth, mapFileUrl);
			tileMap.addLayer(l);
		}
		return tileMap;
	}

	/**
	 * Creates a new {@link Layer} from a {@link TileMapBean.LayerBean}.
	 * @param t - template
	 * @param tm - {@link TileMap} to get tile data from.
	 * @param depth - depth to give new {@link Layer}
	 * @return a new {@link Layer}
	 */
	private static Layer readTemplate(LayerBean t, TileMap tm, float depth, URL mapFileUrl) {
		Layer ret = null;
		if (t.getType().equals("tilelayer")) {
			if (t.getProperties() != null && t.getProperties().containsKey("chunksize")) {
				ret = new TileLayer(depth, tm , Integer.parseInt(t.getProperties().get("chunksize")));
			} else {
				ret = new TileLayer(depth, tm);
			}
			initLayer(ret, t);

			TileLayer tl = ((TileLayer)ret);
			for (int x = 0; x < t.getWidth(); x++) {
				for (int y = 0; y < t.getHeight(); y++) {

					int gid = t.getData()[y*t.getWidth() + x];

					tl.set(x, y, gid);
				}
			}
		} else if (t.getType().equals("objectgroup")) {
			ret = new ObjectLayer(depth, tm);
			ObjectLayer ol = (ObjectLayer)ret;
			for (Object o : t.getObjects()) {
				ol.addMapObject((ObjectBean) o);
			}
			initLayer(ret, t);
		} else if (t.getType().equals("imagelayer")) {
			ret = new ImageLayer(depth, tm);
			initLayer(ret, t);
			ImageLayer il = (ImageLayer) ret;
			il.setImage(t.getImage(), mapFileUrl);
		}
		return ret;
	}

	/**
	 * Initiates a {@link Layer} from a {@link TileMapBean.LayerBean}.
	 * @param l - {@link Layer} to initiate
	 * @param t - template
	 */
	private static void initLayer(Layer l, LayerBean t) {
		l.setHeight(t.getHeight());
		l.setWidth(t.getWidth());
		l.setX(t.getX());
		l.setY(t.getY());
		l.setVisible(t.isVisible());
		l.setOpacity(t.getOpacity());
		l.setName(t.getName());
		if (t.getProperties() != null)
			l.getProperties().putAll(t.getProperties());
	}

	/**
	 * Creates a new {@link TileSet} from a {@link TileMapBean.TileSetBean} template.
	 * @param t - template
	 * @return new {@link TileSet}.
	 */
	private static TileSet readTemplate(TileSetBean t, URL mapUrl) {
		URL url = TileMapComponent.getResource(t.getImage(), mapUrl);
		TileSet tileSet = new TileSet(url, t.getName(), t.getTilewidth(), t.getTileheight());
		tileSet.setMargin(t.getMargin());
		tileSet.setxSeperation(t.getSpacing());
		tileSet.setySeperation(t.getSpacing());
		tileSet.setTileProperties(t.getTileProperties());
		if (t.getTransparentcolor() != null) {
			tileSet.setTransparentColor(Integer.parseInt(t
					.getTransparentcolor().substring(1), 16));
		}
		if (t.getTileoffset() != null) {
			tileSet.setxOffset(t.getTileoffset().get("x"));
			tileSet.setyOffset(t.getTileoffset().get("y"));
		}
		return tileSet;
	}
	
	/**
	 * 
	 * @return
	 */
	public TileMapBean writeTemplate() {
		TileMapBean tmb = new TileMapBean();
		tmb.setHeight(0);
		tmb.setWidth(0);
		tmb.setOrientation("orthagonal");
		tmb.setLayers(new LayerBean[layers.size()]);
		Map<String, String> newProperties = new HashMap<String, String>();
		newProperties.putAll(getProperties());
		tmb.setProperties(newProperties);
		tmb.setTileheight(tileHeight);
		tmb.setTilewidth(tileWidth);
		tmb.setTilesets(new TileSetBean[tileSets.size()]);
		tmb.setLayers(new LayerBean[layers.size()]);
		
		int i;
		
		i = 0;
		for (TileSet tileSet : tileSets.values()) {
			tmb.getTilesets()[i] = tileSet.writeTemplate();
			i++;
		}
		
		i = 0;
		for (Layer layer : layers.values()) {
			tmb.getLayers()[i] = layer.writeTemplate();
			i++;
		}
		
		return tmb;
	}

	/**
	 * The width in pixels of each tile
	 */
	private int tileWidth;

	/**
	 * The height in pixels of each tile
	 */
	private int tileHeight;

	/**
	 * A map of the layers sorted by depth.
	 */
	private SortedMap<Float ,Layer> layers;

	/**
	 * A map of the layers allowing for access by name.
	 */
	private Map<String, Layer> layersByName;

	/**
	 * A map of the tileSets allowing for access by name.
	 */
	private Map<String, TileSet> tileSets;
	
	/**
	 * A map of the tileSets to their first global tile ids
	 */
	private Map<TileSet, Integer> tileSetFirstIds;

	/**
	 * A dynamic lookup table for {@link TileSet}s.
	 */
	private TileLookUp[] tileSetLookUp;

	/**
	 * The next available tileId.
	 */
	private int nextTileId;

	/**
	 * Simple class used for tile lookup based on a tile ID.
	 * @author Calvin
	 *
	 */
	private static class TileLookUp {
		public TileLookUp(TileSet tileSet, int localIndex) {
			this.tileSet = tileSet;
			this.localIndex = localIndex;
		}
		public int localIndex;
		public TileSet tileSet;
		@Override
		public String toString() {
			if (tileSet == null) {
				return "[EmptyTile]";
			}
			return "[" + tileSet.getName() + ", " + localIndex + "]";
		}
	}

	/**
	 * Creates a new empty {@link TileMap} with specified tile size.
	 * @param tileWidth
	 * @param tileHeight
	 */
	public TileMap(int tileWidth, int tileHeight) {
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.layers = new TreeMap<Float, Layer>();
		this.layersByName = new HashMap<String, Layer>();
		this.tileSets = new HashMap<String, TileSet>();
		this.tileSetFirstIds = new HashMap<TileSet, Integer>();
		this.tileSetLookUp = new TileLookUp[100];
		tileSetLookUp[0] = new TileLookUp(null, 0);
		nextTileId = 1;
	}

	/**
	 * @param tileId
	 * @return the {@link TileSet} associated with a certain tileId
	 */
	public TileSet getTileSet(int tileId) {
		return tileSetLookUp[tileId].tileSet;
	}

	/**
	 * @param tileId
	 * @return the index of a tile in its {@link TileSet}
	 */
	public int getLocalIndex(int tileId) {
		return tileSetLookUp[tileId].localIndex;
	}
	
	/**
	 * Returns the global id associated with a specific tile.
	 * @param tileSet
	 * @param localIndex
	 * @return the global id or -1 if tile has no global id
	 */
	public int getGlobalIndex(TileSet tileSet, int localIndex) {
		Integer fid = tileSetFirstIds.get(tileSet);
		if (fid == null)
			return -1;
		return fid + localIndex;
	}

	/**
	 * @param id
	 * @return the tile associated with a certain id in this map, or null
	 */
	public Tile getTile(int id) {
		if (id < 0 || id >= tileSetLookUp.length)
			return null;
		TileLookUp pair = tileSetLookUp[id];
		if (pair.tileSet == null)
			return null;
		return pair.tileSet.getTile(pair.localIndex);
	}

	/**
	 * Re-binds all Tiles with a certain ID in a constant time operation.
	 * Very fast, probably good for animating tiles.
	 * @param id
	 * @param tile
	 */
	public void rebindID(int id, Tile tile) {
		TileLookUp pair = tileSetLookUp[id];
		pair.localIndex = tile.localIndex;
		pair.tileSet = tile.tileSet;
	}

	/**
	 * @return the height of tiles in world space
	 */
	public int getTileHeight() {
		return tileHeight;
	}

	/**
	 * Sets the height of tiles in world space
	 * @param tileHeight
	 */
	public void setTileHeight(int tileHeight) {
		this.tileHeight = tileHeight;
	}

	/**
	 * @return the width of tiles in world space
	 */
	public int getTileWidth() {
		return tileWidth;
	}

	/**
	 * Sets the width of tiles in world space.
	 * @param tileWidth
	 */
	public void setTileWidth(int tileWidth) {
		this.tileWidth = tileWidth;
	}

	/**
	 * Gets the {@link Layer} at the specified depth or null if there is no <code>Layer</code>.
	 * @param depth
	 * @return a {@link Layer} or null
	 */
	public Layer getLayer(float depth) {
		return layers.get(depth);
	}

	/**
	 * Gets the {@link Layer} with the specified name or null if there is no <code>Layer</code>.
	 * @param name
	 * @return a {@link Layer} or null
	 */
	public Layer getLayer(String name) {
		return layersByName.get(name);
	}

	/**
	 * Adds a {@link Layer} to the map.
	 * @param layer
	 */
	public void addLayer(Layer layer) {
		layers.put(layer.getDepth(), layer);
		layersByName.put(layer.getName(), layer);
	}

	/**
	 * Removes a {@link Layer} from the map at the specified depth. 
	 * @param depth
	 * @return the <code>Layer</code> that was removed or null if there was no such <code>Layer</code>.
	 */
	public Layer removeLayer(float depth) {
		Layer ret = layers.remove(depth);
		if (ret != null) {
			layersByName.remove(ret.getName());
		}
		return ret;
	}

	/**
	 * Removes a {@link Layer} from the map with the specified name. 
	 * @param name
	 * @return the <code>Layer</code> that was removed or null if there was no such <code>Layer</code>.
	 */
	public Layer removeLayer(String name) {
		Layer ret = layersByName.remove(name);
		if (ret != null) {
			layers.remove(ret.getDepth());
		}
		return ret;
	}

	/**
	 * Gets the {@link TileSet} with the given name or null if none exists
	 * @param name
	 * @return a {@link TileSet} or null
	 */
	public TileSet getTileSet(String name) {
		return tileSets.get(name);
	}

	/**
	 * Adds a {@link TileSet} to this.
	 * @param tileSet
	 */
	public void addTileSet(TileSet tileSet) {
		tileSets.put(tileSet.getName(), tileSet);
		int i = nextTileId;
		tileSetFirstIds.put(tileSet, nextTileId);
		nextTileId += tileSet.getWidth() * tileSet.getHeight();
		if (tileSetLookUp.length < nextTileId) {
			//resize the lookup table
			tileSetLookUp = Arrays.copyOf(tileSetLookUp, (int)(nextTileId*(1 + LOOKUP_OVERSIZE_FACTOR) + 1));
		}
		for (int j = 0; i < nextTileId; i++, j++) {
			tileSetLookUp[i] = new TileLookUp(tileSet, j);
		}
	}

	@SuppressWarnings("unused")
	private void trimLookUpTable() {
		if (tileSetLookUp.length > nextTileId)
			tileSetLookUp = Arrays.copyOf(tileSetLookUp, nextTileId);
	}

	/**
	 * @return an unmodifiable view of the layers of the tile-map in order of depth from least to greatest.
	 */
	public Collection<Layer> getLayers() {
		return Collections.unmodifiableCollection(layers.values());
	}

	/**
	 * @return an unmodifiable map of <code>TileSet</code>s keyed by their names
	 * @see TileSet
	 */
	public Map<String, TileSet> getTileSets() {
		return Collections.unmodifiableMap(tileSets);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TileMap [tileHeight=");
		builder.append(tileHeight);
		builder.append(", tileWidth=");
		builder.append(tileWidth);
		builder.append(", layers=");
		builder.append(layers);
		builder.append(", tileSets=");
		builder.append(tileSets);
		builder.append("]");
		return builder.toString();
	}	

}

package bakpakin.egf.util.tilemap;

import java.io.IOException;
import java.net.URL;

/**
 * An interface that allows flexible loading of {@link TileMap}s.
 * @author Calvin
 *
 */
public interface TileMapLoader {
	
	/**
	 * Fetches data from a {@link URL} and returns a template that can be used to construct a new {@link TileMap}.
	 * @param url
	 * @return a new {@link TileMapBean} that can be used to construct a {@link TileMap}
	 */
	TileMapBean load(URL url) throws IOException;

}

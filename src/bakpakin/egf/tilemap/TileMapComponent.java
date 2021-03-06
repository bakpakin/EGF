package bakpakin.egf.tilemap;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * A component of a {@link TileMap}. Contains a map of properties for custom use.
 * @author Calvin
 * @see TileMap
 */
public abstract class TileMapComponent {
	
	/**
	 * This method handles the format in which Tiled stores references to its resources.
	 * @param resourcePath a String to a resource
	 * @param basedir - the URL of the file in which the resource path comes from.
	 * @return a URL to the resource in the Tiled Map file.
	 */
	public static URL getResource(String resourcePath, URL basedir) {
		URL url = null;
		if (resourcePath.matches(".+:{1}.+")) {// if resourcePath is a url with protocol
			try {
				url = new URL(resourcePath);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		} else {//assume it is relative file path
			try {
				url = new URL(basedir, resourcePath);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		return url;
	}

	private Map<String , String> properties;

	/**
	 * Creates a new {@link TileMapComponent}
	 */
	public TileMapComponent() {
		properties = new HashMap<String, String>();
	}

	/**
	 * @return the map of the properties of this {@link TileMapComponent}.
	 */
	public Map<String, String> getProperties() {
		return properties;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((properties == null) ? 0 : properties.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof TileMapComponent))
			return false;
		TileMapComponent other = (TileMapComponent) obj;
		if (properties == null) {
			if (other.properties != null)
				return false;
		} else if (!properties.equals(other.properties))
			return false;
		return true;
	}

}

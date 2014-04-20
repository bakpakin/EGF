package bakpakin.egf.util.tilemap;

import java.io.Serializable;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;

/**
 * A Java Bean that holds the data needed to construct a {@link TileMap}.
 * @author Calvin
 *
 */
public class TileMapBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6291780693122224652L;
	
	private URL url;

	private int width;
	private int height;
	private String orientation;
	private Map<String, String> properties;
	private int tilewidth;
	private int tileheight;
	private LayerBean[] layers;
	private TileSetBean[] tilesets;

	private int hashCode;

	public URL getURL() {
		return url;
	}

	public void setURL(URL url) {
		this.url = url;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getOrientation() {
		return orientation;
	}

	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

	public int getTilewidth() {
		return tilewidth;
	}

	public void setTilewidth(int tilewidth) {
		this.tilewidth = tilewidth;
	}

	public int getTileheight() {
		return tileheight;
	}

	public void setTileheight(int tileheight) {
		this.tileheight = tileheight;
	}

	public LayerBean[] getLayers() {
		return layers;
	}

	public void setLayers(LayerBean[] layers) {
		this.layers = layers;
	}

	public TileSetBean[] getTilesets() {
		return tilesets;
	}

	public void setTilesets(TileSetBean[] tilesets) {
		this.tilesets = tilesets;
	}

	@Override
	public int hashCode() {
		if (hashCode == 0) {
			final int prime = 97;
			int result = 1;
			result = prime * result
					+ ((url == null) ? 0 : url.hashCode());
			result = prime * result + height;
			result = prime * result + Arrays.hashCode(layers);
			result = prime * result
					+ ((orientation == null) ? 0 : orientation.hashCode());
			result = prime * result
					+ ((properties == null) ? 0 : properties.hashCode());
			result = prime * result + tileheight;
			result = prime * result + Arrays.hashCode(tilesets);
			result = prime * result + tilewidth;
			result = prime * result + width;
			hashCode = result;
		}
		return hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (obj.hashCode() != hashCode()) {
			return false;
		}
		if (!(obj instanceof TileMapBean))
			return false;
		TileMapBean other = (TileMapBean) obj;
		if (!url.equals(other.url)) {
			return false;
		}
		if (height != other.height)
			return false;
		if (!Arrays.equals(layers, other.layers))
			return false;
		if (orientation == null) {
			if (other.orientation != null)
				return false;
		} else if (!orientation.equals(other.orientation))
			return false;
		if (properties == null) {
			if (other.properties != null)
				return false;
		} else if (!properties.equals(other.properties))
			return false;
		if (tileheight != other.tileheight)
			return false;
		if (!Arrays.equals(tilesets, other.tilesets))
			return false;
		if (tilewidth != other.tilewidth)
			return false;
		if (width != other.width)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TileMapBean [");
		if (url != null) {
			builder.append("url=");
			builder.append(url);
			builder.append(", ");
		}
		builder.append("width=");
		builder.append(width);
		builder.append(", height=");
		builder.append(height);
		builder.append(", ");
		if (orientation != null) {
			builder.append("orientation=");
			builder.append(orientation);
			builder.append(", ");
		}
		if (properties != null) {
			builder.append("properties=");
			builder.append(properties);
			builder.append(", ");
		}
		builder.append("tilewidth=");
		builder.append(tilewidth);
		builder.append(", tileheight=");
		builder.append(tileheight);
		builder.append(", ");
		if (layers != null) {
			builder.append("layers=");
			builder.append(Arrays.toString(layers));
			builder.append(", ");
		}
		if (tilesets != null) {
			builder.append("tilesets=");
			builder.append(Arrays.toString(tilesets));
		}
		builder.append("]");
		return builder.toString();
	}

	/**
	 * A Java Bean that holds that data needed to construct a {@link Layer}.
	 * @author Calvin
	 *
	 */
	public static class LayerBean implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1416838465779361936L;

		private int [] data;
		private int width;
		private int height;
		private float opacity;
		private String type;
		private String name;
		private String image;
		private ObjectBean[] objects;
		private boolean visible;
		private Map<String, String> properties;
		private int x;
		private int y;

		private int hashCode;

		public int[] getData() {
			return data;
		}
		public void setData(int[] data) {
			this.data = data;
		}
		public int getWidth() {
			return width;
		}
		public void setWidth(int width) {
			this.width = width;
		}
		public int getHeight() {
			return height;
		}
		public void setHeight(int height) {
			this.height = height;
		}
		public float getOpacity() {
			return opacity;
		}
		public void setOpacity(float opacity) {
			this.opacity = opacity;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getImage() {
			return image;
		}
		public void setImage(String image) {
			this.image = image;
		}
		public Object[] getObjects() {
			return objects;
		}
		public void setObjects(ObjectBean[] objects) {
			this.objects = objects;
		}
		public boolean isVisible() {
			return visible;
		}
		public void setVisible(boolean visible) {
			this.visible = visible;
		}
		public Map<String, String> getProperties() {
			return properties;
		}
		public void setProperties(Map<String, String> properties) {
			this.properties = properties;
		}
		public int getX() {
			return x;
		}
		public void setX(int x) {
			this.x = x;
		}
		public int getY() {
			return y;
		}
		public void setY(int y) {
			this.y = y;
		}
		@Override
		public int hashCode() {
			if (hashCode == 0) {
				final int prime = 73;
				int result = 1;
				result = prime * result + Arrays.hashCode(data);
				result = prime * result + height;
				result = prime * result + ((image == null) ? 0 : image.hashCode());
				result = prime * result + ((name == null) ? 0 : name.hashCode());
				result = prime * result + Arrays.hashCode(objects);
				result = prime * result + Float.floatToIntBits(opacity);
				result = prime * result
						+ ((properties == null) ? 0 : properties.hashCode());
				result = prime * result + ((type == null) ? 0 : type.hashCode());
				result = prime * result + (visible ? 1231 : 1237);
				result = prime * result + width;
				result = prime * result + x;
				result = prime * result + y;
				hashCode = result;
			}
			return hashCode;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (obj.hashCode() != hashCode()) {
				return false;
			}
			if (!(obj instanceof LayerBean))
				return false;
			LayerBean other = (LayerBean) obj;
			if (!Arrays.equals(data, other.data))
				return false;
			if (height != other.height)
				return false;
			if (image == null) {
				if (other.image != null)
					return false;
			} else if (!image.equals(other.image))
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			if (!Arrays.equals(objects, other.objects))
				return false;
			if (Float.floatToIntBits(opacity) != Float
					.floatToIntBits(other.opacity))
				return false;
			if (properties == null) {
				if (other.properties != null)
					return false;
			} else if (!properties.equals(other.properties))
				return false;
			if (type == null) {
				if (other.type != null)
					return false;
			} else if (!type.equals(other.type))
				return false;
			if (visible != other.visible)
				return false;
			if (width != other.width)
				return false;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("LayerBean [");
			if (data != null) {
				builder.append("data=");
				builder.append(Arrays.toString(data));
				builder.append(", ");
			}
			builder.append("width=");
			builder.append(width);
			builder.append(", height=");
			builder.append(height);
			builder.append(", opacity=");
			builder.append(opacity);
			builder.append(", ");
			if (type != null) {
				builder.append("type=");
				builder.append(type);
				builder.append(", ");
			}
			if (name != null) {
				builder.append("name=");
				builder.append(name);
				builder.append(", ");
			}
			if (image != null) {
				builder.append("image=");
				builder.append(image);
				builder.append(", ");
			}
			if (objects != null) {
				builder.append("objects=");
				builder.append(Arrays.toString(objects));
				builder.append(", ");
			}
			builder.append("visible=");
			builder.append(visible);
			builder.append(", ");
			if (properties != null) {
				builder.append("properties=");
				builder.append(properties);
				builder.append(", ");
			}
			builder.append("x=");
			builder.append(x);
			builder.append(", y=");
			builder.append(y);
			builder.append("]");
			return builder.toString();
		}

	}

	/**
	 * A Java Bean that holds the data needed to construct a {@link TileSet}.
	 * 
	 * @author Calvin
	 *
	 */
	public static class TileSetBean implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = -84070573999989431L;

		private int firstgid;
		private String image;
		private int imagewidth;
		private int imageheight;
		private int margin;
		private String name;
		private Map<String, String> properties;
		private int spacing;
		private int tilewidth;
		private int tileheight;
		private Map<String, Integer> tileoffset;
		private String transparentcolor;

		private int hashCode;

		public int getFirstgid() {
			return firstgid;
		}
		public void setFirstgid(int firstgid) {
			this.firstgid = firstgid;
		}
		public String getImage() {
			return image;
		}
		public void setImage(String image) {
			this.image = image;
		}
		public int getImagewidth() {
			return imagewidth;
		}
		public void setImagewidth(int imagewidth) {
			this.imagewidth = imagewidth;
		}
		public int getImageheight() {
			return imageheight;
		}
		public void setImageheight(int imageheight) {
			this.imageheight = imageheight;
		}
		public int getMargin() {
			return margin;
		}
		public void setMargin(int margin) {
			this.margin = margin;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Map<String, String> getProperties() {
			return properties;
		}
		public void setProperties(Map<String, String> properties) {
			this.properties = properties;
		}
		public int getSpacing() {
			return spacing;
		}
		public void setSpacing(int spacing) {
			this.spacing = spacing;
		}
		public int getTilewidth() {
			return tilewidth;
		}
		public void setTilewidth(int tilewidth) {
			this.tilewidth = tilewidth;
		}
		public int getTileheight() {
			return tileheight;
		}
		public void setTileheight(int tileheight) {
			this.tileheight = tileheight;
		}
		public Map<String, Integer> getTileoffset() {
			return tileoffset;
		}
		public void setTileoffset(Map<String, Integer> tileoffset) {
			this.tileoffset = tileoffset;
		}
		public String getTransparentcolor() {
			return transparentcolor;
		}
		public void setTransparentcolor(String transparentcolor) {
			this.transparentcolor = transparentcolor;
		}
		@Override
		public int hashCode() {
			if (hashCode == 0) {
				final int prime = 127;
				int result = 1;
				result = prime * result + firstgid;
				result = prime * result + ((image == null) ? 0 : image.hashCode());
				result = prime * result + imageheight;
				result = prime * result + imagewidth;
				result = prime * result + margin;
				result = prime * result + ((name == null) ? 0 : name.hashCode());
				result = prime * result
						+ ((properties == null) ? 0 : properties.hashCode());
				result = prime * result + spacing;
				result = prime * result + tileheight;
				result = prime * result
						+ ((tileoffset == null) ? 0 : tileoffset.hashCode());
				result = prime * result + tilewidth;
				result = prime
						* result
						+ ((transparentcolor == null) ? 0 : transparentcolor
								.hashCode());
				hashCode = result;
			}
			return hashCode;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (obj.hashCode() != hashCode()) {
				return false;
			}
			if (!(obj instanceof TileSetBean))
				return false;
			TileSetBean other = (TileSetBean) obj;
			if (firstgid != other.firstgid)
				return false;
			if (image == null) {
				if (other.image != null)
					return false;
			} else if (!image.equals(other.image))
				return false;
			if (imageheight != other.imageheight)
				return false;
			if (imagewidth != other.imagewidth)
				return false;
			if (margin != other.margin)
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			if (properties == null) {
				if (other.properties != null)
					return false;
			} else if (!properties.equals(other.properties))
				return false;
			if (spacing != other.spacing)
				return false;
			if (tileheight != other.tileheight)
				return false;
			if (tileoffset == null) {
				if (other.tileoffset != null)
					return false;
			} else if (!tileoffset.equals(other.tileoffset))
				return false;
			if (tilewidth != other.tilewidth)
				return false;
			if (transparentcolor == null) {
				if (other.transparentcolor != null)
					return false;
			} else if (!transparentcolor.equals(other.transparentcolor))
				return false;
			return true;
		}
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("TileSetBean [firstgid=");
			builder.append(firstgid);
			builder.append(", ");
			if (image != null) {
				builder.append("image=");
				builder.append(image);
				builder.append(", ");
			}
			builder.append("imagewidth=");
			builder.append(imagewidth);
			builder.append(", imageheight=");
			builder.append(imageheight);
			builder.append(", margin=");
			builder.append(margin);
			builder.append(", ");
			if (name != null) {
				builder.append("name=");
				builder.append(name);
				builder.append(", ");
			}
			if (properties != null) {
				builder.append("properties=");
				builder.append(properties);
				builder.append(", ");
			}
			builder.append("spacing=");
			builder.append(spacing);
			builder.append(", tilewidth=");
			builder.append(tilewidth);
			builder.append(", tileheight=");
			builder.append(tileheight);
			builder.append(", ");
			if (tileoffset != null) {
				builder.append("tileoffset=");
				builder.append(tileoffset);
				builder.append(", ");
			}
			if (transparentcolor != null) {
				builder.append("transparentcolor=");
				builder.append(transparentcolor);
			}
			builder.append("]");
			return builder.toString();
		}

	}
	
	public static class ObjectBean implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 4766609995670640706L;
		
		private Map<String, String> properties;
		private int height;
		private int width;
		private int x;
		private int y;
		private boolean visible;
		private int gid;
		private Map<String, Integer>[] polyline;
		private String name;
		private String type;
		private boolean ellipse;
		
		public boolean isEllipse() {
			return ellipse;
		}
		public void setEllipse(boolean ellipse) {
			this.ellipse = ellipse;
		}
		private int hashCode;
		
		public Map<String, String> getProperties() {
			return properties;
		}
		public void setProperties(Map<String, String> properties) {
			this.properties = properties;
		}
		public int getHeight() {
			return height;
		}
		public void setHeight(int height) {
			this.height = height;
		}
		public int getWidth() {
			return width;
		}
		public void setWidth(int width) {
			this.width = width;
		}
		public int getX() {
			return x;
		}
		public void setX(int x) {
			this.x = x;
		}
		public int getY() {
			return y;
		}
		public void setY(int y) {
			this.y = y;
		}
		public boolean isVisible() {
			return visible;
		}
		public void setVisible(boolean visible) {
			this.visible = visible;
		}
		public int getGid() {
			return gid;
		}
		public void setGid(int gid) {
			this.gid = gid;
		}
		public Map<String, Integer>[] getPolyline() {
			return polyline;
		}
		public void setPolyline(Map<String, Integer>[] polyline) {
			this.polyline = polyline;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		
		@Override
		public int hashCode() {
			if (hashCode == 0) {
			final int prime = 31;
			int result = 1;
			result = prime * result + height;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result + Arrays.hashCode(polyline);
			result = prime * result
					+ ((properties == null) ? 0 : properties.hashCode());
			result = prime * result + ((type == null) ? 0 : type.hashCode());
			result = prime * result + (visible ? 1231 : 1237);
			result = prime * result + gid;
			result = prime * result + width;
			result = prime * result + x;
			result = prime * result + y;
			result = prime * result + (ellipse ? 1543 : 3019);
			hashCode = result;
			}
			return hashCode;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (obj.hashCode() != hashCode()) 
				return false;
			if (!(obj instanceof ObjectBean))
				return false;
			ObjectBean other = (ObjectBean) obj;
			if (height != other.height)
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			if (!Arrays.equals(polyline, other.polyline))
				return false;
			if (properties == null) {
				if (other.properties != null)
					return false;
			} else if (!properties.equals(other.properties))
				return false;
			if (type == null) {
				if (other.type != null)
					return false;
			} else if (!type.equals(other.type))
				return false;
			if (visible != other.visible)
				return false;
			if (gid != other.gid)
				return false;
			if (width != other.width)
				return false;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			if (ellipse != other.ellipse)
				return false;
			return true;
		}
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("ObjectBean [");
			if (properties != null) {
				builder.append("properties=");
				builder.append(properties);
				builder.append(", ");
			}
			builder.append("height=");
			builder.append(height);
			builder.append(", width=");
			builder.append(width);
			builder.append(", x=");
			builder.append(x);
			builder.append(", y=");
			builder.append(y);
			builder.append(", visible=");
			builder.append(visible);
			builder.append(", gid=");
			builder.append(gid);
			builder.append(", ");
			if (polyline != null) {
				builder.append("polyline=");
				builder.append(Arrays.toString(polyline));
				builder.append(", ");
			}
			if (name != null) {
				builder.append("name=");
				builder.append(name);
				builder.append(", ");
			}
			if (type != null) {
				builder.append("type=");
				builder.append(type);
				builder.append(", ");
			}
			builder.append(", ellipse=");
			builder.append(ellipse);
			builder.append("]");
			return builder.toString();
		}
		
		
		
	}

}

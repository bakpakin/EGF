package bakpakin.egf.util.tilemap;

import java.util.HashMap;
import java.util.Map;

import bakpakin.egf.util.tilemap.TileMapBean.LayerBean;

/**
 * A subclass of <code>Layer</code> that is used primarily to represent geometric objects, such
 * as collision boundaries, and other images that don't fit into a tilemap. These objects are 
 * represented with the {@link MapObject} class. The user can also use MapObjects to represent
 * whatever they want.
 * @author Calvin
 *
 */
public class ObjectLayer extends Layer {
	
	/**
	 * 
	 */
	private Map<String, MapObject> mapObjects;

	/**
	 * Creates a new <code>ObjectLayer</code> at the specified depth.
	 * @param depth
	 * @param map
	 */
	public ObjectLayer(float depth, TileMap map) {
		super(depth, map);
		this.mapObjects = new HashMap<String, MapObject>();
	}
	
	/**
	 * 
	 * @param obj
	 */
	public void addMapObject(MapObject obj) {
		
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public MapObject removeMapObject(String name) {
		return mapObjects.remove(name);
	}
	
	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ObjectLayer [depth=");
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
		LayerBean lb =  super.writeTemplate();
		lb.setType("objectgroup");
		return lb;
	}
	
	
	
}

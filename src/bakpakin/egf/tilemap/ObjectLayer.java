package bakpakin.egf.tilemap;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import bakpakin.egf.framework.Entity;
import bakpakin.egf.framework.World;
import bakpakin.egf.geom.Transform;
import bakpakin.egf.physics.DeltaTransform;
import bakpakin.egf.render.Drawable;
import bakpakin.egf.render.RenderComponent;
import bakpakin.egf.tilemap.TileMapBean.LayerBean;
import bakpakin.egf.tilemap.TileMapBean.ObjectBean;

/**
 * A subclass of <code>Layer</code> that is used primarily to represent geometric objects, such
 * as collision boundaries, and other images that don't fit into a tilemap. These objects are 
 * represented with the {@link ObjectBean} class. The user can also use ObjectBeans to represent
 * whatever they want.
 * @author Calvin
 *
 */
public class ObjectLayer extends Layer {
	
	/**
	 * 
	 */
	private Map<String, ObjectBean> mapObjects;

	/**
	 * Creates a new <code>ObjectLayer</code> at the specified depth.
	 * @param depth
	 * @param map
	 */
	public ObjectLayer(float depth, TileMap map) {
		super(depth, map);
		this.mapObjects = new HashMap<String, ObjectBean>();
	}
	
	/**
	 * 
	 * @param obj
	 */
	public void addMapObject(ObjectBean obj) {
		mapObjects.put(obj.getName(), obj);
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public ObjectBean removeMapObject(String name) {
		return mapObjects.remove(name);
	}
	
	/**
	 * Adds objects in the layer as entities to the engine.
	 * @param world
	 * @return
	 */
	public List<Entity> addObjectsAsEntities(World world) {
		List<Entity> ret = new LinkedList<Entity>();
		for (ObjectBean b : mapObjects.values()) {
			Entity e = new Entity();
			Transform t = new Transform();
			t.setX(b.getX() + x);
			t.setY(b.getY() + y);
			e.add(t);
			e.add(new DeltaTransform());
			e.addTag(b.getType());
			e.setProperty("name", b.getName());
			for(Map.Entry<String, String> entry : b.getProperties().entrySet()) {
				e.setProperty(entry.getKey(), entry.getValue());
			}
			if (b.isVisible()) {
				RenderComponent rc = new RenderComponent(new Drawable[0]);
				Object _depth = b.getProperties().get("depth");
				if (_depth != null) {
					rc.setDepth((Float)_depth);
				}
				if (b.getGid() == 0) {
					
				} else {
					TileDrawer td = new TileDrawer(this.getMap().getTile(b.getGid()));
					rc.setDrawable(td);
				}
				e.add(rc);
			}
			world.add(e);
			ret.add(e);
		}
		return ret;
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

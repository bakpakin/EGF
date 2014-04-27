package bakpakin.egf.tilemap;

import bakpakin.egf.framework.Entity;

public interface TileCollisionResponse {
	
	public void collide(Entity e, Tile tile);

}

package bakpakin.egf.tilemap;

import bakpakin.egf.physics.BoxCollider;
import bakpakin.egf.physics.DeltaTransform;
import bakpakin.egf.framework.Entity;
import bakpakin.egf.framework.Matcher;
import bakpakin.egf.geom.AxisAlignedBox;
import bakpakin.egf.geom.Transform;

public class PlatformingSystem extends bakpakin.egf.framework.ProcessingSystem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2648226284655078126L;

	private TileMap tileMap;

	private TileLayer collisionLayer;

	public PlatformingSystem(TileMap tileMap, String collisionLayer) {
		super();
		this.setTileMap(tileMap);
		this.setCollisionLayer(collisionLayer);
	}

	@Override
	public void update(Entity e) {
		Grounded g = e.get(Grounded.class);
		if (g == null) {
			e.add(g = new Grounded(false));
		} else {
			g.grounded = false;
		}
		Transform t = e.get(Transform.class);
		BoxCollider bc = e.get(BoxCollider.class);
		DeltaTransform dt = e.get(DeltaTransform.class);
		collideWorld(t, bc, dt, g, 3);
	}

	//Lol unreadable
	private boolean collideWorld(Transform t, BoxCollider bc, DeltaTransform deltaTransform, Grounded g, int count) {

		float dx = deltaTransform.getX() * (float)getWorld().getDelta();
		float dy = deltaTransform.getY() * (float)getWorld().getDelta();
		if (count <= 0) {
			return false;
		}
		AxisAlignedBox hb = bc.getHitbox(t);

		float[] pos0 = {hb.getOriginX(), hb.getOriginY()};
		float[] extents = {hb.getWidth()*.5f, hb.getHeight()*.5f};
		float[] pos1 = {pos0[0] + dx, pos0[1] + dy};

		int minx, maxx, miny, maxy;

		if (dx > 0) {
			minx = (int)Math.floor((t.getX() - extents[0]) / tileMap.getTileWidth());
			maxx = (int)Math.ceil((t.getX() + extents[0] + dx) / tileMap.getTileWidth() - 1.0);
		} else {
			minx = (int)Math.floor((t.getX() - extents[0] + dx) / tileMap.getTileWidth());
			maxx = (int)Math.ceil((t.getX() + extents[0]) / tileMap.getTileWidth() - 1.0);
		}
		if (dy > 0) {
			miny = (int)Math.floor((t.getY() - extents[1]) / tileMap.getTileHeight());
			maxy = (int)Math.ceil((t.getY() + extents[1] + dy) / tileMap.getTileHeight() - 1.0);
		} else {
			miny = (int)Math.floor((t.getY() - extents[1] + dy) / tileMap.getTileHeight());
			maxy = (int)Math.ceil((t.getY() + extents[1]) / tileMap.getTileHeight() - 1.0);
		}

		float[] sweep, tilePosition;
		float[] tileExtents = {tileMap.getTileWidth()*.5f, tileMap.getTileHeight()*.5f};

		boolean collided = false;
		float toc = 2.0f;
		int axis = 0; //0 for horizontal, 1 for vertical, 2 for corner
		int hc = 0, vc = 0;

		for (int xx = minx; xx <= maxx; xx += 1) {
			for (int yy = miny; yy <= maxy; yy += 1) {
				tilePosition = new float[2];
				tilePosition[0] = (xx * tileMap.getTileWidth()) + tileExtents[0];
				tilePosition[1] = (yy * tileMap.getTileHeight()) + tileExtents[1];
				Tile tile = collisionLayer.get(xx, yy);
				if (tile != null && tile.tileSet != null) {
					sweep = AABBsweep(extents, pos0, pos1, tileExtents, tilePosition, tilePosition);
					float tmptoc = (float) Math.max(sweep[0], sweep[1]);
					if (tmptoc >= 0) {
						if (sweep[0] > sweep[1]) {
							hc++;
						} else if (sweep[1] > sweep[0]) {
							vc++;
						}
						if (tmptoc < toc && tmptoc >= 0) {
							if (sweep[0] > sweep[1]) {
								axis = 0;
							} else if (sweep[1] > sweep[0]) {
								axis = 1;
							} else {
								axis = 2;
							}
							toc = tmptoc;
							collided = true;
						}
					}

				}
			}
		}

		if (!collided) {
			return false;
		}

		switch (axis) {
		case 0:
			t.translate((dx * toc), 0f);
			deltaTransform.setX(0f);
			break;
		case 1:
			t.translate(0f, (dy * toc));
			deltaTransform.setY(0f);
			if (dy > 0f)
				g.grounded = true;
			break;
		case 2:
			if (hc > vc) {
				t.translate((dx * toc), 0f);
				deltaTransform.setX(0f);
			} else if (vc > hc) {
				t.translate(0f, (dy * toc));
				deltaTransform.setY(0f);
			} else {
				t.translate((dx * toc), (dy * toc));
				deltaTransform.setX(0f);
				deltaTransform.setY(0f);
			}
			break;
		default:
			break;
		}

		collideWorld(t, bc, deltaTransform, g, count - 1);
		return true;
	}

	/**
	 * 
	 * @param aExtents
	 * @param a0
	 * @param a1
	 * @param bExtents
	 * @param b0
	 * @param b1
	 * @return
	 */
	public static float[] AABBsweep(final float[] aExtents, final float[] a0, final float[] a1, final float[] bExtents, final float[] b0, final float[] b1) {
		//n is the number of dimension
		final int n = aExtents.length;

		//v is the relative velocity of the two Axis Aligned Bounding Boxes..
		//u0 is the first normalized time of collision for each axis.
		//u1 is the second normalized time of collision for each axis.
		final float[] v = new float[n];
		final float[] u0 = new float[n];
		final float[] u1 = new float[n];
		for (int i = 0; i < n; i++) {
			v[i] = (b1[i] - b0[i]) - (a1[i] - a0[i]);
			u0[i] = -1f;
			u1[i] = -1f;
		}

		//for each dimension
		for (int i = 0; i < aExtents.length; i++) {
			if ((a0[i]+aExtents[i] <= b0[i]-bExtents[i]) && v[i] < 0f) {

				u0[i] = ((a0[i]+aExtents[i]) - (b0[i]-bExtents[i])) / v[i];

			} else if ((b0[i]+bExtents[i] <= a0[i]-aExtents[i]) && v[i] > 0f) {

				u0[i] = ((a0[i]-aExtents[i]) - (b0[i]+bExtents[i])) / v[i];

			}

			if ((b0[i]+bExtents[i] >= a0[i]-aExtents[i]) && v[i] < 0f) {

				u1[i] = ((a0[i]-aExtents[i]) - (b0[i]+bExtents[i])) / v[i];

			} else if ((a0[i]+aExtents[i] >= b0[i]-bExtents[i]) && v[i] > 0f) {

				u1[i] = ((a0[i]+aExtents[i]) - (b0[i]-bExtents[i])) / v[i];

			}
		}

		float t0 = 0f;
		float t1 = 1f;
		for (int i = 0; i < n; i++) {
			t0 = t0 > u0[i] ? t0 : u0[i];
			t1 = t1 < u1[i] ? t1 : u1[i];
		}

		return u0;
	}

	@Override
	protected Matcher initMatcher() {
		return new Matcher(Transform.class).require(DeltaTransform.class).require(BoxCollider.class);
	}

	public TileMap getTileMap() {
		return tileMap;
	}

	public void setTileMap(TileMap tileMap) {
		this.tileMap = tileMap;
	}

	public TileLayer getCollisionLayer() {
		return collisionLayer;
	}

	public void setCollisionLayer(String collisionLayerName) {
		Layer layer = tileMap.getLayer(collisionLayerName);
		if (layer instanceof TileLayer) {
			this.collisionLayer = (TileLayer) layer;
		} else {
			throw new IllegalArgumentException("Layer must be of type TileLayer.");
		}
	}

}

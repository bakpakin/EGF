package bakpakin.egf.util.tilemap;

import static org.lwjgl.opengl.GL11.*;

import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import bakpakin.egf.util.geom.Transform;
import bakpakin.egf.util.render.Drawable;
import bakpakin.egf.util.render.RenderSystem;

/**
 * A class that uses openGL and Slick2D to draw a {@link TileMap}.
 * @author Calvin
 *
 */
public class TileMapRenderer implements Drawable {

	/**
	 * The map that this draws.
	 */
	private TileMap tileMap;
	 	
	/**
	 * Creates a new {@link TileMapRenderer}.
	 * @param tileMap - the map to draw
	 */
	public TileMapRenderer(TileMap tileMap) {
		setTileMap(tileMap);
	}

	@Override
	public void draw(RenderSystem rs, float depth, Color color, Transform t) {
		glDisable(GL_POLYGON_SMOOTH);
		t.apply();
		Color realColor;
		GL11.glTranslatef(0f, 0f, depth);
		for (Layer layer : tileMap.getLayers()) {
			if (!layer.isVisible()) continue;
			realColor = new Color(color.r, color.g, color.b, layer.getOpacity()*color.a);
			realColor.bind();
			GL11.glTranslatef(layer.getX(), layer.getY(), layer.depth);
			if (layer instanceof TileLayer) {
					drawTileLayer((TileLayer)layer, rs);
			} else if (layer instanceof ImageLayer) {
				drawImageLayer((ImageLayer)layer, rs);
			} else if (layer instanceof ObjectLayer) {
				drawObjectLayer((ObjectLayer)layer, rs);
			}
			GL11.glTranslatef(-layer.getX(), -layer.getY(), -layer.depth);
		} 
		GL11.glTranslatef(0f, 0f, -depth);
		t.applyInverse();
	}

	/*
	 * Image layer handling code
	 */
	private void drawImageLayer(ImageLayer imageLayer, RenderSystem rs) {
		
	}
	
	/*
	 * Object layer handling code
	 */
	private void drawObjectLayer(ObjectLayer imageLayer, RenderSystem rs) {
		
	}
	
	/*
	 * Tile layer handling code
	 */
	private void drawTileLayer(TileLayer tileLayer, RenderSystem rs) {
		final float xFactor = tileMap.getTileWidth() * tileLayer.getChunkSize();
		final float yFactor = tileMap.getTileHeight() * tileLayer.getChunkSize();
		
		Map<ChunkCoordinate, TileChunk> visibleChunks = getVisibleChunks(tileLayer, rs);
		
		for (Map.Entry<ChunkCoordinate, TileChunk> e : visibleChunks.entrySet()) {
				ChunkCoordinate coordinate = e.getKey();
				TileChunk chunk = e.getValue();
				if (chunk == null) continue;
				GL11.glTranslatef(coordinate.x*xFactor, coordinate.y*yFactor, 0f);
				drawTileChunk(chunk, rs);
				GL11.glTranslatef(-coordinate.x*xFactor, -coordinate.y*yFactor, 0f);
			
		}
	}

	//TODO for optimization
	private Map<ChunkCoordinate, TileChunk> getVisibleChunks(TileLayer tileLayer, RenderSystem rs) {
		return tileLayer.getChunks();
	}

	/*
	 * draw a single tile chunk
	 */
	private void drawTileChunk(TileChunk chunk, RenderSystem rs) {
		
		glBegin(GL_QUADS);
		
		int x, y, row, col;
		float x1, y1, x2, y2, tx1, tx2, ty1, ty2, texXFactor = 0, texYFactor = 0, w = 1, h = 1;
		Tile tile;
		Texture lastBoundTexture = null;
		
		for (x = 0; x < chunk.size; x++) {
			for (y = 0; y < chunk.size; y++) {
				
				tile = chunk.get(x, y);
				if (tile == null || tile.tileSet == null) continue;
				
				if (lastBoundTexture != tile.tileSet.getTexture()) {
					glEnd();
					tile.tileSet.getTexture().bind();
					tile.tileSet.getTexture().setTextureFilter(rs.isLinearSampling() ? GL_LINEAR : GL_NEAREST);
					glBegin(GL_QUADS);
					w = tile.tileSet.getTileWidth();
					h = tile.tileSet.getTileHeight();
					texXFactor = tile.tileSet.getTexture().getWidth() / tile.tileSet.getWidth();
					texYFactor = tile.tileSet.getTexture().getHeight() / tile.tileSet.getHeight();
				}
				lastBoundTexture = tile.tileSet.getTexture();
				
				
				row = tile.localIndex / tile.tileSet.getWidth();
				col = tile.localIndex - tile.tileSet.getWidth()*row;
				
				tx1 = col*texXFactor + 0.001f*texXFactor;
				ty2 = row*texYFactor + 0.001f*texYFactor;
				tx2 = tx1 + texXFactor - 0.002f*texXFactor;
				ty1 = ty2 + texYFactor - 0.002f*texYFactor;
				
				x1 = x*tileMap.getTileWidth();
				y2 = (y+1)*tileMap.getTileHeight();
				x2 = x1 + w;
				y1 = y2 - h;
				
				glTexCoord3f(tx1, ty2, 0f);
				glVertex3f(x1, y1, 0f);
				
				glTexCoord3f(tx1, ty1, 0f);
				glVertex3f(x1, y2, 0f);

				glTexCoord2d(tx2, ty1);
				glVertex3f(x2, y2, 0f);

				glTexCoord2d(tx2, ty2);
				glVertex3f(x2, y1, 0f);
				
			}
		}
        glEnd();
	}

	/**
	 * @return the {@link TileMap} that this renders
	 */
	public TileMap getTileMap() {
		return tileMap;
	}

	/**
	 * Sets the {@link TileMap} used for rendering
	 * @param tileMap
	 */
	public void setTileMap(TileMap tileMap) {
		this.tileMap = tileMap;
	}

}

package bakpakin.egf.util.tilemap;

import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2d;
import static org.lwjgl.opengl.GL11.glTexCoord3f;
import static org.lwjgl.opengl.GL11.glVertex3f;

import org.newdawn.slick.Color;

import bakpakin.egf.util.geom.Transform;
import bakpakin.egf.util.render.Drawable;
import bakpakin.egf.util.render.RenderSystem;

public class TileDrawer implements Drawable {
	
	private Tile tile;
	
	public TileDrawer(Tile tile) {
		setTile(tile);
	}

	@Override
	public void draw(RenderSystem rs, float depth, Color color, Transform t) {
		t.apply();
		color.bind();
		tile.tileSet.getTexture().bind();
		tile.tileSet.getTexture().setTextureFilter(rs.isLinearSampling() ? GL_LINEAR : GL_NEAREST);
		float w = tile.tileSet.getTileWidth();
		float h = tile.tileSet.getTileHeight();
		float texXFactor = tile.tileSet.getTexture().getWidth() / tile.tileSet.getWidth();
		float texYFactor = tile.tileSet.getTexture().getHeight() / tile.tileSet.getHeight();
		float row = tile.localIndex / tile.tileSet.getWidth();
		float col = tile.localIndex - tile.tileSet.getWidth()*row;
		float tx1 = col*texXFactor + 0.001f*texXFactor;
		float ty2 = row*texYFactor + 0.001f*texYFactor;
		float tx2 = tx1 + texXFactor - 0.002f*texXFactor;
		float ty1 = ty2 + texYFactor - 0.002f*texYFactor;

		glBegin(GL_QUADS);
		
		glTexCoord3f(tx1, ty2, 0f);
		glVertex3f(0f, 0f, 0f);
		
		glTexCoord3f(tx1, ty1, 0f);
		glVertex3f(0f, h, 0f);

		glTexCoord2d(tx2, ty1);
		glVertex3f(w, h, 0f);

		glTexCoord2d(tx2, ty2);
		glVertex3f(w, 0f, 0f);
		
        glEnd();
		
		t.applyInverse();
	}

	public Tile getTile() {
		return tile;
	}

	public void setTile(Tile tile) {
		this.tile = tile;
	}

}

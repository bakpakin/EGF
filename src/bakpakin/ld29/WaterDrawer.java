package bakpakin.ld29;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import bakpakin.egf.geom.Transform;
import bakpakin.egf.gui.NineBox;
import bakpakin.egf.render.Drawable;
import bakpakin.egf.render.RenderSystem;
import bakpakin.egf.util.AssetManager;

public class WaterDrawer implements Drawable {

	private NineBox waterBox;

	public WaterDrawer() {
		this.waterBox = new NineBox(AssetManager.getTexture("res/water.png"));
	}

	@Override
	public void draw(RenderSystem renderSystem, float depth, Color color,
			Transform t) {
		//ignore transform
		GL11.glTranslatef(0, 0, depth);
		color.bind();
		waterBox.draw(-100000, 0, 100000, 100000);
		GL11.glTranslatef(0, 0, -depth);
	}


	public NineBox getWaterBox() {
		return waterBox;
	}

	public void setWaterBox(NineBox waterBox) {
		this.waterBox = waterBox;
	}

}

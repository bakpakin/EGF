package bakpakin.egf.util.gui;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import bakpakin.egf.util.geom.Transform;
import bakpakin.egf.util.render.Drawable;
import bakpakin.egf.util.render.RenderSystem;

class UIDrawer implements Drawable {
	
	private final UI ui;
	
	public UIDrawer(UI ui) {
		this.ui = ui;
	}

	@Override
	public void draw(RenderSystem rs, float depth, Color color, Transform t) {
		t.apply();
		color.bind();
		GL11.glTranslatef(0f, 0f, depth);
		ui.update();
		ui.render();
		GL11.glTranslatef(0f, 0f, -depth);
		t.applyInverse();
	}

}

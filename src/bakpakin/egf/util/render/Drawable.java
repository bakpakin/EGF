package bakpakin.egf.util.render;

import org.newdawn.slick.Color;

import bakpakin.egf.util.geom.Transform;

public interface Drawable {
	
	void draw(RenderSystem renderSystem, float depth, Color color, Transform t);

}

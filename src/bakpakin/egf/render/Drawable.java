package bakpakin.egf.render;

import org.newdawn.slick.Color;

import bakpakin.egf.geom.Transform;

public interface Drawable {
	
	void draw(RenderSystem renderSystem, float depth, Color color, Transform t);

}

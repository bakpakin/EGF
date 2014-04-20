package bakpakin.egf.util.render;

import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.Color;

import bakpakin.egf.util.geom.Transform;

public class MultiDraw implements Drawable {
	
	private final List<Drawable> drawables;
	
	public MultiDraw(Drawable...drawables) {
		this.drawables = new LinkedList<Drawable>();
		for (Drawable d : drawables) {
			this.drawables.add(d);
		}
	}

	@Override
	public void draw(RenderSystem renderSystem, float depth, Color color,
			Transform t) {
		for (Drawable d : drawables)
			d.draw(renderSystem, depth, color, t);
	}

}

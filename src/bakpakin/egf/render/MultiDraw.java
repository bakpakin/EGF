package bakpakin.egf.render;

import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.Color;

import bakpakin.egf.geom.Transform;

public class MultiDraw implements Drawable {
	
	private final List<Drawable> drawables;
	
	public MultiDraw(Drawable...drawables) {
		this.drawables = new LinkedList<Drawable>();
		for (Drawable d : drawables) {
			this.drawables.add(d);
		}
	}

	@Override
	public void draw(RenderSystem rs, float depth, Color color,
			Transform t) {
		for (Drawable d : drawables)
			d.draw(rs, depth, color, t);
	}

}

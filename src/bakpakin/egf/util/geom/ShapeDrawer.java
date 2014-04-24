package bakpakin.egf.util.geom;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

import bakpakin.egf.util.render.Drawable;
import bakpakin.egf.util.render.RenderSystem;

public class ShapeDrawer implements Drawable {

	private Shape shape;
	
	public ShapeDrawer(Shape shape) {
		this.setShape(shape);
	}

	@Override
	public void draw(RenderSystem renderSystem, float depth, Color color, Transform t) {
		color.bind();
		Shape s = shape.transformLocal(t);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glBegin(GL11.GL_TRIANGLE_FAN);
		if (s instanceof Polygon) {
			for (Vector2f pt : (Polygon)s) {
				GL11.glVertex3f(pt.x, pt.y, depth);
			}
		} else if (s instanceof Circle) { 
			Circle c = (Circle)s;
			for (float r = 0; r < Math.PI * 2.0; r += .08f) {
				float cos = (float)Math.cos(r);
				float sin = (float)Math.sin(r);
				GL11.glVertex3f(c.x + cos*c.radius, c.y + sin*c.radius, depth);
			}
		} else if (s instanceof AxisAlignedBox) {
			AxisAlignedBox aab = (AxisAlignedBox)s;
			GL11.glVertex3f(aab.x1, aab.y1, depth);
			GL11.glVertex3f(aab.x1, aab.y2, depth);
			GL11.glVertex3f(aab.x2, aab.y2, depth);
			GL11.glVertex3f(aab.x2, aab.y1, depth);
		} else if (s instanceof OrientedBox) {
			OrientedBox ob = (OrientedBox)s;
			float x = ob.getOriginX();
			float y = ob.getOriginY();
			float hw = ob.getWidth() * 0.5f;
			float hh = ob.getHeight() * 0.5f;
			double rad = Math.toRadians(ob.getRotation());
			float cos = (float)Math.cos(rad);
			float sin = (float)Math.sin(rad);
			float xsin = hw*sin;
			float ysin = hh*sin;
			float xcos = hw*cos;
			float ycos = hh*cos;
			GL11.glVertex3f(x+xcos+ysin, y+ycos-xsin, depth);
			GL11.glVertex3f(x+xcos-ysin, y-ycos-xsin, depth);
			GL11.glVertex3f(x-xcos-ysin, y-ycos+xsin, depth);
			GL11.glVertex3f(x-xcos+ysin, y+ycos+xsin, depth);
		}
		GL11.glEnd();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}

}

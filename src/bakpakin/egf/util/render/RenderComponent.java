package bakpakin.egf.util.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GLContext;
import org.newdawn.slick.Color;

import bakpakin.egf.framework.Component;
import bakpakin.egf.util.geom.Transform;

public final class RenderComponent implements Component {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5090075250248093829L;
	private Drawable drawable;
	private float depth;
	private int blendModeSrc;
	private int blendModeDest;
	private int shader;
	private Color color;

	public RenderComponent(Drawable drawable) {
		this(drawable, 0f, Color.white);
	}

	public RenderComponent(Drawable drawable, float depth) {
		this(drawable, depth, Color.white);
	}

	public RenderComponent(Drawable drawable, Color color) {
		this(drawable, 0f, color);
	}

	public RenderComponent(Drawable drawable, float depth, Color color) {
		this.drawable = drawable;
		this.depth = depth;
		this.setColor(color);
		setBlendMode(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}

	public void draw(RenderSystem renderSystem, Transform t) {
		if (drawable != null) {
			if (GLContext.getCapabilities().OpenGL20) {
				if (shader > 0) {
					GL20.glUseProgram(shader);
				} else {
					GL20.glUseProgram(0);
					GL11.glBlendFunc(blendModeSrc, blendModeDest);
				}
			} else {
				GL11.glBlendFunc(blendModeSrc, blendModeDest);
			}
			drawable.draw(renderSystem, depth, color, t);
		}
	}

	public float getDepth() {
		return depth;
	}

	public void setDepth(float depth) {
		this.depth = depth;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Drawable getDrawable() {
		return drawable;
	}

	public void setDrawable(Drawable drawable) {
		this.drawable = drawable;
	}

	public int getBlendModeSrc() {
		return blendModeSrc;
	}

	public int getBlendModeDest() {
		return blendModeDest;
	}

	public void setBlendMode(int src, int dest) {
		this.blendModeSrc = src;
		this.blendModeDest = dest;
	}

	public int getShader() {
		return shader;
	}

	public void setShader(int shader) {
		this.shader = shader;
	}

}

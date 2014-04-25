package bakpakin.egf.render;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import bakpakin.egf.geom.Transform;
import bakpakin.egf.util.AssetManager;

public class AnimatedBackground extends Background{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7116325462435662762L;
	private Texture[] textures;
	private float texIndex;
	private float speed;

	public AnimatedBackground(float speed, String... keys) {
		super(keys[0]);
		textures = new Texture[keys.length];
		textures[0] = texture;
		for (int i = 1; i < keys.length; i++) {
			String key = keys[i];
			textures[i] = AssetManager.getTexture(key);
		}
		this.setSpeed(speed);
	}
	
	@Override
	public void draw(RenderSystem renderSystem, float depth, Color color, Transform t) {
		texIndex += speed*(float)renderSystem.getWorld().getDelta();
		if (texIndex > textures.length) {
			texIndex -= textures.length;
		}
		texture = textures[(int)texIndex];
		super.draw(renderSystem, depth, color, t);
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public int getTexIndex() {
		return (int)Math.floor(texIndex);
	}

	public void setTexIndex(int texIndex) {
		this.texIndex = texIndex;
	}

}

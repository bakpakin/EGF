package bakpakin.egf.render;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import bakpakin.egf.geom.Transform;
import bakpakin.egf.util.AssetManager;

public class SimpleAnimation extends Sprite {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3092763448586195519L;
	private ArrayList<Texture> textures;
	private double timer;
	private double speed;	

	public SimpleAnimation(double speed, String... imageUrls) {
		super(imageUrls[0]);
		this.speed = speed;
		textures = new ArrayList<Texture>();
		for (String iu : imageUrls) {
			textures.add(AssetManager.getTexture(iu));
		}

	}
	
	public SimpleAnimation(String... imageUrls) {
		this(1.0, imageUrls);
	}
	
	public SimpleAnimation(double speed, String imageBaseName, int first, int last) {
		this(speed, getNames(imageBaseName, first, last));
	}
	
	private static String[] getNames(String base, int first, int last) {
		if (!(last >= first)) {
			throw new IllegalArgumentException("last index must be greater than first index.");
		}
		String[] ret = new String[last - first + 1];
		int dotIndex = base.lastIndexOf('.');
		String name = base.substring(0, dotIndex);
		String extension = base.substring(dotIndex);
		for (int i = first; i <= last; i++) {
			ret[i-first] = name + i + extension;
		}
		return ret;
	}
	
	public double getTimer() {
		return timer;
	}

	public void setTimer(double timer) {
		this.timer = timer;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public ArrayList<Texture> getTexture() {
		return textures;
	}

	@Override
	public void draw(RenderSystem renderSystem, float depth, Color color, Transform t) {
		float dt = (float)renderSystem.getWorld().getDelta();
		timer += dt*speed;
		texture = textures.get((int)timer % textures.size());
		super.draw(renderSystem, depth, color, t);
	}

}

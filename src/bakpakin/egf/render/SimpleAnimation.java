package bakpakin.egf.render;

import java.util.ArrayList;

import org.newdawn.slick.Color;

import bakpakin.egf.geom.Transform;

public class SimpleAnimation implements Drawable {
	
	private ArrayList<Sprite> sprites;
	private double timer;
	private double speed;	

	public SimpleAnimation(double speed, String... imageUrls) {
		this.speed = speed;
		sprites = new ArrayList<Sprite>();
		for (String url : imageUrls) {
			sprites.add(new Sprite(url));
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

	public ArrayList<Sprite> getSprites() {
		return sprites;
	}

	@Override
	public void draw(RenderSystem renderSystem, float depth, Color color, Transform t) {
		float dt = (float)renderSystem.getWorld().getDelta();
		timer += dt*speed;
		sprites.get((int)timer % sprites.size()).draw(renderSystem, depth, color, t);
	}

}

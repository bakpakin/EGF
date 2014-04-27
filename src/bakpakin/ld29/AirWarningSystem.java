package bakpakin.ld29;

import org.newdawn.slick.Color;
import org.newdawn.slick.openal.Audio;

import bakpakin.egf.framework.EmptySystem;
import bakpakin.egf.framework.Entity;
import bakpakin.egf.geom.Transform;
import bakpakin.egf.render.RenderComponent;
import bakpakin.egf.render.Text;
import bakpakin.egf.util.AssetManager;

public class AirWarningSystem extends EmptySystem {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9129950359435978828L;
	private Entity player;
	private RenderComponent drawer;
	private Text text;
	private float timer;
	private Audio danger;

	public AirWarningSystem(OceanScene os, Entity player) {
		this.player = player;
		text = new Text("Warning: Low on Oxygen", "res/gilligansisland.ttf", 40);
		drawer = new RenderComponent(text, 10000, Color.red).drawHud();
		os.createEntity(drawer, new Transform(5, 140));
		danger = AssetManager.getSound("res/danger.wav");
	}

	@Override
	public void update() {
		timer += getWorld().getDeltaf();
		int air = (Integer) player.getProperty("Air");
		if (air < 5) {
			if (!danger.isPlaying()) {
				if (air < 3) {
					danger.playAsSoundEffect(2f, 1f, false);
				} else {
					danger.playAsSoundEffect(1f, 1f, false);
				}
			}
		} else {
			timer = 0;
		}
		drawer.getColor().a = (float)(-Math.cos(timer)/2 + 0.5);
	}

}

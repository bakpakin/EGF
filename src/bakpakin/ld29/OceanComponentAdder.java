package bakpakin.ld29;

import org.newdawn.slick.Color;

import bakpakin.egf.framework.Entity;
import bakpakin.egf.geom.Transform;
import bakpakin.egf.physics.BoxCollider;
import bakpakin.egf.physics.CircleCollider;
import bakpakin.egf.physics.Friction;
import bakpakin.egf.render.RenderComponent;
import bakpakin.egf.render.Sprite;
import bakpakin.egf.tilemap.ComponentAdder;
import bakpakin.egf.tilemap.TileMapBean.ObjectBean;
import bakpakin.egf.util.BehaviorComponent;

public class OceanComponentAdder implements ComponentAdder {

	public OceanComponentAdder() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addComponents(Entity e, ObjectBean b) {
		RenderComponent rc = e.get(RenderComponent.class);
		if (b.getType().equals("fish")) {
			rc.setDrawable(new Sprite("res/fish.png").center());
			rc.setColor(randColor());
			e.add(new BehaviorComponent(new FishBehavior()));
			e.add(new CircleCollider(15));
			e.add(new Friction(25));
			e.add(new BoxCollider(20, 20));
			e.add(new Target(e.get(Transform.class).getX() + 10, e.get(Transform.class).getY() + 10));
			e.setProperty("Accel", 200);
			e.setProperty("MaxSpeed", 1000);
			e.addTag(EntityFactory.ENEMY_TAG);
		} else if (b.getType().equals("jellyfish")) {
			rc.setDrawable(new Sprite("res/jellyfish.png").center());
			rc.setColor(randColor());
			e.add(new BehaviorComponent(new JellyfishBehavior()));
			e.addTag(EntityFactory.ENEMY_TAG);
		} else if (b.getType().equals("chest")) {
			
		} else if (b.getType().equals("coin")) {
			
		} else if (b.getType().equals("turret")) {
			e.addTag(EntityFactory.ENEMY_TAG);
		}
	}

	private Color randColor() {
		java.awt.Color c = java.awt.Color.getHSBColor((float)Math.random(), 1, 1);
		return new Color(c.getRed(), c.getGreen(), c.getBlue());
	}

}

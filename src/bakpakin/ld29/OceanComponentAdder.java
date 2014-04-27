package bakpakin.ld29;

import org.newdawn.slick.Color;

import bakpakin.egf.framework.Entity;
import bakpakin.egf.geom.Transform;
import bakpakin.egf.physics.BoxCollider;
import bakpakin.egf.physics.CircleCollider;
import bakpakin.egf.physics.DeltaTransform;
import bakpakin.egf.physics.Friction;
import bakpakin.egf.render.RenderComponent;
import bakpakin.egf.render.Sprite;
import bakpakin.egf.tilemap.ComponentAdder;
import bakpakin.egf.tilemap.TileMapBean.ObjectBean;
import bakpakin.egf.util.BehaviorComponent;

public class OceanComponentAdder implements ComponentAdder {

	private Entity player;
	
	public OceanComponentAdder(Entity player) {
		this.player = player;
	}

	@Override
	public void addComponents(Entity e, ObjectBean b) {
		RenderComponent rc = e.get(RenderComponent.class);
		rc.setDepth(3);
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
			e.add(new BehaviorComponent(new JellyfishBehavior(player)));
			e.add(new CircleCollider(15));
			e.add(new Friction(25));
			e.add(new BoxCollider(20, 20));
			e.add(new Target(e.get(Transform.class).getX() + 10, e.get(Transform.class).getY() + 10));
			e.setProperty("Accel", 200);
			e.setProperty("MaxSpeed", 600);
			e.setProperty("Range", 200);
			e.addTag(EntityFactory.ENEMY_TAG);
		} else if (b.getType().equals("chest")) {
			rc.setDrawable(new Sprite("res/chest.png").center());
			e.get(DeltaTransform.class).setY(20);
			e.add(new BoxCollider(50, 50));
			e.add(new CircleCollider(75, 0));
			e.addTag(EntityFactory.CHEST_TAG);
		} else if (b.getType().equals("coin")) {
			rc.setDrawable(new Sprite("res/coin.png").center());
			e.addTag(EntityFactory.COLLECTIBLE_TAG);
			e.add(new CircleCollider(15, 0));
			e.add(new Friction(10000));
		} else if (b.getType().equals("turret")) {
			e.addTag(EntityFactory.ENEMY_TAG);
		}
	}

	private Color randColor() {
		java.awt.Color c = java.awt.Color.getHSBColor((float)Math.random(), 1, 1);
		return new Color(c.getRed(), c.getGreen(), c.getBlue());
	}

}

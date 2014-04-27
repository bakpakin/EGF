package bakpakin.ld29;

import bakpakin.egf.framework.Entity;
import bakpakin.egf.framework.World;
import bakpakin.egf.geom.Transform;

public class JellyfishBehavior extends FishBehavior {

	private Entity player;
	
	public JellyfishBehavior(Entity player) {
		this.player = player;
	}

	@Override
	public void step(World world, Entity entity) {
		super.step(world, entity);
		Transform t = entity.get(Transform.class);
		Transform pt = player.get(Transform.class);
		Integer range = (Integer) entity.getProperty("Range");
		float dx = t.getX() - pt.getX();
		float dy = t.getY() - pt.getY();
		if (dx*dx + dy*dy < range*range) {
			Target trgt = entity.get(Target.class);
			trgt.x = pt.getX();
			trgt.y = pt.getY();
		}
	}

	@Override
	public void start(World world, Entity entity) {
		super.start(world, entity);
	}

}

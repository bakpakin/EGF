package bakpakin.ld29;

import bakpakin.egf.framework.Entity;
import bakpakin.egf.framework.World;
import bakpakin.egf.geom.Transform;
import bakpakin.egf.util.Behavior;

public class FishBehavior implements Behavior {
		
	private float timer;

	public FishBehavior() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void step(World world, Entity entity) {
		timer += world.getDeltaf();
		Transform t = entity.get(Transform.class);
		Target trgt = entity.get(Target.class);
		if (trgt == null) return;
		float dx = trgt.x - t.getX();
		float dy = trgt.y - t.getY();
		if (dx*dx + dy*dy < 200*200) {
			moveTarget(entity);
		}
		if (timer > 4)
			moveTarget(entity);
	}

	@Override
	public void start(World world, Entity entity) {
		moveTarget(entity);
	}
	
	protected void moveTarget(Entity entity) {
		Transform t = entity.get(Transform.class);
		Target trgt = entity.get(Target.class);
		if (trgt == null) return;
		double dir = Math.random()*Math.PI*2;
		double r = Math.random()*500;
		float dx = (float) (r*Math.cos(dir));
		float dy = (float) (r*Math.sin(dir));
		trgt.x = t.getX() + dx;
		trgt.y = t.getY() + dy;
		timer = 0;
	}

}

package bakpakin.ld29;

import bakpakin.egf.EGF;
import bakpakin.egf.framework.EmptySystem;
import bakpakin.egf.framework.Entity;
import bakpakin.egf.geom.Transform;

public class AirSystem extends EmptySystem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private float timer;
	
	private Entity player;

	public AirSystem(Entity player) {
		this.player= player;
	}

	@Override
	public void update() {
		int tm = (Integer) player.getProperty("TimePerAirBubble");
		if (player.get(Transform.class).getY() > 0) {
			timer += getWorld().getDeltaf();
			if (timer > tm) {
				timer = 0;
				player.setProperty("Air", (Integer)player.getProperty("Air") - 1);
			}
		} else {
			timer = 0;
			player.setProperty("Air", 10);
		}
		if ((Integer)player.getProperty("Air") < 0) {
			OceanScene os = ((OceanScene)EGF.getScene());
			os.youDied();
		}
	}

}

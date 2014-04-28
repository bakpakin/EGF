package bakpakin.ld29;

import org.lwjgl.input.Keyboard;

import bakpakin.egf.framework.EmptySystem;
import bakpakin.egf.framework.Entity;
import bakpakin.egf.geom.AxisAlignedBox;
import bakpakin.egf.geom.Transform;
import bakpakin.egf.physics.BoxCollider;

public class BoatCollisionSystem extends EmptySystem {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8599262881594964778L;
	private Entity player;
	private Entity boat;
	private OceanScene os;
	private boolean won;

	public BoatCollisionSystem(OceanScene os, Entity player, Entity boat) {
		this.player = player;
		this.boat = boat;
		this.os = os;
	}

	@Override
	public void update() {
		Transform pt = player.get(Transform.class);
		Transform bt = boat.get(Transform.class).clone();
		bt.setAngle(0);
		BoxCollider bb = boat.get(BoxCollider.class);
		AxisAlignedBox baab = bb.getHitbox(bt);
		if (baab.contains(pt.getX(), pt.getY())) {
			if (player.hasTag("FoundTreasure") && !won)
				{os.win(); won = true;}
			else if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
				os.showStore();
			}
		} else {
			os.hideStore();
		}

	}

}

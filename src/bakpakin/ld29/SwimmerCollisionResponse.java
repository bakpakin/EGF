package bakpakin.ld29;

import bakpakin.egf.EGF;
import bakpakin.egf.framework.Entity;
import bakpakin.egf.geom.Transform;
import bakpakin.egf.particles.ParticleSystem;
import bakpakin.egf.physics.CollisionResponse;
import bakpakin.egf.physics.DeltaTransform;
import bakpakin.egf.util.AssetManager;

public class SwimmerCollisionResponse implements CollisionResponse {

	private ParticleSystem ps;

	public SwimmerCollisionResponse(ParticleSystem ps) {
		this.ps = ps;
	}

	@Override
	public void response(Entity e1, Entity e2) {
		if (e2.hasTag(EntityFactory.ENEMY_TAG)) {
			DeltaTransform dt1 = e1.get(DeltaTransform.class);
			DeltaTransform dt2 = e2.get(DeltaTransform.class);
			dt1.translate((dt2.getX() - dt1.getX())*3, (dt2.getY() - dt1.getY())*3);
			e1.setProperty("Health", (Integer)e1.getProperty("Health") - 1);
			if ((Integer)e1.getProperty("Health") <= 0) {
				OceanScene os = ((OceanScene)EGF.getScene());
				os.youDied();
			}
			Transform t = e1.get(Transform.class);
			float x = t.getX();
			float y = t.getY();
			int max = (int)(Math.random() * 12) + 3;
			for (int i = 0; i < max; i++)
				ps.createParticle(x, y, EntityFactory.BLOOD);
			AssetManager.getSound("res/hurt.wav").playAsSoundEffect(1f, 1f, false);
		} else if (e2.hasTag(EntityFactory.COLLECTIBLE_TAG)) {
			EGF.getScene().removeEntity(e2);
			OceanScene oc= ((OceanScene)EGF.getScene());
			oc.getCoinText().setText("Coins: " + ++oc.coins);			
			AssetManager.getSound("res/coin.wav").playAsSoundEffect(1f, 1f, false);
		} else if (e2.hasTag(EntityFactory.CHEST_TAG)) {
			EGF.getScene().removeEntity(e2);
			OceanScene oc= ((OceanScene)EGF.getScene());
			oc.findTreasure();
			AssetManager.getSound("res/findTreasure.wav").playAsSoundEffect(1f, 1f, false);
		}
	}

}

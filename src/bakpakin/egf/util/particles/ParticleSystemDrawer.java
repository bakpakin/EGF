package bakpakin.egf.util.particles;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import bakpakin.egf.util.geom.Transform;
import bakpakin.egf.util.render.Drawable;
import bakpakin.egf.util.render.RenderSystem;

public class ParticleSystemDrawer implements Drawable, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8657947531384474788L;
	
	private ParticleSystem particleSystem;

	public ParticleSystemDrawer(ParticleSystem system) {
		this.setParticleSystem(system);
	}

	@Override
	public void draw(RenderSystem renderSystem, float depth, Color color,
			Transform t) {
		
		//clone t as it is mutated in place for efficiency
		t = t.clone();
		
		for (Map.Entry<ParticleDef, List<Particle>> entry : particleSystem.particles.entrySet()) {
			ParticleDef def = entry.getKey();
			GL11.glBlendFunc(def.blendModeSrc, def.blendModeDest);
			List<Particle> list = entry.getValue();
			for (Particle p : list) {
				t.setX(p.x);
				t.setY(p.y);
				t.setAngle(p.orientation);
				t.setXScale(p.size);
				t.setYScale(p.size);
				def.sprite.draw(renderSystem, depth, p.color, t);
			}
		}
		
	}

	public ParticleSystem getParticleSystem() {
		return particleSystem;
	}

	public void setParticleSystem(ParticleSystem particleSystem) {
		this.particleSystem = particleSystem;
	}

}

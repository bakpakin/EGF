package bakpakin.egf.util.particles;

import java.util.HashMap;
import java.util.Map;

import bakpakin.egf.util.geom.Shape;

public class ParticleEmitter extends ParticleComponent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4851416946702242634L;
	
	Map<ParticleDef, Float> streams; 
	Map<ParticleDef, Float> bursts;

	/**
	 * 
	 * @param shape
	 */
	public ParticleEmitter(Shape shape) {
		super(shape);
		streams = new HashMap<ParticleDef, Float>();
		bursts = new HashMap<ParticleDef, Float>();
	}
	
	/**
	 * 
	 * @param type
	 * @param particlesPerSecond
	 */
	public void stream(ParticleDef type, float particlesPerSecond) {
		streams.put(type, particlesPerSecond);
	}
	
	/**
	 * 
	 * @param type
	 */
	public void stopStream(ParticleDef type) {
		streams.remove(type);
	}
	
	/**
	 * 
	 * @param type
	 * @param particlesPerSecond
	 */
	public void burst(ParticleDef type, float number) {
		bursts.put(type, number);
	}

}

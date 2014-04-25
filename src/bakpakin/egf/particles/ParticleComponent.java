package bakpakin.egf.particles;

import bakpakin.egf.framework.Component;
import bakpakin.egf.geom.Shape;
import bakpakin.egf.geom.Transform;

public abstract class ParticleComponent implements Component {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1543127245691032435L;
	
	protected final Shape shape;
	private Shape transformedShape;
	
	/**
	 * 
	 * @param shape
	 */
	public ParticleComponent(Shape shape) {
		this.shape = shape;
		this.transformedShape = shape;
	}
	
	/**
	 * 
	 * @param t
	 */
	public void setActiveTransform(Transform t) {
		this.transformedShape = shape.transformLocal(t);
	}
	
	/**
	 * 
	 * @param p
	 * @return
	 */
	public boolean containsParticle(Particle p) {
		return transformedShape.contains(p.x, p.y);
	}

}

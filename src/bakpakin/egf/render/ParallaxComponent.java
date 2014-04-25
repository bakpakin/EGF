package bakpakin.egf.render;

import bakpakin.egf.framework.Component;

public class ParallaxComponent implements Component {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4152705489613401686L;
	
	private float factor;

	public ParallaxComponent(float factor) {
		this.factor = factor;
	}

	public float getFactor() {
		return factor;
	}

	public void setFactor(float factor) {
		this.factor = factor;
	}

}

package bakpakin.egf.util.particles;

import java.io.Serializable;

import org.newdawn.slick.Color;

public class Particle implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6079850591898076642L;
	
	public float x, y, direction, orientation, size, speed, lifetime, totalLifetime, deltaDirection, deltaOrientation, deltaSize, deltaSpeed;
	
	public Color color, delta;
	
	Particle() {
		
	}

}

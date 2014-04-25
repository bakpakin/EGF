package bakpakin.egf.tilemap;

import bakpakin.egf.framework.Component;

public class Grounded implements Component {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6738630861627373606L;

	public Grounded(boolean grounded) {
		this.grounded = grounded;
	}
	
	public boolean grounded;

}

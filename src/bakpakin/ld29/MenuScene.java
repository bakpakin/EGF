package bakpakin.ld29;

import static bakpakin.ld29.EntityFactory.*;

public class MenuScene extends Scene {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MenuScene() {
		super();
		this.addSystem(new SwimmerControlSystem(this.getRenderSystem().getCamera()));
		this.add(boat(0, 0));
		this.add(sun(-200, -140));
		this.add(swimmer(0, 200));
	}

}

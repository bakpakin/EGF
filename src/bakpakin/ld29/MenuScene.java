package bakpakin.ld29;

import bakpakin.egf.geom.Transform;
import bakpakin.egf.render.RenderComponent;
import bakpakin.egf.render.Sprite;

public class MenuScene extends Scene {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MenuScene() {
		super();
		this.createEntity(new Transform(), new RenderComponent(new Sprite("res/boat.png").center()));
	}

}

package bakpakin.ld29;

import static bakpakin.ld29.EntityFactory.boat;
import static bakpakin.ld29.EntityFactory.sun;

import org.newdawn.slick.Color;

import bakpakin.egf.render.RenderComponent;

public class InstructionsScene extends Scene {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7347049267810641789L;

	public InstructionsScene() {
		InstructionsUI.makeUI().addToRenderSystem(this.getRenderSystem(), 10000);
		getRenderSystem().setBackgroundColor(new Color(.8f, 1f, 1f, 1f));
		this.add(boat(-250, -43));
		this.add(sun(100, -240));
		this.createEntity(new RenderComponent(new WaterDrawer(), 25, new Color(1, 1, 1, .5f)));
		this.createEntity(new RenderComponent(new Backdrop(), -2000));		}

}

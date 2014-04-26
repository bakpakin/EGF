package bakpakin.ld29;

import bakpakin.egf.framework.World;
import bakpakin.egf.input.InputSystem;
import bakpakin.egf.physics.MovementSystem;
import bakpakin.egf.render.RenderSystem;

public class Scene extends World {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4022390450273879143L;
	
	private RenderSystem renderSystem;
	private MovementSystem movementSystem;
	private InputSystem inputSystem;

	public Scene() {
		super();
		this.inputSystem = new InputSystem();
		this.movementSystem = new MovementSystem();
		this.renderSystem = new RenderSystem();
		this.addSystem(renderSystem);
		this.addSystem(inputSystem);
		this.addSystem(movementSystem);
	}

	public RenderSystem getRenderSystem() {
		return renderSystem;
	}

	public MovementSystem getMovementSystem() {
		return movementSystem;
	}

	public InputSystem getInputSystem() {
		return inputSystem;
	}

}

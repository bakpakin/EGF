package bakpakin.ld29;

import org.newdawn.slick.Color;

import bakpakin.egf.framework.World;
import bakpakin.egf.input.InputSystem;
import bakpakin.egf.physics.MovementSystem;
import bakpakin.egf.render.RenderSystem;
import bakpakin.egf.util.BehaviorSystem;

public class Scene extends World {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4022390450273879143L;
	
	private RenderSystem renderSystem;
	private MovementSystem movementSystem;
	private InputSystem inputSystem;
	private BehaviorSystem behaviorSystem;

	public Scene() {
		super();
		this.inputSystem = new InputSystem();
		this.movementSystem = new MovementSystem();
		this.renderSystem = new RenderSystem();
		this.behaviorSystem = new BehaviorSystem();
		this.addSystem(renderSystem);
		this.addSystem(inputSystem);
		this.addSystem(movementSystem);
		renderSystem.getCamera().getTransform().scale(.5f);
		renderSystem.setBackgroundColor(Color.cyan);
	}

	public RenderSystem getRenderSystem() {
		return renderSystem;
	}

	public MovementSystem getMovementSystem() {
		return movementSystem;
	}
	
	public BehaviorSystem getBehaviorSystem() {
		return behaviorSystem;
	}

	public InputSystem getInputSystem() {
		return inputSystem;
	}

}

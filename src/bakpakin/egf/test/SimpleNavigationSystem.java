package bakpakin.egf.test;

import org.lwjgl.input.Keyboard;

import bakpakin.egf.framework.EmptySystem;
import bakpakin.egf.framework.World;
import bakpakin.egf.render.RenderSystem;

public class SimpleNavigationSystem extends EmptySystem {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6394221289180984039L;

	private RenderSystem renderSystem;

	private float speed = 200;
	private float turnSpeed = 200;
	private float scaleSpeed = 2;

	public RenderSystem getRenderSystem() {
		return renderSystem;
	}

	public void setRenderSystem(RenderSystem renderSystem) {
		this.renderSystem = renderSystem;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getTurnSpeed() {
		return turnSpeed;
	}

	public void setTurnSpeed(float turnSpeed) {
		this.turnSpeed = turnSpeed;
	}

	public float getScaleSpeed() {
		return scaleSpeed;
	}

	public void setScaleSpeed(float scaleSpeed) {
		this.scaleSpeed = scaleSpeed;
	}

	public SimpleNavigationSystem(RenderSystem renderSystem) {
		super();
		this.renderSystem = renderSystem;
	}
	
	@Override
	public void update() {
		float dx = 0, dy = 0, dang = 0, ds = 1;
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT) || Keyboard.isKeyDown(Keyboard.KEY_A)) 
			dx -= speed;
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT) || Keyboard.isKeyDown(Keyboard.KEY_D)) 
			dx += speed;
		if (Keyboard.isKeyDown(Keyboard.KEY_UP) || Keyboard.isKeyDown(Keyboard.KEY_W)) 
			dy -= speed;
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN) || Keyboard.isKeyDown(Keyboard.KEY_S)) 
			dy += speed;
		if (Keyboard.isKeyDown(Keyboard.KEY_Q)) 
			dang -= turnSpeed;
		if (Keyboard.isKeyDown(Keyboard.KEY_E)) 
			dang += turnSpeed;
		if (Keyboard.isKeyDown(Keyboard.KEY_Z)) 
			ds *= scaleSpeed;
		if (Keyboard.isKeyDown(Keyboard.KEY_X)) 
			ds /= scaleSpeed;
		World world = getWorld();
		renderSystem.getCamera().getTransform().translate(dx * (float)world.getDelta(), dy * (float)world.getDelta());
		renderSystem.getCamera().getTransform().rotate(dang * (float)world.getDelta());
		renderSystem.getCamera().getTransform().scale((float)Math.pow(ds, world.getDelta()), (float)Math.pow(ds, world.getDelta()));
	}

}

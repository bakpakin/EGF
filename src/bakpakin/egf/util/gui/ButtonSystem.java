package bakpakin.egf.util.gui;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;

import org.lwjgl.util.vector.Vector2f;

import org.newdawn.slick.openal.Audio;

import bakpakin.egf.framework.Entity;
import bakpakin.egf.framework.Matcher;
import bakpakin.egf.framework.ProcessingSystem;
import bakpakin.egf.util.AssetManager;
import bakpakin.egf.util.geom.Transform;
import bakpakin.egf.util.render.Camera;
import bakpakin.egf.util.render.RenderSystem;

public class ButtonSystem extends ProcessingSystem {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7100784255278341318L;

	private RenderSystem renderSystem;

	private Audio buttonPressed;

	private boolean mouseWasDown;
	private boolean clicked;

	public ButtonSystem(RenderSystem rs, String buttonSound) {
		super();
		setRenderSystem(rs);
		try {
			Mouse.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		setButtonPressedSound(AssetManager.getSound(buttonSound));
	}

	@Override
	public void update() {
		clicked = false;
		if (Mouse.isButtonDown(0)) {
			if (!mouseWasDown)
				clicked = true;
			mouseWasDown = true;
		} else {
			mouseWasDown = false;
		}
		super.update();
	}

	@Override
	public void update(Entity e) {
		ButtonBox bb = e.get(ButtonBox.class);
		Camera cam = e.hasTag("Hud") ? renderSystem.getHudCamera() : renderSystem.getCamera();
		Vector2f mouse = cam.worldPt(Mouse.getX(), Mouse.getY());
		if (bb.getHitbox(e.get(Transform.class)).contains(mouse)) {
			if (!clicked) {
				ButtonHoverAction bha = e.get(ButtonHoverAction.class);
				if (bha != null) {
					bha.behave();
				}
			} else {
				ButtonAction ba = e.get(ButtonAction.class);
				if (ba != null) {
					ba.behave();
				}
			}
			if (!bb.isMouseOver()) {
				ButtonEnterAction bea = e.get(ButtonEnterAction.class);
				if (bea != null) {
					bea.behave();
				}
			}
			bb.setMouseOver(true);
		} else {
			ButtonNoHoverAction bnha = e.get(ButtonNoHoverAction.class);
			if (bnha != null) {
				bnha.behave();
			}
			if (bb.isMouseOver()) {
				ButtonExitAction bea = e.get(ButtonExitAction.class);
				if (bea != null) {
					bea.behave();
				}
			}
			bb.setMouseOver(false);
		}
	}

	@Override
	protected Matcher initMatcher() {
		return new Matcher(ButtonBox.class).require(Transform.class).require(ButtonAction.class);
	}

	public RenderSystem getRenderSystem() {
		return renderSystem;
	}

	public void setRenderSystem(RenderSystem renderSystem) {
		this.renderSystem = renderSystem;
	}

	public Audio getButtonPressedSound() {
		return buttonPressed;
	}

	public void setButtonPressedSound(Audio buttonPressed) {
		this.buttonPressed = buttonPressed;
	}

}

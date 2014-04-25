package bakpakin.egf.input;

import java.util.HashMap;
import java.util.Map.Entry;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import bakpakin.egf.framework.Entity;
import bakpakin.egf.framework.Matcher;
import bakpakin.egf.util.Routine;
import static bakpakin.egf.input.InputListener.*;

public class InputSystem extends bakpakin.egf.framework.ProcessingSystem {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1444373009762829044L;
	
	private HashMap<Integer, Integer> keys;
	private HashMap<Integer, Integer> oldKeys;
	private HashMap<Integer, Integer> mouse;
	private HashMap<Integer, Integer> oldMouse;

	public InputSystem() {
		super();
		try {
			Mouse.create();
			Keyboard.create();
			Mouse.poll();
			Keyboard.poll();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		keys = new HashMap<Integer, Integer>();
		mouse = new HashMap<Integer, Integer>();
		oldKeys = new HashMap<Integer, Integer>();
		oldMouse = new HashMap<Integer, Integer>();
	}

	@Override
	protected Matcher initMatcher() {
		return new Matcher(InputListener.class);
	}

	@Override
	public void update() {

		//swap old and new mouse map pointers
		{
			HashMap<Integer, Integer> tmp = mouse;
			mouse = oldMouse;
			oldMouse = tmp;
		}

		mouse.clear();
		Mouse.poll();
		while(Mouse.next()) {
			int button = Mouse.getEventButton();
			if (Mouse.getEventButtonState()) {
				mouse.put(button, PRESS);
			} else {
				mouse.put(button, RELEASE);
			}
		}
		for (Integer button : oldMouse.keySet()) {
			if (!mouse.containsKey(button) && Mouse.isButtonDown(button)) {
				mouse.put(button, HOLD);
			}
		}

		//swap old and new keyboard map pointers
		{
			HashMap<Integer, Integer> tmp = keys;
			keys = oldKeys;
			oldKeys = tmp;
		}

		keys.clear();
		Keyboard.poll();
		while(Keyboard.next()) {
			int key = Keyboard.getEventKey();
			if (Keyboard.getEventKeyState()) {
				keys.put(key, PRESS);
			} else {
				keys.put(key, RELEASE);
			}
		}
		for (Integer key : oldKeys.keySet()) {
			if (!keys.containsKey(key) && Keyboard.isKeyDown(key)) {
				keys.put(key, HOLD);
			}
		}

		super.update();
	}

	@Override
	public void update(Entity e) {
		InputListener il = e.get(InputListener.class);
		Routine b = il.getBehavior();

		for (Entry<Integer, Integer> entry : mouse.entrySet()) {
			if (il.hasMouseTrigger(entry.getKey(), entry.getValue())) {
				b.doRoutine();
				return;
			}
		}

		for (Entry<Integer, Integer> entry : keys.entrySet()) {
			if (il.hasKeyTrigger(entry.getKey(), entry.getValue())) {
				b.doRoutine();
				return;
			}
		}
	}



}

package bakpakin.egf.util.input;

import java.util.HashMap;

import org.lwjgl.input.Keyboard;

import bakpakin.egf.framework.Component;
import bakpakin.egf.util.VoidBehavior;

public class InputListener implements Component {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8010000030051292392L;
	public static final int HOLD = 1;
	public static final int PRESS = 2;
	public static final int RELEASE = 3;
	
	public static InputListener keyListener(VoidBehavior behavior, int keyCode, int holdPressOrRelease) {
		InputListener ret = new InputListener();
		ret.setBehavior(behavior);
		ret.addKeyTrigger(keyCode, holdPressOrRelease);
		return ret;
	}
	
	public static InputListener mouseListener(VoidBehavior behavior, int button, int holdPressOrRelease) {
		InputListener ret = new InputListener();
		ret.setBehavior(behavior);
		ret.addMouseTrigger(button, holdPressOrRelease);
		return ret;
	}
	
	private VoidBehavior behavior;
	
	private HashMap<Integer, Integer> mouseTriggers;
	private HashMap<Integer, Integer> keyTriggers;
	
	public InputListener() {
		mouseTriggers = new HashMap<Integer, Integer>();
		keyTriggers = new HashMap<Integer, Integer>();
	}
	
	public boolean hasKeyTrigger(int keyCode, int holdPressOrRelease) {
		Integer integer = keyTriggers.get(keyCode);
		if (integer == null) return false;
		int val = integer.intValue();
		if (val == holdPressOrRelease) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean hasMouseTrigger(int button, int holdPressOrRelease) {
		Integer integer = mouseTriggers.get(button);
		if (integer == null) return false;
		int val = integer.intValue();
		if (val == holdPressOrRelease) {
			return true;
		} else {
			return false;
		}
	}
	
	public void addMouseTrigger(int button, int holdPressOrRelease) {
		mouseTriggers.put(button, holdPressOrRelease);
	}
	
	public void addKeyTrigger(String key, int holdPressOrRelease) {
		addKeyTrigger(Keyboard.getKeyIndex(key), holdPressOrRelease);
	}
	
	public void addKeyTrigger(int keyCode, int holdPressOrRelease) {
		keyTriggers.put(keyCode, holdPressOrRelease);
	}
	
	public VoidBehavior getBehavior() {
		return behavior;
	}

	public void setBehavior(VoidBehavior behavior) {
		this.behavior = behavior;
	}

}
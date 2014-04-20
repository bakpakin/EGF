package bakpakin.egf.util.timer;

import bakpakin.egf.framework.Component;
import bakpakin.egf.util.VoidBehavior;

public class TimerComponent implements Component {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1892365674481207552L;
	
	private float timeLeft;
	private float timeRepeat;
	private boolean isPaused;
	private VoidBehavior behavior;
		
	public TimerComponent(float time, VoidBehavior behavior) {
		this(time, -1f, behavior);
	}
	
	public TimerComponent(float delay, float repeatDelay, VoidBehavior behavior) {
		this.timeLeft = delay;
		this.timeRepeat = repeatDelay;
		this.behavior = behavior;
	}

	public float getTimeLeft() {
		return timeLeft;
	}

	public void setTimeLeft(float timeLeft) {
		this.timeLeft = timeLeft;
	}

	public boolean isPaused() {
		return isPaused;
	}

	public void setPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}

	public VoidBehavior getBehavior() {
		return behavior;
	}

	public void setBehavior(VoidBehavior behavior) {
		this.behavior = behavior;
	}

	public float getTimeRepeat() {
		return timeRepeat;
	}

	public void setTimeRepeat(float timeRepeat) {
		this.timeRepeat = timeRepeat;
	}

}

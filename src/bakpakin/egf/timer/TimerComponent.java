package bakpakin.egf.timer;

import bakpakin.egf.framework.Component;
import bakpakin.egf.util.Routine;

public class TimerComponent implements Component {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1892365674481207552L;
	
	private float timeLeft;
	private float timeRepeat;
	private boolean isPaused;
	private Routine behavior;
		
	public TimerComponent(float time, Routine behavior) {
		this(time, -1f, behavior);
	}
	
	public TimerComponent(float delay, float repeatDelay, Routine behavior) {
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

	public Routine getBehavior() {
		return behavior;
	}

	public void setBehavior(Routine behavior) {
		this.behavior = behavior;
	}

	public float getTimeRepeat() {
		return timeRepeat;
	}

	public void setTimeRepeat(float timeRepeat) {
		this.timeRepeat = timeRepeat;
	}

}

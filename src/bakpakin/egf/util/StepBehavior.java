package bakpakin.egf.util;

import bakpakin.egf.framework.Component;

/**
 * 
 * @author Calvin
 *
 */
public class StepBehavior implements Component {

	/**
	 * 
	 */
	private static final long serialVersionUID = -766640423662843065L;

	private Routine step;
	
	private Routine start;
	
	boolean started;
	
	public StepBehavior(Routine step) {
		this(step, null);
	}
	
	public StepBehavior(Routine step, Routine start) {
		this.setStep(step);
		this.setStart(start);
	}

	public Routine getStep() {
		return step;
	}

	public void setStep(Routine step) {
		this.step = step;
	}

	public Routine getStart() {
		return start;
	}

	public void setStart(Routine start) {
		this.start = start;
	}
	
}

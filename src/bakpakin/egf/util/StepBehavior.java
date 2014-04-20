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

	private VoidBehavior step;
	
	private VoidBehavior start;
	
	boolean started;
	
	public StepBehavior(VoidBehavior step) {
		this(step, null);
	}
	
	public StepBehavior(VoidBehavior step, VoidBehavior start) {
		this.setStep(step);
		this.setStart(start);
	}

	public VoidBehavior getStep() {
		return step;
	}

	public void setStep(VoidBehavior step) {
		this.step = step;
	}

	public VoidBehavior getStart() {
		return start;
	}

	public void setStart(VoidBehavior start) {
		this.start = start;
	}
	
}

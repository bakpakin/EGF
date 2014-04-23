package bakpakin.egf.util.gui;

public class UIStateChangedEvent extends UIEvent {

	public final Object newState;
	
	public final Object oldState;
	
	public UIStateChangedEvent(String tag, Object oldState, Object newState) {
		super(tag);
		this.newState = newState;
		this.oldState = oldState;
	}

}

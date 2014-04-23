package bakpakin.egf.util.gui;

import java.util.Collection;

public abstract class UILayout {
	
	UIContainer container;

	public abstract void doLayout();
	
	public Collection<UIElement> getElements() {
		return container.getChildren();
	}

}

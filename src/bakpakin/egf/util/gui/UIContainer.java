package bakpakin.egf.util.gui;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public abstract class UIContainer extends UIElement {
	
	private List<UIElement> children;
	
	private UILayout layout;
	
	public UIContainer() {
		children = new LinkedList<UIElement>();
	}
	
	@Override
	public void paint() {
		paintSelf();
		for (UIElement e : children) {
			e.paint();
		}
	}
	
	public Collection<UIElement> getChildren() {
		return Collections.unmodifiableCollection(children);
	}
	
	public void update() {
		layout.doLayout();
	}
	
	@Override
	public void setUi(UI ui) {
		for (UIElement e : children) {
			if (e.isInheritTheme())
				e.setUi(ui);
		}
	}
	
	public abstract void paintSelf();

}

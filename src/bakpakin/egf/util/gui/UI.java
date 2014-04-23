
package bakpakin.egf.util.gui;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

import bakpakin.egf.util.render.Camera;

public class UI {
	
	private UITheme theme;
	
	private UIElement root;
	
	private Camera camera;
	
	private Map<String, Collection<UIActionListener>> actionListeners;
	private LinkedList<UIActionEvent> actionEvents;
	
	public UI(UITheme theme, UIElement root, Camera camera) {
		this.theme = theme;
		this.root = root;
		this.setCamera(camera);
		if (!Mouse.isCreated()) {
			try {
				Mouse.create();
			} catch (LWJGLException e) {
				e.printStackTrace();
			}
		}
		if (!Keyboard.isCreated()) {
			try {
				Keyboard.create();
			} catch (LWJGLException e) {
				e.printStackTrace();
			}
		}
		this.actionListeners = new HashMap<String, Collection<UIActionListener>>();
		this.actionEvents = new LinkedList<UIActionEvent>();
	}
	
	public void addActionListener(String eventTag, UIActionListener listener) {
		Collection<UIActionListener> col = actionListeners.get(eventTag);
		if (col == null) {
			col = new LinkedList<UIActionListener>();
			actionListeners.put(eventTag, col);
		}
		col.add(listener);
	}
	
	public void addActionEvent(UIActionEvent e) {
		actionEvents.add(e);
	}
	
	private void handleEvents() {
		while (!actionEvents.isEmpty()) {
			UIActionEvent e = actionEvents.removeFirst();
			Collection<UIActionListener> col = actionListeners.get(e.tag);
			if (col != null) {
				for (UIActionListener l : col)
					l.action(e);
			}
		}
	}

	public UITheme getTheme() {
		return theme;
	}

	public void setTheme(UITheme theme) {
		this.theme = theme;
	}

	public UIElement getRoot() {
		return root;
	}

	public void setRoot(UIContainer root) {
		this.root = root;
	}
	
	public void update() {
		if (root != null)
			root.update();
		handleEvents();
	}

	public void render() {
		if (root != null)
			root.paint();
	}
	
	public Vector2f getMouse() {
		return camera.worldPt(Mouse.getX(), Mouse.getY());
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}
	
}

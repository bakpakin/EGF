
package bakpakin.egf.util.gui;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import bakpakin.egf.framework.Entity;
import bakpakin.egf.framework.World;
import bakpakin.egf.util.render.Camera;
import bakpakin.egf.util.render.RenderComponent;
import bakpakin.egf.util.render.RenderSystem;

/**
 * The {@link UI} class serves as a GUI system that can be embedded in 
 * a game. A UI consists of a root {@link UIElement}, which can be updated
 * and rendered.  All events generated by children UIElements are sent to the UI, 
 * where registered listeners can respond to events based on their tags. UIs also support
 * a {@link UITheme}. A theme determines how children elements will render.
 * @author Calvin
 *
 */
public class UI {

	/**
	 * The {@link UITheme} used to render elements.
	 */
	private UITheme theme;

	/**
	 * The outermost {@link UIElement}.
	 */
	private UIElement root;

	private boolean hud = true;

	private Map<String, Collection<UIActionListener>> actionListeners;
	private LinkedList<UIActionEvent> actionEvents;

	private Map<String, Collection<UIStateListener>> stateListeners;
	private LinkedList<UIStateChangedEvent> stateEvents;

	private Entity renderingEntity;

	private RenderSystem rs;

	/**
	 * Creates a new {@link UI}. Simply creating a new UI will
	 * not cause it to be rendered or updated in a world. In order
	 * to render and update a UI, add a {@link UIDrawer} in a {@link RenderComponent}
	 * to a {@link World}. Or you can call
	 * @param theme
	 * @param root
	 * @param camera
	 */
	public UI(UITheme theme, UIElement root) {
		this.theme = theme;
		this.root = root;
		root.setUi(this);
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
		this.stateListeners = new HashMap<String, Collection<UIStateListener>>();
		this.stateEvents = new LinkedList<UIStateChangedEvent>();
	}

	public void addToRenderSystem(RenderSystem rs) {
		this.rs = rs;
		renderingEntity = rs.getWorld().createEntity(new RenderComponent(new UIDrawer(this)));
		setHud(hud);
		update();
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

	public void addStateListener(String eventTag, UIStateListener listener) {
		Collection<UIStateListener> col = stateListeners.get(eventTag);
		if (col == null) {
			col = new LinkedList<UIStateListener>();
			stateListeners.put(eventTag, col);
		}
		col.add(listener);
	}

	public void addStateEvent(UIStateChangedEvent e) {
		stateEvents.add(e);
	}

	private void handleEvents() {
		while (!actionEvents.isEmpty()) {
			UIActionEvent e = actionEvents.removeFirst();
			Collection<UIActionListener> col = actionListeners.get(e.tag);
			if (col != null) {
				for (UIActionListener l : col) {
					l.action(e);
				}
			}
		}
		while (!stateEvents.isEmpty()) {
			UIStateChangedEvent e = stateEvents.removeFirst();
			Collection<UIStateListener> col = stateListeners.get(e.tag);
			if (col != null) {
				for (UIStateListener l : col) {
					l.stateChanged(e);
				}
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

	void update() {
		if (root != null)
			root.update();
		handleEvents();
	}

	void render() {
		if (root != null) {
			GL11.glTranslatef(root.getX(), root.getY(), 0f);
			root.paint();
			GL11.glTranslatef(-root.getX(), -root.getY(), 0f);
		}
	}

	public Camera getCamera() {
		if (hud) {
			return rs.getHudCamera();
		} else {
			return rs.getCamera();
		}
	}

	public Vector2f getMouse() {
		return getCamera().worldPt(Mouse.getX(), Mouse.getY());
	}

	public boolean isHud() {
		return hud;
	}

	public void setHud(boolean hud) {
		this.hud = hud;
		if (this.renderingEntity != null)
			this.renderingEntity.get(RenderComponent.class).setDrawHud(hud);
	}

	public boolean isActive() {
		return renderingEntity.isActive();
	}

	public void setActive(boolean active) {
		renderingEntity.setActive(active);
	}

}

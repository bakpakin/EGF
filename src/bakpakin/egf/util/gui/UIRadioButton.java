package bakpakin.egf.util.gui;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

public class UIRadioButton extends UIAbstractButton {

	private boolean selected;
	
	private Texture texture;
	
	private String eventTag;
	
	public UIRadioButton(String eventTag) {
		setEventTag(eventTag);
		state = State.RELEASED;
	}

	@Override
	public void paint() {
		Color.white.bind();
		Texture t = selected ? getTheme().getRadioButtonSelected() : getTheme().getRadioButtonUnselected();
		texture = t;
		t.bind();
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0f, 0f);
		GL11.glVertex2f(0f, 0f);
		
		GL11.glTexCoord2f(t.getWidth(), 0f);
		GL11.glVertex2f(t.getImageWidth(), 0f);
		
		GL11.glTexCoord2f(t.getWidth(), t.getHeight());
		GL11.glVertex2f(t.getImageWidth(), t.getImageHeight());
		
		GL11.glTexCoord2f(0f, t.getHeight());
		GL11.glVertex2f(0f, t.getImageHeight());
		
		GL11.glEnd();
	}
	

	@Override
	public void buttonPressed() {
		selected = !selected;
		getUi().addStateEvent(new UIStateChangedEvent(eventTag, !selected, selected));
	}

	@Override
	public int getWidth() {
		if (texture == null)
			return 10;
		return texture.getImageWidth();
	}

	@Override
	public int getHeight() {
		if (texture == null)
			return 10;
		return texture.getImageHeight();
	}

	/**
	 * @return the selected
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * @param selected the selected to set
	 */
	public void setSelected(boolean selected) {
		if (this.selected != selected) {
			getUi().addStateEvent(new UIStateChangedEvent(eventTag, this.selected, selected));
		}
		this.selected = selected;
	}

	/**
	 * @return the eventTag
	 */
	public String getEventTag() {
		return eventTag;
	}

	/**
	 * @param eventTag the eventTag to set
	 */
	public void setEventTag(String eventTag) {
		this.eventTag = eventTag;
	}

}

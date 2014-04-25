package bakpakin.egf.particles;

import java.io.Serializable;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import bakpakin.egf.render.Sprite;

public class ParticleDef implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2406091326118837477L;
	
	private static int nextId = 0;
	
	public Sprite sprite;
	
	private int id;
	
	public float speedMin, speedMax, deltaSpeed, delta2Speed, wiggleSpeed;
	
	public float lifeMin = 2, lifeMax = 5;
	
	public float orientationMin, orientationMax, deltaOrientation, delta2Orientation, wiggleOrientation;
	
	public float directionMin, directionMax, deltaDirection, delta2Direction, wiggleDirection;
	
	public float alphaMin = 1f, alphaMax = 1f, deltaAlpha, delta2Alpha, wiggleAlpha;
	public float redMin = 1f, redMax = 1f, deltaRed, delta2Red, wiggleRed;
	public float greenMin = 1f, greenMax = 1f, deltaGreen, delta2Green, wiggleGreen;
	public float blueMin = 1f, blueMax = 1f, deltaBlue, delta2Blue, wiggleBlue;
	
	public float sizeMin = 1f, sizeMax = 1f, deltaSize, delta2Size, wiggleSize;
	
	public int blendModeSrc = GL11.GL_SRC_ALPHA;
	public int blendModeDest = GL11.GL_ONE_MINUS_SRC_ALPHA;
	
	public ParticleDef() {
		id = nextId++;
	}
	
	public void setSpeed(float min, float max, float delta, float delta2, float wiggle) {
		speedMin = min;
		speedMax = max;
		deltaSpeed = delta;
		delta2Speed = delta2;
		wiggleSpeed = wiggle;
	}
	
	public void setLife(float min, float max) {
		lifeMin = min;
		lifeMax = max;
	}
	
	public void setDirection(float min, float max, float delta, float delta2, float wiggle) {
		directionMin = min;
		directionMax = max;
		deltaDirection = delta;
		delta2Direction = delta2;
		wiggleDirection = wiggle;
	}
	
	public void setOrientation(float min, float max, float delta, float delta2, float wiggle) {
		orientationMin = min;
		orientationMax = max;
		deltaOrientation = delta;
		delta2Orientation = delta2;
		wiggleOrientation = wiggle;
	}
	
	public void setSize(float min, float max, float delta, float delta2, float wiggle) {
		sizeMin = min;
		sizeMax = max;
		deltaSize = delta;
		delta2Size = delta2;
		wiggleSize = wiggle;
	}
	
	public void setRed(float min, float max, float delta, float delta2, float wiggle) {
		redMin = min;
		redMax = max;
		deltaRed = delta;
		delta2Red = delta2;
		wiggleRed = wiggle;
	}
	
	public void setGreen(float min, float max, float delta, float delta2, float wiggle) {
		greenMin = min;
		greenMax = max;
		deltaGreen = delta;
		delta2Green = delta2;
		wiggleGreen = wiggle;
	}
	
	public void setBlue(float min, float max, float delta, float delta2, float wiggle) {
		blueMin = min;
		blueMax = max;
		deltaBlue = delta;
		delta2Blue = delta2;
		wiggleBlue = wiggle;
	}
	
	public void setAlpha(float min, float max, float delta, float delta2, float wiggle) {
		alphaMin = min;
		alphaMax = max;
		deltaAlpha = delta;
		delta2Alpha = delta2;
		wiggleAlpha = wiggle;
	}
	
	public void setColor(Color color) {
		setColor(color.r, color.g, color.b, color.a);
	}

	public void setColor(float r, float g, float b, float a) {
		setRed(r, r, 0f, 0f, 0f);
		setGreen(g, g, 0f, 0f, 0f);
		setBlue(b, b, 0f, 0f, 0f);
		setAlpha(a, a, 0f, 0f, 0f);
	}
	
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public void setSprite(String sprite) {
		setSprite(new Sprite(sprite));
	}
	
	public void setBlending(boolean additive) {
		if (additive) {
			blendModeSrc = GL11.GL_SRC_ALPHA;
			blendModeDest = GL11.GL_ONE;
		} else {
			blendModeSrc = GL11.GL_SRC_ALPHA;
			blendModeDest = GL11.GL_ONE_MINUS_SRC_ALPHA;
		}
	}

	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		return this == obj;
	}

}

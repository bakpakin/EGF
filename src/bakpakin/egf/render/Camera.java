package bakpakin.egf.render;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.Display;

import bakpakin.egf.geom.AxisAlignedBox;
import bakpakin.egf.geom.Transform;

import org.lwjgl.util.vector.Vector2f;

public class Camera implements Cloneable {
	
	private Transform transform;
	
	/**
	 * 
	 * @param viewx
	 * @param viewy
	 * @param viewWidth
	 * @param viewHeight
	 * @param viewAngle
	 */
	public Camera(float viewx, float viewy, float viewWidth, float viewHeight, float viewAngle) {
		this.setTransform(new Transform(viewx, viewy, viewWidth, viewHeight, viewAngle));
	}
		
	/**
	 * 
	 */
	public Camera() {
		this(0, 0, Display.getWidth(), Display.getHeight(), 0f);
	}
	
	/**
	 * 
	 * @param t
	 */
	public Camera(Transform t) {
		this.setTransform(t);
	}

	/**
	 * 
	 * @return
	 */
	public Transform getTransform() {
		return transform;
	}

	/**
	 * 
	 * @param transform
	 */
	public void setTransform(Transform transform) {
		this.transform = transform;
	}

	public void apply() {
		glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(
        		transform.getX()-transform.getXScale()*.5,
        		transform.getX()+transform.getXScale()*.5, 
        		transform.getY()+transform.getYScale()*.5,
        		transform.getY()-transform.getYScale()*.5,
        		-10000000, 
        		1000000
        		);	
        glTranslatef(transform.getX(), transform.getY(), 0);
        glRotatef(transform.getAngle(), 0f, 0f, 1f);
        glTranslatef(-transform.getX(), -transform.getY(), 0);
		glMatrixMode(GL_MODELVIEW);
	}
	
	public Vector2f screenPt(Vector2f worldPt) {
		return screenPt(worldPt.x, worldPt.y);
	}
	
	public Vector2f screenPt(float worldX, float worldY) {
		float x, y, x1, y1, cos, sin;
		final double ang = -transform.getAngle()*Math.PI/180.0;
		cos = (float) Math.cos(ang);
		sin = (float) Math.sin(ang);
		x1 = (worldX * cos) + (worldY * sin) - transform.getX();
		y1 = (worldY * cos) - (worldX * sin) - transform.getY();
		x = (x1 + transform.getXScale()*.5f)*Display.getWidth()/transform.getXScale();
		y = (y1 + transform.getYScale()*.5f)*Display.getHeight()/transform.getYScale();
		return new Vector2f(x, Display.getHeight() - y);
	}
	
	public Vector2f worldPt(Vector2f screenPt) {
		return worldPt(screenPt.x, screenPt.y);
	}
	
	public Vector2f worldPt(float screenX, float screenY) {
		float x, y, x1, y1, cos, sin;
		
		final double ang = transform.getAngle()*Math.PI/180.0;
		cos = (float) Math.cos(ang);
		sin = (float) Math.sin(ang);	
		
		x = (screenX)*transform.getXScale()/Display.getWidth() - transform.getXScale()*.5f;
		y = (Display.getHeight() - screenY)*transform.getYScale()/Display.getHeight() - transform.getYScale()*.5f;
		
		x1 = (x * cos) + (y * sin) + transform.getX();
		y1 = (y * cos) - (x * sin) + transform.getY();
		
		return new Vector2f(x1, y1);
	}
	
	public AxisAlignedBox getHitbox() {
		final double angle = transform.getAngle()/180*Math.PI;
		float hw = (float) (transform.getXScale()*Math.cos(angle) + (transform.getYScale()*Math.sin(angle)));
		hw *= .5f;
		float hh = (float) (transform.getXScale()*Math.sin(angle) + (transform.getYScale()*Math.cos(angle)));
		hh *= .5f;
		final float xx = transform.getX();
		final float yy = transform.getY();
		return new AxisAlignedBox(xx - hw, yy - hh, xx + hw, yy + hh);
	}
	
	@Override
	public Camera clone() {
		return new Camera(transform.clone());
		
	}

}

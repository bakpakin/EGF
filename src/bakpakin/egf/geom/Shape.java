package bakpakin.egf.geom;

import java.io.Serializable;

import org.lwjgl.util.vector.Vector2f;

public interface Shape extends Serializable {
	
	/**
	 * 
	 * @return
	 */
	public AxisAlignedBox getAxisAlignedBox();
	
	/**
	 * 
	 * @param t
	 * @return
	 */
	public AxisAlignedBox getAxisAlignedBox(Transform t);
	
	/**
	 * 
	 * @return
	 */
	public float getOriginX();
	
	/**
	 * 
	 * @return
	 */
	public float getOriginY();
	
	/**
	 * 
	 * @return
	 */
	public float getRotation();
	
	/**
	 * 
	 * @param t
	 * @return
	 */
	public Shape transformLocal(Transform t);
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean contains(float x, float y);
	
	/**
	 * 
	 * @return
	 */
	public Vector2f randomPoint();

}

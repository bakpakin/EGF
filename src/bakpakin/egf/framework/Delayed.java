package bakpakin.egf.framework;

/**
 * The {@link Delayed} interface describes
 * {@link EntitySystem}s that don't update
 * every frame. Instead, a Delayed updates
 * periodically. This can save computing power
 * for expensive tasks that don't need to 
 * be executed as frequently as the render loop.
 * @author Calvin
 *
 */
public interface Delayed {
	
	public float getDelay();
	
	public void setDelay(float seconds);

}

package bakpakin.egf.geom;

import org.lwjgl.util.vector.Vector2f;

public abstract class AbstractShape implements Shape {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8592602114013125230L;

	@Override
	public AxisAlignedBox getAxisAlignedBox(Transform t) {
		return transformLocal(t).getAxisAlignedBox();
	}

	@Override
	public Vector2f randomPoint() {
		return getAxisAlignedBox().randomPoint();
	}

}

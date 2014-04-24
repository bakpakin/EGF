package bakpakin.egf.util.geom;

import java.util.Arrays;
import java.util.Iterator;

import org.lwjgl.util.vector.Vector2f;

import static java.lang.Math.*;

public class Polygon extends AbstractShape implements Cloneable, Iterable<Vector2f> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7646481999142011119L;

	private final float[] points;
	private AxisAlignedBox boundingBox = null;
	protected final int numPoints;

	protected final float x, y, angle;

	/**
	 * 
	 * @param points
	 * @param numPoints
	 * @param originX
	 * @param originY
	 * @param angle
	 */
	public Polygon(float[] points, int numPoints, float originX, float originY, float angle) {
		this.points = Arrays.copyOf(points, 2*numPoints);
		this.numPoints = numPoints;
		this.x = originX;
		this.y = originY;
		this.angle = angle;
	}

	/**
	 * 
	 * @param points
	 */
	public Polygon(float[] points, int numPoints) {
		this(points, numPoints, 0, 0, 0);
	}

	/**
	 * 
	 * @param points
	 */
	public Polygon(float[] points) {
		this(points, points.length/2, 0, 0, 0);
	}

	/**
	 * 
	 * @param xpoints
	 * @param ypoints
	 * @param numPoints
	 * @param originX
	 * @param originY
	 * @param angle
	 */
	public Polygon(float[] xpoints, float[] ypoints, int numPoints, float originX, float originY, float angle) {
		assert xpoints.length >= numPoints && ypoints.length >= numPoints;
		this.points = new float[numPoints*2];
		for (int i = 0; i < numPoints; i++) {
			points[i*2] = xpoints[i];
			points[i*2+1] = ypoints[i];
		}
		this.numPoints = numPoints;
		this.x = originX;
		this.y = originY;
		this.angle = angle;
	}

	/**
	 * 
	 * @param xpoints
	 * @param ypoints
	 * @param numPoints
	 */
	public Polygon(float[] xpoints, float[] ypoints, int numPoints) {
		this(xpoints, ypoints, numPoints, 0, 0, 0);
	}

	/**
	 * 
	 * @param xpoints
	 * @param ypoints
	 */
	public Polygon(float[] xpoints, float[] ypoints) {
		this(xpoints, ypoints, xpoints.length > ypoints.length ? ypoints.length : xpoints.length);
	}

	/**
	 * Private copy constructor - copies without copying array.
	 * @param points
	 * @param dud
	 */
	private Polygon(float[] points, float originX, float originY, float angle, boolean dud) {
		this.points = points;
		this.numPoints = points.length/2;
		this.x = originX;
		this.y = originY;
		this.angle = angle;
	}

	@Override
	public AxisAlignedBox getAxisAlignedBox() {
		if (boundingBox == null) {
			float minX, maxX, minY, maxY, x, y;
			maxX = minX = points[0];
			maxY = minY = points[1];
			for (int i = 1; i < numPoints; i++) {
				x = getX(i);
				y = getY(i);
				maxX = x > maxX ? x : maxX;
				minX = x < minX ? x : minX;
				maxY = y > maxY ? y : maxY;
				minY = y < minY ? y : minY;
			}
			boundingBox = new AxisAlignedBox(minX, minY, maxX, maxY);
		}
		return boundingBox;
	}

	@Override
	public Polygon transformLocal(Transform t) {
		final float[] newPoints = new float[points.length];
		for (int i = 0; i < points.length; i += 2) {
			float x1 = points[i];
			float y1 = points[i+1];
			Vector2f v2 = new Vector2f(x1, y1);
			
			translate(v2, -x, -y);
			rotate(v2, -angle);
			scale(v2, t.xScale, t.yScale);
			rotate(v2, angle + t.angle);
			translate(v2, x + t.x, y + t.y);
			
			//put x2 and y2 into new array
			newPoints[i] = v2.x;
			newPoints[i+1] = v2.y;
		}
		return new Polygon(newPoints, x + t.x, y + t.y, angle + t.angle, false);
	}
	
	private void rotate(Vector2f v, float angle) {
		final float rad = (float) toRadians(angle);
		final float cos = (float)cos(rad);
		final float sin = (float)sin(rad);
		float px = v.x * cos - v.y * sin; 
		float py = v.x * sin + v.y * cos;
		v.x = px;
		v.y = py;
	}
	
	private void translate(Vector2f v, float dx, float dy) {
		v.x += dx;
		v.y += dy;
	}
	
	private void scale(Vector2f v, float fx, float fy) {
		v.x *= fx;
		v.y *= fy;
	}
	
	private class PointIterator implements Iterator<Vector2f> {
		
		private int index;

		@Override
		public boolean hasNext() {
			return index < points.length;
		}

		@Override
		public Vector2f next() {
			Vector2f ret = new Vector2f(points[index], points[index+1]);
			index += 2;
			return ret;
		}

		@Override
		public void remove() {
			
		}
	
	}

	@Override
	public boolean contains(float px, float py) {

		if (!getAxisAlignedBox().contains(px, py))
			return false;

		if (numPoints < 3)
			return false;

		boolean oddNodes = false;
		float x2 = getX(numPoints - 1);
		float y2 = getY(numPoints - 1);
		float x1, y1;
		for (int i = 0; i < numPoints; x2 = x1, y2 = y1, ++i) {
			x1 = getX(i);
			y1 = getY(i);
			if (((y1 < py) && (y2 >= py))
					|| (y1 >= py) && (y2 < py)) {
				if ((py - y1) / (y2 - y1)
						* (x2 - x1) < (px - x1))
					oddNodes = !oddNodes;
			}
		}
		return oddNodes;
	}
	
	/**
	 * 
	 * @param index
	 * @return
	 */
	public Vector2f getPoint(int index) {
		return new Vector2f(getX(index), getY(index));
	}
	
	/**
	 * 
	 * @return
	 */
	public int getNumPoints() {
		return numPoints;
	}

	/**
	 * 
	 * @param pointNum
	 * @return
	 */
	 private float getX(int pointNum) {
		return points[2*pointNum];
	}

	/**
	 * 
	 * @param pointNum
	 * @return
	 */
	 private float getY(int pointNum) {
		 return points[2*pointNum+1];
	 }

	 @Override
	 public Polygon clone() {
		 return new Polygon(points, x, y, angle, true);
	 }

	 @Override
	 public float getOriginX() {
		 return x;
	 }

	 @Override
	 public float getOriginY() {
		 return y;
	 }

	 @Override
	 public float getRotation() {
		 return angle;
	 }

	@Override
	public Iterator<Vector2f> iterator() {
		return new PointIterator();
	}

}

package bakpakin.egf.util.rig;

import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.newdawn.slick.Color;

import bakpakin.egf.util.geom.Transform;
import bakpakin.egf.util.render.Drawable;
import bakpakin.egf.util.render.RenderSystem;

/**
 * 
 * @author Calvin
 *
 */
public class RigDrawer implements Drawable {
	
	private static final Comparator<Bone> DEPTH_COMPARATOR = new Comparator<Bone>() {

		@Override
		public int compare(Bone o1, Bone o2) {
			float d1 = o1.getDepth();
			float d2 = o2.getDepth();
			return d2 < d1 ? -1 : 1;
		}
		
	};
	
	/**
	 * 
	 */
	private Bone rootBone;
	
	private SortedMap<Bone, Transform> depthSortedBones;
	
	/**
	 * 
	 * @param root
	 */
	public RigDrawer(Bone root) {
		setRootBone(root);
		depthSortedBones = new TreeMap<Bone, Transform>(DEPTH_COMPARATOR);
	}

	/**
	 * 
	 * @return
	 */
	public Bone getRootBone() {
		return rootBone;
	}

	/**
	 * 
	 * @param rootBone
	 */
	public void setRootBone(Bone rootBone) {
		this.rootBone = rootBone;
	}

	@Override
	public void draw(RenderSystem rs, float depth, Color color, Transform t) {
		depthSortedBones.clear();
		calcBone(rootBone, t);
		for (Map.Entry<Bone, Transform> entry : depthSortedBones.entrySet()) {
			Bone b = entry.getKey();
			Transform txm = entry.getValue();
			b.getDrawable().draw(rs, depth, color, txm);
		}
	}

	/**
	 * 
	 * @param renderSystem
	 * @param bone
	 * @param depth
	 * @param color
	 */
	private void calcBone(Bone bone, Transform parentTransform) {
		Transform t = Transform.add(parentTransform, bone.getTransform());
		float angle = (float)(Math.PI / 180 * parentTransform.getAngle());
		float w = bone.getTransform().getX() * bone.getTransform().getXScale();
		float h = bone.getTransform().getY() * bone.getTransform().getYScale();
		float cos = (float)Math.cos(angle);
		float sin = (float)Math.sin(angle);
		t.setX(parentTransform.getX() + (w*cos) + (h*sin));
		t.setY(parentTransform.getY() + (w*sin) + (h*cos));
		depthSortedBones.put(bone, t);
		for (Bone child : bone.getChildren()) {
			calcBone(child, t);
		}
	}

}

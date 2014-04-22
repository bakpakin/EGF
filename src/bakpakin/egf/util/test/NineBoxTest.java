package bakpakin.egf.util.test;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import bakpakin.egf.framework.World;
import bakpakin.egf.util.AssetManager;
import bakpakin.egf.util.Runner;
import bakpakin.egf.util.geom.Transform;
import bakpakin.egf.util.gui.NineBox;
import bakpakin.egf.util.render.Drawable;
import bakpakin.egf.util.render.RenderComponent;
import bakpakin.egf.util.render.RenderSystem;

public class NineBoxTest {

	public static void test() throws Exception {
		final World world = new World();
		final RenderSystem renderSystem = new RenderSystem();
		final SimpleNavigationSystem navigationSystem = new SimpleNavigationSystem(renderSystem);

		world.addSystem(renderSystem);
		world.addSystem(navigationSystem);
				
		final NineBox nb = new NineBox(AssetManager.getTexture("bakpakin/egf/util/test/ninebox.png"));
		
		world.createEntity(new RenderComponent(new Drawable(){

			@Override
			public void draw(RenderSystem renderSystem, float depth,
					Color color, Transform t) {
				color.bind();
				t.apply();
				GL11.glTranslatef(0f, 0f, depth);
				nb.draw(0, 0, 500, 500);
				t.applyInverse();
			}
			
		}));
		
		Runner.mainLoop(world);
	}
	
}

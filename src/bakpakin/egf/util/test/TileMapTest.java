package bakpakin.egf.util.test;

import bakpakin.egf.framework.World;
import bakpakin.egf.util.Runner;
import bakpakin.egf.util.physics.MovementSystem;
import bakpakin.egf.util.render.RenderComponent;
import bakpakin.egf.util.render.RenderSystem;
import bakpakin.egf.util.tilemap.JSONLoader;
import bakpakin.egf.util.tilemap.ObjectLayer;
import bakpakin.egf.util.tilemap.TileMap;
import bakpakin.egf.util.tilemap.TileMapRenderer;

public class TileMapTest {
	
	public static void test() throws Exception {
		final World world = new World();
		final RenderSystem renderSystem = new RenderSystem();
		final SimpleNavigationSystem navigationSystem = new SimpleNavigationSystem(renderSystem);

		world.addSystem(renderSystem);
		world.addSystem(new MovementSystem());
		world.addSystem(navigationSystem);
		
		TileMap tm = TileMap.load("bakpakin/egf/util/test/tileTest.json", new JSONLoader());
		ObjectLayer ol = (ObjectLayer) tm.getLayer("Object Layer 1");
		ol.addObjectsAsEntities(world);
		
		world.createEntity(new RenderComponent(new TileMapRenderer(tm)));
		
		Runner.mainLoop(world);
	}

}

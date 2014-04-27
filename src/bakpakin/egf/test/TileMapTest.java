package bakpakin.egf.test;

import bakpakin.egf.EGF;
import bakpakin.egf.framework.World;
import bakpakin.egf.physics.MovementSystem;
import bakpakin.egf.render.RenderComponent;
import bakpakin.egf.render.RenderSystem;
import bakpakin.egf.tilemap.JSONLoader;
import bakpakin.egf.tilemap.ObjectLayer;
import bakpakin.egf.tilemap.TileMap;
import bakpakin.egf.tilemap.TileMapRenderer;

public class TileMapTest {
	
	public static void test() throws Exception {
		EGF.init();

		final World world = new World();
		final RenderSystem renderSystem = new RenderSystem();
		final SimpleNavigationSystem navigationSystem = new SimpleNavigationSystem(renderSystem);

		world.addSystem(renderSystem);
		world.addSystem(new MovementSystem());
		world.addSystem(navigationSystem);
				
		TileMap tm = TileMap.load("bakpakin/egf/test/tileTest.json", new JSONLoader());
		ObjectLayer ol = (ObjectLayer) tm.getLayer("Object Layer 1");
		ol.addObjectsAsEntities(world, null);
		
		world.createEntity(new RenderComponent(new TileMapRenderer(tm)));
		
		EGF.mainLoop(world);
		
		EGF.cleanUp();
	}

}

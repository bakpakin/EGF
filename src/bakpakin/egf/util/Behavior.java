package bakpakin.egf.util;

import bakpakin.egf.framework.Entity;
import bakpakin.egf.framework.World;

public interface Behavior {
	
	void step(World world, Entity entity);
	
	void start(World world, Entity entity);
	
}

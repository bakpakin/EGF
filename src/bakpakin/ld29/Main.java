package bakpakin.ld29;

import bakpakin.egf.EGF;

public class Main {

	public static void main(String[] args) {
		EGF.setFps(60);
		EGF.init();
		
		EGF.mainLoop(new OceanScene());
		
		EGF.cleanUp();
	}

}

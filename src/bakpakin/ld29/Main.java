package bakpakin.ld29;

import bakpakin.egf.EGF;

public class Main {

	public static void main(String[] args) {
		//EGF.setFullscreen(false);
		//EGF.setDisplaySize(1000, 700);
		EGF.init();
		EGF.mainLoop(new MenuScene());
		
		EGF.cleanUp();
	}

}

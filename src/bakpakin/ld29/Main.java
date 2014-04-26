package bakpakin.ld29;

import bakpakin.egf.EGF;

public class Main {

	public static void main(String[] args) {
		EGF.init();
		
		EGF.mainLoop(new MenuScene());
		
		EGF.cleanUp();
	}

}

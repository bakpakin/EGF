package bakpakin.ld29;

import bakpakin.egf.EGF;

public class Main {

	public static void main(String[] args) {
		EGF.setDisplaySize(800, 600);
		EGF.init();
		
		EGF.mainLoop(new Scene());
		
		EGF.cleanUp();
	}

}

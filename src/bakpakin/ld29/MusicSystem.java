package bakpakin.ld29;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.openal.Audio;

import bakpakin.egf.framework.EmptyDelayedSystem;
import bakpakin.egf.util.AssetManager;

public class MusicSystem extends EmptyDelayedSystem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Audio> music;
	
	private Audio currentMusic;

	public MusicSystem() {
		this.setDelay(3);
		music = new ArrayList<Audio>();
		music.add(AssetManager.getSound("res/drumloop1.wav"));
		music.add(AssetManager.getSound("res/drumloop2.wav"));
	}

	@Override
	public void update() {
		if (Math.random() * 7 < 1) {
			if (currentMusic == null || !currentMusic.isPlaying()) {
				currentMusic = randomMusic();
				currentMusic.playAsMusic(1f, 0f, false);
			}
		}
	}
	
	private Audio randomMusic() {
		return music.get((int)(Math.random()*music.size()));
	}

}

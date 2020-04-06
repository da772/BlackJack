package application;

import engine.audio.AudioSource;

public class MusicSource extends AudioSource {

	public MusicSource(String fileName) {
		super( fileName, fileName, .25f, 1f, false, false, "music");
	}
	
	@Override
	public void OnEnd() {
		MusicPlayer.Next();
	}
	
	

}

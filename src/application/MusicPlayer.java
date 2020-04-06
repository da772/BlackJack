package application;

import java.util.Random;

import engine.audio.AudioManager;
import engine.audio.AudioSource;

public class MusicPlayer {

	private static final String[] songs = new String[] {"Audio/Music/ComputerGame.wav", "Audio/Music/ElectricDisco.wav",
			"Audio/Music/KilledRadioStar.wav", "Audio/Music/RadioStar.wav", "Audio/Music/RetroElectricDisco.wav", "Audio/Music/SuperStar.wav"};
	private static String[] _songs = songs.clone();
	private static int playedSongs = 0;
	private static int playingSong = -1;
	private static boolean setup = false;
	
	
	public static void Next() {
		if (!setup) {
			for (String s: songs) {
				AudioSource.Create(new MusicSource(s));
			}
			setup = true;
		}
		if (playedSongs >= songs.length) {
			_songs = songs.clone();
			playedSongs = 0;
		}
		int nSong = Math.abs(_songs.length-playedSongs-1);
		playingSong = nSong == 0 ? 0 : new Random().nextInt(nSong);
		String pSong = _songs[playingSong];
		_songs[playingSong] = _songs[Math.abs(_songs.length-playedSongs-1)];
		playedSongs++;
		AudioSource s = AudioSource.Create(new MusicSource(pSong));
		AudioManager.CreateAudioSource(s);
		AudioManager.PlaySource(pSong);
	}
	
}

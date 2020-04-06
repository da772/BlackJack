package engine.audio;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.openal.AL10;




public class AudioSource {

	private int sourceId;
	String name, fileName, category = "global";
	float volume,pitch, length;
	int bufferId, loop;
	int referenceCount = 0;
	private boolean setup = false, autoDelete = false, loaded = false;
	
	private static Map<String, AudioSource> sources = new HashMap<String, AudioSource>();
	

	public static AudioSource Create(String name, String fileName, float volume, float pitch, boolean loop, boolean autoDelete, String category) {
		if (sources.containsKey(name)) {
			sources.get(name).AddReferenceCount(1);
			return sources.get(name);
		}
		AudioSource t = new AudioSource(name, fileName, volume, pitch, loop, autoDelete, category);
		t.AddReferenceCount(1);
		t.loaded = AudioManager.loadAudio(t);
		sources.put(t.name, t);
		return t;
	}
	
	public static AudioSource Create(AudioSource source) {
		if (sources.containsKey(source.name)) {
			sources.get(source.name).AddReferenceCount(1);
			return sources.get(source.name);
		}
		source.AddReferenceCount(1);
		source.loaded = AudioManager.loadAudio(source);	
		sources.put(source.fileName, source);
		return source;
	}
	
	
	public static AudioSource Create(String fileName) {
		if (sources.containsKey(fileName)) {
			AudioSource t =  sources.get(fileName);
			t.AddReferenceCount(1);
			return t;
		} 
		return null;
	}
	
	public static AudioSource Get(String name) {
		if (sources.containsKey(name)) {
			return sources.get(name);
		}
		
		return null;
	}
	
	
	public static int GetPoolSize() {
		return sources.size();
	}
	/**
	 * Removes texture from pool
	 * @param fileName - path to image file
	 */
	public static void Remove(String fileName) {
		if (sources.containsKey(fileName)) {
			AudioSource t = sources.get(fileName);
			t.AddReferenceCount(-1);
		}
	}
	
	/**
	 * Removes texture from pool
	 * @param texture - texture to remove
	 */
	public static void Remove(AudioSource audio) {
		if (audio != null && sources.containsKey(audio.name)) {
			AudioSource t = sources.get(audio.name);
			t.AddReferenceCount(-1);
		}
	}
	
	private void AddReferenceCount(int i) {
		this.referenceCount += i;
		if (this.referenceCount <= 0) {
			sources.remove(this.name);
			this.clean();
		}
	}
	
	
	protected AudioSource(String name, String fileName, float volume, float pitch, boolean loop, boolean autoDelete, String category)
	{
		this.name = name;
		this.fileName = fileName;
		this.volume = volume;
		this.pitch = pitch;
		this.loop = loop ? 1 : 0;
		this.autoDelete = autoDelete;
		this.category = category;
	}
	
	public boolean ShouldAutoDelete() {
		return this.autoDelete;
	}
	
	public void SetLoaded(boolean b) {
		this.loaded = b;
	}
	
	public boolean IsLoaded() {
		return this.loaded;
	}
	
	public boolean IsPlaying() {
		return AL10.alGetSourcei(sourceId, AL10.AL_SOURCE_STATE) == AL10.AL_PLAYING;
	}
	
	public void SetUp() {
		if (setup) return;
		sourceId = AL10.alGenSources();
		AL10.alSourcef(sourceId, AL10.AL_GAIN, this.volume);
		AL10.alSourcef(sourceId, AL10.AL_PITCH, this.pitch);
		AL10.alSource3f(sourceId, AL10.AL_POSITION, 0f,0f,0f);
		AL10.alSourcei(sourceId, AL10.AL_LOOPING, this.loop);
		setup = true;
	}
	
	public void Play() {
		AL10.alSourcei(sourceId, AL10.AL_BUFFER, bufferId);
		AL10.alSourcePlay(sourceId);
	}
	
	public void Stop() {
		AL10.alSourcei(sourceId, AL10.AL_BUFFER, bufferId);
		AL10.alSourceStop(sourceId);
		//OnEnd();
	}
	
	
	public void OnEnd() {};
	
	public void SetVolume(float volume) {
		AL10.alSourcef(sourceId, AL10.AL_GAIN, volume);
	}
	
	public void SetPitch(float pitch) {
		AL10.alSourcef(sourceId, AL10.AL_PITCH, pitch);
	}
	
	
	public void SetLoop(int i) {
		AL10.alSourcef(sourceId, AL10.AL_LOOPING, i);
	}
	
	public void clean() {
		AL10.alDeleteSources(sourceId);
		AudioManager.DeleteBuffer(bufferId);
	}
	
	
	
	
}

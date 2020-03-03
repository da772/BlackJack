package engine.audio;

import org.lwjgl.openal.AL10;

public class AudioSource {

	private int sourceId;
	String name, fileName;
	float volume,pitch, length;
	int bufferId, loop;
	private boolean setup = false, autoDelete = false;
	

	public AudioSource(String name, String fileName, float volume, float pitch, boolean loop, boolean autoDelete)
	{
		this.name = name;
		this.fileName = fileName;
		this.volume = volume;
		this.pitch = pitch;
		this.loop = loop ? 1 : 0;
		this.autoDelete = autoDelete;
	}
	
	public boolean ShouldAutoDelete() {
		return this.autoDelete;
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
	
	public void SetVolume(float volume) {
		AL10.alSourcef(sourceId, AL10.AL_GAIN, volume);
	}
	
	public void SetPitch(float pitch) {
		AL10.alSourcef(sourceId, AL10.AL_PITCH, pitch);
	}
	
	
	public void SetLoop(int i) {
		AL10.alSourcef(sourceId, AL10.AL_LOOPING, i);
	}
	
	
	
	public void Cleanup() {
		AL10.alDeleteSources(sourceId);
		AudioManager.DeleteBuffer(bufferId);
	}
	
	
	
}

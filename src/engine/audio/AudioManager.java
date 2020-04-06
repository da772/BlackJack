package engine.audio;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.apache.commons.io.IOUtils;
import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.EXTEfx;

import engine.util.FileLoader;

public class AudioManager {
	
	private long device = -1;
	private long context = -1;
	static AudioManager manager;
	private List<Integer> buffers = new ArrayList<Integer>();
	private Map<String, Float> sourceCategoryVolume = new HashMap<String, Float>();
	private Map<String, List<String>> sourceCategory = new HashMap<String, List<String>>();
	private Map<String, AudioSource> sources = new HashMap<String, AudioSource>();
	private Stack<AudioSource> delete = new Stack<AudioSource>();
	private Stack<AudioSource> play = new Stack<AudioSource>();
	
	private void SetUp() {
		device = ALC10.alcOpenDevice((ByteBuffer)null);
		if (device == -1) {
			throw new IllegalStateException("Failed to open the default OpenAL device.");
	    }
		ALCCapabilities deviceCap = ALC.createCapabilities(device);
		IntBuffer contextAttribList = BufferUtils.createIntBuffer(16);
		
		
		contextAttribList.put(ALC10.ALC_REFRESH);
        contextAttribList.put(60);

        contextAttribList.put(ALC10.ALC_SYNC);
        contextAttribList.put(ALC10.ALC_FALSE);

        contextAttribList.put(EXTEfx.ALC_MAX_AUXILIARY_SENDS);
        contextAttribList.put(2);

        contextAttribList.put(0);
        contextAttribList.flip();
		
		
		context = ALC10.alcCreateContext(device, contextAttribList);
		
		if(!ALC10.alcMakeContextCurrent(context)) {
            throw new IllegalStateException("Failed to make context current");
        }
		AL.createCapabilities(deviceCap);
		
		SetListenerData();
		contextAttribList.clear();
		System.out.println("OpenAL " + ALC10.alcGetInteger(device, ALC10.ALC_MAJOR_VERSION) + "." + ALC10.alcGetInteger(device, ALC10.ALC_MINOR_VERSION)
		+  " Initialized: " + ALC10.alcGetString(device, ALC10.ALC_DEVICE_SPECIFIER)   );
	}
	
	private void SetListenerData() {
		AL10.alListener3f(AL10.AL_POSITION, 0, 0, 0);
		AL10.alListener3f(AL10.AL_VELOCITY, 0, 0, 0);
	}
	
	/**
	 * 
	 * @param category - category of sound to set volume of
	 * @param volume - how loud the volume is
	 */
	public static void SetCategoryVolume(String category, float volume) {
		if (manager.sourceCategory.containsKey(category)) {
			manager.sourceCategoryVolume.put(category, volume);
			for (String s : manager.sourceCategory.get(category)) {
				ResetVolume(s);
			}
		} else {
			manager.sourceCategoryVolume.put(category, volume);
		}
	}
	
	/**
	 * 
	 * @param category - category of sound to get
	 * @return
	 */
	public static float GetCategoryVolume(String category) {
		if (manager.sourceCategoryVolume.containsKey(category)) {
			return manager.sourceCategoryVolume.get(category);
		}
		return 1f;
	}
	
	public static void Update() {
		manager.OnUpdate();
	}
	
	private void OnUpdate() {
		if (!play.isEmpty()) {
			AudioSource s = AudioSource.Get(play.pop().name);
			s.SetUp();
			SetVolume(s.name, s.volume);
			if (!s.IsLoaded()) {
				System.out.println(s.fileName + " - " + s);
				loadAudio(s);	
			}
			s.Play();
			if (s.ShouldAutoDelete()) {
				delete.push(s);
			}
		}
			
		if (!delete.isEmpty()) {
			AudioSource s = delete.peek();
			if (!s.IsPlaying()) {
				s.OnEnd();
				RemoveSource(delete.pop());
			}
		}
	}
	
	/****
	 * 
	 * @param name - unique identifier
	 * @param fileName - location of audio to play
	 * @param category - category of sound
	 * @param volume - volume of audio to play
	 * @param pitch - pitch of audio to play
	 * @param loop - should it loop?
	 * @param autoDelete - should it delete after its done?
	 * @return
	 */
	public static String CreateAudioSource(String name, String fileName, String category, float volume, float pitch, boolean loop, boolean autoDelete) {
		if (!manager.sources.containsKey(name)) {
			AddSource(AudioSource.Create(name, fileName, volume, pitch, loop, autoDelete, category));
			ResetVolume(name);
		}
		return name;
	}
	
	public static String CreateAudioSource(AudioSource source) {
		if (!manager.sources.containsKey(source.name)) {
			AddSource(source);
			ResetVolume(source.name);
			return source.name;
		} else {
			RemoveSource(source.name);
			CreateAudioSource(source);
		}
		return null;
	}
	
	/****
	 * 
	 * @param name - unique identifier
	 * @param volume - new volume
	 */
	public static void SetVolume(String name, float volume) {
		if (manager.sources.containsKey(name)) {
			manager.sources.get(name).SetVolume(volume*manager.sourceCategoryVolume.get(manager.sources.get(name).category));
		}
	}
	

	/****
	 * 
	 * @param name - unique identifier
	 * @param volume - new volume
	 */
	public static void ResetVolume(String name) {
		if (manager.sources.containsKey(name)) {
			manager.sources.get(name).SetVolume(manager.sources.get(name).volume*
			manager.sourceCategoryVolume.get(manager.sources.get(name).category));
		}
	}
	
	
	/**
	 * 
	 * @param name - unique identifier
	 * @param pitch - new pitch
	 */
	public static void SetPitch(String name, float pitch) {
		if (manager.sources.containsKey(name)) {
			manager.sources.get(name).SetPitch(pitch);
		}
	}
	
	/****
	 * 
	 * @param name - unique identifier
	 * @param loop - should loop?
	 */
	public static void SetLoop(String name, boolean loop) {
		if (manager.sources.containsKey(name)) {
			manager.sources.get(name).SetLoop(loop?1:0);
		}
	}
	
	private static void AddSource(AudioSource s) {
		manager.sources.put(s.name, s);
		
		if (manager.sourceCategory.containsKey(s.category)) {
			if (manager.sourceCategory.get(s.category) != null) {
				manager.sourceCategory.get(s.category).add(s.name);
				
			} 
		} else {
			manager.sourceCategory.put(s.category, new ArrayList<String>());
			if (!manager.sourceCategoryVolume.containsKey(s.category)) {
				manager.sourceCategoryVolume.put(s.category, 1f);	
			}
			manager.sourceCategory.get(s.category).add(s.name);
		}
	}
	
	/**
	 * 
	 * @param name - unique identifier
	 */
	public static void PlaySource(String name) {
		if (manager.sources.containsKey(name)) {
			manager.play.push(manager.sources.get(name));
		}
	}
	
	/**
	 * 
	 * @param s - unique identifier
	 */
	public static void RemoveSource(AudioSource s) {
		if (s == null) return;
		manager.sources.remove(s.name);
		if (manager.sourceCategory.containsKey(s.category)) {
			manager.sourceCategory.get(s.category).remove(s.name);
			if (manager.sourceCategory.get(s.category).size() <= 0) {
				manager.sourceCategory.remove(s.category);
			}
		}
		AudioSource.Remove(s);
	}
	
	/**
	 * 
	 * @return - returns category names as string
	 */
	public static String[] GetCategories() {
		String[] categories = new String[manager.sourceCategory.size()];
		int counter=  0;
		for (Map.Entry<String,List<String>> key  : manager.sourceCategory.entrySet()) {
			categories[counter++] = key.getKey();
			
		}
		return categories;
	}
	
	public static void DeleteBuffer(int bufferId) {
		if (manager.buffers.contains(bufferId)) {
			AL10.alDeleteBuffers(bufferId);
			manager.buffers.remove((Object)bufferId);
		}
	}
	
	public static void RemoveSource(String name) {
		if (manager.sources.containsKey(name)) {
			RemoveSource(manager.sources.get(name));
		}
	}
	
	public static void Init() {
		manager = new AudioManager();
		manager.SetUp();
	}
	

	public static boolean loadAudio(AudioSource s) {
		final int MONO = 1, STEREO = 2;
		int buffer = AL10.alGenBuffers();
		manager.buffers.add(buffer);
		try {
			AudioInputStream stream = AudioSystem.getAudioInputStream(FileLoader.getResourceAsFile(s.fileName));
			AudioFormat format = stream.getFormat();

	        if(format.isBigEndian()) { stream.close(); throw new UnsupportedAudioFileException("Can't handle Big Endian formats yet"); };
	       
	        int openALFormat = -1;
	        switch(format.getChannels()) {
	            case MONO:
	                switch(format.getSampleSizeInBits()) {
	                    case 8:
	                        openALFormat = AL10.AL_FORMAT_MONO8;
	                        break;
	                    case 16:
	                        openALFormat = AL10.AL_FORMAT_MONO16;
	                        break;
	                }
	                break;
	            case STEREO:
	                switch(format.getSampleSizeInBits()) {
	                    case 8:
	                        openALFormat = AL10.AL_FORMAT_STEREO8;
	                        break;
	                    case 16:
	                        openALFormat = AL10.AL_FORMAT_STEREO16;
	                        break;
	                }
	                break;
	        }
	        byte[] b = IOUtils.toByteArray(stream);
	        ByteBuffer data = BufferUtils.createByteBuffer(b.length).put(b);
	        data.flip();

	        AL10.alBufferData(buffer, openALFormat, data, (int)format.getSampleRate());
	        stream.close();
	        s.bufferId = buffer;
	        s.length = (1000f * stream.getFrameLength() / format.getFrameRate());
	        data.clear();
	        
	        return true;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return false;
		
	}
	
	public static void Shutdown() {
		manager.CleanUp();
	}
	
	private void CleanUp() {
		play.clear();
		delete.clear();
		for (int buffer : buffers) {
			AL10.alDeleteBuffers(buffer);
		}
		buffers.clear();
		for (Map.Entry<String,AudioSource> key : sources.entrySet()) {
			key.getValue().clean();
		}
		sources.clear();
		ALC10.alcCloseDevice(device);
		ALC10.alcDestroyContext(context);
		device = -1;
		context = -1;
	}
	
}

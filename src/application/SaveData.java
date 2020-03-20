package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import engine.util.FileLoader;

public abstract class SaveData implements java.io.Serializable {

	private static final long serialVersionUID = -1059486843029262743L;

	/**
	 * 
	 * Game Settings Save Object
	 *
	 */
	public static class Settings extends SaveData {

		public Settings(int width, int height, int fpsCap, boolean fullscreen, boolean vsync,
				float musicVolume, float sfxVolume, float renderScale) {
			this.width = width;
			this.height = height;
			this.fpsCap = fpsCap;
			this.fullscreen = fullscreen;
			this.vsync = vsync;
			this.musicVolume = musicVolume;
			this.sfxVolume = sfxVolume;
			this.renderScale = renderScale;
		}
		
		public Settings() {
			this.width = 1280;
			this.height = 720;
			this.fpsCap = 244;
			this.fullscreen = true;
			this.vsync = false;
			this.musicVolume = 1f;
			this.sfxVolume = 1f;
			this.renderScale = 1f;
		}
		
		public void SetWidth(int width) {
			this.width = width;
			SaveSettings(this);
		}
		
		public void SetHeight(int height) {
			this.height = height;
			SaveSettings(this);
		}
		
		public void SetFpsCap(int fpsCap) {
			this.fpsCap = fpsCap;
			SaveSettings(this);
		}
		
		public void SetFullScreen(boolean fullscreen) {
			this.fullscreen = fullscreen;
			SaveSettings(this);
		}
		
		public void SetVsync(boolean vSync) {
			this.vsync = vSync;
			SaveSettings(this);
		}
		
		public void SetMusicVolume(float musicVolume) {
			this.musicVolume = musicVolume;
			SaveSettings(this);
		}
		
		public void SetSfxVolume(float sfxVolume) {
			this.sfxVolume = sfxVolume;
			SaveSettings(this);
		}
		
		public void SetRenderScale(float renderScale) {
			this.renderScale = renderScale;
			SaveSettings(this);
		}
		
		public int GetWidth() {
			return this.width;
		}
		
		public int GetHeight() {
			return this.height;
		}
		
		public int GetFpsCap() {
			return this.fpsCap;
		}
		
		public boolean GetFullScreen() {
			return this.fullscreen;
		}
		
		public boolean GetVSync() {
			return this.vsync;
		}
		
		public float GetMusicVolume() {
			return this.musicVolume;
		}
		
		public float GetSfxVolume() {
			return this.sfxVolume;
		}
		
		public float GetRenderScale() {
			return this.renderScale;
		}
 		
		private static final long serialVersionUID = 6974426236134027694L;
		private int width, height, fpsCap;
		private boolean fullscreen, vsync;
		private float musicVolume, sfxVolume, renderScale;
		
		
		
	}
	
	public static void SaveSettings(SaveData.Settings s) {
		         try { 
		        	File f = FileLoader.CreateFile(FileLoader.getAppDataDirectory()+"/BlackJack/Saves","Settings.sav");
		        	FileOutputStream fileOut =
		    		         new FileOutputStream(f);
					ObjectOutputStream out = new ObjectOutputStream(fileOut);
					out.writeObject(s);
					out.close();
					fileOut.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	}
	
	public static SaveData.Settings GetSettings() {
		try {
			 File f = new File(FileLoader.getAppDataDirectory()+"/BlackJack/Saves/Settings.sav");
			 if (!f.exists()) {
				 SaveSettings(new Settings());
				 f = new File(FileLoader.getAppDataDirectory()+"/BlackJack/Saves/Settings.sav");
			 }
			 
	         FileInputStream fileIn = new FileInputStream(f);
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         SaveData.Settings settings = (SaveData.Settings) in.readObject();
	         in.close();
	         fileIn.close();
	         return settings;
	      } catch (IOException i) {
	         i.printStackTrace();
	      } catch (ClassNotFoundException c) {
	         System.out.println("Settings not found");
	         c.printStackTrace();
	      }
		
		return null;
	}
	
	
}

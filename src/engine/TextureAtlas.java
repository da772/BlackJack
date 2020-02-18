package engine;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import renderer.Renderer;
import util.FileLoader; 

public class TextureAtlas {

	
	static Map<String, TextureAtlas> atlas = new HashMap<String, TextureAtlas>();
	
	protected String fileName;
	protected int referenceCount = 0;
	JSONObject root;
	
	/**
	 * 
	 * @param fileName - atlas json file to load without json extension, uses fileName to pool atlas
	 * @return
	 */
	public static TextureAtlas Create(String fileName) {
		if (atlas.containsKey(fileName)) {
			TextureAtlas t =  atlas.get(fileName);
			t.AddReferenceCount(1);
			return t;
		}
		TextureAtlas t = new TextureAtlas(fileName);
		t.fileName = fileName;
		t.AddReferenceCount(1);
		atlas.put(fileName, t);
		return t;
	}
	
	/**
	 * 
	 * @param texture - texture atlas to remove
	 */
	public static void Remove(TextureAtlas texture) {
		if (atlas.containsKey(texture.fileName)) {
			TextureAtlas t = atlas.get(texture.fileName);
			t.AddReferenceCount(-1);
		}
	}
	
	private void AddReferenceCount(int i) {
		this.referenceCount += i;
		if (this.referenceCount <= 0) {
			atlas.remove(this.fileName);
			this.CleanUp();
			Renderer.RemoveAtlas(this);
		}
	}

	private void CleanUp() {
		
	}
	
	public static int GetCount() {
		return atlas.size();
	}

	public static void Remove(String file) {
		atlas.remove(file);
	}
	
	
	protected TextureAtlas(String file) {
		this.fileName = file;
		try {
			root = (JSONObject) new JSONParser().parse(FileLoader.getResourceAsString(file+".json"));
		} catch (ParseException e) {
			e.printStackTrace();
			root = null;
		}
		Renderer.AddTextureAtlas(this);
	}
	
	/**
	 * 
	 * @param name - Json object to get by name
	 * @return
	 */
	public JSONReader GetObject(String name) {
		return new JSONReader((JSONObject) root.get(name));
	}
	
	
	public static class JSONReader {
		JSONObject obj;
		public JSONReader(JSONObject obj) {
			this.obj = obj;
		}
		/**
		 * 
		 * @param s - Get object in json by String
		 * @return
		 */
		public JSONReader GetObject(String s) {
			this.obj = (JSONObject) obj.get(s);
			return this;
		}
		
		/**
		 * 
		 * @param s - get string of object by string
		 * @return
		 */
		public String GetString(String s) {
			return (String) obj.get(s);
		}
		
		/**
		 * 
		 * @param s - get int of object by string
		 * @return
		 */
		public int GetInt(String s) {
			return (int) (long)obj.get(s);
		}
		/**
		 * 
		 * @param s - get float of object by string
		 * @return
		 */
		public float GetFloat(String s) {
			return (float) Float.parseFloat((String) obj.get(s));
		}
		
		
	}
	
	
}



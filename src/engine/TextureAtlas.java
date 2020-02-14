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
	
	public JSONReader GetObject(String name) {
		return new JSONReader((JSONObject) root.get(name));
	}
	
	
	public static class JSONReader {
		JSONObject obj;
		public JSONReader(JSONObject obj) {
			this.obj = obj;
		}
		
		public JSONReader GetObject(String s) {
			this.obj = (JSONObject) obj.get(s);
			return this;
		}
		
		public String GetString(String s) {
			return (String) obj.get(s);
		}
		
		public int GetInt(String s) {
			return (int) (long)obj.get(s);
		}
		
		public float GetFloat(String s) {
			return (float) Float.parseFloat((String) obj.get(s));
		}
		
		
	}
	
	
}



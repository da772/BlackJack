package renderer.text;

import java.util.HashMap;
import java.util.Map;

import util.FileLoader;


public class FontType {


	private TextMeshCreator loader;
	private String fontFile;
	private int referenceCount = 0;

	private static Map<String, FontType> Fonts = new HashMap<String, FontType>();
	
	public static FontType Create(String fontFile) {
		
		if (Fonts.containsKey(fontFile)) {
			FontType t =  Fonts.get(fontFile);
			t.AddReferenceCount(1);
			return t;
		}
		FontType t = new FontType(fontFile);
		t.fontFile = fontFile;
		t.AddReferenceCount(1);
		Fonts.put(fontFile, t);
		return t;
	
	}
	
	public static int GetPoolSize() {
		return Fonts.size();
	}
	
	public static void Remove(String fileName) {
		if (Fonts.containsKey(fileName)) {
			FontType t = Fonts.get(fileName);
			t.AddReferenceCount(-1);
		}
	}
	
	public static void Remove(String fileName, boolean remove) {
		if (Fonts.containsKey(fileName)) {
			FontType t = Fonts.get(fileName);
			if (remove)
				t.AddReferenceCount(-1);
		}
	}
	
	public static void Remove(FontType fontType) {
		if (Fonts.containsKey(fontType.fontFile)) {
			FontType t = Fonts.get(fontType.fontFile);
			t.AddReferenceCount(-1);
		}
	}
	
	private void AddReferenceCount(int i) {
		this.referenceCount += i;
		if (this.referenceCount <= 0) {
			Fonts.remove(this.fontFile);
		}
	}
	

	/**
	 * Creates a new font and loads up the data about each character from the
	 * font file.
	 * 
	 * @param textureAtlas
	 *            - the ID of the font atlas texture.
	 * @param fontFile
	 *            - the font file containing information about each character in
	 *            the texture atlas.
	 */
	private FontType(String fontFile) {
		this.fontFile = fontFile;
		this.loader = new TextMeshCreator(FileLoader.getResourceAsFile(fontFile+".fnt"));
	}

	

	/**
	 * Takes in an unloaded text and calculate all of the vertices for the quads
	 * on which this text will be rendered. The vertex positions and texture
	 * coords and calculated based on the information from the font file.
	 * 
	 * @param text
	 *            - the unloaded text.
	 * @return Information about the vertices of all the quads.
	 */
	public TextMeshData loadText(GUIText text) {
		return loader.createTextMesh(text);
	}

}

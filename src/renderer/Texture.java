package renderer;



import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.stb.STBImage.*;

import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryStack;

import engine.Application;
import util.FileLoader;


public class Texture {
	
	int width;
	int height;
	ByteBuffer data;
	private int rendererId = -1;
	int referenceCount = 0;
	String fileName;
	boolean generateMipMap = false, fontTexture = false;
	
	private static Map<String, Texture> textures = new HashMap<String, Texture>();
	
	/**
	 * Creates texture and adds it to a pool for future use
	 * @param fileName - path to image file 
	 * @return - Texture
	 */
	public static Texture Create(String fileName) {
		return Texture.Create(fileName, false, true);
	}
	
	public static Texture Create(String name, ByteBuffer data, int width, int height, int format) {
		if (textures.containsKey(name)) {
			Texture t =  textures.get(name);
			t.AddReferenceCount(1);
			return t;
		}
		Texture t = new Texture(name, data, width, height, format);
		t.fileName = name;
		t.AddReferenceCount(1);
		textures.put(name, t);
		return t;
	}
	
	/**
	 * Creates texture and adds it to a pool for future use
	 * @param fileName - path to image file
	 * @param flip - flip y?
	 * @param generateMipMap - generateMipMap?
	 * @return - Texture
	 */
	public static Texture Create(String fileName, boolean flip, boolean generateMipMap) {
		if (textures.containsKey(fileName)) {
			Texture t =  textures.get(fileName);
			t.AddReferenceCount(1);
			return t;
		}
		Texture t = new Texture(fileName, flip, generateMipMap);
		t.fileName = fileName;
		t.AddReferenceCount(1);
		textures.put(fileName, t);
		return t;
	}
	
	
	public static int GetPoolSize() {
		return textures.size();
	}
	
	/**
	 * Removes texture from pool
	 * @param fileName - path to image file
	 */
	public static void Remove(String fileName) {
		if (textures.containsKey(fileName)) {
			Texture t = textures.get(fileName);
			t.AddReferenceCount(-1);
		}
	}
	
	/**
	 * Removes texture from pool
	 * @param texture - texture to remove
	 */
	public static void Remove(Texture texture) {
		if (texture != null && textures.containsKey(texture.fileName)) {
			Texture t = textures.get(texture.fileName);
			t.AddReferenceCount(-1);
		}
	}
	
	private void AddReferenceCount(int i) {
		this.referenceCount += i;
		if (this.referenceCount <= 0) {
			textures.remove(this.fileName);
			this.CleanUp();
			Renderer.RemoveTexture(this);
		}
	}
	
	protected Texture(String fileName) {
		rendererId = GL30.glGenTextures();
		
		Renderer.AddTexture(this);
		LoadImage(fileName, true);
	}
	
	
	protected Texture(String name, ByteBuffer data, int width, int height, int format) {
		rendererId = GL30.glGenTextures();
		this.width = width;
		this.height = height;
		Renderer.AddTexture(this);
		LoadImageData(data, width, height, format);
	}
	
	protected Texture(String fileName, boolean fontTexture, boolean mipMap) {
		rendererId = GL30.glGenTextures();
		this.generateMipMap = mipMap;
		this.fontTexture = fontTexture;
		
		Renderer.AddTexture(this);
		LoadImage(fileName, !fontTexture);
		
	}
	
	public void LoadImageData(ByteBuffer data, int width, int height, int format) {
		Bind();
		setParameter(GL30.GL_TEXTURE_MIN_FILTER, GL30.GL_LINEAR);
		setParameter(GL30.GL_TEXTURE_MAG_FILTER, GL30.GL_LINEAR);
		uploadData(format, width, height, format, data);
		UnBind();
	}
	
	public void Bind(int slot) {
		GL30.glActiveTexture(GL30.GL_TEXTURE1);
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, rendererId);
	}
	
	public void Bind() {
		GL30.glActiveTexture(GL30.GL_TEXTURE0);
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, rendererId);
    }
	
	public void UnBind() {
		GL30.glActiveTexture(GL30.GL_TEXTURE0);
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, 0);
    }
	
	public int GetID() {
		return rendererId;
	}
	
	/**
	 * Set currently bound texture's parameter
	 * @param name - name of parameter
	 * @param value - value of parameter
	 */
	public void setParameter(int name, int value) {
		GL30.glTexParameteri(GL30.GL_TEXTURE_2D, name, value);
		
	}
	
	/**
	 * Set currently bound texture's parameter
	 * @param name - name of parameter
	 * @param value - value of parameter
	 */
	public void setParameter(int name, float value) {
		GL30.glTexParameterf(GL30.GL_TEXTURE_2D, name, value);
	}
	
	private void uploadData(int internalFormat, int width, int height, int format, ByteBuffer data) {
        GL30.glTexImage2D(GL30.GL_TEXTURE_2D, 0, internalFormat, width, height, 0, format, GL30.GL_UNSIGNED_BYTE, data);
	}
    
	public void CreateTexture() {
		Bind();
	    setParameter(GL30.GL_TEXTURE_WRAP_S, GL30.GL_REPEAT);
	    setParameter(GL30.GL_TEXTURE_WRAP_T, GL30.GL_REPEAT);
	    if (!fontTexture && generateMipMap) {
		    setParameter(GL30.GL_TEXTURE_MAG_FILTER, GL30.GL_LINEAR_MIPMAP_LINEAR);
		    setParameter(GL30.GL_TEXTURE_MIN_FILTER, GL30.GL_LINEAR_MIPMAP_LINEAR);
		    GL30.glGenerateMipmap(GL30.GL_TEXTURE_2D);
	    } else if (fontTexture && generateMipMap) {
			setParameter(GL30.GL_TEXTURE_WRAP_S, GL30.GL_CLAMP_TO_EDGE);
		    setParameter(GL30.GL_TEXTURE_WRAP_T, GL30.GL_CLAMP_TO_EDGE);
		    setParameter(GL30.GL_TEXTURE_MAG_FILTER, GL30.GL_LINEAR_MIPMAP_LINEAR);
		    setParameter(GL30.GL_TEXTURE_MIN_FILTER, GL30.GL_LINEAR_MIPMAP_LINEAR);
		    setParameter(GL30.GL_TEXTURE_LOD_BIAS, -.6f);   
		   
		} else {
	    	setParameter(GL30.GL_TEXTURE_MAG_FILTER, GL30.GL_LINEAR);
	  		setParameter(GL30.GL_TEXTURE_MIN_FILTER, GL30.GL_LINEAR);
	    }
	    uploadData(GL30.GL_RGBA, width, height, GL30.GL_RGBA, data);
	    GL30.glGenerateMipmap(GL30.GL_TEXTURE_2D);
	    stbi_image_free(data);
	    UnBind();
	    
	}
	
	private void LoadImage(String path, boolean flip) {
	try (MemoryStack stack = MemoryStack.stackPush()){
			
			IntBuffer w = stack.mallocInt(1);
			IntBuffer h = stack.mallocInt(1);
			IntBuffer comp = stack.mallocInt(1);
			
			stbi_set_flip_vertically_on_load(flip);
			
			
			ByteBuffer buffer = FileLoader.getResourceAsByteBuffer(path);
			
			data = stbi_load_from_memory(buffer, w, h, comp, 4);
			
			if (data == null) {
				 throw new RuntimeException("Failed to load a texture file!"
                         + System.lineSeparator() + stbi_failure_reason());
			}
			
			width = w.get();
			height = h.get();
			w.clear();
			h.clear();
			comp.clear();
			
	}
							
		CreateTexture();
	}
	
	protected void CleanUp() {
		if (Application.ThreadSafe())
			GL30.glDeleteTextures(rendererId);
	}
	
	public String GetFileName() {
		return this.fileName;
	}
	

}

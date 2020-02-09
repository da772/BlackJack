package renderer;


import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.stb.STBImage.*;

import org.apache.commons.io.IOUtils;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryStack;


public class Texture {
	
	int width;
	int height;
	ByteBuffer data;
	private int rendererId;
	int referenceCount = 0;
	String fileName;
	
	private static Map<String, Texture> textures = new HashMap<String, Texture>();
	
	public static Texture Create(String fileName) {
		return Texture.Create(fileName, false);
	}
	
	public static Texture Create(String fileName, boolean flip) {
		if (textures.containsKey(fileName)) {
			Texture t =  textures.get(fileName);
			t.AddReferenceCount(1);
			return t;
		}
		Texture t = new Texture(fileName, flip);
		t.fileName = fileName;
		t.AddReferenceCount(1);
		textures.put(fileName, t);
		return t;
	}
	
	
	public static int GetPoolSize() {
		return textures.size();
	}
	
	public static void Remove(String fileName) {
		if (textures.containsKey(fileName)) {
			Texture t = textures.get(fileName);
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
	
	protected Texture(String fileName, boolean fontTexture) {
		rendererId = GL30.glGenTextures();
		
		Renderer.AddTexture(this);
		LoadImage(fileName, !fontTexture);
		
		if (fontTexture) {
			setParameter(GL30.GL_TEXTURE_WRAP_S, GL30.GL_CLAMP_TO_EDGE);
		    setParameter(GL30.GL_TEXTURE_WRAP_T, GL30.GL_CLAMP_TO_EDGE);
		    setParameter(GL30.GL_TEXTURE_MAG_FILTER, GL30.GL_LINEAR);
		    setParameter(GL30.GL_TEXTURE_MIN_FILTER, GL30.GL_LINEAR_MIPMAP_LINEAR);
		}
		
	}
	
	public void Bind() {
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, rendererId);
    }
	
	public void UnBind() {
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, 0);
    }
	
	public int GetID() {
		return rendererId;
	}
	
	public void setParameter(int name, int value) {
		GL30.glTexParameteri(GL30.GL_TEXTURE_2D, name, value);
	}
	
	private void uploadData(int internalFormat, int width, int height, int format, ByteBuffer data) {
        GL30.glTexImage2D(GL30.GL_TEXTURE_2D, 0, internalFormat, width, height, 0, format, GL30.GL_UNSIGNED_BYTE, data);
	}
    
	public void CreateTexture() {
		Bind();
		uploadData(GL30.GL_RGBA, width, height, GL30.GL_RGBA, data);
	    setParameter(GL30.GL_TEXTURE_WRAP_S, GL30.GL_REPEAT);
	    setParameter(GL30.GL_TEXTURE_WRAP_T, GL30.GL_REPEAT);
	    setParameter(GL30.GL_TEXTURE_MAG_FILTER, GL30.GL_LINEAR_MIPMAP_LINEAR);
	    setParameter(GL30.GL_TEXTURE_MIN_FILTER, GL30.GL_LINEAR_MIPMAP_LINEAR);
	    GL30.glGenerateMipmap(GL30.GL_TEXTURE_2D);
	    stbi_image_free(data);
	    
	}
	
	private void LoadImage(String path, boolean flip) {
	try (MemoryStack stack = MemoryStack.stackPush()){
			
			IntBuffer w = stack.mallocInt(1);
			IntBuffer h = stack.mallocInt(1);
			IntBuffer comp = stack.mallocInt(1);
			
			stbi_set_flip_vertically_on_load(flip);
			
			InputStream ss = this.getClass().getClassLoader().getResourceAsStream(path);
			byte[] bytes = IOUtils.toByteArray(ss);
			
			ByteBuffer buffer = BufferUtils.createByteBuffer(bytes.length);
			buffer.put(bytes);
			buffer.flip();
			
			data = stbi_load_from_memory(buffer, w, h, comp, 4);
			
			if (data == null) {
				ss.close();
				 throw new RuntimeException("Failed to load a texture file!"
                         + System.lineSeparator() + stbi_failure_reason());
			}
			
			width = w.get();
			height = h.get();
			ss.close();
			w.clear();
			h.clear();
			comp.clear();
			
			
	} catch (IOException e1) {
		e1.printStackTrace();
	}
							
		CreateTexture();
	}
	
	public void CleanUp() {
		GL30.glDeleteTextures(rendererId);
	}
	

}

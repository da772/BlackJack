package renderer;


import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;


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
	
	public Texture(String fileName) {
		rendererId = GL30.glGenTextures();
		
		Renderer.AddTexture(this);
		LoadImage(fileName);
	}
	
	
	
	public void Bind() {
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, rendererId);
    }
	
	public void UnBind() {
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, 0);
    }
	
	public void setParameter(int name, int value) {
		GL30.glTexParameteri(GL30.GL_TEXTURE_2D, name, value);
	}
	
	private void uploadData(int internalFormat, int width, int height, int format, ByteBuffer data) {
        GL30.glTexImage2D(GL30.GL_TEXTURE_2D, 0, internalFormat, width, height, 0, format, GL30.GL_UNSIGNED_BYTE, data);
	}
    
	public void CreateTexture() {
		Bind();
		
	    setParameter(GL30.GL_TEXTURE_WRAP_S, GL30.GL_CLAMP_TO_EDGE);
	    setParameter(GL30.GL_TEXTURE_WRAP_T, GL30.GL_CLAMP_TO_EDGE);
	    setParameter(GL30.GL_TEXTURE_MIN_FILTER, GL30.GL_NEAREST);
	    setParameter(GL30.GL_TEXTURE_MAG_FILTER, GL30.GL_NEAREST);
	    GL30.glGenerateMipmap(GL30.GL_TEXTURE_2D);
	    uploadData(GL30.GL_RGBA, width, height, GL30.GL_RGBA, data);
	}
	
	public void LoadImage(String path) {
	try (MemoryStack stack = MemoryStack.stackPush()){
			
			IntBuffer w = stack.mallocInt(1);
			IntBuffer h = stack.mallocInt(1);
			IntBuffer comp = stack.mallocInt(1);
			
			stbi_set_flip_vertically_on_load(true);
			
			InputStream ss = this.getClass().getClassLoader().getResourceAsStream(path);
			byte[] bytes = IOUtils.toByteArray(ss);
			
			ByteBuffer buffer = BufferUtils.createByteBuffer(bytes.length);
			buffer.put(bytes);
			buffer.flip();
			
			data = stbi_load_from_memory(buffer, w, h, comp, 4);
			
			if (data == null) {
				 throw new RuntimeException("Failed to load a texture file!"
                         + System.lineSeparator() + stbi_failure_reason());
			}
			
			width = w.get();
			height = h.get();
			
			
			
	} catch (IOException e1) {
		e1.printStackTrace();
	}
							
		CreateTexture();
	}
	
	public void CleanUp() {
		GL30.glDeleteTextures(rendererId);
		if (data.hasRemaining())
			stbi_image_free(data);
	}
	

}

package renderer;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import static org.lwjgl.stb.STBImage.*;

import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryStack;

public class Texture {
	
	int width;
	int height;
	ByteBuffer data;
	private int rendererId;
	
	public Texture(String fileName) {
		rendererId = GL30.glGenTextures();
		Renderer.AddTexture(rendererId);
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
	
	private void uploadData(int width, int height, ByteBuffer data) {
       uploadData(GL30.GL_RGBA8, width, height, GL30.GL_RGBA, data);
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
			
			data = stbi_load(path, w, h, comp,4);
			
			if (data == null) {
				 throw new RuntimeException("Failed to load a texture file!"
                         + System.lineSeparator() + stbi_failure_reason());
			}
			
			width = w.get();
			height = h.get();
			
		}
		
		CreateTexture();
	}
	

}

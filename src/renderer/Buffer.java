package renderer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

public class Buffer {

	
	public static class VertexBuffer {
	
		private int rendererId;
		
		/**
		 * 
		 * @param vertices - array of vertices 
		 * @param size - size of array
		 */
		public VertexBuffer(float[] vertices, int size) {
			rendererId = GL15.glGenBuffers();
			SetBufferData(vertices, size);
		}
		
		/**
		 * 
		 * @param vertices - array of vertices
		 * @param size = size of array
		 */
		public void SetBufferData(float[] vertices, int size) {
			Bind();
			FloatBuffer ptr = BufferUtils.createFloatBuffer(size);
			ptr.put(vertices);
			ptr.flip();
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, ptr, GL15.GL_STATIC_DRAW);
			ptr.clear();
			Renderer.AddBuffer(rendererId);
		}
		
		public void Bind() {
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, rendererId);
		}
		
		public void UnBind() {
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		}
		
		public void CleanUp() {
			GL30.glDeleteBuffers(rendererId);
			Renderer.RemoveBuffer(rendererId);
		}
		
	}
	
	public static class IndexBuffer {
		
		private int rendererId;
		
		/**
		 * 
		 * @param indices - array of indices
		 * @param size - size of array
		 */
		public IndexBuffer(int[] indices, int size) {
			rendererId = GL15.glGenBuffers();
			SetIndexData(indices, size);
		}
		
		/**
		 * 
		 * @param indices - array of indices
		 * @param size - size of array
		 */
		public void SetIndexData(int[] indices, int size) {
			Bind();
			IntBuffer ptr = BufferUtils.createIntBuffer(size);
			ptr.put(indices);
			ptr.flip();
			GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, ptr, GL15.GL_STATIC_DRAW);
			ptr.clear();
			Renderer.AddBuffer(rendererId);
		}
		
		public void Bind() {
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, rendererId);
		}
		
		public void UnBind() {
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		}
		
		public void CleanUp() {
			GL30.glDeleteBuffers(rendererId);
			Renderer.RemoveBuffer(rendererId);
		}
		
	}
	
public static class FrameBuffer {
		
		private int rendererId;
		Texture texture;
		
		/**
		 * 
		 * 
		 */
		public FrameBuffer(String name, int width, int height) {
			rendererId = GL30.glGenFramebuffers();
			Bind();
			texture = Texture.Create(name, null, width, height);
			texture.Bind();
			GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0,
					GL30.GL_TEXTURE_2D, texture.GetID(), 0);
			texture.UnBind();
			UnBind();
		}
		
		/**
		 * 
		 * @param indices - array of indices
		 * @param size - size of array
		 */
		
		public void Bind() {
			GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, rendererId);
		}
		
		public void UnBind() {
			GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
		}
		
		public void Render(RenderBuffer buffer) {
			Bind();
			texture.Bind();
			buffer.Bind();
			GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT,
					GL30.GL_RENDERBUFFER, buffer.GetRendererId());
			
			if (GL30.glCheckFramebufferStatus(rendererId) != GL30.GL_FRAMEBUFFER_COMPLETE) {
				//System.out.println("OpenGL: Framebuffer not complete!");
			}
			
			buffer.UnBind();
			texture.UnBind();
			UnBind();
		}
		
		public void UpdateSize(int width, int height) {
			texture.LoadImageData(null, width, height);
		}
		
		public Texture GetTexture() {
			return texture;
		}
		
		public void CleanUp() {
			texture.CleanUp();
			GL30.glDeleteFramebuffers(rendererId);
		}
		
	}


public static class RenderBuffer {
	
	private int rendererId;
	
	/**
	 * 
	 * 
	 */
	public RenderBuffer(int width, int height) {
		rendererId = GL30.glGenRenderbuffers();
		Bind();
		GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, GL30.GL_DEPTH24_STENCIL8, width, height);
		UnBind();
	}
	
	/**
	 * 
	 * @param indices - array of indices
	 * @param size - size of array
	 */
	
	public void Bind() {
		GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, rendererId);
	}
	
	public void UnBind() {
		GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, 0);
	}
	
	public void CleanUp() {
		GL30.glDeleteRenderbuffers(rendererId);
	}
	
	public int GetRendererId() {
		return rendererId;
	}
	
}
	
	
	
}

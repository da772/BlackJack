package renderer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;

public class Buffer {

	
	public static class VertexBuffer {
	
		private int rendererId;
		
		public VertexBuffer(float[] vertices, int size) {
			rendererId = GL15.glGenBuffers();
			SetBufferData(vertices, size);
		}
		
		public void SetBufferData(float[] vertices, int size) {
			Bind();
			FloatBuffer ptr = BufferUtils.createFloatBuffer(size);
			ptr.put(vertices);
			ptr.flip();
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, ptr, GL15.GL_STATIC_DRAW);
			Renderer.AddBuffer(rendererId);
		}
		
		public void Bind() {
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, rendererId);
		}
		
		public void UnBind() {
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		}
		
	}
	
	public static class IndexBuffer {
		
		private int rendererId;
		
		public IndexBuffer(int[] indices, int size) {
			rendererId = GL15.glGenBuffers();
			SetIndexData(indices, size);
		}
		
		public void SetIndexData(int[] indices, int size) {
			Bind();
			IntBuffer ptr = BufferUtils.createIntBuffer(size);
			ptr.put(indices);
			ptr.flip();
			GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, ptr, GL15.GL_STATIC_DRAW);
			Renderer.AddBuffer(rendererId);
		}
		
		public void Bind() {
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, rendererId);
		}
		
		public void UnBind() {
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		}
	}
	
	
	
}

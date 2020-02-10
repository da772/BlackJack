package renderer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import renderer.Buffer.IndexBuffer;
import renderer.Buffer.VertexBuffer;


public class VertexArray {

	private int rendererId;
	
	public VertexArray() {
		rendererId = GL30.glGenVertexArrays();
		Renderer.AddVertexArray(rendererId);
	}
	
	public void Bind() {
		GL30.glBindVertexArray(rendererId);
	}
	
	public void UnBind() {
		GL30.glBindVertexArray(0);
	}
	
	public void CleanUp() {
		Renderer.RemoveVertexArray(rendererId);
		GL30.glDeleteVertexArrays(rendererId);
	}
	
	public VertexArray AddVertexBuffer(VertexBuffer vb, BufferLayout layout) {
		Bind();
		vb.Bind();
		for (int i = 0; i < layout.size(); i++) {
			GL30.glEnableVertexAttribArray(i);
			GL30.glVertexAttribPointer(i, layout.get(i).GetComponentCount(), layout.get(i).GetGLBaseType(), layout.get(i).normalized, layout.GetStride(), layout.get(i).offset);
		}
		vb.UnBind();
		UnBind();
		return this;
	}
	
	public VertexArray AddIndexBuffer(IndexBuffer ib) {
		Bind();
		ib.Bind();
		UnBind();
		ib.UnBind();
		return this;
	}
	
	
	public enum ElementType {
		None, Float, Float2, Float3, Float4, Int, Int2, Int3, Int4, Mat3, Mat4, Bool;
	};
	
	
	public static class BufferLayout {
		List<BufferElement> layout;
		private int stride;
		
		public BufferLayout(BufferElement[] e) {
			layout = new ArrayList<BufferElement>(Arrays.asList(e));
			CalculateOffsetsAndStride();
		}
		
		BufferLayout() {
			 layout = new ArrayList<BufferElement>();
		}
		
		public int GetStride() {
			return stride;
		}
		
		public void Add(BufferElement e) {
			layout.add(e);
			CalculateOffsetsAndStride();
		}
		
		public int size() {
			return layout.size();
		}
		
		public BufferElement get(int i) {
			if (i >= layout.size()) return null;
			return layout.get(i);
		}
		
		private void CalculateOffsetsAndStride() {
			int offset = 0;
			stride = 0;
			
			for (int i = 0; i < layout.size(); i++) {
				BufferElement e = layout.get(i);
				e.offset = offset;
				offset += e.size;
				stride += e.size;
			}
			
			
		}
		
		
	}
	
	public static class BufferElement {
		
		public String name;
		public int size;
		public int offset;
		public ElementType type;
		public boolean normalized;
		
		public BufferElement(ElementType type, String name, boolean normalized) {
			this.name = name;
			this.type = type;
			this.normalized = normalized;
			
			this.size = GetComponentSize();
			
		}
		
		public BufferElement(ElementType type, String name) {
			this.name = name;
			this.type = type;
			this.normalized = false;
			
			this.size = GetComponentSize();
			
		}
		
		private int GetComponentSize() {
			switch (type) {
			case Float:
				return 4;
			case Float2:
				return 8;
			case Float3:
				return 12;
			case Float4:
				return 16;
			case Mat3:
				return 36;
			case Mat4:
				return 64;
			case Int:
				return 4;
			case Int2:
				return 8;
			case Int3:
				return 12;
			case Int4:
				return 16;
			case Bool:
				return 1;
			default:
				return 0;
			}
		}
		
		public int GetComponentCount() {
			switch (type) {
			case Float:
				return 1;
			case Float2:
				return 2;
			case Float3:
				return 3;
			case Float4:
				return 4;
			case Mat3:
				return 9;
			case Mat4:
				return 16;
			case Int:
				return 1;
			case Int2:
				return 2;
			case Int3:
				return 3;
			case Int4:
				return 4;
			case Bool:
				return 1;
			default:
				return 0;
			}
		}
		
		public int GetGLBaseType() {
			switch (type) {
			case Float:
				return GL11.GL_FLOAT;
			case Float2:
				return GL11.GL_FLOAT;
			case Float3:
				return GL11.GL_FLOAT;
			case Float4:
				return GL11.GL_FLOAT;
			case Mat3:
				return GL11.GL_FLOAT;
			case Mat4:
				return GL11.GL_FLOAT;
			case Int:
				return GL11.GL_INT;
			case Int2:
				return GL11.GL_INT;
			case Int3:
				return GL11.GL_INT;
			case Int4:
				return GL11.GL_INT;
			case Bool:
				return GL11.GL_INT;
			default:
				return 0;
			}
		}
		
	}
	
	
}

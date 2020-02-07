package renderer;

import renderer.Buffer.IndexBuffer;
import renderer.Buffer.VertexBuffer;

public class Mesh {

	private float[] vertices;
	private int[] indices;
	private VertexArray.BufferLayout layout ;
	private VertexBuffer vb;
	private IndexBuffer ib;
	private VertexArray va;
	private Shader shader;
	private Texture texture;
	
	public Mesh(float[] vertices, VertexArray.BufferElement[] layout ,int[] indices, Shader shader){
		this.vertices = vertices;
		this.indices = indices;
		this.shader = shader;
		this.layout = new VertexArray.BufferLayout(layout);
		vb = new VertexBuffer(vertices, vertices.length);
		ib = new IndexBuffer(indices, indices.length);
		
		va = new VertexArray();
		va.AddVertexBuffer(vb, this.layout);
		va.AddIndexBuffer(ib);
			
	}
	
	public Mesh(float[] vertices, VertexArray.BufferElement[] layout ,int[] indices, Shader shader, Texture texture){
		this.vertices = vertices;
		this.indices = indices;
		this.shader = shader;
		this.texture = texture;
		this.layout = new VertexArray.BufferLayout(layout);
		vb = new VertexBuffer(vertices, vertices.length);
		ib = new IndexBuffer(indices, indices.length);
		
		va = new VertexArray();
		va.AddVertexBuffer(vb, this.layout);
		va.AddIndexBuffer(ib);
			
	}
	
	public Mesh(float[] vertices ,int[] indices, Shader shader){
		this.vertices = vertices;
		this.indices = indices;
		this.shader = shader;
		this.layout = new VertexArray.BufferLayout( new VertexArray.BufferElement[]
				{ new VertexArray.BufferElement(VertexArray.ElementType.Float3, "u_Position")
				});
		vb = new VertexBuffer(vertices, vertices.length);
		ib = new IndexBuffer(indices, indices.length);
		
		va = new VertexArray();
		va.AddVertexBuffer(vb, this.layout);
		va.AddIndexBuffer(ib);
			
	}
	
	public Mesh(float[] vertices ,int[] indices, Shader shader, Texture texture){
		this.vertices = vertices;
		this.indices = indices;
		this.shader = shader;
		this.texture = texture;
		this.layout = new VertexArray.BufferLayout( new VertexArray.BufferElement[]
				{ new VertexArray.BufferElement(VertexArray.ElementType.Float3, "u_Position")
				});
		vb = new VertexBuffer(vertices, vertices.length);
		ib = new IndexBuffer(indices, indices.length);
		
		va = new VertexArray();
		va.AddVertexBuffer(vb, this.layout);
		va.AddIndexBuffer(ib);
			
	}
	
	public Shader GetShader() {
		return shader;
	}
	
	public VertexArray GetVertexArray() {
		return va;
	}
	
	public VertexBuffer GetVertexBuffer() {
		return vb;
	}
	
	public Texture GetTexture() {
		return texture;
	}
	
	public int GetIndexCount() {
		return indices.length;
	}
	
	public int GetVertexCount() {
		return vertices.length;
	}
	
}

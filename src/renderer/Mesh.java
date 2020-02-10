package renderer;

import org.joml.Matrix4f;

import renderer.Buffer.IndexBuffer;
import renderer.Buffer.VertexBuffer;




public class Mesh {

	
	public static class Hud extends Mesh {
		
		public Hud(float[] vertices, VertexArray.BufferElement[] layout ,int[] indices, Shader shader, Texture texture, Matrix4f transform, Camera cam) {
			super(vertices, layout, indices, shader, texture, transform, cam);
		}
		
		@Override
		public void Bind() {
			GetShader().Bind();
			GetShader().UploadUniformMat4("u_transform", transform);
			if (GetTexture() != null)
				GetTexture().Bind();
			GetVertexArray().Bind();
		}
		
	}
	
	protected float[] vertices;
	protected int[] indices;
	protected VertexArray.BufferLayout layout ;
	protected VertexBuffer vb;
	protected IndexBuffer ib;
	protected VertexArray va;
	protected Shader shader;
	protected Texture texture;
	protected Camera cam;
	protected Matrix4f transform;
	
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
	
	public Mesh(float[] vertices, VertexArray.BufferElement[] layout ,int[] indices, Shader shader, Texture texture, Matrix4f transform, Camera cam){
		this.vertices = vertices;
		this.indices = indices;
		this.shader = shader;
		this.texture = texture;
		this.transform = transform;
		this.cam = cam;
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
	
	public void Bind() {
		GetShader().Bind();
		GetShader().UploadUniformMat4("u_transform", transform);
		GetShader().UploadUniformMat4("u_viewProjection", cam.GetViewProjectionMatrix());
		if (GetTexture() != null)
			GetTexture().Bind();
		GetVertexArray().Bind();
	}
	
	
	public void UnBind() {
		GetShader().UnBind();
		if (GetTexture() != null)
			GetTexture().UnBind();
		GetVertexArray().UnBind();
	}
	
	public void SetTransform(Matrix4f trans) {
		this.transform = trans;
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

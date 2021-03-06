package engine.renderer.mesh;

import org.joml.Vector2f;
import org.joml.Vector4f;

import engine.Camera;
import engine.ShaderLib;
import engine.renderer.Renderer;
import engine.renderer.Transform;
import engine.renderer.VertexArray;
import engine.renderer.Buffer.IndexBuffer;
import engine.renderer.Buffer.VertexBuffer;
import engine.renderer.VertexArray.BufferLayout;

public class MeshBackground extends Mesh {
	
	final static float[] vertices = {
			 -.5f,  -.5f,   0f,   0.f,0.f, 
			  .5f,  -.5f,   0f,  1.f, 0.f,
			 .5f,   .5f,    0f,  1.f, 1.f,
			 -.5f,  .5f,    0f,  0.f, 1.f,
	};
	
	final static int[] indices = {
			0,1,2,
			2,3,0,
	};
	
	private final static VertexBuffer vbuffer = new VertexBuffer(vertices, vertices.length);
	private final static IndexBuffer ibuffer = new IndexBuffer(indices, indices.length);
	private final static VertexArray.BufferLayout bufferLayout = new BufferLayout(new VertexArray.BufferElement[] {
		new VertexArray.BufferElement(VertexArray.ElementType.Float3, "u_Position"),
		new VertexArray.BufferElement(VertexArray.ElementType.Float2, "u_TexCoord") 
	});
	private final static VertexArray varray = new VertexArray().AddVertexBuffer(vbuffer, bufferLayout)
			.AddIndexBuffer(ibuffer);
	private Vector2f UVScale;
	private Vector4f color;
	
	/**
	 * 
	 * @param name - unique identifier
	 * @param transform
	 * @param shader
	 * @param texture
	 * @param UVScale
	 * @param camera - active camera
	 */
	public MeshBackground(String name, Transform transform, String[] shader, String texture, Vector2f UVScale, Camera camera) {
		super(name, transform, shader, texture, camera);
		this.UVScale = UVScale;
	}
	/**
	 * 
	 * @param name - unique identifier
	 * @param transform
	 * @param texture
	 * @param UVScale
	 * @param camera - active camera
	 */
	public MeshBackground(String name, Transform transform, String texture, Vector2f UVScale, Camera camera) {
		super(name, transform, ShaderLib.Shader_2DBackground, texture, camera);
		this.UVScale = UVScale;
	}
	
	/**
	 * 
	 * @param name - unique identifier
	 * @param transform
	 * @param texture
	 * @param color
	 * @param UVScale
	 * @param camera - active camera
	 */
	public MeshBackground(String name,Transform transform, String texture, Vector4f color, Vector2f UVScale, Camera camera) {
		super(name, transform, ShaderLib.Shader_2DBackground, texture, camera);
		this.color = color;
		this.UVScale = UVScale;
	}
	

	@Override
	public void OnBind() {
		shader.UploadUniformFloat2("u_UVScale", UVScale);
		shader.UploadUniformFloat4("u_Color", color);
		varray.Bind();
	}

	@Override
	public void Draw() {
		Renderer.DrawIndexed(indices.length);
	}

	
	@Override
	protected void OnUnBind() {
		varray.UnBind();
	}



	@Override
	protected void OnInit() {
		
		
	}



	@Override
	protected void OnCleanUp() {
		
		
	}



	@Override
	public VertexArray GetVertexArray() {
		return varray;
	}



	@Override
	public VertexBuffer GetVertexBuffer() {
		return vbuffer;
	}



	@Override
	public int GetIndexCount() {
		return indices.length;
	}



	@Override
	public int GetVertexCount() {
		return vertices.length;
	}



	@Override
	public IndexBuffer GetIndexBuffer() {
		return ibuffer;
	}



}

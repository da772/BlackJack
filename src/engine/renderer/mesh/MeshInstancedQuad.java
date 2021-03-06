package engine.renderer.mesh;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import engine.Camera;
import engine.ShaderLib;
import engine.renderer.Renderer;
import engine.renderer.TextureCoords;
import engine.renderer.Transform;
import engine.renderer.VertexArray;
import engine.renderer.Buffer.IndexBuffer;
import engine.renderer.Buffer.VertexBuffer;
import engine.renderer.VertexArray.BufferLayout;

public class MeshInstancedQuad extends Mesh {
	
	/**
	 * Cube vertices 
	 */
	protected float[] vertices = {
			 -.5f,  -.5f,   .0f,  0.f, 0.f, //0
			  .5f,  -.5f,   .0f,  1.f, 0.f, //1
			  .5f,   .5f,   .0f,  1.f, 1.f, //2
			 -.5f,   .5f,   .0f,  0.f, 1.f, //3
			 
			 -.5f,  -.5f,  	 0f,   0.f, 1.f, //4
			  .5f,  -.5f,    0f,   1.f, 1.f, //5
			  .5f,   .5f,    0f,   1.f, 0.f, //6
			 -.5f,   .5f,    0f,   0.f, 0.f, //7
			 
	};
	
	/*
	 * Cube indices
	 */
	protected int[] indices = {
			// front
			0, 1, 2,
			2, 3, 0,
			// right
			1, 5, 6,
			6, 2, 1,
			// back
			7, 6, 5,
			5, 4, 7,
			// left
			4, 0, 3,
			3, 7, 4,
			// bottom
			4, 5, 1,
			1, 0, 4,
			// top
			3, 2, 6,
			6, 7, 3
	};
	
	private VertexBuffer vbuffer;
	private IndexBuffer ibuffer;
	private VertexArray.BufferLayout bufferLayout = new BufferLayout(new VertexArray.BufferElement[] {
		new VertexArray.BufferElement(VertexArray.ElementType.Float3, "u_Position"),
		new VertexArray.BufferElement(VertexArray.ElementType.Float2, "u_TexCoord") 
	});
	private VertexArray varray;
	private Vector4f xCoordsFront;
	private Vector4f yCoordsFront;
	private Vector4f xCoordsBack;
	private Vector4f yCoordsBack;
	private Vector2f UVScale;
	private Vector4f color = new Vector4f(1f);
	private int amount;
	private Vector3f offset;
	private float[] offsets;

	
	/**
	 * 
	 * @param name - unique identifier
	 * @param transform
	 * @param shader
	 * @param texture
	 * @param color
	 * @param TexCoords
	 * @param UVScale
	 * @param camera - active camera
	 */
	public MeshInstancedQuad(String name, Transform transform, String[] shader, String texture,
			Vector4f color,TextureCoords TexCoords, Vector2f UVScale, int amount, Vector3f offset, Camera camera) {
		super(name, transform, shader, texture, camera);
		this.xCoordsFront = TexCoords.GetXCoords1();
		this.yCoordsFront = TexCoords.GetYCoords1();
		this.xCoordsBack = TexCoords.GetXCoords2();
		this.yCoordsBack = TexCoords.GetYCoords2();
		this.offset = offset;
		this.UVScale = UVScale;
		this.amount = amount;
		this.color = color;
	}
	
	/**
	 * 
	 * @param name  - unique identifier
	 * @param transform
	 * @param texture
	 * @param color
	 * @param TexCoords
	 * @param UVScale
	 * @param camera - active camera
	 */
	public MeshInstancedQuad(String name, Transform transform, String texture, Vector4f color, TextureCoords TexCoords, Vector2f UVScale, int amount, Vector3f offset, Camera camera) {
		super(name, transform, ShaderLib.Shader_2DQuad, texture, camera);
		this.xCoordsFront = TexCoords.GetXCoords1();
		this.yCoordsFront = TexCoords.GetYCoords1();
		this.xCoordsBack = TexCoords.GetXCoords2();
		this.yCoordsBack = TexCoords.GetYCoords2();
		this.offset = offset;
		this.amount = amount;
		this.color = color;
		this.UVScale = UVScale;
	}
	
	/**
	 * 
	 * @param name - unique identifier
	 * @param transform
	 * @param texture
	 * @param color
	 * @param camera - active camera
	 */
	public MeshInstancedQuad(String name, Transform transform, String texture, Vector4f color, int amount, Vector3f offset, Camera camera) {
		super(name, transform, ShaderLib.Shader_2DQuad, texture, camera);
		this.xCoordsFront = new Vector4f(0,0,1f,1f);
		this.yCoordsFront = new Vector4f(0,0f,1f,1f);
		this.xCoordsBack = new Vector4f(0,0,1f,1f);
		this.yCoordsBack = new Vector4f(0,0f,1f,1f);
		this.amount = amount;
		this.color = color;
		this.offset = offset;
		UVScale = new Vector2f(1f);
	}
	
	/**
	 * 
	 * @param name - unique identifier
	 * @param transform
	 * @param shader
	 * @param texture
	 * @param color
	 * @param camera - active camera
	 */
	public MeshInstancedQuad(String name, Transform transform, String[] shader, 
			String texture, Vector4f color, int amount,Vector3f offset, Camera camera) {
		super(name, transform, shader, texture, camera);
		this.xCoordsFront = new Vector4f(0,0,1f,1f);
		this.yCoordsFront = new Vector4f(0,0f,1f,1f);
		this.xCoordsBack = new Vector4f(0,0,1f,1f);
		this.offset = offset;
		this.yCoordsBack = new Vector4f(0,0f,1f,1f);
		this.amount = amount;
		this.color = color;
		UVScale = new Vector2f(1f);
	}
	
	
	@Override
	public void OnBind() {
		shader.UploadUniformFloat2("u_UVScale", UVScale);
		shader.UploadUniformFloat4("u_Color", color);
		shader.UploadUniformFloat3Array("u_Offsets", offsets);
		varray.Bind();
	}

	@Override
	public void Draw() {
		Renderer.EnableCulling();
		Renderer.SetDepth(true);
		Renderer.DrawIndexedInstanced(indices.length, amount);
	}

	
	@Override
	protected void OnUnBind() {
		varray.UnBind();
	}


	@Override
	protected void OnInit() {
		SetTextureCoords();
		ibuffer = new IndexBuffer(indices, indices.length);
		vbuffer = new VertexBuffer(vertices, vertices.length);
		varray = new VertexArray().AddVertexBuffer(vbuffer, bufferLayout).AddIndexBuffer(ibuffer);
		UpdateOffset();
	}
	
	private void UpdateOffset() {
		offsets = new float[amount*3];
		for (int i = 0; i < amount*3; i+=3) {
			offsets[i] = (i/3) *offset.x;
			offsets[i+1] = (i/3)*-offset.y;
			offsets[i+2] = (i/3)*offset.z;
		}
	}
	
	public void SetAmount(int amount) {
		this.amount = amount;
		UpdateOffset();
	}
	
	public void SetOffset(Vector3f offset) {
		this.offset = offset;
		UpdateOffset();
	}

	public void SetTextureCoords(TextureCoords TexCoords) {
		this.xCoordsFront = TexCoords.GetXCoords1();
		this.yCoordsFront = TexCoords.GetYCoords1();
		this.xCoordsBack = TexCoords.GetXCoords2();
		this.yCoordsBack = TexCoords.GetYCoords2();
		
		SetTextureCoords();
	}
	
	
	private void SetTextureCoords() {
		vertices[3] = this.xCoordsFront.x;
		vertices[4] = -this.yCoordsFront.z;
		
		vertices[8] =  this.xCoordsFront.z;
		vertices[9] = -this.yCoordsFront.w;
		
		vertices[13] = this.xCoordsFront.w;
		vertices[14] = -this.yCoordsFront.x;
		
		vertices[18] = this.xCoordsFront.y;
		vertices[19] = -this.yCoordsFront.y;
		
		vertices[23] =  this.xCoordsBack.w;
		vertices[24] = -this.yCoordsBack.z;
		
		vertices[28] =  this.xCoordsBack.x;
		vertices[29] = -this.yCoordsBack.w;
		
		vertices[33] =  this.xCoordsBack.y;
		vertices[34] = -this.yCoordsBack.x;
		
		vertices[38] =  this.xCoordsBack.z;
		vertices[39] = -this.yCoordsBack.y;
	}
	
	@Override
	protected void OnCleanUp() {
		ibuffer.CleanUp();
		vbuffer.CleanUp();
		varray.CleanUp();
	}

	@Override
	public VertexArray GetVertexArray() {
		return varray;
	}



	@Override
	public VertexBuffer GetVertexBuffer() {
		return vbuffer;
	}

	public void SetColor(Vector4f color) {
		this.color = color;
	}
	
	public void SetColor(float r, float g, float b, float a) {
		this.color = new Vector4f(r,g,b,a);
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

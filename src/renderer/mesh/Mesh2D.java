package renderer.mesh;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import engine.Collider2D;
import engine.Events.Event;
import renderer.Camera;
import renderer.Renderer;
import renderer.Renderer2D;
import renderer.Shader;
import renderer.Texture;
import renderer.Transform;
import renderer.VertexArray;
import util.MathLib;
import renderer.Buffer.IndexBuffer;
import renderer.Buffer.VertexBuffer;


public class Mesh2D extends Collider2D {

	protected float[] vertices;
	protected int[] indices;
	protected VertexArray.BufferLayout layout ;
	protected VertexBuffer vb;
	protected IndexBuffer ib;
	protected VertexArray va;
	protected Shader shader;
	protected Texture texture;
	protected Camera cam;
	protected Transform transform;
	protected String[] shaderString;
	protected String textureString;

	
	public Mesh2D(float[] vertices, VertexArray.BufferElement[] layout ,int[] indices, String[] shader){
		this.vertices = vertices;
		this.indices = indices;
		this.shaderString = shader;
		this.layout = new VertexArray.BufferLayout(layout);

	}
	
	public Mesh2D(float[] vertices, VertexArray.BufferElement[] layout ,int[] indices, String[] shader, String texture,
			Transform transform, Camera cam){
		this.vertices = vertices;
		this.indices = indices;
		this.shaderString = shader;
		this.textureString = texture;
		this.transform = transform;
		this.cam = cam;
		this.layout = new VertexArray.BufferLayout(layout);
		
	}
	
	public Mesh2D(float[] vertices ,int[] indices, String[] shader){
		this.vertices = vertices;
		this.indices = indices;
		this.shaderString = shader;
		this.layout = new VertexArray.BufferLayout( new VertexArray.BufferElement[]
				{ new VertexArray.BufferElement(VertexArray.ElementType.Float3, "u_Position")
				});
	}
	
	public Mesh2D(float[] vertices ,int[] indices, String[] shader, String texture){
		this.vertices = vertices;
		this.indices = indices;
		this.shaderString = shader;
		this.textureString = texture;
		this.layout = new VertexArray.BufferLayout( new VertexArray.BufferElement[]
				{ new VertexArray.BufferElement(VertexArray.ElementType.Float3, "u_Position")
				});
			
	}
	
	public void Bind() {
		GetShader().Bind();
		GetShader().UploadUniformMat4("u_transform", GetTransformMatrix());
		GetShader().UploadUniformMat4("u_viewProjection", cam.GetViewProjectionMatrix());
		if (GetTexture() != null)
			GetTexture().Bind();
		GetVertexArray().Bind();
	}
	
	public void Draw() {
		Renderer.DrawIndexed(this);
	}
	
	public void UnBind() {
		GetShader().UnBind();
		if (GetTexture() != null)
			GetTexture().UnBind();
		GetVertexArray().UnBind();
	}
	
	public void Init() {
		vb = new VertexBuffer(vertices, vertices.length);
		ib = new IndexBuffer(indices, indices.length);
		
		va = new VertexArray();
		va.AddVertexBuffer(vb, this.layout);
		va.AddIndexBuffer(ib);
		shader = Shader.Create(shaderString);
		texture = Texture.Create(textureString);
		
		
	}
	
	public void Add() {
		Renderer2D.Add(this);
	}
	
	public void Remove() {
		Renderer2D.Remove(this);
	}
	
	
	public void CleanUp() {
		vb.CleanUp();
		ib.CleanUp();
		va.CleanUp();
		Shader.Remove(shader);
		Texture.Remove(texture);
	}
	
	public void SetPosition(Vector3f position) {
		SetTransform(new Transform(position, transform.GetRotation(), transform.GetScale()));
	}
	
	
	public void SetPosition(float x, float y, float z) {
		Vector3f p = new Vector3f(x,y,z);
		SetPosition(p);
	}
	
	public void SetScale(Vector3f scale) {
		SetTransform(new Transform(transform.GetPosition(), transform.GetRotation(), scale));
	}
	
	
	public void SetTransform(Transform transform) {
		this.transform = transform;
	}
	
	protected Matrix4f GetTransformMatrix() {
		return MathLib.createTransformMatrix(transform);
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
	
	public Vector3f GetScale() {
		return this.transform.GetScale();
	}
	
	public Vector3f GetPosition() {
		return transform.GetPosition();
	}
	
	@Override
	public CollisionObjectType GetCollisionObjectType() {
		return CollisionObjectType.Mesh;
	}

	@Override
	public Vector4f GetRect() {
		return new Vector4f(-1f);
	}

	@Override
	public float GetZOrder() {
		// TODO Auto-generated method stub
		return GetPosition().z;
	}

	@Override
	public void SelectedOnEvent(Event e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean IsMouseOver() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void SetMouseEnter() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void SetMouseExit() {
		// TODO Auto-generated method stub
		
	}
	
	protected void OnDeselect() {
		// TODO Auto-generated method stub
		
	}

	public void DeselectGUI() {
		OnDeselect();
		
	}

	@Override
	public void RemoveSelection() {
		// TODO Auto-generated method stub
		
	}
	
}

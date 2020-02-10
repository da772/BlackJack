package renderer;


import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import renderer.Buffer.IndexBuffer;
import renderer.Buffer.VertexBuffer;
import util.MathLib;

public class GUI {

	
	protected static final float[] vertices = {
			-1f,  -1f,   0,  0f, 0f,
			 1f,  -1f,   0f, 1f, 0f,
			 1f,   1f,   0f, 1f, 1f,
			-1f,   1f,   0f, 0f, 1f
	};
	
	protected static final int[] indices = {
			0,1,2,
			2,3,0
	};
	
	
	protected static IndexBuffer ibuffer = new IndexBuffer(indices, indices.length);
	protected static VertexBuffer vbuffer = new VertexBuffer(vertices, vertices.length);
	protected static VertexArray varray = new VertexArray().AddVertexBuffer(vbuffer,  new VertexArray.BufferLayout(new VertexArray.BufferElement[]{
			new VertexArray.BufferElement(VertexArray.ElementType.Float3, "u_Position"),
			new VertexArray.BufferElement(VertexArray.ElementType.Float2, "u_TexCoord") 
			})).AddIndexBuffer(ibuffer);;


	
	protected Transform transform;
	protected Transform _transform;
	protected Texture texture;
	protected Vector4f color;
	protected Vector2f UVScale;

	protected Vector3f position;
	
	public float zOrder = 0f;
	
	protected Shader shader;
	
	public GUI() {};
	
	public GUI(Transform transform, Texture texture, Vector4f color, Vector2f UVScale) {
		this.texture = texture;
		this.transform = transform;
		this.color = color;
		this.UVScale = UVScale;
		GUIRenderer.Add(this);
	}
	
	public GUI(Transform transform, Texture texture, Vector4f color, Vector2f UVScale, Shader shader) {
		this.texture = texture;
		this.transform = transform;
		this.color = color;
		this.UVScale = UVScale;
		this.shader = shader;
		GUIRenderer.Add(this);
	}
	
	public GUI(Transform transform, String texture, Vector4f color, Vector2f UVScale) {
		this.texture = Texture.Create(texture);
		this.transform = transform;
		this.color = color;
		this.UVScale = UVScale;
		GUIRenderer.Add(this);
	}
	
	public GUI(Transform transform, Vector4f color) {
		this.texture = null;
		this.transform = transform;
		this.color = color;
		this.UVScale = new Vector2f(1.f,1.f);
		GUIRenderer.Add(this);
	}
	
	public GUI(Transform transform, String texture, Vector4f color) {
		this.texture = Texture.Create(texture);
		this.transform = transform;
		this.color = color;
		this.UVScale = new Vector2f(1.f,1.f);
		GUIRenderer.Add(this);
	}
	
	public GUI(Transform transform, String texture) {
		this.texture = Texture.Create(texture);
		this.transform = transform;
		this.color = new Vector4f(1.f,1.f,1.f,1.f);
		this.UVScale = new Vector2f(1.f,1.f);
		GUIRenderer.Add(this);
	}
	
	
	public void Init() {
		SetTransform(transform);
		shader = Shader.Create(ShaderLib.Shader_HUD);
	}
	
	public void Add() {
		GUIRenderer.Add(this);
	}
	
	public void Remove() {
		GUIRenderer.Remove(this);
	}
	
	public void Bind() {
		shader.Bind();
		texture.Bind();
		shader.UploadUniformFloat4("u_Color", color);
		shader.UploadUniformFloat2("u_UVScale", UVScale);
		shader.UploadUniformMat4("u_Transform", _transform.GetTransformMatrix() );
		varray.Bind();
	}
	
	public void UnBind() {
		shader.UnBind();
		texture.UnBind();
		varray.UnBind();
	}
	
	public int IndicesCount() {
		return indices.length;
	}
	
	public Transform GetTransform() {
		return transform;
	}
	
	public Vector4f GetColor() {
		return color;
	}
	
	public Vector2f GetUVScale() {
		return UVScale;
	}
	
	public void SetPosition(Vector3f position) {
		SetTransform(new Transform(position, transform.GetRotation(), transform.GetScale()));
	}
	
	public void SetScale(Vector3f scale) {
		SetTransform(new Transform(transform.GetPosition(), transform.GetRotation(), scale));
	}
	
	public void SetTransform(Transform transform) {
		this.transform = transform;
		this.zOrder = transform.GetPosition().z;
		this.transform.SetPosition(transform.GetPosition().x, transform.GetPosition().y, 0f);
		this._transform = new Transform(transform.GetPosition(), transform.GetRotation(), transform.GetScale());
		this._transform.SetPosition(new Vector3f(MathLib.GetMappedRangeValueUnclamped(-1, 1, -2, 2, MathLib.Clamp(_transform.GetPosition().x,-1,1)), 
				-MathLib.GetMappedRangeValueUnclamped(-1, 1, -2, 2, -MathLib.Clamp(_transform.GetPosition().y,-1,1)),_transform.GetPosition().z ));
		this._transform = Transform.ScaleBasedPosition(_transform);
		
	}
	
	public void CleanUp () {
		varray.CleanUp();
		ibuffer.CleanUp();
		vbuffer.CleanUp();
		Texture.Remove(texture);
		Shader.Remove(shader);
	}
	
	
	
	
}

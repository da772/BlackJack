package renderer;


import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import util.MathLib;

public abstract class GUI {

	protected Transform transform;
	protected Transform _transform;
	protected Texture texture;
	protected Vector4f color;
	protected Vector2f UVScale;

	protected int renderType = 1;
	
	protected String[] shader_strings = ShaderLib.Shader_HUD;
	
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
	
	public GUI(Transform transform, Texture texture, Vector4f color, Vector2f UVScale, String[] shaders) {
		this.texture = texture;
		this.transform = transform;
		this.color = color;
		this.UVScale = UVScale;
		this.shader_strings = shaders;
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
		UpdateTransform();
		shader = Shader.Create(shader_strings);
		_Init();
	}
	
	protected void _Init() {
		
	}
	
	public void Add() {
		GUIRenderer.Add(this);
	}
	
	public void Remove() {
		GUIRenderer.Remove(this);
	}
	
	public abstract void Bind();
	
	public abstract void UnBind();
	
	public abstract int IndicesCount();
	
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
	
	public void SetColor(Vector4f color) {
		this.color = color;
	}
	
	public void SetColor(float r, float g, float b, float a) {
		this.color = new Vector4f(r,g,b,a);
	}
	
	public void SetTransform(Transform transform) {
		this.transform = transform;
		UpdateTransform();
	}
	
	protected void UpdateTransform() {
		this.zOrder = transform.GetPosition().z;
		this.transform.SetPosition(transform.GetPosition().x, transform.GetPosition().y, 0f);
		this._transform = new Transform(transform.GetPosition(), transform.GetRotation(), transform.GetScale());
		this._transform.SetPosition(new Vector3f(MathLib.GetMappedRangeValueUnclamped(-1, 1, -2, 2, MathLib.Clamp(_transform.GetPosition().x,-1,1)), 
				-MathLib.GetMappedRangeValueUnclamped(-1, 1, -2, 2, -MathLib.Clamp(_transform.GetPosition().y,-1,1)),_transform.GetPosition().z ));
		this._transform = Transform.ScaleBasedPosition(_transform);
	}
	
	public abstract int GetRenderType();
	
	public abstract void CleanUp ();
	
	public abstract int VertexCount();
	
	
	
	
}

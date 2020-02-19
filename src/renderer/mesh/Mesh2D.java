package renderer.mesh;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import engine.Application;
import engine.Camera;
import engine.Collider2D;
import engine.Events.Event;
import renderer.Renderer2D;
import renderer.Shader;
import renderer.Texture;
import renderer.Transform;
import renderer.VertexArray;
import util.MathLib;
import renderer.Buffer.IndexBuffer;
import renderer.Buffer.VertexBuffer;


public abstract class  Mesh2D extends Collider2D {

	protected Texture texture;
	protected Shader shader;
	protected Camera cam;
	protected Transform transform;
	protected String[] shaderString;
	protected String textureString;
	protected float zOrder = 0;
	protected Transform _transform;
	protected boolean generateMipMap = true;

	
	/**
	 * 
	 * @param name - unique identifier
	 * @param transform
	 * @param shader
	 * @param texture
	 * @param cam - current camera
	 */
	public Mesh2D(String name, Transform transform, String[] shader, String texture, Camera cam) {
		super(name);
		SetTransform(transform);
		this.cam = cam;
		this.shaderString = shader;
		this.textureString = texture;
	}
	
	
	public void Bind() {
		GetShader().Bind();
		GetShader().UploadUniformMat4("u_transform", GetTransformMatrix());
		GetShader().UploadUniformMat4("u_viewProjection", cam.GetViewProjectionMatrix());
		if (GetTexture() != null)
			GetTexture().Bind();
		OnBind();
	}
	
	public abstract void OnBind();
	
	public abstract void Draw();
	
	public void UnBind() {
		GetShader().UnBind();
		if (GetTexture() != null)
			GetTexture().UnBind();
		OnUnBind();
	}
	
	protected abstract void OnUnBind();
	
	public void Init() {
		shader = Shader.Create(shaderString);
		texture = Texture.Create(textureString, false, generateMipMap);
		OnInit();
		
	}
	
	protected abstract void OnInit();
	
	public void Add() {
		Renderer2D.Add(this);
	}
	
	public void Remove() {
		Renderer2D.Remove(this);
	}
	
	
	public void CleanUp() {
		if (Application.ThreadSafe()) {
			Shader.Remove(shader);
			Texture.Remove(texture);
			OnCleanUp();
		}
	}
	
	protected abstract void OnCleanUp();
	
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
	
	public void SetRotation(float x, float y, float z) {
		SetRotation(new Vector3f(x,y,z));
	}

	public void SetRotation(Vector3f rot) {
		SetTransform(new Transform(transform.GetPosition(), rot, transform.GetScale()));
	}
	
	public Vector3f GetRotation() {
		return transform.GetRotation();
	}
	
	
	public void SetTransform(Transform transform) {
		zOrder = transform.GetPosition().z;
		this.transform = transform;
		this._transform = new Transform(
				new Vector3f(transform.GetPosition().x, 
						this.transform.GetPosition().y,
						transform.GetPosition().z),
				transform.GetRotation(),
				new Vector3f(transform.GetScale().x, 
						this.transform.GetScale().y,
						1f)
				);
	}
	
	protected Matrix4f GetTransformMatrix() {
		return MathLib.createTransformMatrix(_transform);
	}
	
	
	public Shader GetShader() {
		return shader;
	}
	
	public abstract VertexArray GetVertexArray();
	
	public abstract IndexBuffer GetIndexBuffer();
	
	public abstract VertexBuffer GetVertexBuffer();
	
	public Texture GetTexture() {
		return texture;
	}
	
	public abstract int GetIndexCount();
	
	public abstract int GetVertexCount();
	
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
		return zOrder;
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
	
	@Override
	public void OnBegin() {
		Add();
	}

	@Override
	public void OnEnd() {
		Remove();
	}
	
	
}

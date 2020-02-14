package renderer.GUI;


import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import engine.Collider2D;
import engine.Collision2D;
import engine.Events.Event;
import renderer.GUIRenderer;
import renderer.Shader;
import renderer.ShaderLib;
import renderer.Texture;
import renderer.Transform;
import util.MathLib;

public abstract class GUI extends Collider2D {

	protected Transform transform;
	protected Transform _transform;
	protected Texture texture;
	protected Vector4f color;
	protected Vector2f UVScale;

	protected int renderType = 1;
	
	protected String[] shader_strings = ShaderLib.Shader_GUIQuad;
	protected String texturePath;
	
	protected Vector3f position;
	
	
	private Vector2f dragPos = new Vector2f(0f);
	
	protected Shader shader;
	protected boolean added = false;
	
	protected boolean MouseOver = false;
	protected boolean EnableCollision = true;
	
	public GUI() {};
	
	public GUI(Transform transform, Texture texture, Vector4f color, Vector2f UVScale) {
		this.texture = texture;
		this.texturePath = texture.GetFileName();
		this.transform = transform;
		this.color = color;
		this.UVScale = UVScale;
	}
	
	public GUI(Transform transform, Texture texture, Vector4f color, Vector2f UVScale, String[] shaders) {
		this.texture = texture;
		this.texturePath = texture.GetFileName();
		this.transform = transform;
		this.color = color;
		this.UVScale = UVScale;
		this.shader_strings = shaders;
	}
	
	public GUI(Transform transform, String texture, Vector4f color, Vector2f UVScale) {
		this.texture = Texture.Create(texture);
		this.texturePath = texture;
		this.transform = transform;
		this.color = color;
		this.UVScale = UVScale;
	}
	
	public GUI(Transform transform, Vector4f color) {
		this.texture = null;
		this.transform = transform;
		this.color = color;
		this.UVScale = new Vector2f(1.f,1.f);
	}
	
	public GUI(Transform transform, String texture, Vector4f color) {
		this.texture = Texture.Create(texture);
		this.texturePath = texture;
		this.transform = transform;
		this.color = color;
		this.UVScale = new Vector2f(1.f,1.f);
	}
	
	public GUI(Transform transform, String texture) {
		this.texture = Texture.Create(texture);
		this.texturePath = texture;
		this.transform = transform;
		this.color = new Vector4f(1.f,1.f,1.f,1.f);
		this.UVScale = new Vector2f(1.f,1.f);
	}
	
	
	public void Init() {
		UpdateTransform();
		_Init();
	}
	
	protected void _Init() {
		
	}
	
	public void Add() {
		GUIRenderer.Add(this);
		added = true;
	}
	
	public void Remove() {
		GUIRenderer.Remove(this);
		added = false;
	}
	
	public abstract void Draw();
	
	public abstract void Bind();
	
	public abstract void UnBind();
	
	public  int IndicesCount() {
		return 0;
	};
	
	public Transform GetTransform() {
		return transform;
	}
	
	public Vector4f GetColor() {
		return color;
	}
	
	public Vector2f GetUVScale() {
		return UVScale;
	}
	
	public Vector3f GetPosition() {
		return transform.GetPosition();
	}
	
	public Vector3f GetRealPosition() {
		UpdateTransform();
		return _transform.GetPosition();
	}
	
	public Vector3f GetScale() {
		return this.transform.GetScale();
	}
	
	public Vector4f GetRect() {
		return new Vector4f(
			(GUIRenderer.GetWidth()*this.transform.GetScale().x/2)+
			(GUIRenderer.GetWidth()* (this.transform.GetPosition().x/2f+.5f))-
			(GUIRenderer.GetWidth()*this.transform.GetScale().x),
			
			(GUIRenderer.GetWidth()*this.transform.GetScale().x/2)+
			(GUIRenderer.GetWidth()* (this.transform.GetPosition().x/2f+.5f)),
			
			-((GUIRenderer.GetHeight()*this.transform.GetScale().y/2)+
			(GUIRenderer.GetHeight()* (this.transform.GetPosition().y/2f-.5f))),
			
			-((GUIRenderer.GetHeight()*this.transform.GetScale().y/2)+
			(GUIRenderer.GetHeight()* (this.transform.GetPosition().y/2f-.5f))-
			(GUIRenderer.GetHeight()*this.transform.GetScale().y))
		
			);
	}
	
	
	public void BeginDrag(float x, float y) {
		dragPos.x = x;
		dragPos.y = y;
	}
	
	
	public void Drag(float x, float y) {
		SetPosition( this.transform.GetPosition().x + ( ((x-dragPos.x)/GUIRenderer.GetWidth())*2f ),
				this.transform.GetPosition().y - ( ((y-dragPos.y)/GUIRenderer.GetHeight()*2f)),
				this.transform.GetPosition().z
				);
		dragPos.x = x;
		dragPos.y = y;
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
		this._transform.SetPosition(new Vector3f(MathLib.GetMappedRangeValueUnclamped(-1, 1, -2, 2,_transform.GetPosition().x)/2f, 
				-MathLib.GetMappedRangeValueUnclamped(-1, 1, -2, 2, -_transform.GetPosition().y)/2f,_transform.GetPosition().z ));
		//this._transform = Transform.ScaleBasedPosition(_transform);
	}
	
	public void SetMouseEnter() {
		MouseOver = true;
		OnMouseEnter();
	}
	
	protected void OnMouseEnter() {
		
	}
	
	protected void OnMouseExit() {
		
	}
	
	public void SetMouseExit() {
		MouseOver = false;
		OnMouseExit();
	}
	
	public boolean IsMouseOver() {
		return MouseOver;
	}
	
	public boolean HasCollision() {
		return this.EnableCollision;
	}
	
	public abstract void CleanUp ();
	
	public int VertexCount() {
		return 0;
	};
	
	public boolean IsSelected() {
		return this.equals(Collision2D.GetSelected());
	}
	
	public void SelectGUI() {
		Collision2D.SetSelected(this);
		OnSelect();
	}
	
	public void DeselectGUI() {
		if (IsSelected()) {
			Collision2D.SetSelected(null);
			OnDeselect();
		}
	}
	
	public void RemoveSelection() {
		OnDeselect();
	}
	
	public void OnDeselect() {
	}

	public abstract void SelectedOnEvent(Event e);

	protected void OnSelect() {
		
		
	}
	
	@Override
	public CollisionObjectType GetCollisionObjectType() {
		return CollisionObjectType.GUI;
	}

	@Override
	public float GetZOrder() {
		return zOrder;
	}

	
	
	
	
}

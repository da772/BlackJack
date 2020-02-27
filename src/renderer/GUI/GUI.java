package renderer.GUI;


import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import engine.Application;
import engine.Collider2D;
import engine.Collision2D;
import engine.ShaderLib;
import engine.Events.Event;
import renderer.GUIRenderer;
import renderer.Shader;
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
	protected List<GUI> children = new ArrayList<GUI>();
	protected GUI parent = null;
	
	
	protected Vector2f dragPos = new Vector2f(0f);
	protected Vector3f RelativePosition = new Vector3f(0);
	
	protected Shader shader;
	protected boolean added = false;
	
	protected boolean MouseOver = false;
	protected boolean EnableCollision = true;
	
	/**
	 * 
	 * @param name - unique identifier
	 */
	public GUI(String name) {
		super(name);
	}
	
	/**
	 * 
	 * @param name - unique identifier
	 * @param transform - gui location
	 * @param texture - gui texture
	 * @param color - gui color
	 * @param UVScale - gui uv scale
	 */
	public GUI(String name, Transform transform, Texture texture, Vector4f color, Vector2f UVScale) {
		super(name);
		this.texture = texture;
		this.texturePath = texture.GetFileName();
		this.transform = transform;
		this.color = color;
		this.UVScale = UVScale;
	}
	
	/**
	 * 
	 * @param name - unique identifier
	 * @param transform
	 * @param texture
	 * @param color
	 * @param UVScale
	 * @param shaders
	 */
	public GUI(String name,Transform transform, String texture, Vector4f color, Vector2f UVScale, String[] shaders) {
		super(name);
		this.texture = Texture.Create(texture);
		this.texturePath = texture;
		this.transform = transform;
		this.color = color;
		this.UVScale = UVScale;
		this.shader_strings = shaders;
	}
	
	/**
	 * 
	 * @param name - unique identifier
	 * @param transform
	 * @param texture
	 * @param color
	 * @param UVScale
	 */
	public GUI(String name,Transform transform, String texture, Vector4f color, Vector2f UVScale) {
		super(name);
		this.texture = Texture.Create(texture);
		this.texturePath = texture;
		this.transform = transform;
		this.color = color;
		this.UVScale = UVScale;
	}
	
	/**
	 * 
	 * @param name - unique identifier
	 * @param transform
	 * @param color
	 */
	public GUI(String name,Transform transform, Vector4f color) {
		super(name);
		this.texture = null;
		this.transform = transform;
		this.color = color;
		this.UVScale = new Vector2f(1.f,1.f);
	}
	
	/**
	 * 
	 * @param name - unique identifier
	 * @param transform
	 * @param texture
	 * @param color
	 */
	public GUI(String name,Transform transform, String texture, Vector4f color) {
		super(name);
		this.texture = Texture.Create(texture);
		this.texturePath = texture;
		this.transform = transform;
		this.color = color;
		this.UVScale = new Vector2f(1.f,1.f);
	}
	
	/**
	 * 
	 * @param name - unique identifier
	 * @param transform
	 * @param texture
	 */
	public GUI(String name,Transform transform, String texture) {
		super(name);
		this.texture = Texture.Create(texture);
		this.texturePath = texture;
		this.transform = transform;
		this.color = new Vector4f(1.f,1.f,1.f,1.f);
		this.UVScale = new Vector2f(1.f,1.f);
	}
	
	
	public void Init() {
		this.RelativePosition = transform.GetPosition();
		UpdateTransform();
		_Init();
	}
	
	protected void _Init() {
		
	}
	
	/**
	 * Add Gui to GUI render pipeline
	 */
	public void Add() {
		if (added) return;
		for (GUI c : children) {
			c.Add();
		}
		GUIRenderer.Add(this);
		added = true;
	}
	
	/**
	 * Remove Gui from GUI render pipeline
	 */
	public void Remove() {
		added = false;
		DeselectGUI();
		for (GUI c : children) {
			c.Remove();
		}
		GUIRenderer.Remove(this);
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
		return new Vector3f(_transform.GetPosition().x,_transform.GetPosition().y, transform.GetPosition().z);
	}
	
	
	public Vector3f GetScale() {
		return this.transform.GetScale();
	}
	
	/**
	 * Get bounds of gui
	 */
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
	
	/**
	 * 
	 * @param x - drag begin mouse x location
	 * @param y - drag begin mouse y location
	 */
	public void BeginDrag(float x, float y) {
		dragPos.x = x;
		dragPos.y = y;
	}
	
	/**
	 * 
	 * @param x - current mouse x position
	 * @param y - current mouse y position
	 */
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
	
	public void SetRelativePosition(float x, float y, float z) {
		RelativePosition = new Vector3f(x,y,z);
		UpdateTransform();
	}
	
	public Vector3f GetRelativePosition() {
		return RelativePosition;
	}
	
	public void SetShader(Shader s) {
		Shader.Remove(this.shader);
		this.shader = s;
	}
	
	public void SetRelativePosition(Vector3f pos) {
		RelativePosition = pos;
		if (this.parent != null) {
			SetPosition(this.parent.GetPosition());
		}
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
	
	public GUI AddChild(GUI g) {
		children.add(g);
		if (g.GetActor() != null) g.GetActor().RemoveComponent_NOCLEAN(g);
		g.SetParent(this);
		if (added && !g.added) {
			g.Add();
		}
		return this;
	}
	
	public GUI AddChild(GUI g, boolean b) {
		children.add(g);
		if (b) g.SetRelativePosition( g.GetPosition().x - this.GetPosition().x, g.GetPosition().y - this.GetPosition().y , g.GetZOrder());
		if (g.GetActor() != null) g.GetActor().RemoveComponent_NOCLEAN(g);
		g.SetParent(this);
		if (added && !g.added) g.Add();
		return this;
		
	}
	 
	public GUI GetChild(String id) {
		for (GUI c : children) {
			if (c.GetName().equals(id)) {
				return c;
			}
		}
		return null;
	}
		
	public GUI RemoveChild(GUI g) {
		if (children.contains(g)) {
			children.remove(g);
			g.SetParent(null);
			if (g.added) g.Remove();
		}
		return this;
	}
	
	private void SetParent(GUI p) {
		this.parent = p;
	}
	
	public void SetWindowElement(boolean b) {
		this.isWindow = b;
		for (GUI c : children) {
			c.SetWindowElement(b);
		}
	}
	
	public void SetMeshScreen(boolean b) {
		this.isMeshScreen = b;
		for (GUI c : children) {
			c.SetMeshScreen(b);
		}
	}
	
	public void SetTransform(Transform transform) {
		this.transform = transform;
		UpdateTransform();
	}
	
	public void UpdateTransform() {
		this.zOrder = transform.GetPosition().z + (parent == null ? 0 : GetRelativePosition().z);
		this.transform.SetPosition(transform.GetPosition().x + (parent == null ? 0 : GetRelativePosition().x)
				, transform.GetPosition().y + (parent == null ? 0 : GetRelativePosition().y), 0f);
		this._transform = new Transform(transform.GetPosition(), transform.GetRotation(), 
				transform.GetScale());
		this._transform.SetPosition(new Vector3f(MathLib.GetMappedRangeValueUnclamped(-1, 1, -2, 2,_transform.GetPosition().x)/2f, 
				-MathLib.GetMappedRangeValueUnclamped(-1, 1, -2, 2, -_transform.GetPosition().y)/2f,_transform.GetPosition().z ));
		for (GUI c : children) c.SetPosition(this.GetPosition().x, this.GetPosition().y, this.zOrder);
	
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
	
	public void CleanUp () {
		if (Application.ThreadSafe()) {
			OnCleanUp();
			children.clear();
			parent = null;
		}
	};
	
	public abstract void OnCleanUp();
	
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
	
	protected void OnDeselect() {
	}

	public abstract void SelectedOnEvent(Event e);

	protected void OnSelect() {
		
		
	}
	
	public GUI DetachChild(GUI c) {
		if (children.contains(c)) {
			if (c.GetActor() == null) {
				GetActor().AddComponent(c);
			}
			children.remove(c);
			c.SetParent(null);
		}
		
		return this;
	}
	
	public GUI SetGUICollision(boolean b) {
		SetCollision(b);
		return this;
	}
	
	@Override
	public CollisionObjectType GetCollisionObjectType() {
		return CollisionObjectType.GUI;
	}

	@Override
	public float GetZOrder() {
		return zOrder;
	}

	
	@Override
	public void OnBegin() {
		Add();
	}

	@Override
	public void OnEnd() {
		Remove();
	}

	public void SetTexture(Texture newTexture) {
		Texture.Remove(this.texture);
		this.texture = newTexture;
		
	}
	
	
}

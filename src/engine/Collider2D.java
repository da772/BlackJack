package engine;

import org.joml.Vector4f;

import engine.Events.Event;

public abstract class Collider2D {

	public enum CollisionObjectType {
		None, GUI, Mesh
	}
	
	public abstract CollisionObjectType GetCollisionObjectType();
	
	public abstract Vector4f GetRect();
	
	public abstract float GetZOrder();
	
	public abstract void SelectedOnEvent(Event e);
	
	public abstract boolean IsMouseOver();
	
	public abstract void SetMouseEnter();
	
	public abstract void SetMouseExit();

	public abstract void RemoveSelection();
	
	protected float zOrder = 0f;

	
}

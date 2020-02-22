package engine;

import org.joml.Vector4f;

import engine.Events.Event;

public abstract class Collider2D extends Component {

	/**
	 * 
	 * @param s - unique identifier
	 */
	public Collider2D(String s) {
		super(s);
	}
	
	public enum CollisionObjectType {
		None, GUI, Mesh
	}
	
	public abstract CollisionObjectType GetCollisionObjectType();
	
	/**
	 * 
	 * @return Vector4f - rect of object
	 */
	public abstract Vector4f GetRect();
	
	/**
	 * 
	 * @return float - order to be renderered
	 */
	public abstract float GetZOrder();
	
	public abstract void SelectedOnEvent(Event e);
	
	public abstract boolean IsMouseOver();
	
	public abstract void SetMouseEnter();
	
	public abstract void SetMouseExit();

	public abstract void RemoveSelection();
	
	
	public boolean isWindow = false;
	
	protected float zOrder = 0f;

	protected boolean collision = true;
	
	protected void SetCollision(boolean b) {
		collision = b;
		if (!b) {
			Collision2D.Remove(this);
		} else {
			Collision2D.Add(this);
		}
	}
	
	
}

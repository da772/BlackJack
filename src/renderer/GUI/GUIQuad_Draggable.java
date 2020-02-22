package renderer.GUI;

import org.joml.Vector4f;

import engine.Events;
import engine.Input;
import engine.KeyCodes;
import engine.Events.Event;
import renderer.GUIRenderer;
import renderer.Transform;
import util.MathLib;

public class GUIQuad_Draggable extends GUIQuad {
	
	protected boolean isDragging = false;
	/**
	 * 
	 * @param name - unique identifier
	 * @param transform - quad transfom
	 * @param texture - quad texture
	 * @param quadColor - quad color
	 * @param textOffset - text position offset
	 * @param font - font file path
	 * @param text - text to write
	 * @param textColor - text color
	 * @param textWidth - text width
	 * @param textHeight - text height
	 * @param center - center text?
	 * @param autoSize - auto size width?
	 */
	public GUIQuad_Draggable(String name, Transform transform, String texture, Vector4f color) {
		super(name, transform, texture, color);
		nXPos = this.transform.GetPosition().x;
		nYPos = this.transform.GetPosition().y;
	}
	
	protected float nXPos, nYPos;
	
	
	@Override
	protected void OnMouseEnter() {
		SelectGUI();
	}
	
	@Override
	protected void OnSelect() {
		SetColor(new Vector4f(.125f, .125f,.45f,.9f));
	}
	
	protected void StartDragging() {
		isDragging = true;
		BeginDrag(Input.GetMouseX(), Input.GetMouseY());
		OnDragStart();
	}
	
	protected void OnDragStart() {
		
	}
	
	protected void OnDragEnd() {
		
	}
	
	
	protected void StopDragging() {
		isDragging = false;
		OnDragEnd();
	}
	
	
	@Override
	protected void OnMouseExit() {
		DeselectGUI();
	}
	
	@Override
	public void OnDeselect() {
		SetColor(new Vector4f(.125f, .125f,.25f,.9f));
		isDragging = false;
	}
	
	protected void OnDrag() {
		
	}
	 
	protected void _Drag(float x, float y) {
		Drag( x, y);
		nXPos = this.transform.GetPosition().x;
		nYPos = this.transform.GetPosition().y;
		OnDrag();	
		
	}
	
	protected void OnMouseUp() {
		
	}
	
	@Override
	public void SelectedOnEvent(Event e) {
		if (e instanceof Events.MouseButtonPressedEvent) {
			if (((Events.MouseButtonEvent)e).GetKeyCode() == KeyCodes.MOUSE_LEFT) {
				if (!isDragging) StartDragging();	
			}
		}
		
		if (e instanceof Events.MouseButtonReleasedEvent) {
			if (((Events.MouseButtonEvent)e).GetKeyCode() == KeyCodes.MOUSE_LEFT) {
				if (isDragging) StopDragging();
				OnMouseUp();
			}
		}

		if (e instanceof Events.MouseMovedEvent) {
			if ( !MathLib.InBounds( ((Events.MouseMovedEvent)e).GetMouseX(), 0f, (float)GUIRenderer.GetWidth())
					|| !MathLib.InBounds( ((Events.MouseMovedEvent)e).GetMouseY(), 0f, (float)GUIRenderer.GetHeight()))
			{
				DeselectGUI();
				return;
			}
			if (isDragging) {
				_Drag( ((Events.MouseMovedEvent)e).GetMouseX(),
				((Events.MouseMovedEvent)e).GetMouseY());
				}
		}
			
		
	}
	
	
}

package engine.renderer.GUI;

import org.joml.Vector4f;


import engine.Events;
import engine.KeyCodes;
import engine.Events.Event;
import engine.renderer.GUIRenderer;
import engine.renderer.Texture;
import engine.renderer.Transform;
import engine.util.MathLib;


/**
 * 
 * @param name - unique identifier
 * @param transform - quad transform
 * @param texture - quad texture
 * @param quadColor - quad color

 */
public abstract class GUIButton extends GUIQuad {

	boolean leftClicked = false;
	private String textureString1, textureString2;
	
	/**
	 * 
	 * @param name - unique identifier
	 * @param transform - quad transform
	 * @param texture1 - quad texture before clicked
	 * @param texture - quad texture when clicked
	 * @param quadColor - quad color

	 */
	public GUIButton(String name, Transform transform, String texture1, String texture2, Vector4f quadColor) {
		super(name, transform, texture1, quadColor);
		textureString2 = texture2;
		textureString1 = texture1;
		SetTexture(textureString2);
	}
	
	
	private Texture texture1;
	private Texture texture2;
	
	public void SetTexture(String texturePath) {
		if (textureString2 == null && !this.texturePath.equals(texturePath)) {
			this.textureString2 = texturePath;
		}
		if (texture2 == null) {
			texture2 = Texture.Create(this.textureString2);
		}
		
		if (this.texturePath.equals(texturePath)) {
			texture = texture1;
		}
		if (textureString2.equals(texturePath)) {
			texture1 = texture;
			texture = texture2;
		}
	}
	
	
	@Override
	public void OnCleanUp() {
		super.OnCleanUp();
		Texture.Remove(texture1);
		Texture.Remove(texture2);
	}
	
	
	
	@Override
	protected void OnMouseEnter() {
		leftClicked = false;
		//Application.GetWindow().SetCursor(engine.Window.CursorType.HandCursor);
		SelectGUI();
	}
	
	@Override
	protected void OnMouseExit() {
		leftClicked = false;
		//Application.GetWindow().SetCursor(engine.Window.CursorType.ArrowCursor);
		DeselectGUI();
	}
	
	
	@Override
	protected abstract void OnSelect();

	protected abstract void OnMousePressed();
	
	protected abstract void OnMouseReleased();
	
	public void SetButtonTexture(boolean pressed) {
		SetTexture(pressed ? textureString2 : textureString1);
	}
	

	@Override
	public abstract void OnDeselect();
	
	@Override
	public void SelectedOnEvent(Event e) {

		
		if (e instanceof Events.MouseButtonPressedEvent) {
			if (((Events.MouseButtonEvent)e).GetKeyCode() == KeyCodes.MOUSE_LEFT) {
				if (IsMouseOver()) {
					leftClicked = true;
					OnMousePressed();
				}
			}
		}
		
		if (e instanceof Events.MouseButtonReleasedEvent) {
			if (((Events.MouseButtonEvent)e).GetKeyCode() == KeyCodes.MOUSE_LEFT) {
				if (IsMouseOver() && leftClicked) OnMouseReleased();
			}
		}
		
		if (e instanceof Events.MouseMovedEvent) {
			if ( !MathLib.InBounds( ((Events.MouseMovedEvent)e).GetMouseX(), 0f, (float)GUIRenderer.GetWidth())
				|| !MathLib.InBounds( ((Events.MouseMovedEvent)e).GetMouseY(), 0f, (float)GUIRenderer.GetHeight()))
			{
				leftClicked = false;
				DeselectGUI();
				return;
			}
		}
			
		
	}
	
}

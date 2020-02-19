package renderer.GUI;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import engine.Events;
import engine.KeyCodes;
import engine.Events.Event;
import renderer.GUIRenderer;
import renderer.Shader;
import renderer.Texture;
import renderer.Transform;
import util.MathLib;


/**
 * 
 * @param name - unique identifier
 * @param transform - quad transform
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
public abstract class GUIButton extends GUITextQuad {

	boolean leftClicked = false;
	private String textureString2;
	
	/**
	 * 
	 * @param name - unique identifier
	 * @param transform - quad transfom
	 * @param texture1 - quad texture before clicked
	 * @param texture - quad texture when clicked
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
	public GUIButton(String name, Transform transform, String texture1, String texture2, Vector4f quadColor, Vector2f textOffset,
			String font, String text, Vector4f textColor, float textWidth, float textHeight, boolean center,
			boolean autoSize) {
		super(name, transform, texture1, quadColor, textOffset, font, text, textColor, textWidth, textHeight, center, autoSize);
		textureString2 = texture2;
	}
	
	
	@Override
	public void _Init() {
		text = new GUIText(name+"1",
				textString, // Text to display
				FontHeight, // Font height
				font, // Font path without png or fnt
				 // Create transform
				new Transform(
				new Vector3f(transform.GetPosition().x+TextOffset.x-TextWidth/2f,
						transform.GetPosition().y+TextOffset.y,
						transform.GetPosition().z), // Position (x, y,z)
				transform.GetRotation(),  // Rotation (x, y ,z)
				new Vector3f(1f)),  // Scale (x, y, z)
				TextColor, // Color (r, g, b)
				TextWidth, // Text Length 0-1 (Percentage of screen)
				textCentered // Center Text   
				);
		
		quad = new GUIQuad (name+"2",
				transform,
				QuadTexture, // Texture of the hud
				QuadColor // Color of the hud
				) {
			
			public Texture texture1;
			public Texture texture2;

			private String texture2String;
			
			
			public void SetTexture(String texturePath) {
				if (texture2String == null && !this.texturePath.equals(texturePath)) {
					this.texture2String = texturePath;
				}
				if (texture2 == null) {
					texture2 = Texture.Create(this.texture2String);
				}
				
				if (this.texturePath.equals(texturePath)) {
					texture = texture1;
				}
				if (texture2String.equals(texturePath)) {
					texture1 = texture;
					texture = texture2;
				}
			}
			
			
			@Override
			public void OnCleanUp() {
				Texture.Remove(texture);
				Texture.Remove(texture1);
				Texture.Remove(texture2);
				Shader.Remove(shader);
			}
		};
		quad.SetTexture(textureString2);
 		quad.Init();
		text.Init();
	}
	
	
	
	@Override
	protected void OnMouseEnter() {
		leftClicked = false;
		SelectGUI();
	}
	
	@Override
	protected abstract void OnSelect();

	protected abstract void OnMousePressed();
	
	protected abstract void OnMouseReleased();
	
	public void SetButtonTexture(boolean pressed) {
		quad.SetTexture(pressed ? textureString2 : QuadTexture );
	}
	
	@Override
	protected void OnMouseExit() {
		leftClicked = false;
		DeselectGUI();
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

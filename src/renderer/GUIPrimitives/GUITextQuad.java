package renderer.GUIPrimitives;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import engine.Events.Event;
import renderer.GUI;
import renderer.GUIQuad;
import renderer.GUIRenderer;
import renderer.Transform;
import renderer.text.GUIText;

public class GUITextQuad extends GUI {
	
	private GUIText text;
	private GUIQuad quad;
	private boolean autoSize;
	
	Vector2f TextOffset;
	
	public GUITextQuad (Transform transform, String QuadTexture, Vector4f QuadColor, 
			Vector2f TextOffset, String font, String textString, Vector4f TextColor, 
			float TextWidth, float FontHeight, boolean textCentered, boolean autoSizeText) {
		this.transform = transform;
		this.TextOffset = TextOffset;
		this.autoSize = autoSizeText;
		text = new GUIText(
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
		
		quad = new GUIQuad (
				transform,
				QuadTexture, // Texture of the hud
				QuadColor // Color of the hud
				);
		
		if (this.autoSize) {
			SetTransform(transform);
		}
		
	}
	
	@Override
	public void Add() {

		GUIRenderer.Add(this);
		added = true;
	}
	
	@Override
	public void _Init() {
		quad.Init();
		text.Init();
	}
	
	
	public void SetTransform(Transform transform) {
		this.transform = transform;
		if (this.autoSize) {
			this.text.SetMaxLineSize(transform.GetScale().x);
		}
		this.text.SetTransform(new Transform(
				new Vector3f(transform.GetPosition().x+TextOffset.x-(text.getMaxLineSize()/2f),
						transform.GetPosition().y+TextOffset.y,
						transform.GetPosition().z), // Position (x, y,z)
				transform.GetRotation(),  // Rotation (x, y ,z)
				new Vector3f(1f)));
		
		this.quad.SetTransform(transform);
	}
	
	public void SetText(String text) {
		this.text.SetText(text);
	}
		
	
	public void SetTextColor(Vector4f color) {
		this.text.SetColor(color);
	}
	
	public void SetTextColor(float r, float g, float b, float a) {
		SetTextColor(new Vector4f(r,g,b,a));
	}
	
	public void SetQuadColor(float r,float g,float b,float a ) {
		SetQuadColor(new Vector4f(r,g,b,a));
	}
	
	public void SetQuadColor(Vector4f rgba) {
		this.quad.SetColor(rgba);
	}
	
	public void SetQuadTexture(String texturePath) {
		quad.SetTexture(texturePath);
	}
	
	@Override
	public void Bind() {}

	@Override
	public void UnBind() {}
	
	@Override
	public void Draw() {
		quad.Bind();
		quad.Draw();
		quad.UnBind();
		text.Bind();
		text.Draw();
		text.UnBind();
	}

	@Override
	public void CleanUp() {
		text.CleanUp();
		quad.CleanUp();
	}

	@Override
	public void SelectedOnEvent(Event e) {
		
		
	}


	

}

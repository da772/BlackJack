package renderer.GUI;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import engine.Events.Event;
import renderer.GUIRenderer;
import renderer.Transform;
import renderer.text.TextMeshCreator;

public class GUIText extends GUI {
	
	protected GUITextBase text;
	protected GUIQuad quad;
	protected boolean autoSize;
	protected String name, textString, font, QuadTexture;
	protected Vector4f TextColor,  QuadColor;
	protected float TextWidth, FontHeight;
	protected boolean textCentered;
	protected Vector2f TextOffset;
	
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
	public GUIText (String name, Transform transform, String QuadTexture, Vector4f QuadColor, 
			Vector2f TextOffset, String font, String textString, Vector4f TextColor, 
			float TextWidth, float FontHeight, boolean textCentered, boolean autoSizeText) {
		super(name);
		this.transform = transform;
		this.TextOffset = TextOffset;
		this.autoSize = autoSizeText;
		this.name = name;
		this.QuadTexture = QuadTexture;
		this.QuadColor = QuadColor;
		this.font = font;
		this.textString = textString;
		this.TextColor = TextColor;
		this.TextWidth = TextWidth;
		this.FontHeight = FontHeight;
		this.textCentered = textCentered;
		
		
		if (this.autoSize) {
			this.TextWidth = this.transform.GetScale().x;
		}
		
	}

	@Override
	public void Add() {
		GUIRenderer.Add(this);
		added = true;
	}
	
	@Override
	public void _Init() {
		SetupText();
		SetupQuad();
		SetTransform(this.transform);
	}
	
	void SetupText() {
		text = new GUITextBase(name+"1",
				textString, // Text to display
				FontHeight, // Font height
				font, // Font path without png or fnt
				 // Create transform
				new Transform(
				new Vector3f(transform.GetPosition().x+TextOffset.x+(1-TextWidth),
						-transform.GetPosition().y-TextOffset.y + (1-(FontHeight*TextMeshCreator.GetLineHeight())),
						transform.GetPosition().z), // Position (x, y,z)
				transform.GetRotation(),  // Rotation (x, y ,z)
				new Vector3f(1f)),  // Scale (x, y, z)
				TextColor, // Color (r, g, b)
				TextWidth, // Text Length 0-1 (Percentage of screen)
				textCentered // Center Text   
				);
		text.Init();
	}
	
	void SetupQuad() {
		quad = new GUIQuad (name+"2",
				transform,
				QuadTexture, // Texture of the hud
				QuadColor // Color of the hud
				);
		quad.Init();
	}

	
	public void SetTransform(Transform transform) {
		this.transform = transform;
		this.text.SetTransform(new Transform(
				new Vector3f(transform.GetPosition().x+TextOffset.x+(1-TextWidth),
						-transform.GetPosition().y-TextOffset.y + (1-(FontHeight*TextMeshCreator.GetLineHeight()))-
						((text.getNumberOfLines()-1f)*(FontHeight*TextMeshCreator.GetLineHeight())),
						transform.GetPosition().z), // Position (x, y,z)
				transform.GetRotation(),  // Rotation (x, y ,z)
				new Vector3f(1f)));
		
		this.quad.SetTransform(transform);
	}
	
	public void SetText(String text) {
		this.text.SetText(text);
		SetTransform(this.transform);
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
	public void OnCleanUp() {
		if (text != null)
		text.CleanUp();
		if (quad != null)
		quad.CleanUp();
		text = null;
		quad = null;
	}

	@Override
	public void SelectedOnEvent(Event e) {
		
		
	}


	

}

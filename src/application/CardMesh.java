package application;

import org.joml.Vector3f;
import org.joml.Vector4f;

import engine.Camera;
import engine.ShaderLib;
import engine.TextureAtlas;
import engine.renderer.Texture;
import engine.renderer.TextureCoords;
import engine.renderer.Transform;
import engine.renderer.mesh.Mesh2DQuad;
import engine.util.MathLib;

/**
 * 
 * @param name - unique identifier for component
 * @param transform - location in world space
 * @param cardFront - card front image ex: "AS" = ace of spaces, "4H" = 4 of hearts 
 * @param cardBack - card front image ex: "AS" = ace of spaces, "4H" = 4 of hearts, "card_back_red" = red card back
 * @param cam - camera to render to
 */
public class CardMesh extends Mesh2DQuad {

	
	TextureAtlas textureAtlas;
	TextureCoords textureCoord;
	
	String cardFront, cardBack;
	
	static Texture _texture = Texture.Create("Atlas/cardAtlas.png", false, true);
	
	
	/**
	 * 
	 * @param name - unique identifier for component
	 * @param transform - location in world space
	 * @param cardFront - card front image ex: "AS" = ace of spaces, "4H" = 4 of hearts 
	 * @param cardBack - card front image ex: "AS" = ace of spaces, "4H" = 4 of hearts, "card_back_red" = red card back
	 * @param cam - camera to render to
	 */
	public CardMesh(String name, Transform transform, String cardFront, String cardBack, Camera cam) {
		super(name, transform, ShaderLib.Shader_2DQuad, "Atlas/cardAtlas.png", new Vector4f(1f), cam);
		textureAtlas = TextureAtlas.Create("Atlas/cardAtlas");
		this.generateMipMap = true;
		this.lodBias = true;
		this.SetCollision(false);
		this.cardFront = cardFront;
		this.cardBack = cardBack;
		this.SetScale(transform.GetScale().x * .75f * 1.25f, transform.GetScale().y*1.5f, transform.GetScale().z);
		this.SetRotation(transform.GetRotation().x,transform.GetRotation().y,transform.GetRotation().z+180f);
		SetupTextureCoords();
		
	}
	
	public boolean AnimateTo(Transform transform, float deltaTime, float speed, float threshold) {
		Transform trans = new Transform(new Vector3f(
				MathLib.Lerp(this.GetPosition().x, transform.GetPosition().x, deltaTime*speed), 
				MathLib.Lerp(this.GetPosition().y, transform.GetPosition().y, deltaTime*speed),
				MathLib.Lerp(this.GetPosition().z, transform.GetPosition().z, deltaTime*speed)),
				new Vector3f(
				MathLib.Lerp(this.GetRotation().x, transform.GetRotation().x, deltaTime*speed), 
				MathLib.Lerp(this.GetRotation().y, transform.GetRotation().y, deltaTime*speed),
				MathLib.Lerp(this.GetRotation().z, transform.GetRotation().z, deltaTime*speed)),
				this.transform.GetScale()
				);
		this.SetTransform(trans); 
		// Check if we are close enough
		return trans.GetPosition().distance(transform.GetPosition()) + trans.GetRotation().distance(transform.GetRotation()) <= threshold;
		
	}
	
	private void SetupTextureCoords() {
		
		float width = (int) textureAtlas.GetObject("info").GetInt("width");
		float height = (int) textureAtlas.GetObject("info").GetInt("width");
		
		textureCoord = new TextureCoords(
				// Front
				
				new Vector4f(
				(textureAtlas.GetObject(cardFront).GetInt("x")+textureAtlas.GetObject(cardFront).GetInt("width") )/width,
				(textureAtlas.GetObject(cardFront).GetInt("x")+textureAtlas.GetObject(cardFront).GetInt("width") )/width,
				textureAtlas.GetObject(cardFront).GetInt("x")/width,
				textureAtlas.GetObject(cardFront).GetInt("x")/width
				),
				
				new Vector4f(
				(textureAtlas.GetObject(cardFront).GetInt("y")+textureAtlas.GetObject(cardFront).GetInt("height") )/height,
				(textureAtlas.GetObject(cardFront).GetInt("y")+textureAtlas.GetObject(cardFront).GetInt("height") )/height,
				textureAtlas.GetObject(cardFront).GetInt("y")/height,
				textureAtlas.GetObject(cardFront).GetInt("y")/height),
				// Back
				new Vector4f(
				(textureAtlas.GetObject(cardBack).GetInt("x")+textureAtlas.GetObject(cardBack).GetInt("width") )/width,
				(textureAtlas.GetObject(cardBack).GetInt("x")+textureAtlas.GetObject(cardBack).GetInt("width") )/width,
				textureAtlas.GetObject(cardBack).GetInt("x")/width,
				textureAtlas.GetObject(cardBack).GetInt("x")/width)
				,
				new Vector4f(
				(textureAtlas.GetObject(cardBack).GetInt("y")+textureAtlas.GetObject(cardBack).GetInt("height") )/height,
				(textureAtlas.GetObject(cardBack).GetInt("y")+textureAtlas.GetObject(cardBack).GetInt("height") )/height,
				textureAtlas.GetObject(cardBack).GetInt("y")/height,
				textureAtlas.GetObject(cardBack).GetInt("y")/height)
				);
		
		SetTextureCoords(textureCoord);	
	}
	
	@Override
	protected void OnCleanUp() {
		super.OnCleanUp();
		TextureAtlas.Remove(textureAtlas);
	}

	
	
}
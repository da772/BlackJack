package application;

import org.joml.Vector4f;

import engine.Camera;
import engine.TextureAtlas;
import renderer.ShaderLib;
import renderer.TextureCoords;
import renderer.Transform;
import renderer.mesh.Mesh2DQuad;


public class CardMesh extends Mesh2DQuad {

	
	TextureAtlas textureAtlas;
	TextureCoords textureCoord;
	
	String cardFront, cardBack;
	
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
		this.generateMipMap = false;
		this.cardFront = cardFront;
		this.cardBack = cardBack;
		this.SetRotation(transform.GetRotation().x,transform.GetRotation().y,transform.GetRotation().z+180f);
		SetupTextureCoords();
		
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
	
	
}
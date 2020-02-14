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
	
	public CardMesh(Transform transform, String cardFront, String cardBack, Camera cam) {
		super(transform, ShaderLib.Shader_2DQuad, "Atlas/cardAtlas.png", new Vector4f(1f), cam);
		textureAtlas = TextureAtlas.Create("Atlas/cardAtlas");
		this.cardFront = cardFront;
		this.cardBack = cardBack;
		SetupTextureCoords();
		
	}
	
	private void SetupTextureCoords() {
		
		float width = (int) textureAtlas.GetObject("info").GetInt("width");
		float height = (int) textureAtlas.GetObject("info").GetInt("width");
		
		textureCoord = new TextureCoords(
				// Front
				new Vector4f(
				(textureAtlas.GetObject(cardFront).GetInt("x")+textureAtlas.GetObject("5H").GetInt("width") )/width,
				(textureAtlas.GetObject(cardFront).GetInt("x")+textureAtlas.GetObject("5H").GetInt("width") )/width,
				textureAtlas.GetObject(cardFront).GetInt("x")/width,
				textureAtlas.GetObject(cardFront).GetInt("x")/width
				),
				
				new Vector4f(
				(textureAtlas.GetObject(cardFront).GetInt("y")+textureAtlas.GetObject("5H").GetInt("height") )/height,
				(textureAtlas.GetObject(cardFront).GetInt("y")+textureAtlas.GetObject("5H").GetInt("height") )/height,
				textureAtlas.GetObject(cardFront).GetInt("y")/height,
				textureAtlas.GetObject(cardFront).GetInt("y")/height),
				
				new Vector4f(
				(textureAtlas.GetObject(cardBack).GetInt("x")+textureAtlas.GetObject("5H").GetInt("width") )/width,
				(textureAtlas.GetObject(cardBack).GetInt("x")+textureAtlas.GetObject("5H").GetInt("width") )/width,
				textureAtlas.GetObject(cardBack).GetInt("x")/width,
				textureAtlas.GetObject(cardBack).GetInt("x")/width)
				,
				new Vector4f(
				(textureAtlas.GetObject(cardBack).GetInt("y")+textureAtlas.GetObject("5H").GetInt("height") )/height,
				(textureAtlas.GetObject(cardBack).GetInt("y")+textureAtlas.GetObject("5H").GetInt("height") )/height,
				textureAtlas.GetObject(cardBack).GetInt("y")/height,
				textureAtlas.GetObject(cardBack).GetInt("y")/height)
				);
		
		SetTextureCoords(textureCoord);	
	}
	
	
	
	
	
	
}

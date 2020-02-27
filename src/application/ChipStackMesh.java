package application;

import org.joml.Vector3f;
import org.joml.Vector4f;

import engine.Camera;
import engine.TextureAtlas;
import renderer.TextureCoords;
import renderer.Transform;
import renderer.mesh.Mesh2DInstancedQuad;

/**
 * 
 * @param name - unique identifier for component
 * @param transform - location in world space
 * @param cardFront - card front image ex: "AS" = ace of spaces, "4H" = 4 of hearts 
 * @param cardBack - card front image ex: "AS" = ace of spaces, "4H" = 4 of hearts, "card_back_red" = red card back
 * @param cam - camera to render to
 */
public class ChipStackMesh extends Mesh2DInstancedQuad {

	TextureAtlas textureAtlas;
	TextureCoords textureCoord;
	
	String cardFront;
	
	
	
	public ChipStackMesh(String name, Transform transform, String chipType, int amount, Vector3f offset, Camera cam) {
		super(name, transform, GameShaderLib.Shader_ChipStack, "Atlas/cardAtlas.png", new Vector4f(1f), amount, offset, cam);
		textureAtlas = TextureAtlas.Create("Atlas/cardAtlas");
		this.generateMipMap = true;
		this.SetCollision(false);
		this.cardFront = chipType;
		this.SetScale(transform.GetScale().x * .25f, transform.GetScale().y * .25f, transform.GetScale().z);
		this.SetRotation(transform.GetRotation().x,transform.GetRotation().y,transform.GetRotation().z+180f);
		SetupTextureCoords();
	}
	
	public ChipStackMesh(String name, Transform transform, String[] shader, String chipType, int amount, Vector3f offset, Camera cam) {
		super(name, transform, shader, "Atlas/cardAtlas.png", new Vector4f(1f), amount, offset, cam);
		textureAtlas = TextureAtlas.Create("Atlas/cardAtlas");
		this.generateMipMap = true;
		this.SetCollision(false);
		this.cardFront = chipType;
		this.SetScale(transform.GetScale().x * .25f, transform.GetScale().y * .25f, transform.GetScale().z);
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
				(textureAtlas.GetObject(cardFront).GetInt("x")+textureAtlas.GetObject(cardFront).GetInt("width") )/width,
				(textureAtlas.GetObject(cardFront).GetInt("x")+textureAtlas.GetObject(cardFront).GetInt("width") )/width,
				textureAtlas.GetObject(cardFront).GetInt("x")/width,
				textureAtlas.GetObject(cardFront).GetInt("x")/width)
				,
				new Vector4f(
				(textureAtlas.GetObject(cardFront).GetInt("y")+textureAtlas.GetObject(cardFront).GetInt("height") )/height,
				(textureAtlas.GetObject(cardFront).GetInt("y")+textureAtlas.GetObject(cardFront).GetInt("height") )/height,
				textureAtlas.GetObject(cardFront).GetInt("y")/height,
				textureAtlas.GetObject(cardFront).GetInt("y")/height)
				);
		
		SetTextureCoords(textureCoord);	
	}
	



	@Override
	protected void OnCleanUp() {
		super.OnCleanUp();
		TextureAtlas.Remove(textureAtlas);
	}

	
	
}
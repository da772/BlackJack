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
	boolean isAnimating;
	Transform animStart;
	float animationTime = 0.f;
	
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
	
	/**
	 * 
	 * @param transform - target transformation
	 * @param deltaTime - time since last frame
	 * @param speed - speed of animation
	 * @param threshold - how close to target transformation we want to be
	 * @return boolean - animation complete
	 */
	public boolean AnimateTo(Transform targetTransform, float deltaTime, float speed, float threshold) {
		
		if (!isAnimating) {
			this.animStart = this.transform;
			this.isAnimating = true;
			this.animationTime = 0.f;
		}
		
		if (isAnimating) {
			
			
			float posDis = animStart.GetPosition().distance(targetTransform.GetPosition());
			float rotDis = animStart.GetRotation().distance(targetTransform.GetRotation());
			float scaleDis = animStart.GetScale().distance(targetTransform.GetScale());
			
			float maxDis = Math.max(Math.max(posDis, rotDis), scaleDis);
			
			animationTime += deltaTime*speed;
			
			Transform trans = new Transform(
					MathLib.Lerp(animStart.GetPosition(), targetTransform.GetPosition(),animationTime),
					MathLib.Lerp(animStart.GetRotation(), targetTransform.GetRotation(), animationTime),
					MathLib.Lerp(animStart.GetScale(), targetTransform.GetScale(),animationTime)
					);
			
			//check if our direction vector does not match the original direction vector this means we overshot our target
			if (!MathLib.VectorEquals(MathLib.DirectionSign(trans.GetPosition(), targetTransform.GetPosition()),
							MathLib.DirectionSign(animStart.GetPosition(), targetTransform.GetPosition()), threshold)) {
				isAnimating = false;
				return true;
			}
			this.SetTransform(trans);
			// Check if we close enough to our target to stop
			if (MathLib.VectorEquals(trans.GetPosition(), targetTransform.GetPosition(), threshold)) {
				isAnimating = false;
				return true;
			}
		}
		
		
		return false;
		
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
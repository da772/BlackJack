package application;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import engine.Actor;
import engine.Application;
import engine.CameraController;
import engine.Events;
import engine.KeyCodes;
import engine.Events.Event;
import renderer.Transform;
import renderer.GUI.GUI;
import renderer.GUI.GUIButton;
import renderer.GUI.GUIText;
import renderer.GUI.GUIQuad_Draggable;
import renderer.mesh.Mesh2DBackground;
import engine.Scene;
import engine.ShaderLib;
import engine.WindowFrame;

public class TestScene extends Scene {

	private float rotationSpeed = 180f;
	boolean scanLines = false;
	
	public TestScene(String name, CameraController cam) {
		super(name, cam);
	}

	@Override
	public void OnUpdate(float deltaTime) {
		if (GetActor("card") != null) {
		((CardMesh)GetActor("card").GetComponent("Mesh")).SetRotation(((CardMesh)GetActor("card").GetComponent("Mesh")).GetRotation().x,
				((CardMesh)GetActor("card").GetComponent("Mesh")).GetRotation().y-rotationSpeed*deltaTime, ((CardMesh)GetActor("card").GetComponent("Mesh")).GetRotation().z+rotationSpeed*deltaTime );
		}
		if (GetActor("card4") != null) {
			((ChipMesh)GetActor("card4").GetComponent("Mesh")).SetRotation(((ChipMesh)GetActor("card4").GetComponent("Mesh")).GetRotation().x,
					((ChipMesh)GetActor("card4").GetComponent("Mesh")).GetRotation().y+rotationSpeed*deltaTime, ((ChipMesh)GetActor("card4").GetComponent("Mesh")).GetRotation().z-rotationSpeed*deltaTime );
		}
	}

	@Override
	public void OnBegin() {
		
		new Actor("card").AddComponent(new CardMesh("Mesh", 
				new Transform( // Card Transform
						new Vector3f(0, -.35f, 1f), // Position
						new Vector3f(0f, 0, 0), // Rotation
						new Vector3f(1f, 1f, 1f) ), // Scale
				"AS", // Card front Suit
				"card_back_red", // Card back Suit
				this.cam.GetCamera()));// Camera))
		
		new Actor("card3").AddComponent(new CardMesh("Mesh", 
				new Transform( // Card Transform
						new Vector3f(-.75f, -.35f, 0), // Position
						new Vector3f(0f, 0, 0), // Rotation
						new Vector3f(1f, 1f, 1f) ), // Scale
				"JH", // Card front Suit
				"card_back_red", // Card back Suit
				this.cam.GetCamera()));// Camera))
		
		
		int amt = 25;
		
		new Actor("chipStack1").AddComponent(new ChipStackMesh("chipStack",
				new Transform(new Vector3f(.75f, .55f, .1f) ),
				"Chip1", 10, new Vector3f(-.4f, .0f,.01f), this.cam.GetCamera()
				));
		
		new Actor("chipStack5").AddComponent(new ChipStackMesh("chipStack",
				new Transform(new Vector3f(.45f, .55f, .1f) ),
				"Chip5", amt,new Vector3f(0,.1f,.01f),  this.cam.GetCamera()
				));
	
		new Actor("chipStack10").AddComponent(new ChipStackMesh("chipStack",
				new Transform(new Vector3f(.15f, .55f, .1f) ),
				"Chip10", amt,new Vector3f(0,.1f,.01f),  this.cam.GetCamera()
				));
		
		new Actor("chipStack25").AddComponent(new ChipStackMesh("chipStack",
				new Transform(new Vector3f(-.15f, .55f, .1f) ),
				"Chip25", amt,new Vector3f(0,.1f,.01f),  this.cam.GetCamera()
				));
		
		
		new Actor("chipStack50").AddComponent(new ChipStackMesh("chipStack",
				new Transform(new Vector3f(-.45f, .55f, .1f) ),
				"Chip50", amt,new Vector3f(0,.1f,.01f),  this.cam.GetCamera()
				));
		
		new Actor("chipStack100").AddComponent(new ChipStackMesh("chipStack",
				new Transform(new Vector3f(-.75f, .55f, .1f) ),
				"Chip100", amt,new Vector3f(0,.1f,.01f),  this.cam.GetCamera()
				));
		
		new Actor("chipStack500").AddComponent(new ChipStackMesh("chipStack",
				new Transform(new Vector3f(-1.05f, .55f, .1f) ),
				"Chip500", amt,new Vector3f(0,.1f,.01f),  this.cam.GetCamera()
				));
	
		new Actor("chipStack1000").AddComponent(new ChipStackMesh("chipStack",
				new Transform(new Vector3f(-1.35f, .55f, .1f) ),
				"Chip1000", amt,new Vector3f(.0f,.1f,.01f),  this.cam.GetCamera()
				));
		
		
		
		/*
		Actor.Create("example", this).AddComponent(new Mesh2DQuad("quad", //Name
				new Transform( // Card Transform
				new Vector3f(2, 0, 0), // Position
				new Vector3f(0f, 0, 0f), // Rotation
				new Vector3f(3f, 3f, 1f) ),// Scale)
				"Atlas/cardAtlas.png",// Texture
				new Vector4f(1f), // Color
				this.cam.GetCamera())
				);
		*/
		new Actor("background").AddComponent(new Mesh2DBackground("background",
				new Transform(
						new Vector3f(0f,0,-1f),  // Position
						new Vector3f(0f), // Rotation
						new Vector3f(10000, 10000, 1f)), // Scale
				"Images/blankTexture.png", // Texture
				new Vector4f(.1f,.4f,.1f,1f) , // Color
				new Vector2f(1f), // UV Scale
				this.cam.GetCamera() // Camera
				));
	
		
		
		new Actor("blackJackText").AddComponent(new GUIQuad_Draggable(
				"quad",
				new Transform(
						new Vector3f(0f,0f,3f),
						new Vector3f(0f),
						new Vector3f(.25f,.25f,1f)
						),
				"Images/blankTexture.png", // Texture of the hud
				new Vector4f(.125f, .125f,.25f,.9f)).AddChild(new GUIText(
						"text",
						new Transform(
								new Vector3f(0f,0f,.1f),
								new Vector3f(0f),
								new Vector3f(1f)
								),
						"Fonts/BebasNeue",
						"Drag Me!",
						new Vector4f(1f),
						.2f,
						1f,
						true
						)).AddChild(new GUIButton(
								"AttachButton",new Transform( 
						new Vector3f(0,-.25f,.1f), // Position x,y, Z-Order higher is on top
						new Vector3f(0f, 0f,0f),  // Rotation
						new Vector3f(.2f,.1f,1f)), // Scale x,y,z
						"Images/Buttons/mainMenuButtonUp.png",  // Button texture
						"Images/Buttons/mainMenuButtonDown.png", // Button pressed texture
						new Vector4f(.55f,.6f,.075f,1f) // Quad Color r,g,b,a
						
						) {
							private GUI lastParent = null;
							@Override
							protected void OnSelect() {
								if (this.GetChild("AttachText") != null) {
									this.GetChild("AttachText").SetColor(1,0,0,1);
								}
							}
							@Override
							protected void OnMousePressed() {
								SetButtonTexture(true);
							}
							@Override
							protected void OnMouseReleased() {
								SetButtonTexture(false);
								if (this.parent != null) {
									lastParent = this.parent;
									this.parent.DetachChild(this.parent.GetChild(this.GetName()));
									if (this.GetChild("AttachText") != null) {
										((GUIText)this.GetChild("AttachText")).SetText("Re-Attach?");
									}
								} else {
									lastParent.AddChild(this, true);
									this.parent.UpdateTransform();
									if (this.GetChild("AttachText") != null) {
										((GUIText)this.GetChild("AttachText")).SetText("Detach?");
									}
								}
							}
							@Override
							public void OnDeselect() {
								SetButtonTexture(false);
								if (this.GetChild("AttachText") != null) {
									this.GetChild("AttachText").SetColor(1,1,1,1);
								}
							}
						}.AddChild(new GUIText(
								"AttachText",
								new Transform(
										new Vector3f(0f,0f,.1f),
										new Vector3f(0f),
										new Vector3f(1f)
										),
								"Fonts/BebasNeue",
								"Deatch?",
								new Vector4f(1f),
								.2f,
								1f,
								true
								)))
				);
	
		
	}

	@Override
	public void OnEnd() {
		
	}

	@Override
	public void OnEvent(Event e) {
		if (e instanceof Events.KeyPressedEvent) {
			if (((Events.KeyPressedEvent)e ).GetKeyCode() == KeyCodes.KEY_T) {
				if (GetActor("cardRunTime") == null) {
					new Actor("cardRunTime");
				}
				else if (GetActor("cardRunTime") != null && GetActor("cardRunTime").GetComponent("Mesh") != null) {
					Actor.Remove("cardRunTime", this);
				}
				if (GetActor("cardRunTime") != null) {
					GetActor("cardRunTime").AddComponent(new CardMesh("Mesh", 
							new Transform( // Card Transform
									new Vector3f(-.75f, .75f, 0f), // Position
									new Vector3f(0f, 0, 0), // Rotation
									new Vector3f(.75f, 1f, 1f) ), // Scale
							"AH", // Card front Suit
							"card_back_red", // Card back Suit
							this.cam.GetCamera()));
				}
			}
			
			if (((Events.KeyPressedEvent)e ).GetKeyCode() == KeyCodes.KEY_Z) {
				if (!scanLines) {
					WindowFrame.SetMeshShader(ShaderLib.Shader_GUIQuad_CRTTV);
					scanLines = true;
				} else {
					WindowFrame.SetMeshShader(ShaderLib.Shader_GUIQuad);
					scanLines = false;
				}
			}
			
			if (((Events.KeyPressedEvent)e ).GetKeyCode() == KeyCodes.KEY_ESCAPE) { 
				Application.GetApp().SetPaused(!Application.IsPaused());
			}
		}
		
	}


	
	
}

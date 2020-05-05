package application;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import engine.Actor;
import engine.CameraController;
import engine.Events;
import engine.KeyCodes;
import engine.Events.Event;
import engine.renderer.Transform;
import engine.renderer.GUI.GUIButton;
import engine.renderer.GUI.GUIText;
import engine.renderer.mesh.*;
import engine.Scene;


/**
 * 
 * Game play scene
 * Requirement (3.2.0)
 *
 */
public class GameScene extends Scene {

	boolean scanLines = false;
	Table table;
	
	public GameScene(String name, CameraController cam) {
		super(name, cam);
	}

	@Override
	public void OnUpdate(float deltaTime) {
		if (table != null) {
			if (table.getRound() != null) {
				table.getRound().OnUpdate(deltaTime);
			}
		}
	}

	// Called when scene is created
	@Override
	public void OnBegin() {
		// Set up Camera
		cam.SetPosition(new Vector3f(0,-3.25f, cam.Position.z));
		cam.SetRotation(51f);
		((CameraController.Orthographic)this.cam).SetZoomLevel(4.f);
		table = new Table();
		
		table.playRound();
		// Create poker table and decorations
		// Requirement (3.2.1)
		CreateBackground();
		// Create pause menu button
		// Requirement (3.2.6)
		CreatePauseButton();

	}



	@Override
	public void OnEnd() {
		
	}

	
	@Override
	public void OnEvent(Event e) {

		if (e instanceof Events.KeyPressedEvent) {
			// Pause menu
			if (((Events.KeyPressedEvent)e ).GetKeyCode() == KeyCodes.KEY_ESCAPE) { 
				if (!GamePauseMenu.IsActive()) GamePauseMenu.Show(); else GamePauseMenu.Hide();
				
			}
		}
		
	}


	// Create static game objects
	private void CreateBackground() {

		Vector2f stackPosY = new Vector2f(7f, 7.75f);
		float stackPosX = -0.5f; 
		int amt = 30;
		
		// 
		new Actor("cardBorder1").AddComponent(new MeshQuad("blackJackTitle",
				new Transform(new Vector3f(0,.5f,0f), new Vector3f(0,0f,0f), new Vector3f(1.25f,1.75f,1f)),
				"Images/cardBorder.png",
				new Vector4f(1f),
				this.cam.GetCamera()
				));
		
		

		new Actor("cardBorder2").AddComponent(new MeshQuad("blackJackTitle",
				new Transform(new Vector3f(-5.5f,1.25f,0f), new Vector3f(0,0f,-40f), new Vector3f(1.25f,1.75f,1f)),
				"Images/cardBorder.png",
				new Vector4f(1f),
				this.cam.GetCamera()
				));
		
		new Actor("cardBorder3").AddComponent(new MeshQuad("blackJackTitle",
				new Transform(new Vector3f(5.5f,1.25f,0f), new Vector3f(0,0f, 40f), new Vector3f(1.25f,1.75f,1f)),
				"Images/cardBorder.png",
				new Vector4f(1f),
				this.cam.GetCamera()
				));

		new Actor("headerText").AddComponent(new MeshQuad("blackJackTitle",
				new Transform(new Vector3f(0,6.25f,-1.5f), new Vector3f(-15f,0f,0f), new Vector3f(6f,3,1f)),
				"Images/blackJackHeader.png",
				new Vector4f(1f),
				this.cam.GetCamera()
				));
		
		new Actor("headerInfoText").AddComponent(new MeshQuad("blackJackTitle",
				new Transform(new Vector3f(0,4.75f,-1f), new Vector3f(-15f,0f,0f), new Vector3f(5.5f,2.5f,1f)),
				"Images/blackJackInfoHeader.png",
				new Vector4f(1f),
				this.cam.GetCamera()
				));
		
		new Actor("headerText2").AddComponent(new MeshQuad("blackJackTitle",
				new Transform(new Vector3f(0,6.75f,-1.5f), new Vector3f(-15f,0f,0f), new Vector3f(2.5f,1.5f,1f)),
				"Images/menuLogoWhite.png",
				new Vector4f(1f),
				this.cam.GetCamera()
				));
		
		
		
		new Actor("border1").AddComponent(new MeshBackground("background",
				new Transform(
						new Vector3f(0f,10.4f,-5f),  // Position
						new Vector3f(0f), // Rotation
						new Vector3f(6f, 3.25f, 1f)), // Scale
				"Images/wideBorder.png", // Texture
				new Vector4f(1f) , // Color
				new Vector2f(1f), // UV Scale
				this.cam.GetCamera() // Camera
				));
		
		
		new Actor("edge").AddComponent(new MeshBackground("background",
				new Transform(
						new Vector3f(0f,2,-5f),  // Position
						new Vector3f(0f), // Rotation
						new Vector3f(14.25f, 5f, 1f)), // Scale
				"Images/tableEdge4.png", // Texture
				new Vector4f(1f) , // Color
				new Vector2f(.99f), // UV Scale
				this.cam.GetCamera() // Camera
				));
		
		new Actor("background").AddComponent(new MeshBackground("background",
				new Transform(
						new Vector3f(0f,0,-50f),  // Position
						new Vector3f(0f), // Rotation
						new Vector3f(1000, 1000, 1f)), // Scale
				"Images/pokerFelt.png", // Texture
				ColorPalette.HotPink , // Color
				new Vector2f(1e3f), // UV Scale
				this.cam.GetCamera() // Camera
				));
		
		
		stackPosX += -.5f;
		
		
		// Create chips at the top left of screen
		for (int i = 0; i < 3 ; i++) {
			
		stackPosX += -2f; 
			
		new Actor("chipStack1_"+i).AddComponent(new ChipStackMesh("chipStack",
				new Transform(new Vector3f(1f+stackPosX, stackPosY.x, .1f) ),
				"Chip1", amt,new Vector3f(0,0f,.025f),  this.cam.GetCamera()
				));
		

		new Actor("chipStack5_"+i).AddComponent(new ChipStackMesh("chipStack",
				new Transform(new Vector3f(1f+stackPosX, stackPosY.y, .095f) ),
				"Chip5", amt,new Vector3f(0,0f,.025f),  this.cam.GetCamera()
				));
		
		
		new Actor("chipStack10_"+i).AddComponent(new ChipStackMesh("chipStack",
				new Transform(new Vector3f(.5f+stackPosX, stackPosY.x, .1f) ),
				"Chip10", amt,new Vector3f(0,0f,.025f),  this.cam.GetCamera()
				));
		

		new Actor("chipStack25_"+i).AddComponent(new ChipStackMesh("chipStack",
				new Transform(new Vector3f(.5f+stackPosX, stackPosY.y, .095f) ),
				"Chip25", amt,new Vector3f(0,0f,.025f),  this.cam.GetCamera()
				));
		
		
		new Actor("chipStack50_"+i).AddComponent(new ChipStackMesh("chipStack",
				new Transform(new Vector3f(0f+stackPosX, stackPosY.x, .1f) ),
				"Chip50", amt,new Vector3f(0,0f,.025f),  this.cam.GetCamera()
				));
		

		new Actor("chipStack100_"+i).AddComponent(new ChipStackMesh("chipStack",
				new Transform(new Vector3f(0f+stackPosX, stackPosY.y, .095f) ),
				"Chip100", amt,new Vector3f(0,0f,.025f),  this.cam.GetCamera()
				));
		
		new Actor("chipStack500_"+i).AddComponent(new ChipStackMesh("chipStack",
				new Transform(new Vector3f(-.50f+stackPosX, stackPosY.x, .1f) ),
				"Chip500", amt,new Vector3f(0,0f,.025f),  this.cam.GetCamera()
				));
		

		new Actor("chipStack1000_"+i).AddComponent(new ChipStackMesh("chipStack",
				new Transform(new Vector3f(-.50f+stackPosX, stackPosY.y, .095f) ),
				"Chip1000", amt,new Vector3f(0,0f,.025f),  this.cam.GetCamera()
				));
		
		

		}
		
		stackPosX = 6f;
		float offset = 0;
		float row = 0;
		// Create deck of cards at top right of screen
		for (int i=0; i < 1; i++) {
			offset += 1f;
			offset = i == 3 ? 1f : offset;
			row = i >= 3? -1.5f : 0f;
			new Actor("cardStack1_"+i).AddComponent(new CardStackMesh("cardStack",
					new Transform(new Vector3f(stackPosX+offset, stackPosY.y+row, .1f) ),
					"card_back_black", "card_back_black", 52, new Vector3f(.05f,-0.0025f,.005f),  this.cam.GetCamera()
					));
		
		
		}
		
	}

	private void CreatePauseButton() {
		new Actor("Pause").AddComponent((new GUIButton("pause", new Transform(new Vector3f(-.875f, .85f, .1f), // Position x,y,
							new Vector3f(0f, 0f, 0f), // Rotation
							new Vector3f(.025f, .05f, 1f)), // Scale x,y,z
							"Images/Buttons/mainMenuButtonUp.png", // Button texture
							"Images/Buttons/mainMenuButtonDown.png", // Button pressed texture
							ColorPalette.DraculaOrchid // Quad Color r,g,b,a

					) {
						@Override
						protected void OnSelect() {

						}

						@Override
						protected void OnMousePressed() {
							SetButtonTexture(true);
						}

						@Override
						protected void OnMouseReleased() {
							SetButtonTexture(false);
							if (!GamePauseMenu.IsActive()) GamePauseMenu.Show(); else GamePauseMenu.Hide();
						}

						@Override
						public void OnDeselect() {
							SetButtonTexture(false);

						}
					}.AddChild(new GUIText("buttonText", new Transform(new Vector3f(0f, 0f, .01f)), "Fonts/BebasNeue",
							"||", new Vector4f(1f), .125f, 1f, true))));
	
		
	}
	
}

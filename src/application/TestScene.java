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
import engine.renderer.Transform;
import engine.renderer.GUI.*;
import engine.renderer.mesh.*;
import engine.Scene;
import engine.ShaderLib;
import engine.WindowFrame;
import engine.audio.AudioManager;

public class TestScene extends Scene {

	boolean scanLines = false;
	Table table = new Table();
	
	public TestScene(String name, CameraController cam) {
		super(name, cam);
	}

	@Override
	public void OnUpdate(float deltaTime) {
		
	}

	// Called when scene is created
	@Override
	public void OnBegin() {
		// Set up Camera
		cam.SetPosition(new Vector3f(0,-3.25f, cam.Position.z));
		cam.SetRotation(51f);
		((CameraController.Orthographic)this.cam).SetZoomLevel(4.f);
		
		
		table.playRound();
		
		for (int i =0; i < table.getPlayers().size(); i++) {
			this.SetupPlayer(table.getPlayerFromID(i), i);
		}
		
		this.SetupDealer(table.getDealer());
		
		// Add chips (place holder for now)
		new Actor("chipStack25_p1").AddComponent(new ChipStackMesh("chipStack",
				new Transform(new Vector3f(0.f, 0f, 1.25f) ),
				"Chip25", 2,new Vector3f(0,0f,.025f),  this.cam.GetCamera()
				));
		
		new Actor("chipStack10_p1").AddComponent(new ChipStackMesh("chipStack",
				new Transform(new Vector3f(0.525f, 0f, 1.25f) ),
				"Chip10", 7,new Vector3f(0,0f,.025f),  this.cam.GetCamera()
				));
		
		new Actor("chipStack5_p1").AddComponent(new ChipStackMesh("chipStack",
				new Transform(new Vector3f(-0.525f, 0f, 1.25f) ),
				"Chip5", 8,new Vector3f(0,0f,.025f),  this.cam.GetCamera()
				));

		// Create player 2 chips
		float o_set = -5f;
		new Actor("chipStack2_p2").AddComponent(new ChipStackMesh("chipStack",
				new Transform(new Vector3f(0.f+o_set, 1f, 1.25f) ),
				"Chip5", 6,new Vector3f(0,0f,.025f),  this.cam.GetCamera()
				));
		
		new Actor("chipStack50_p2").AddComponent(new ChipStackMesh("chipStack",
				new Transform(new Vector3f(0.525f+o_set, .75f, 1.25f) ),
				"Chip50", 3,new Vector3f(0,0f,.025f),  this.cam.GetCamera()
				));
		
		new Actor("chipStack1_p2").AddComponent(new ChipStackMesh("chipStack",
				new Transform(new Vector3f(o_set + 1f, .25f, 1.25f), new Vector3f(0,0,34f), new Vector3f(1,1,1) ),
				"Chip100", 2,new Vector3f(0,0f,.025f),  this.cam.GetCamera()
				));
		

		// Create background
		CreateBackground();

		
		// Test GUI (eventually will be player's GUI, for now just a draggable quad with slider and a button that plays sound when pressed)
		new Actor("blackJackText").AddComponent(new GUIQuad_Draggable(
				"quad",
				new Transform(
						new Vector3f(0f,-.775f,3f),
						new Vector3f(0f),
						new Vector3f(.25f,.225f,1f)
						),
				"Images/blankTexture.png", // Texture of the hud
				new Vector4f(.125f, .125f,.25f,.9f))
				
				.AddChild(new GUISlider(
				"slider",
				new Transform(
						new Vector3f(0f,0f,.01f),
						new Vector3f(0f),
						new Vector3f(.20f,.02f,1f)
						),
				"Images/blankTexture.png", // Texture of the hud
				new Vector4f(.85f, .85f,.85f,.9f),0f,50f){

					@Override
					protected void OnValueChanged(float value) {
						((GUIText)this.parent.GetChild("sliderValueText")).SetText(""+Math.round(value));
					}
			
					
					}.AddChild(new GUISliderBar(
						"sliderBar",
						new Transform(
								new Vector3f(0f,0f,.01f),
								new Vector3f(0f),
								new Vector3f(.021f,.0175f,1f)
								),
						"Images/blankTexture.png", // Texture of the hud
						new Vector4f(.35f, .35f,.35f,1f),.5f,false))
						
						
				).AddChild(new GUIText(
						"sliderValueText",
						new Transform(new Vector3f(0f,-.05f,.01f)),
						"Fonts/BebasNeue",
						"0.00",
						new Vector4f(1f),
						.2f,
						1f,
						true
						))
				.AddChild(new GUIButton(
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
								AudioManager.CreateAudioSource("yeet", "Audio/yeet-sound-effect.wav", "sfx",.15f, 1f, false, true);
								AudioManager.PlaySource("yeet");
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
	
	private void SetupDealer(Dealer d) {
		// Dealer
		Hand hand = d.getHand();

		// Create dealer cards mesh from hand
		for (int i = 0; i < hand.GetCardCount(); i++) {
			new Actor("cardDealer"+i).AddComponent(new CardMesh("Mesh", 
					new Transform( // Card Transform
							new Vector3f(.25f-(.25f*i), 5f-(.01f*i), 1f+(.01f*i)), // Position
							new Vector3f(0f, 0f, 0f), // Rotation
							new Vector3f(1.f, 1.f, 1f) ), // Scale
					i == 0 ? "card_back_red" : hand.getCard(i).getCardTextureID(), // Card front Suit
					"card_back_red", // Card back Suit
					this.cam.GetCamera()));// Camera))
		}
		
		// Create dealer UI
		new Actor("dealerUI").AddComponent(
				new GUIQuad("quad", new Transform(new Vector3f(0,.8f,1f), new Vector3f(0f), new Vector3f(.125f,.125f, 1f)), "Images/roundedTexture.png", 
						new Vector4f(0f,0f,0f,.5f))
				.AddChild(new GUIText(
						"name",
						new Transform(new Vector3f(0f,.075f,.01f)),
						"Fonts/morningStar",
						"Dealer",
						new Vector4f(1f),
						.125f,
						1f,
						true)).AddChild(
						new GUIText(
						"text",
						new Transform(new Vector3f(0f,-.035f,.01f)),
						"Fonts/BebasNeue",
						"Hand Total: "+ hand.getTotalDealer() + (hand.getTotalDealer() >21 ? "\nBust..." : hand.getTotal() == 21 ? "\nBlack Jack!" : "\nWaiting..."),
						new Vector4f(1f),
						.125f,
						1f,
						true))
				);

	}
	
	private void SetupPlayer(Player p, int location) {
		Hand hand = p.getHand();
		int balance = p.getBalance();
		int bet = hand != null ? hand.getBet() : -1;
	
		switch (location) {
		case 0: {
			// Add player cards in correct position (creates meshes)
			for (int i = 0; i < hand.GetCardCount(); i ++) {
				new Actor("cardPlayer_"+i).AddComponent(new CardMesh("Mesh", 
						new Transform( // Card Transform
								new Vector3f(-.125f+(i*.25f), -.75f-(i*.01f), 1.f+(i*.01f)), // Position
								new Vector3f(0f, 0, 0), // Rotation
								new Vector3f(1f, 1f, 1f) ), // Scale
						hand.getCard(i).getCardTextureID(), // Card front Suit
						"card_back_red", // Card back Suit
						this.cam.GetCamera()));// Camera))
			}
			break;
		}
		case 1: {
			for (int i = 0; i < hand.GetCardCount(); i++) {
				new Actor("cardPlayer2_"+i).AddComponent(new CardMesh("Mesh", 
						new Transform( // Card Transform
								new Vector3f(-5.5f+(.25f*i), 0f+(-.21f*i), .99f+(.01f*i)), // Position
								new Vector3f(0, 0, -40f), // Rotation
								new Vector3f(1.f, 1.f, 1f) ), // Scale
						hand.getCard(i).getCardTextureID(), // Card front Suit
						"card_back_red", // Card back Suit
						this.cam.GetCamera()));// Camera))
			}
				
				new Actor("cpu1").AddComponent(
						// background image
						new GUIQuad("quad", new Transform(new Vector3f(-.825f,.15f,1f), new Vector3f(0f), new Vector3f(.125f,.175f, 1f)), "Images/roundedTexture.png", 
								new Vector4f(0f,0f,0f,.5f))
						.AddChild(
								// Text
								new GUIText(
								"name",
								new Transform(new Vector3f(0f,.12f,.01f)),
								"Fonts/morningStar",
								"Computer One",
								new Vector4f(1f),
								.125f,
								1f,
								true)).AddChild(
								new GUIText(
								"text",
								new Transform(new Vector3f(0f,-.04f,.01f)),
								"Fonts/BebasNeue",
								"Balance: $"+balance+"\nBet: "+bet+"\nHand Total: "+ hand.getTotal() + (hand.getTotal()>21 ? "\nBust..." : hand.getTotal() == 21 ? "\nBlack Jack!" : "\nWaiting..."),
								new Vector4f(1f),
								.125f,
								1f,
								true))
						);
				break;
			}
		
		
		case 2: {

			for (int i = 0; i < hand.GetCardCount(); i++) {
			
			new Actor("cardPlayer3_"+i).AddComponent(new CardMesh("Mesh", 
					new Transform( // Card Transform
							new Vector3f(5.5f-(.25f*i), 0f-(.21f*i), 1f+(.01f*i)), // Position
							new Vector3f(0f, 0, 40f), // Rotation
							new Vector3f(1.f, 1.f, 1f) ), // Scale
					hand.getCard(i).getCardTextureID(), // Card front Suit
					"card_back_red", // Card back Suit
					this.cam.GetCamera()));// Camera))
			}
			
			// Create GUI for player 2 
			new Actor("cpu2").AddComponent(
					// create background image
					new GUIQuad("quad", new Transform(new Vector3f(.825f,.15f,1f), new Vector3f(0f), new Vector3f(.125f,.175f, 1f)), "Images/roundedTexture.png", 
							new Vector4f(0f,0f,0f,.5f))
					.AddChild(
							// Create text
							new GUIText(
							"name",
							new Transform(new Vector3f(0f,.12f,.01f)),
							"Fonts/morningStar",
							"Computer Two",
							new Vector4f(1f),
							.125f,
							1f,
							true)).AddChild(
							new GUIText(
							"text",
							new Transform(new Vector3f(0f,-.04f,.01f)),
							"Fonts/BebasNeue",
							"Balance: $"+balance+"\nBet: "+bet+"\nHand Total: "+ hand.getTotal() + (hand.getTotal()>21 ? "\nBust..." : hand.getTotal() == 21 ? "\nBlack Jack!" : "\nWaiting..."),
							new Vector4f(1f),
							.125f,
							1f,
							true)));
			break;
		}
		}
	}

	@Override
	public void OnEvent(Event e) {
		/*
		 * Debug Create card at runtime
		 */
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
			
			// Disable/Enable shader
			if (((Events.KeyPressedEvent)e ).GetKeyCode() == KeyCodes.KEY_Z) {
				if (!scanLines) {
					WindowFrame.SetScreenShader(ShaderLib.Shader_GUIQuad);
					//WindowFrame.SetMeshShader(ShaderLib.Shader_GUIQuad_CRT_FishEye);
					scanLines = true;
				} else {
					WindowFrame.SetScreenShader(ShaderLib.Shader_GUIQuad_CRT_Outline);
					//WindowFrame.SetMeshShader(ShaderLib.Shader_GUIQuad_CRT_FishEye);
					scanLines = false;
				}
			}
				
			// Pause menu
			if (((Events.KeyPressedEvent)e ).GetKeyCode() == KeyCodes.KEY_ESCAPE) { 
				if (!GamePauseMenu.IsActive()) GamePauseMenu.Show(); else GamePauseMenu.Hide();
				Application.GetApp().SetPaused(!Application.IsPaused());
			}
		}
		
	}


	// Create static game objects
	private void CreateBackground() {

		Vector2f stackPosY = new Vector2f(7f, 7.75f);
		float stackPosX = -0.5f; 
		int amt = 30;
		
		// 
		new Actor("cardBorder1").AddComponent(new Mesh2DQuad("blackJackTitle",
				new Transform(new Vector3f(0,.5f,0f), new Vector3f(0,0f,0f), new Vector3f(1.25f,1.75f,1f)),
				"Images/cardBorder.png",
				new Vector4f(1f),
				this.cam.GetCamera()
				));
		
		

		new Actor("cardBorder2").AddComponent(new Mesh2DQuad("blackJackTitle",
				new Transform(new Vector3f(-5.5f,1.25f,0f), new Vector3f(0,0f,-40f), new Vector3f(1.25f,1.75f,1f)),
				"Images/cardBorder.png",
				new Vector4f(1f),
				this.cam.GetCamera()
				));
		
		new Actor("cardBorder3").AddComponent(new Mesh2DQuad("blackJackTitle",
				new Transform(new Vector3f(5.5f,1.25f,0f), new Vector3f(0,0f, 40f), new Vector3f(1.25f,1.75f,1f)),
				"Images/cardBorder.png",
				new Vector4f(1f),
				this.cam.GetCamera()
				));

		new Actor("headerText").AddComponent(new Mesh2DQuad("blackJackTitle",
				new Transform(new Vector3f(0,6.25f,-1.5f), new Vector3f(-15f,0f,0f), new Vector3f(6f,3,1f)),
				"Images/blackJackHeader.png",
				new Vector4f(1f),
				this.cam.GetCamera()
				));
		
		new Actor("headerInfoText").AddComponent(new Mesh2DQuad("blackJackTitle",
				new Transform(new Vector3f(0,4.75f,-1f), new Vector3f(-15f,0f,0f), new Vector3f(5.5f,2.5f,1f)),
				"Images/blackJackInfoHeader.png",
				new Vector4f(1f),
				this.cam.GetCamera()
				));
		
		new Actor("headerText2").AddComponent(new Mesh2DQuad("blackJackTitle",
				new Transform(new Vector3f(0,6.75f,-1.5f), new Vector3f(-15f,0f,0f), new Vector3f(2.5f,1.5f,1f)),
				"Images/menuLogoWhite.png",
				new Vector4f(1f),
				this.cam.GetCamera()
				));
		
		
		
		new Actor("border1").AddComponent(new Mesh2DBackground("background",
				new Transform(
						new Vector3f(0f,10.4f,-5f),  // Position
						new Vector3f(0f), // Rotation
						new Vector3f(6f, 3.25f, 1f)), // Scale
				"Images/wideBorder.png", // Texture
				new Vector4f(1f) , // Color
				new Vector2f(1f), // UV Scale
				this.cam.GetCamera() // Camera
				));
		
		
		new Actor("edge").AddComponent(new Mesh2DBackground("background",
				new Transform(
						new Vector3f(0f,2,-5f),  // Position
						new Vector3f(0f), // Rotation
						new Vector3f(14.25f, 5f, 1f)), // Scale
				"Images/tableEdge4.png", // Texture
				new Vector4f(1f) , // Color
				new Vector2f(.99f), // UV Scale
				this.cam.GetCamera() // Camera
				));
		
		new Actor("background").AddComponent(new Mesh2DBackground("background",
				new Transform(
						new Vector3f(0f,0,-50f),  // Position
						new Vector3f(0f), // Rotation
						new Vector3f(1000, 1000, 1f)), // Scale
				"Images/pokerFelt.png", // Texture
				new Vector4f(0,.75f,.2f,1) , // Color
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
					"card_back_red", "card_back_red", 52, new Vector3f(.05f,-0.0025f,.005f),  this.cam.GetCamera()
					));
		
		
		}
		
	}
	
}

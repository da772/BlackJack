package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.joml.Vector3f;
import org.joml.Vector4f;

import engine.Actor;
import engine.CameraController;
import engine.SceneManager;
import engine.audio.AudioManager;
import engine.renderer.Transform;
import engine.renderer.GUI.GUI;
import engine.renderer.GUI.GUIButton;
import engine.renderer.GUI.GUIQuad;
import engine.renderer.GUI.GUISlider;
import engine.renderer.GUI.GUISliderBar;
import engine.renderer.GUI.GUIText;
import engine.util.MathLib;
import engine.util.Timing;

public class Player {

	// make a static variablefor the # of players made sofar
	public static int CURRENT_ID = 0;

	// now each player needs id and balance
	private int id;
	private int balance;
	private int handSize = 0;
	private Hand hand;
	private Round round;
	private int result = -1;
	private int lastBet = 1;
	private double waitTimeInMS = 0;
	private ArrayList<Hand> splitHands;
	private Card nextCard = null;
	private boolean computer = true, handCreated = false, uiCreated = false, showBetControl = false, showButtonsControl = false, showResetButton = false, betting = false;

	// new simple constructor
	public Player(int startingBalance, boolean computer) {
		this.id = CURRENT_ID++;
		this.balance = startingBalance;
		this.hand = new Hand();
		this.splitHands = new ArrayList<Hand>();
		this.computer = computer;
	}
	
	public void reset() {
		this.hand = new Hand();
		this.round = null;
		this.splitHands = new ArrayList<Hand>();
		this.result = -1;
		this.updateHand();
		this.updateUI();
	}

	public Hand getHand() {
		return this.hand;
	}

	public int getBalance() {
		return this.balance;
	}

	public void addToBalance(int amount) {
		this.balance += amount;
		updateUI();
	}
	
	public int getBet() {
		return this.hand.getBet();
	}

	public void setBet(int bet) {
		this.hand.setBet(bet);
		this.lastBet = bet;
		updateUI();
	}
	
	public void doubleBet() {
		this.hand.doubleBet();
		this.balance -= this.hand.getBet()/2;
		updateUI();
	}
	
	
	public void SetResult(int result) {
		this.result = result;
		updateUI();
	}

	public void subtractFromBalance(int amount) {
		this.balance -= amount;
		updateUI();
	}

	public void clearBalance() {
		this.balance = 0;
		updateUI();
	}

	public void addCard(Card card, boolean f) {
		this.getHand().addCard(card);
		updateUI();
		updateHand();
	}
	
	public boolean addCard(Deck d, float deltaTime) {
		if (this.nextCard == null) {
			this.nextCard = d.drawCard();
		}
		Actor a = Actor.Get(this.toString()+"_"+nextCard.getCardTextureID());
		if (a != null) {
			CardMesh mesh = (CardMesh) a.GetComponent("cardMesh");
			if (mesh != null) {
				if (mesh.AnimateTo(getCardPos(this.id, this.getHand().GetCardCount()), deltaTime, 1.5f, .0f)) {
					this.getHand().addCard(nextCard);
					updateUI();
					updateHand();
					this.nextCard = null;
					Actor.Remove(a.GetName());
					return true;
				}
			}
		} else {
			CardMesh mesh = new CardMesh("cardMesh", new Transform(new Vector3f(4.25f,0,6.25f), new Vector3f(0.f, 180.f,180f), new Vector3f(1f))
					, nextCard.getCardTextureID(), "card_back_red", SceneManager.GetCurrentScene().GetCameraController().GetCamera());
			new Actor(this.toString()+"_"+nextCard.getCardTextureID()).AddComponent(mesh);
			AudioManager.CreateAudioSource(this.toString()+"_"+nextCard.getCardTextureID()+"_place", "Audio/cardPlace3.wav", "sfx", 1.f, 1.f, false, true);
			AudioManager.PlaySource(this.toString()+"_"+nextCard.getCardTextureID()+"_place");
			return false;
		}
		
		return false;
	}

	public boolean isComputer() {
		return this.computer;
	}

	public void setIsComputer(boolean computer) {
		this.computer = computer;
	}
	
	public void setup() {
		createUI();
		createHand();
	}

	public void createUI() {
		if (this.uiCreated) {
			destroyUI();
			return;
		}
		if (this.isComputer()) {
			createComputerUI();
		} else {
			createPlayerUI();
		}

	}

	public void destroyUI() {
		if (this.uiCreated) {
			Actor.Remove("player" + id + "ui");
			this.uiCreated = false;
		}
	}

	public void updateUI() {
		if (this.uiCreated) {
			if ( this.isComputer()) {
			((GUIQuad) Actor.Get("player" + id + "ui").GetComponent("quad")).RemoveChild("text")
					.AddChild(new GUIText("text", new Transform(new Vector3f(0f, -.04f, .01f)), "Fonts/BebasNeue",
							GenerateComputerText(), new Vector4f(1f), .125f, 1f, true));
			} else {
				((GUIQuad) Actor.Get("player" + id + "ui").GetComponent("quad")).RemoveChild("text")
				.AddChild(GeneratePlayerText());
			}
		}
	}

	public void createPlayerUI() {
		if (!this.uiCreated) {
			new Actor("player" + id + "ui").AddComponent(new GUIQuad(
					"quad",
					new Transform(
							new Vector3f(0f,-.775f,3f),
							new Vector3f(0f),
							new Vector3f(.25f,.225f,1f)
							),
					"Images/blankTexture.png", // Texture of the hud
					new Vector4f(0f, 0f, 0f, .5f)).AddChild(GeneratePlayerText()));
			this.uiCreated = true;
		}
	}


	
	public void showPlayerButtonControl(Round r) {
		this.round = r;
		Player realPlayer = this.round.getRealPlayer();
		Hand curHand = realPlayer.getHand();
		
		if (this.uiCreated && !this.showButtonsControl) {
			this.showButtonsControl = true;
			this.SetResult(0);
			GUIQuad buttons = new GUIQuad("buttons",
					new Transform(new Vector3f(0f,-.125f,.1f), new Vector3f(0f), new Vector3f(.25f, .075f, 1f)),
					"Images/blankTexture.png",
					new Vector4f(0f)
				);
			((GUI) Actor.Get("player" + id + "ui").GetComponent("quad")).AddChild(buttons);
					if(curHand.canHit()) {
						buttons.AddChild(new GUIButton("hitButton", new Transform(new Vector3f(-.15f, 0f, .1f), // Position x,y,
								new Vector3f(0f, 0f, 0f), // Rotation
								new Vector3f(.05f, .1f, 1f)), // Scale x,y,z
								"Images/Buttons/mainMenuButtonUp.png", // Button texture
								"Images/Buttons/mainMenuButtonDown.png", // Button pressed texture
								ColorPalette.HotPink // Quad Color r,g,b,a
	
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
								round.setCurrentPlayerInput(0);
							}
	
							@Override
							public void OnDeselect() {
								SetButtonTexture(false);
	
							}
						}.AddChild(new GUIText("buttonText", new Transform(new Vector3f(0f, 0f, .01f)), "Fonts/morningStar",
								"Hit", new Vector4f(1f), .125f, 1f, true)));
					}
					
					if(curHand.canDouble() && hand.getBet() <= balance) {
						buttons.AddChild(new GUIButton("doubleButton", new Transform(new Vector3f(-.05f, 0f, .1f), // Position x,y,
										new Vector3f(0f, 0f, 0f), // Rotation
										new Vector3f(.05f, .1f, 1f)), // Scale x,y,z
										"Images/Buttons/mainMenuButtonUp.png", // Button texture
										"Images/Buttons/mainMenuButtonDown.png", // Button pressed texture
										ColorPalette.HotPink // Quad Color r,g,b,a
	
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
										round.setCurrentPlayerInput(2);
									}
	
									@Override
									public void OnDeselect() {
										SetButtonTexture(false);
	
									}
								}.AddChild(new GUIText("buttonText", new Transform(new Vector3f(0f, 0f, .01f)), "Fonts/morningStar",
										"Double", new Vector4f(1f), .125f, 1f, true))
								);
					}
					
					if(curHand.canSplit()) {
						buttons.AddChild(new GUIButton("splitButton", new Transform(new Vector3f(.05f, 0f, .1f), // Position x,y,
								new Vector3f(0f, 0f, 0f), // Rotation
								new Vector3f(.05f, .1f, 1f)), // Scale x,y,z
								"Images/Buttons/mainMenuButtonUp.png", // Button texture
								"Images/Buttons/mainMenuButtonDown.png", // Button pressed texture
								ColorPalette.HotPink // Quad Color r,g,b,a
	
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
								round.setCurrentPlayerInput(3);
							}
	
							@Override
							public void OnDeselect() {
								SetButtonTexture(false);
	
							}
						}.AddChild(new GUIText("buttonText", new Transform(new Vector3f(0f, 0f, .01f)), "Fonts/morningStar",
								"Split", new Vector4f(1f), .125f, 1f, true)));
					}
					
					if(curHand.canStay()) {
						buttons.AddChild(new GUIButton("stayButton", new Transform(new Vector3f(.15f, 0f, .1f), // Position x,y,
								new Vector3f(0f, 0f, 0f), // Rotation
								new Vector3f(.05f, .1f, 1f)), // Scale x,y,z
								"Images/Buttons/mainMenuButtonUp.png", // Button texture
								"Images/Buttons/mainMenuButtonDown.png", // Button pressed texture
								ColorPalette.HotPink // Quad Color r,g,b,a
	
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
								round.setCurrentPlayerInput(1);
							}
	
							@Override
							public void OnDeselect() {
								SetButtonTexture(false);
	
							}
						}.AddChild(new GUIText("buttonText", new Transform(new Vector3f(0f, 0f, .01f)), "Fonts/morningStar",
								"Stay", new Vector4f(1f), .125f, 1f, true))
						
						);
					}
					
					List<GUI> children = buttons.GetChildren();
					int size = children.size();
					if (size < 4) {
						for (int i = 0; i < size; i++) {
							children.get(i).SetRelativePosition(ButtonLayout(size)[i]);
						}
					}
					
		}
		
	}
	
	private Vector3f[] ButtonLayout(int size) {
		switch (size) {
			default:
			case 0:
			return null;
			case 1:
				return new Vector3f[] {
						new Vector3f(0f)
				};
			case 2:
				return new Vector3f[] {
					new Vector3f(-.05f, 0f, 0f),
					new Vector3f(.05f, 0f, 0f)
				};
			case 3:
				return new Vector3f[] {
					new Vector3f(-.1f, 0f, 0f),
					new Vector3f(.0f, 0f, 0f),
					new Vector3f(.1f, 0f, 0f)
				};
		}
	}
	
	public void hidePlayerButtonControl() {
		if (this.uiCreated && this.showButtonsControl) {
			this.showButtonsControl = false;
			((GUI) Actor.Get("player" + id + "ui").GetComponent("quad")).RemoveChild("buttons");
		}
	}
	
	
	public void showPlayerResetButtonControl(Round r) {
		this.round = r; 
		 
		if (this.uiCreated && !this.showResetButton) {
			this.showResetButton = true;
			
			((GUI) Actor.Get("player" + id + "ui").GetComponent("quad"))
			.AddChild(new GUIButton("ResetControl", new Transform(new Vector3f(0, -.125f, .1f), // Position x,y,
					new Vector3f(0f, 0f, 0f), // Rotation
					new Vector3f(.2f, .075f, 1f)), // Scale x,y,z
					"Images/Buttons/mainMenuButtonUp.png", // Button texture
					"Images/Buttons/mainMenuButtonDown.png", // Button pressed texture
					ColorPalette.HotPink // Quad Color r,g,b,a

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
					round.setCurrentPlayerInput(0);
				}

				@Override
				public void OnDeselect() {
					SetButtonTexture(false);

				}
			}.AddChild(new GUIText("buttonText", new Transform(new Vector3f(0f, 0f, .01f)), "Fonts/morningStar",
					"Continue", new Vector4f(1f), .125f, 1f, true)));
			
			
			
		}
	}
	
	public void hidePlayerResetButtonControl() {
		if (this.uiCreated && this.showResetButton) {
			this.showResetButton = false;
			((GUI) Actor.Get("player" + id + "ui").GetComponent("quad")).RemoveChild("ResetControl");
		}
	}
	
	public void computerSetBet(Round r, double firstCallTimeinMS) {
		
		// Make computer wait until they place their bet
		//
		if (!betting) {
			waitTimeInMS = new Random().nextInt(5)*100;	
			this.betting = true;
			this.updateUI();
		}
		
		if (Timing.getTimeMS() - firstCallTimeinMS >= waitTimeInMS) {
			//setBet(playerTurn, new Random().nextInt(players.get(playerTurn).getBalance())/4+1);	
			int bet = new Random().nextInt(getBalance())/4+1;
			r.setCurrentPlayerInput(bet);
			this.betting = false;
		}
	}
	
	public void computerPlayTurn(Round r, double firstCallTimeinMS) {
		
		if (!betting) {
			waitTimeInMS = new Random().nextInt(5)*100;
			this.betting = true;
			this.updateUI();
		}
		
		if (Timing.getTimeMS() - firstCallTimeinMS >= waitTimeInMS) {
			int play = ComputerHitLogic.getChoice(getHand().getTotal(), r.getDealer().getHand().getTotalDealer());
			r.setCurrentPlayerInput(play);
			waitTimeInMS = 0;
			this.betting = false;
		}
	
		
	}
	
	public void showPlayerBetControl(Round r) {
		this.round = r;
		if (this.uiCreated && !this.showBetControl) {
			this.showBetControl = true;
			((GUI) Actor.Get("player" + id + "ui").GetComponent("quad"))
					.AddChild(new GUIButton("BetControl", new Transform(new Vector3f(0, -.1f, .1f), // Position x,y,
							new Vector3f(0f, 0f, 0f), // Rotation
							new Vector3f(.2f, .1f, 1f)), // Scale x,y,z
							"Images/Buttons/mainMenuButtonUp.png", // Button texture
							"Images/Buttons/mainMenuButtonDown.png", // Button pressed texture
							ColorPalette.HotPink // Quad Color r,g,b,a

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
							if (this.GetChild("slider") != null) {
								int bet = Math.round(((GUISlider)this.GetChild("slider")).GetValue());
								round.setCurrentPlayerInput(bet);
							}
						}

						@Override
						public void OnDeselect() {
							SetButtonTexture(false);

						}
					}.AddChild(new GUIText("buttonText", new Transform(new Vector3f(0f, 0f, .01f)), "Fonts/morningStar",
							"Set Bet", new Vector4f(1f), .125f, 1f, true)).AddChild(
									new GUISlider(
									"slider",
									new Transform(
											new Vector3f(0f,.15f,.01f),
											new Vector3f(0f),
											new Vector3f(.20f,.02f,1f)
											),
									"Images/blankTexture.png", // Texture of the hud
									new Vector4f(.85f, .85f,.85f,.9f),1f, this.balance){

										@Override
										protected void OnValueChanged(float value) {
											((GUIText)this.parent.GetChild("sliderValueText")).SetText("Bet: $"+Math.round(value));
										}
										}.AddChild(new GUISliderBar(
											"sliderBar",
											new Transform(
													new Vector3f(0f,0f,.01f),
													new Vector3f(0f),
													new Vector3f(.021f,.0175f,1f)
													),
											"Images/blankTexture.png", // Texture of the hud
											new Vector4f(.35f, .35f,.35f,1f), this.lastBet <= this.balance ?  MathLib.GetMappedRangeValue(1f, this.balance, 0, 1f, this.lastBet) : 0f,false))
											
											
									).AddChild(new GUIText(
											"sliderValueText",
											new Transform(new Vector3f(0f, .215f,.01f)),
											"Fonts/BebasNeue",
											"Bet: $0",
											new Vector4f(1f),
											.2f,
											1f,
											true
											)));
		}
	}

	public void hidePlayerBetControl() {
		if (this.showBetControl) {
			((GUI) Actor.Get("player" + id + "ui").GetComponent("quad")).RemoveChild("BetControl");
			this.showBetControl = false;
		}
	}

	public void createComputerUI() {
		switch (id) {
		case 1: {
			new Actor("player" + id + "ui").AddComponent(
					// background image
					new GUIQuad("quad",
							new Transform(new Vector3f(-.825f, .15f, 1f), new Vector3f(0f),
									new Vector3f(.125f, .175f, 1f)),
							"Images/roundedTexture.png", new Vector4f(0f, 0f, 0f, .5f)).AddChild(
									// Text
									new GUIText("name", new Transform(new Vector3f(0f, .12f, .01f)),
											"Fonts/morningStar", "Computer One", new Vector4f(1f), .125f, 1f, true))
									.AddChild(new GUIText("text", new Transform(new Vector3f(0f, -.04f, .01f)),
											"Fonts/BebasNeue", GenerateComputerText(), new Vector4f(1f), .125f, 1f,
											true)));
			this.uiCreated = true;
			break;
		}
		case 2: {
			new Actor("player" + id + "ui").AddComponent(
					// create background image
					new GUIQuad("quad",
							new Transform(new Vector3f(.825f, .15f, 1f), new Vector3f(0f),
									new Vector3f(.125f, .175f, 1f)),
							"Images/roundedTexture.png", new Vector4f(0f, 0f, 0f, .5f)).AddChild(
									// Create text
									new GUIText("name", new Transform(new Vector3f(0f, .12f, .01f)),
											"Fonts/morningStar", "Computer Two", new Vector4f(1f), .125f, 1f, true))
									.AddChild(new GUIText("text", new Transform(new Vector3f(0f, -.04f, .01f)),
											"Fonts/BebasNeue", GenerateComputerText(), new Vector4f(1f), .125f, 1f,
											true)));
			this.uiCreated = true;
			break;
		}
		}
	}

	public void createHand() {
		if (this.handCreated) {
			this.destroyHand();
			return;
		}
		
		CameraController cam = SceneManager.GetCurrentScene().GetCameraController();
		this.handSize = hand.GetCardCount();
		switch (id) {
		case 0: {
			// Add player cards in correct position (creates meshes)
			for (int i = 0; i < hand.GetCardCount(); i++) {
				new Actor("cardPlayer" + id + "_" + i).AddComponent(new CardMesh("Mesh", new Transform( // Card																			
						new Vector3f(-.125f + (i * .25f), -.75f - (i * .01f), 1.f + (i * .01f)), // Position
						new Vector3f(0f, 0, 0), // Rotation
						new Vector3f(1f, 1f, 1f)), // Scale
						hand.getCard(i).getCardTextureID(), // Cardfront Suit
						"card_back_red", // Card back Suit
						cam.GetCamera())); // Camera))
			}
			handCreated = true;
			break;
		}
		case 1: {
			for (int i = 0; i < hand.GetCardCount(); i++) {
				new Actor("cardPlayer" + id + "_" + i).AddComponent(new CardMesh("Mesh", new Transform( // Card
																										// Transform
						new Vector3f(-5.5f + (.25f * i), 0f + (-.21f * i), .99f + (.01f * i)), // Position
						new Vector3f(0, 0, -40f), // Rotation
						new Vector3f(1.f, 1.f, 1f)), // Scale
						hand.getCard(i).getCardTextureID(), // Cardfront Suit
						"card_back_red", // Card back Suit
						cam.GetCamera())); // Camera))
			}
			handCreated = true;
			break;
		}
		case 2: {
			for (int i = 0; i < hand.GetCardCount(); i++) {
				new Actor("cardPlayer" + id + "_" + i).AddComponent(new CardMesh("Mesh", // Card
						new Transform(// Transform
						new Vector3f(5.5f - (.25f * i), 0f - (.21f * i), 1f + (.01f * i)), // Position
						new Vector3f(0f, 0, 40f), // Rotation
						new Vector3f(1.f, 1.f, 1f)), // Scale
						hand.getCard(i).getCardTextureID(), // Cardfront Suit
						"card_back_red", // Card back Suit
						cam.GetCamera())); // Camera))
			}
			handCreated = true;
			break;
		}
		}
		
	}
	
	public Transform getCardPos(int _id, int i) {
		switch (_id) {
		case 0: {
			// Add player cards in correct position (creates meshes)
			return new Transform( // Card																			
					new Vector3f(-.125f + (i * .25f), -.75f - (i * .01f), 1.f + (i * .01f)), // Position
					//Flipped for some reason idk why ???
					new Vector3f(0f, 0, 180f), // Rotation
					new Vector3f(1f, 1f, 1f));
		}
		case 1: {
			return new Transform( 
			new Vector3f(-5.5f + (.25f * i), 0f + (-.21f * i), .99f + (.01f * i)), // Position
			// Flipped for some reason idk why ???
			new Vector3f(0, 0, -40f+180f), // Rotation
			new Vector3f(1.f, 1.f, 1f));
		}
		case 2: {
			return new Transform(// Transform
					new Vector3f(5.5f - (.25f * i), 0f - (.21f * i), 1f + (.01f * i)), // Position
					//Flipped for some reason idk why ???
					new Vector3f(0f, 0, 40f+180f), // Rotation
					new Vector3f(1.f, 1.f, 1f));
		}
		}
		
		return null;
		
	}

	private void destroyHand() {
		if (this.handCreated) {
			for (int i = 0; i < this.handSize; i++) {
				Actor.Remove("cardPlayer" + id + "_" + i);
			}
			this.handCreated = false;
		}
	}

	public void updateHand() {
		if (this.handCreated) {
			destroyHand();
			createHand();
		}
	}

	private String GenerateComputerText() {
		int balance = getBalance();
		int bet = hand != null ? hand.getBet() : -1;
		if (bet != 0) {
			return "Balance: $" + balance + "\nBet: $" + bet + "\nHand Total: " + hand.getTotal()
					+  "\n" + getResultText();
		}
		if (betting) {
			return "Balance: $" + balance + "\n Placing Bet...";
		}
		return "Balance: $" + balance;

	}
	
	private String getResultText() {
		if (betting) {
			return "Playing...";
		}
		switch (this.result) {
			default:
				return "Waiting...";
			case 0: 
				return "Player's Turn";
			case 1:
				return "Winner!";
			case 2:
				return "Loser!";
			case 3:
				return "Tie!";
		}
	}
	
	private GUIText GeneratePlayerText() {
		int bet = hand != null ? hand.getBet() : -1;
		if (bet != 0) {
			return new GUIText("text", new Transform(new Vector3f(0f, .075f, .01f)), "Fonts/BebasNeue",
					GenerateComputerText(), new Vector4f(1f), .125f, 1f, true);
		}
		return new GUIText("text", new Transform(new Vector3f(0f, .175f, .01f)), "Fonts/BebasNeue",
				GenerateComputerText(), new Vector4f(1f), .125f, 1f, true);
	}

	public static void main(String[] args) {

		int DEFAULT_START_BALANCE = 100;

		System.out.println("Making a dealer");
		Player dealer = new Player(DEFAULT_START_BALANCE, true);
		// System.out.println("New player made. Current player type is: " +
		// dealer.getPlayerType() + " Balance is: " + dealer.getBalance());
		dealer.addToBalance(50000);
		System.out.println("Adding 50000 to balance. Balance is: " + dealer.getBalance());
		dealer.subtractFromBalance(50000);
		System.out.println("Subtract 50000 to balance. Balance is: " + dealer.getBalance());

		System.out.println("\n\nMaking a player");
		Player player1 = new Player(DEFAULT_START_BALANCE, false);
		// System.out.println("New player made. Current player type is: " +
		// player1.getPlayerType() + " Balance is: " + player1.getBalance());
		player1.clearBalance();
		System.out.println("Clearing balance. Balance is: " + player1.getBalance());

	}

}
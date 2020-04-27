// 1) Keep track of round #
// 2) Deals cards to player
// 3) Ask player actions
// 4) Calculate values of hands &
package application;

import java.util.ArrayList;

import org.joml.Vector3f;
import org.joml.Vector4f;

import engine.Actor;
import engine.SceneManager;
import engine.renderer.Transform;
import engine.renderer.GUI.GUIButton;
import engine.renderer.GUI.GUIText;
import engine.util.Timing;

public class Round {
	
	private ArrayList<Player> players;
	private Deck deck;
	private RoundState state = RoundState.None;
	private int playerTurn = -1;
	private int playerInput = -1;
	private Dealer dealer;
	private Table table;
	private double lastTime = -1;
	float dt;
	
	public static enum RoundState {
		None, Betting, Dealing, Playing, Ending, Finished;
	}
    
    public Round(Dealer dealer, Table table) { //Everything that happens in 1 round of blackjack
    	deck = table.getDeck(); //Get deck
    	players = table.getPlayers(); //players in the order they are sitting at a table
    	this.dealer = dealer;
    	this.table = table;
    	deck.shuffle(); //Shuffle deck
    	deck.checkDeckCount(); //If deck has 20% of cards or less left, add back used cards and shuffle
    	
    	playerTurn = 0;
    	state = RoundState.Betting;
    	
    }
    
    /**
     * Runs every frame, unless game is paused. Acts as a state machine
     */
    public void OnUpdate(float deltaTime) {
    	dt = deltaTime;
    	// State Machine
    	switch (state) {
    	// Betting Phase
		case Betting: {
			// Check if all players have played, skips dealer since dealer doesn't bet?
			if (playerTurn < players.size()) {
				// Check if player is not computer
				if (!players.get(playerTurn).isComputer()) {
					// Show player betting controls
					players.get(playerTurn).showPlayerBetControl(this);
					// Wait for input
					if (playerInput != -1) {
						// Set bet based on input
						setBet(playerTurn, playerInput);
						// Hide betting controls
						players.get(playerTurn).hidePlayerBetControl();
						// Reset input
						playerInput = -1;
						// Increment player
						playerTurn++;
					}
				// Player is computer
				} else {
					// Check if computer has enough money to bet
					if (players.get(playerTurn).getBalance() > 0) {
						// If they do give them random bet, maybe we should give them behaviors instead of random? for now this works
						// or maybe even a higher chance to bet lower than higher?
						if (lastTime <= 0) {
							lastTime = Timing.getTimeMS();
						}
						players.get(playerTurn).computerSetBet(this, lastTime);
						
						if (playerInput != -1) {
							setBet(playerTurn, playerInput);
							// Reset last Time
							lastTime = -1;
							// Reset player input
							playerInput = -1;
							// Increment player
							playerTurn++;
						}
						
						//setBet(playerTurn, new Random().nextInt(players.get(playerTurn).getBalance())/4+1);	
					} else {
						// if computer has no money set their bet to 0
						setBet(playerTurn, 0);
						// Reset player input
						playerInput = -1;
						// Increment player
						playerTurn++;
					}
					
				}
				
			// All players have bet
			} else {
				// Change state to playing state
				state = RoundState.Dealing;	
				playerTurn = 0;
				playerInput = -1;
			}
			break;
		}
		case Dealing: {
			if (playerTurn < players.size()) {
				Player curPlayer = this.players.get(playerTurn);
				if (curPlayer.addCard(deck, deltaTime)) {
					playerTurn++;
				}
			// Dealer
			} else if (playerTurn == players.size()) {
				if (dealer.addCard(deck, deltaTime)) {
					if (dealer.getHand().GetCardCount() == 2) {
						playerTurn++;	
					} else {
						playerTurn = 0;
					}	
				}
			} else {
				state = RoundState.Playing;
				if(dealer.getHand().getTotal() == 21) {//dealer has 21 as init hand, immediately go to end state
					state = RoundState.Ending;
				}
				// Reset player increment
				playerTurn = 0;
				playerInput = -1;
			}
			break;
		}
		// Playing Phase
		case Playing: {	
			// Check if all players have played
			if (playerTurn < players.size()) {
				// Get current player object
				Player curPlayer = this.players.get(playerTurn);
				// Get current player hand
		    	Hand curPlayerHand = curPlayer.getHand();
		    	// If the player's bet is less than or equal to 0 skip that player
		    	if (curPlayerHand.getBet() <= 0) {
		    		playerTurn++;
		    		return;
		    	}
				// User's turn
				if (!players.get(playerTurn).isComputer()) {
					if (curPlayerHand.getTotal() < 21) {
						// Give Options
						
						if (playerInput != -1) {
							switch (playerInput) {
								// Hit
								case 0: {
									if (curPlayer.addCard(deck, deltaTime)) {
										playerInput = -1;
									};
									break;
								}
								// Stay
								default:
								case 1: {
									playerTurn++;
									curPlayer.SetResult(-1);
									playerInput = -1;
									break;
								}
								// Double
								case 2 : {
									if (curPlayer.addCard(deck, deltaTime)) {
										curPlayer.doubleBet();
										playerInput = -1;
										curPlayer.SetResult(-1);
										playerTurn++;
									};
									break;
								}
								// Split (NOT IMPLEMENTED YET)
								case 3: {
									if (curPlayer.addCard(deck, deltaTime)) {
										playerInput = -1;
									};
									break;
								}
							
							}
							curPlayer.hidePlayerButtonControl();
						} else {
							curPlayer.showPlayerButtonControl(this);
						}
						
					} else {
						// Reset player input
						playerInput = -1;
						curPlayer.SetResult(-1);
						// Increment player
						playerTurn++;
					}
				/**
				 * Computer Turn
				 */
				} else {
					if (curPlayerHand.getTotal() < 21) {
						// Computer logic here
						if (lastTime <= 0) {
							lastTime = Timing.getTimeMS();
						}
						curPlayer.computerPlayTurn(this, lastTime);
						if (playerInput != -1) {
							switch (playerInput) {
								// Hit
								case 0: {
									if (curPlayer.addCard(deck, deltaTime)) {
										playerInput = -1;
										playerInput = -1;
										lastTime = -1;
									};
									break;
								}
								// Stay
								default:
								case 1: {
									playerTurn++;
									curPlayer.SetResult(-1);
									playerInput = -1;
									lastTime = -1;
									break;
								}
								// Double
								case 2 : {
									if (curPlayer.addCard(deck, deltaTime)) {
										if (curPlayer.getBalance() - curPlayer.getBet() >= 0) {
											curPlayer.doubleBet();
											curPlayer.SetResult(-1);
										} 
										playerTurn++;
										playerInput = -1;
										lastTime = -1;
									}
									break;
								}
								// Split (NOT IMPLEMENTED YET)
								case 3: {
									if (curPlayer.addCard(deck, deltaTime)) {
										playerInput = -1;
										lastTime = -1;
									};
									break;
								}
							
							}
							
						}
						// Reset input
						
					} else {
						playerInput = -1;
						lastTime = -1;
						playerTurn++;
					}
				}
			/**
			 *  Dealer's turn
			 */
			} else if (playerTurn == players.size()){
				
				if (dealer.getHand().getTotal() < 21) {
					// Computer logic here
					if (lastTime <= 0) {
						lastTime = Timing.getTimeMS();
					}
					dealer.computerPlayTurn(this, lastTime);
					if (playerInput != -1) {
						switch (playerInput) {
							// Hit
							case 0: {
								if (dealer.addCard(deck, deltaTime)) {
									playerInput = -1;
									playerInput = -1;
									lastTime = -1;
								};
								break;
							}
							// Stay
							default:
							case 1: {
								playerTurn++;
								playerInput = -1;
								lastTime = -1;
								break;
							}
						
						}
						
					}
					// Reset input
					
				} else {
					playerInput = -1;
					lastTime = -1;
					playerTurn++;
				}
			} else {
				// Done incrementing through players, move to ending state
				state = RoundState.Ending;
				// Reset player increment
				playerTurn = 0;
				// Reset player input
				playerInput = -1;
			}
				break;
			}		
		// Ending phase (determine winners/losers)
		case Ending:
			if (!this.dealer.showDealerCard(true, deltaTime)) {
				break;
			}
			if (playerTurn < this.players.size()) {
				Player curPlayer = players.get(playerTurn);
				// Player lost
				if (curPlayer.getHand().getTotal() > 21 || (curPlayer.getHand().getTotal() < dealer.getHand().getTotal() && dealer.getHand().getTotal() <= 21)) {
					dealer.addToBalance(curPlayer.getBet());
					curPlayer.SetResult(2);
				// Player tied with dealer	
				} else if (curPlayer.getHand().getTotal() == dealer.getHand().getTotal()) {
					curPlayer.SetResult(3);
					curPlayer.addToBalance(curPlayer.getBet());
				} 
				// Player won with BlackJack pay 3 to 2 
				else if (curPlayer.getHand().getTotal() == 21 && curPlayer.getHand().GetCardCount() == 2) { 
					curPlayer.SetResult(1);
					curPlayer.addToBalance((int) (curPlayer.getBet()*1.5f));
					dealer.subtractFromBalance((int) (curPlayer.getBet()*.5f));
				}
				// Player won
				else if (curPlayer.getHand().getTotal() > dealer.getHand().getTotal() || dealer.getHand().getTotal() > 21) {
					curPlayer.SetResult(1);
					curPlayer.addToBalance((int) (curPlayer.getBet()*2));
					dealer.subtractFromBalance((int)(curPlayer.getBet()));
				} 
				
				if (!curPlayer.isComputer() && curPlayer.getBalance() <= 0) {
					playerInput = -2;
				}
				playerTurn++;
			} else {
				playerTurn = 0;
				if (playerInput != -2)
					playerInput = -1;
				state = RoundState.None;
			}
			break;
		case None: {
			if (dealer.getBalance() <= 0 || playerInput == -2) {
				state = RoundState.Finished;
			}
			else if (playerTurn < this.players.size()) {
				Player curPlayer = players.get(playerTurn);
				// Find first none computer player
				if (!curPlayer.isComputer()) {
					// Show reset button
					curPlayer.showPlayerResetButtonControl(this);
					// If player input create new round
					if (playerInput != -1) {
						curPlayer.hidePlayerResetButtonControl();
						this.table.playRound();
						break;
					}
				} else {
					// Increment
					playerTurn++;
				}
			}
			break;
		}
		case Finished: {
			// Player is out of money
			if (playerInput == -2 ) {
				if (Actor.Get("gOver") == null) {
					new Actor("gOver").AddComponent(new GUIText("myGUI", // Identifying string
					        new Transform( // Transform (position, rotation, scale)
							        new Vector3f(0f,.3f,1f),// Position -1 to 1, -1 being far left of screen 1 being far right 
							                               // and 0 being center, the Z position indicates the order it should be drawn, higher on top
							        new Vector3f(0f,0f,0f), // Rotation
							        new Vector3f(1f,1f,1f) // Scale, based on percentage of screen, ex 1 = entire screen, .5 = half of screen
								),
							        "Fonts/morningStar", // Name and location of font to use
							        "You lose", // String to draw
							        ColorPalette.DraculaOrchid,  // Color of the quad (red, green, blue, alpha)
							         1.f, // Max Width of each line, 1 is entire screen .5 is half etc.
							         3f, // Font size
							         true
								)).AddComponent((new GUIButton("BetControl", new Transform(new Vector3f(0, -.0f, .1f), // Position x,y,
										new Vector3f(0f, 0f, 0f), // Rotation
										new Vector3f(.2f, .1f, 1f)), // Scale x,y,z
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
										SceneManager.SetCurrentScene("mainMenu");
									}

									@Override
									public void OnDeselect() {
										SetButtonTexture(false);

									}
								}.AddChild(new GUIText("buttonText", new Transform(new Vector3f(0f, 0f, .01f)), "Fonts/morningStar",
										"Quit Game", new Vector4f(1f), .125f, 1f, true))));
				} 	
			}
			// Dealer is out of money
			else {
				if (Actor.Get("gOver") == null) {
					int winner = 0;
					for (int i = 1; i < players.size(); i++) {
						if (players.get(i).getBalance() > players.get(winner).getBalance()) {
							winner = i;
						}
					}
					String w = !players.get(winner).isComputer() ? "You won" : "Computer " + winner + " wins";
					new Actor("gOver").AddComponent(new GUIText("myGUI", // Identifying string
					        new Transform( // Transform (position, rotation, scale)
							        new Vector3f(0f,.3f,1f),// Position -1 to 1, -1 being far left of screen 1 being far right 
							                               // and 0 being center, the Z position indicates the order it should be drawn, higher on top
							        new Vector3f(0f,0f,0f), // Rotation
							        new Vector3f(1f,1f,1f) // Scale, based on percentage of screen, ex 1 = entire screen, .5 = half of screen
								),
							        "Fonts/morningStar", // Name and location of font to use
							        w, // String to draw
							        ColorPalette.DraculaOrchid,  // Color of the quad (red, green, blue, alpha)
							         1.f, // Max Width of each line, 1 is entire screen .5 is half etc.
							         3f, // Font size
							         true
								)).AddComponent((new GUIButton("BetControl", new Transform(new Vector3f(0, -.0f, .1f), // Position x,y,
										new Vector3f(0f, 0f, 0f), // Rotation
										new Vector3f(.2f, .1f, 1f)), // Scale x,y,z
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
										SceneManager.SetCurrentScene("mainMenu");
									}

									@Override
									public void OnDeselect() {
										SetButtonTexture(false);

									}
								}.AddChild(new GUIText("buttonText", new Transform(new Vector3f(0f, 0f, .01f)), "Fonts/morningStar",
										"Quit Game", new Vector4f(1f), .125f, 1f, true))));
				}
			
			}
			
			break;
		}
		default:
			break;
    	
    	}
    	
    }
    
    public void setCurrentPlayerInput(int input) {
    	this.playerInput = input;
    }
    
    public void setBet(int id, int amount) {
    	Player curPlayer = this.players.get(id);
    	curPlayer.setBet(amount);
    	curPlayer.subtractFromBalance(amount);
    }
    
    public Dealer getDealer() {
    	return this.dealer;
    }
    
    public void dealCards(Dealer dealer, ArrayList<Player> players, Deck deck) {
    	//Deal cards out. Deal 1 card to dealer and to each player, then cycle again.
    	//Update GUI inbetween each card deal to reflect hand changes - maybe?? 
    	
    	
    	
    	dealer.addCard(deck.drawCard());
    	
        for(int i = 0; i < players.size(); i++) {
        	Player curPlayer = players.get(i);
        	if (curPlayer.getHand().getBet() > 0) {
        		curPlayer.addCard(deck.drawCard(), false);	
        	}
        }
        
        dealer.addCard(deck.drawCard());
        
        for(int i = 0; i < players.size(); i++) {
        	Player curPlayer = players.get(i);
        	if (curPlayer.getHand().getBet() > 0) {
        		curPlayer.addCard(deck.drawCard(), false);	
        	}
        }
        
    }
    
    public void endRound() {
    	this.state = RoundState.None;
    }
    
    public Player getRealPlayer() {
    	return this.players.get(0);
    }
    
    public void printAllHandTotals(Dealer dealer, ArrayList<Player> players) {
    	//test function
    	Hand dealerHand = dealer.getHand();
    	System.out.print("Dealer cards: ");
    	dealerHand.printHand();
    	System.out.print(" /// Total: " + dealerHand.getTotal());
    	
    	for(int i = 0; i < players.size(); i++) {
    		Player curPlayer = players.get(i);
    		Hand curPlayerHand = curPlayer.getHand();
    		System.out.print("\nPlayer" + i + " Hand: ");
    		curPlayerHand.printHand();
    		System.out.print(" /// Total: " + curPlayerHand.getTotal());
    	}
    	
    }
    
    

    
}
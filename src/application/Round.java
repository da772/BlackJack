// 1) Keep track of round #
// 2) Deals cards to player
// 3) Ask player actions
// 4) Calculate values of hands &
package application;

import java.util.ArrayList;
import java.util.Random;

public class Round {
	
	private ArrayList<Player> players;
	private Deck deck;
	private RoundState state = RoundState.None;
	private int playerTurn = -1;
	private int playerInput = -1;
	private Dealer dealer;
	private Table table;
	
	public static enum RoundState {
		None, Betting, Playing, Ending;
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
    public void OnUpdate() {
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
						setBet(playerTurn, new Random().nextInt(players.get(playerTurn).getBalance())/4+1);	
					} else {
						// if computer has no money set their bet to 0
						setBet(playerTurn, 0);
					}
					// Reset player input
					playerInput = -1;
					// Increment player
					playerTurn++;
				}
				
			// All players have bet
			} else {
				// Change state to playing state
				state = RoundState.Playing;
				// Deal cards
				this.dealCards(dealer, players, deck);
				// Reset player increment
				playerTurn = 0;
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
						curPlayer.showPlayerButtonControl(this);
						if (playerInput != -1) {
							switch (playerInput) {
								// Hit
								case 0: {
									curPlayer.addCard(deck.drawCard());
									break;
								}
								// Stay
								default:
								case 1: {
									playerTurn++;
									curPlayer.SetResult(-1);
									break;
								}
								// Double
								case 2 : {
									curPlayer.doubleBet();
									curPlayer.addCard(deck.drawCard());
									curPlayer.SetResult(-1);
									playerTurn++;
									break;
								}
								// Split (NOT IMPLEMENTED YET)
								case 3: {
									curPlayer.addCard(deck.drawCard());
									break;
								}
							
							}
							// Reset input
							playerInput = -1;
							curPlayer.hidePlayerButtonControl();
						}
						
					} else {
						// Reset player input
						playerInput = -1;
						curPlayer.SetResult(-1);
						// Increment player
						playerTurn++;
					}
				// Computer's turn
				} else {
					if (curPlayerHand.getTotal() < 21) {
						// Computer logic here
						curPlayer.addCard(deck.drawCard());
					} else {
						playerInput = -1;
						playerTurn++;
					}
				}
			} else if (playerTurn == players.size()){
				// Dealer's turn
				if (dealer.getHand().getTotal() < 17) {
					// Play dealers turn
					// Dealer logic here
					dealer.addCard(deck.drawCard());	
				} else {
					playerInput = -1;
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
			this.dealer.showDealerCard();
			if (playerTurn < this.players.size()) {
				Player curPlayer = players.get(playerTurn);
				// Player lost
				if (curPlayer.getHand().getTotal() > 21 || (curPlayer.getHand().getTotal() < dealer.getHand().getTotal() && dealer.getHand().getTotal() <= 21)) {
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
				}
				// Player won
				else if (curPlayer.getHand().getTotal() > dealer.getHand().getTotal() || dealer.getHand().getTotal() > 21) {
					curPlayer.SetResult(1);
					curPlayer.addToBalance((int) (curPlayer.getBet()*2));
				} 
				playerTurn++;
			} else {
				playerTurn = 0;
				playerInput = -1;
				state = RoundState.None;
			}
			break;
		case None: {
			if (playerTurn < this.players.size()) {
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
    
    public void dealCards(Dealer dealer, ArrayList<Player> players, Deck deck) {
    	//Deal cards out. Deal 1 card to dealer and to each player, then cycle again.
    	//Update GUI inbetween each card deal to reflect hand changes - maybe?? 
    	
    	dealer.addCard(deck.drawCard());
    	
        for(int i = 0; i < players.size(); i++) {
        	Player curPlayer = players.get(i);
        	if (curPlayer.getHand().getBet() > 0) {
        		curPlayer.addCard(deck.drawCard());	
        	}
        }
        
        dealer.addCard(deck.drawCard());
        
        for(int i = 0; i < players.size(); i++) {
        	Player curPlayer = players.get(i);
        	if (curPlayer.getHand().getBet() > 0) {
        		curPlayer.addCard(deck.drawCard());	
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
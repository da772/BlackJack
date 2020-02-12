package application;


import java.util.ArrayList;
import java.util.Random;

public class Table {
	//players is an array of player class (have balance, and player tier) in order
	
	
	// have our array of players at the table that can be added or removed
	private ArrayList<Player> players;
	// gonna need a deck of cards for the table
	private Deck deck;
	// gonna need a dealer at the table
	private Dealer dealer;


    // constructor
	public Table() {
		int TOTAL_PLAYERS = 8; //change this later to get a user input
		int STARTING_BALANCE = 100; // this too
		int NUM_DECKS = 8; // this too
		
		// initialize all private fields
		this.players = new ArrayList<Player>();
		this.deck = new Deck(8);
		this.dealer = new Dealer();
		
		// loop and add in 8 players to the table
		for(int i = 0; i < TOTAL_PLAYERS; i++) {
			this.players.add(new Player(STARTING_BALANCE));
		}
	}
	
	
	public void playRound() {
	    // this is where we will create a Round object and then use its methods
	    // to play the game
	    
	    // ORRRR we can just track the number of rounds in this class and then
	    // just perform all the ufnctionality in this class since all of our
	    // data is here anyways
	}
	
	
	public void deletePlayer(int id) {
		if(id == 0) {
			//win condition? dealer runs out of money
		}
		else {
			this.players.get(id).setPlayerType(2); 
		}
	}
	
	
	
	public Player getPlayerFromID(int id) {
		return this.players.get(id);
	}
	
	public Player getRealPlayer() {
		return this.players.get(this.playerID);
	}
	
	
	
	public int getRealPlayerID() {
		return this.playerID;
	}
	
	public static void main(String args[]) {
		//test functions
		Table currentTable = new Table();
		int total_players = 8;
		System.out.println("New table created. Showing every player's balance from left to right at the table: ");
		for(int i = 0; i < total_players; i++) {
			System.out.print(currentTable.getPlayerFromID(i).getBalance() + " ");
		}
		System.out.println("\nthe real player id is: " + currentTable.getRealPlayerID());
		System.out.println("\nputting brackets around the real player. dealer is always 0");
		for(int i = 0; i < total_players; i++) {
			if(i == currentTable.getRealPlayerID()) {
				System.out.print("["+currentTable.getPlayerFromID(i).getBalance() + "] ");
			}
			else {
			System.out.print(currentTable.getPlayerFromID(i).getBalance() + " ");
			}
		}
		
		
		
	}



}

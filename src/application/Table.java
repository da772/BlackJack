package application;


import java.util.ArrayList;

public class Table {
	//players is an array of player class (have balance, and player tier) in order
	
	
	// have our array of players at the table that can be added or removed
	private ArrayList<Player> players;
	// gonna need a deck of cards for the table
	private Deck deck;
	// gonna need a dealer at the table
	private Dealer dealer;
	private int roundsPlayed;
	private Round currentRound;

    // constructor
	public Table() {
		int TOTAL_PLAYERS = 3; //change this later to get a user input
		int STARTING_BALANCE = 100; // this too
		int NUM_DECKS = 8; // this too
		this.roundsPlayed = 0;
		Player.CURRENT_ID = 0;
		
		// initialize all private fields
		this.players = new ArrayList<Player>();
		this.deck = new Deck(NUM_DECKS);
		this.dealer = new Dealer();
		
		// Add user controlled player
		this.players.add(new Player(STARTING_BALANCE, false));
		players.get(0).setup();
		// loop and add in 3 players to the table
		for(int i = 1; i < TOTAL_PLAYERS; i++) {
			this.players.add(new Player(STARTING_BALANCE, true));
			players.get(i).setup();
		}
		dealer.setup();
	}
	
	
	public void playRound() {
	    // this is where we will create a Round object and then use its methods
	    // to play the game
	    
	    // ORRRR we can just track the number of rounds in this class and then
	    // just perform all the ufnctionality in this class since all of our
	    // data is here anyways
		
		//Round(this.dealer, this.players);
		if (currentRound != null) {
			currentRound.endRound();
			currentRound = null;
		}
		for (int i = 0; i < players.size(); i++) {
			players.get(i).reset();
		}
	
		dealer.reset();
		
		currentRound = new Round(dealer, this);
		this.roundsPlayed += 1;
	}
	
	
	public void deletePlayer(int id) {
		if(id == 0) {
			//win condition? dealer runs out of money
		}
		else {
			//this.players.get(id).setPlayerType(2); 
		}
	}
	
	
	
	public Player getPlayerFromID(int id) {
		return this.players.get(id);
	}
	
	public Player getRealPlayer() {
		return null;
		//return this.players.get(this.playerID);
	}
	
	public Dealer getDealer() {
		return this.dealer;
	}
	
	public Round getRound() {
		return this.currentRound;
	}
	
	public Deck getDeck() {
		return this.deck;
	}
	
	public ArrayList<Player> getPlayers() {
		return this.players;
	}
	
	
	public int getRealPlayerID() {
		return 0;
		//return this.playerID;
	}
	
	public static void main(String args[]) {
		//test functions 
		// test functions BROKEN Table now uses opengl functions
		Table currentTable = new Table();
		int total_players = 3;
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

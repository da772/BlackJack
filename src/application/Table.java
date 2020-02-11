package application;


import java.util.ArrayList;
import java.util.Random;

public class Table {
	//players is an array of player class (have balance, and player tier) in order
	
	private ArrayList<Player> players;
	private int playerID;

	public Table() {
		int TOTAL_PLAYERS = 8; //change this later to get a user input
		int STARTING_BALANCE = 100; //this too
		this.players = new ArrayList<Player>();
		
		//generate real player ID (others IDs are NPC)
		Random rand = new Random();
		playerID = rand.nextInt(TOTAL_PLAYERS-1) + 1;
		
		//init a table of players with the dealer, and the real player's ID
		Player dealer = new Player(0, STARTING_BALANCE);
		this.players.add(dealer);
		for(int i = 0; i < TOTAL_PLAYERS - 1; i++) {
			this.players.add(new Player(1, STARTING_BALANCE));
		}
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

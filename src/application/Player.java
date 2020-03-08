package application;

import java.util.ArrayList;

public class Player {
	
	// make a static variable for the # of players made so far
	private int CURRENT_ID = 1;
	
	// now each player needs id and balance
	private int id;
	private int balance; 
	private Hand hand;
	private ArrayList<Hand> splitHands; 

	

    // new simple constructor
	public Player(int startingBalance) {
	    this.id = CURRENT_ID++;
	    this.balance = startingBalance;
	    this.hand = new Hand();
	    this.splitHands = new ArrayList<Hand>();
	}
	
	public Hand getHand() {
		return this.hand;
	}
	
	public int getBalance() {
		return this.balance;
	}
	
	public void addToBalance(int amount) {
		this.balance += amount;
	}
	
	public void subtractFromBalance(int amount) {
		this.balance -= amount;
	}
	
	public void clearBalance() {
		this.balance = 0;
	}
	
	
	
	public static void main(String[] args) {

		int DEFAULT_START_BALANCE = 100;
		
		
		System.out.println("Making a dealer");
		Player dealer = new Player(DEFAULT_START_BALANCE);
		//System.out.println("New player made. Current player type is: " + dealer.getPlayerType() + " Balance is: " + dealer.getBalance());
		dealer.addToBalance(50000);
		System.out.println("Adding 50000 to balance. Balance is: " + dealer.getBalance());
		dealer.subtractFromBalance(50000);
		System.out.println("Subtract 50000 to balance. Balance is: " + dealer.getBalance());
		
		
		
		System.out.println("\n\nMaking a player");
		Player player1 = new Player(DEFAULT_START_BALANCE);
		//System.out.println("New player made. Current player type is: " + player1.getPlayerType() + " Balance is: " + player1.getBalance());
		player1.clearBalance();
		System.out.println("Clearing balance. Balance is: " + player1.getBalance());


	}

}

package application;

public class Dealer {
	
    // dealer just needs some generic name and a balance
	private int balance; 
	private String name;
	private Hand hand;

    // new simple constructor
	public Player() {
	    this.name = "Dealer";
	    this.balance = 500000;
	    this.hand = new Hand();
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
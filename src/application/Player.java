package application; 

public class Player {
	
	private int type;
	private int balance; 
	
	public Player(int type, int startingBalance) {
		//type = 0 is dealer, type = 1 is player
		int DEALER_STARTING_BALANCE = 100000; 
		if(type == 0) {
			this.balance = DEALER_STARTING_BALANCE;
		}
		else if(type == 1) {
			this.balance = startingBalance;
		}
		else {
			System.out.println("Invalid player type.");
			return;
		}
		this.type = type;
		return;
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
	
	public void setPlayerType(int type) {
		this.type = type;
	}
	
	public String getPlayerType() {
		if(this.type == 0) {
			return "Dealer";
		}
		else if(this.type == 1){
			return "Player";
		}
		else {
			return "Out";
		}
	}
	
	
	public static void main(String[] args) {

		int DEFAULT_START_BALANCE = 100;
		
		
		System.out.println("Making a dealer");
		Player dealer = new Player(0, DEFAULT_START_BALANCE);
		System.out.println("New player made. Current player type is: " + dealer.getPlayerType() + " Balance is: " + dealer.getBalance());
		dealer.addToBalance(50000);
		System.out.println("Adding 50000 to balance. Balance is: " + dealer.getBalance());
		dealer.subtractFromBalance(50000);
		System.out.println("Subtract 50000 to balance. Balance is: " + dealer.getBalance());
		
		
		
		System.out.println("\n\nMaking a player");
		Player player1 = new Player(1, DEFAULT_START_BALANCE);
		System.out.println("New player made. Current player type is: " + player1.getPlayerType() + " Balance is: " + player1.getBalance());
		player1.clearBalance();
		System.out.println("Clearing balance. Balance is: " + player1.getBalance());


	}

}

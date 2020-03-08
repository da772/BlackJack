// 1) Keep track of round #
// 2) Deals cards to player
// 3) Ask player actions
// 4) Calculate values of hands &
package application;

import java.util.ArrayList;
import java.util.Scanner;

public class Round {
	
	private ArrayList<Player> players;
	private Deck deck;
	private Hand dealerHand;
	private Scanner getInput;
    
    public Round(Dealer dealer, Table table) { //Everything that happens in 1 round of blackjack
    	deck = table.getDeck(); //Get deck
    	players = table.getPlayers(); //players in the order they are sitting at a table
    	
    	deck.shuffle(); //Shuffle deck
    	deck.checkDeckCount(); //If deck has 20% of cards or less left, add back used cards and shuffle
    	
    	getInput = new Scanner(System.in); 
    	for(int i = 0; i < players.size(); i++) { //Set the bet for each hand (gets user input)
    		setBet(i);
    	}
    	
        dealCards(dealer, players, deck); //Deal cards 
        
        //test function start
        printAllHandTotals(dealer, players);
        //test function end
        
        dealerHand = dealer.getHand();
        if(dealerHand.getTotal() == 21) { 
        	//function for when dealer has 21
        	   //check every persons hand, if it is not 21, then move to next player
        }
        else {
        	for(int i = 0; i < players.size(); i++) {
        		playerMove(i);
        	}
        }
        
        getInput.close();
    }
    
    public void setBet(int id) {
    	System.out.println("Enter bet for player "+id+": ");
    	Player curPlayer = this.players.get(id);
    	Hand curPlayerHand = curPlayer.getHand();
    	int bet = getInput.nextInt();
    	curPlayerHand.setBet(bet);
    	curPlayer.subtractFromBalance(bet);
    }
    
    public void dealCards(Dealer dealer, ArrayList<Player> players, Deck deck) {
    	//Deal cards out. Deal 1 card to dealer and to each player, then cycle again.
    	//Update GUI inbetween each card deal to reflect hand changes - maybe?? 
    	Hand dealerHand = dealer.getHand();
    	
    	dealerHand.addCard(deck.drawCard());
    	
        for(int i = 0; i < players.size(); i++) {
        	Player curPlayer = players.get(i);
        	Hand curPlayerHand = curPlayer.getHand();
        	curPlayerHand.addCard(deck.drawCard());
        }
        
        dealerHand.addCard(deck.drawCard());
        
        for(int i = 0; i < players.size(); i++) {
        	Player curPlayer = players.get(i);
        	Hand curPlayerHand = curPlayer.getHand();
        	curPlayerHand.addCard(deck.drawCard());
        }
        
    }
    
    public void playerMove(int id) {
    	Player curPlayer = this.players.get(id);
    	Hand curPlayerHand = curPlayer.getHand();
    	
		if(curPlayerHand.isSameCard() == true) {
			//split function here
			System.out.print("split function here");
		}

		int turnsCount = 0;
    	while(curPlayerHand.getTotal() < 21) {
    		turnsCount += 1;
    		System.out.println("\n\nPlayer"+id+"'s turn:");
    		curPlayerHand.printHandInfo();
        	System.out.println("\nHit (h), Double (d), Stay (s)?: ");
        	String option = this.getInput.next();
        	if(option.matches("h")) {
        		curPlayerHand.addCard(deck.drawCard());
        	}
        	else if(option.matches("d") && turnsCount == 1) {
        		curPlayerHand.addCard(deck.drawCard());
        		curPlayerHand.doubleBet();
        		break;
        	}
        	else if(option.matches("s")){
        		break;
        	}
        	else {
        		System.out.println("Invalid option");
        	}
    	}
    	
    	
    	curPlayerHand.printHandInfo();
    	if(curPlayerHand.getTotal() == 21) { //hit 21, change some condition to indicate win
    		System.out.println("Player " + id + " hit 21!!");
    	}
    	else if(curPlayerHand.getTotal() < 21){
    		System.out.println("Player " + id + " ended with " + curPlayerHand.getTotal());
    	}
    	else { //over 21 bust condition
    		System.out.println("Player " + id + " bust with " + curPlayerHand.getTotal());
    	}
    	
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
    
    public static void main(String[] args) {
    	Table table = new Table();
    	System.out.println("Real player id: " + table.getRealPlayerID());
    	Round playRound = new Round(table.getDealer(), table);
    	
    	
    }
    
}
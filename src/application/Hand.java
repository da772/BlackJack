/**
 * This class will keep track of a hand of cards that will be used by the Player
 * to keep track of the cards that they will have while they are playing the round
 */
package application;
import java.util.ArrayList;
import java.util.HashMap;
 
 public class Hand {
	//Store cards into a hand. Adding a card updates a list of cards in the hand, and their corresponding values. 
	//Get a player's total with getTotal(). getTotal() may need more test cases.
	 
    private ArrayList<Card> hand;
    private ArrayList<ArrayList<Integer>> handValues;
    private int bet;
    private HashMap<String, Boolean> options;
    private int rounds;
    
    public Hand() {
        this.hand = new ArrayList<Card>();
        this.handValues = new ArrayList<ArrayList<Integer>>();
        this.bet = 0;
        this.options = new HashMap<String, Boolean>();
        this.options.put("split", false);
        this.options.put("hit", false);
        this.options.put("stay", false);
        this.options.put("double", false);
        this.rounds = 0;
    }
    
    public void addCard(Card card) {
    	this.hand.add(card);
    	ArrayList<Integer> curValues = new ArrayList<Integer>();
    	if(card.getValue() == 1) {
    		curValues.add(1);
    		curValues.add(11);
		}
		else if(card.getValue() == 11 || card.getValue() == 12 || card.getValue() == 13) {
			curValues.add(10);
		}
		else {
			curValues.add(card.getValue());
		}
    	this.handValues.add(curValues);
    	this.rounds += 1;
    	//update options after adding a card
    	updateOptions();
    }
    
    public void updateOptions() { //update options
    	if(getTotal() >= 21) {
            this.options.replace("split", false);
            this.options.replace("hit", false);
            this.options.replace("stay", false);
            this.options.replace("double", false);
    	}
    	else { //total less than 21
    		if(sameValueHand()) { //can split
                this.options.replace("split", true);
    		}
    		else {
                this.options.replace("split", false);
    		}
    		if(this.rounds == 2) { //can double only on first turn of the hand
                this.options.replace("double", true);
    		}
    		else {
    			this.options.replace("double", false);
    		}
    		this.options.replace("stay", true); //can stay
    		this.options.replace("hit", true); //can hit
    	}
    }
    
    public boolean canHit() {
    	return this.options.get("hit");
    }
    
    public boolean canStay() {
    	return this.options.get("stay");
    }
    
    public boolean canSplit() {
    	return this.options.get("split");
    }
    
    public boolean canDouble() {
    	return this.options.get("double");
    }
    
    
    public void addCards(int amount, Deck deck) {
    	for (int i = 0; i < amount; i++) {
    		addCard(deck.drawCard());
    	}
    }
    
    public int GetCardCount() {
    	return this.hand.size();
    }
    
    public void clearHand() {
        this.hand = new ArrayList<Card>();
        this.handValues = new ArrayList<ArrayList<Integer>>();
    }
    
    public Card getCard(int index) {
    	return this.hand.get(index);
    }
    
    public void printHand() {
    	System.out.print(this.handValues);
    }
    
    public void printHandInfo() {
    	System.out.print("Current Hand: " + this.handValues + " /// Total: "+getTotal()+" /// Current bet: " + getBet() + "\n");
    }
    
    public void setBet(int bet) {
    	this.bet = bet;
    }
    
    public void doubleBet() {
    	this.bet *= 2;
    	
    }
    
    public int getBet() {
    	return this.bet;
    }
    
    public boolean sameValueHand() {
    	if(this.hand.size() == 2) {
    		int value1 = getCard(0).getValue();
    		int value2 = getCard(1).getValue();
    		if(value1 == 11 || value1 == 12 || value1 == 13) {
    			value1 = 10;
    		}
    		if(value2 == 11 || value2 == 12 || value2 == 13) {
    			value2 = 10;
    		}
    		return value1 == value2; //possible split
    	}
    	return false;
    }
    
    public int getTotal() { //calculate total of current hand
    	ArrayList<Integer> totals = new ArrayList<Integer>(); //all possible totals
    	totals.add(0); //start with 0
    	for(int i = 0; i < this.handValues.size(); i++) { //for every card in a hand
    		ArrayList<Integer> curValue = this.handValues.get(i); //get the value
    		if(curValue.size() == 1) { //not an ace
    			for(int j = 0; j < totals.size(); j++) { 
    				//add current card value to every total
    				totals.set(j, totals.get(j) + curValue.get(0)); 
    			}
    		}
    		else { //ace found
    			ArrayList<Integer> newTotals = new ArrayList<Integer>(); //multiple totals (ace = 1 or 11)
    			for(int j = 0; j < totals.size(); j++) { //for every total
    				int curTotal = totals.get(j); //get the value
    				newTotals.add(curTotal + 1); // add 1 to the current total
    				newTotals.add(curTotal + 11); // add 11 to the current total
    			}
    			totals = newTotals; //old totals are replaced by the new ones with an extra path
    		}
    	}
    	
    	int minimum = Integer.MAX_VALUE;
    	int maximum = Integer.MIN_VALUE;
    	for(int i = 0; i < totals.size(); i++) {
    		int curTotal = totals.get(i);
    		if(curTotal == 21) {//if 21 is a total, then end here
    			return 21;
    		}
    		else {//total is not 21
    			if(curTotal < 21 && curTotal < minimum) {//update minimum under 21
    				minimum = curTotal;
    			}
    			if(curTotal < 21 && curTotal > maximum) {//update maximum under 21
    				maximum = curTotal;
    			}
    			if(curTotal > 21) { //get the largest total over 21 that is not Integer.MAX_VALUE or Integer.MIN_VALUE
    				return Math.max(Math.min(curTotal, maximum), Math.min(curTotal, minimum));
    			}
    		}
    	}
    	if(maximum < 21 || minimum < 21) {
    		return Math.max(maximum, minimum); //return largest value under 21
    	}
    	return 0;//never reached
    }
    
    
	public int getTotalDealer() { // Calculate total of hand skipping the flipped card
    	ArrayList<Integer> totals = new ArrayList<Integer>(); //all possible totals
    	totals.add(0); //start with 0
    	for(int i = 1; i < this.handValues.size(); i++) { //for every card in a hand
    		ArrayList<Integer> curValue = this.handValues.get(i); //get the value
    		if(curValue.size() == 1) { //not an ace
    			for(int j = 0; j < totals.size(); j++) { 
    				//add current card value to every total
    				totals.set(j, totals.get(j) + curValue.get(0)); 
    			}
    		}
    		else { //ace found
    			ArrayList<Integer> newTotals = new ArrayList<Integer>(); //multiple totals (ace = 1 or 11)
    			for(int j = 0; j < totals.size(); j++) { //for every total
    				int curTotal = totals.get(j); //get the value
    				newTotals.add(curTotal + 1); // add 1 to the current total
    				newTotals.add(curTotal + 11); // add 11 to the current total
    			}
    			totals = newTotals; //old totals are replaced by the new ones with an extra path
    		}
    	}
    	
    	int minimum = Integer.MAX_VALUE;
    	int maximum = Integer.MIN_VALUE;
    	for(int i = 0; i < totals.size(); i++) {
    		int curTotal = totals.get(i);
    		if(curTotal == 21) {//if 21 is a total, then end here
    			return 21;
    		}
    		else {//total is not 21
    			if(curTotal < 21 && curTotal < minimum) {//update minimum under 21
    				minimum = curTotal;
    			}
    			if(curTotal < 21 && curTotal > maximum) {//update maximum under 21
    				maximum = curTotal;
    			}
    			if(curTotal > 21) { //get the largest total over 21 that is not Integer.MAX_VALUE or Integer.MIN_VALUE
    				return Math.max(Math.min(curTotal, maximum), Math.min(curTotal, minimum));
    			}
    		}
    	}
    	if(maximum < 21 || minimum < 21) {
    		return Math.max(maximum, minimum); //return largest value under 21
    	}
    	return 0;//never reached
	}
    
    
    public static void main(String[] args) {
    	//test
    	Hand curHand = new Hand();
    	curHand.addCard(new Card(1, "Ace"));
    	curHand.addCard(new Card(10, "Ace"));
    	
    	System.out.println("Check current hand (Ace, 10): ");
    	curHand.printHand();
    	System.out.println("\nGet current total: " + curHand.getTotal());

    	curHand.clearHand();
    	System.out.println("Hand cleared, total is now: " + curHand.getTotal());
    	
    	curHand.addCard(new Card(2, "Ace"));
    	curHand.addCard(new Card(4, "Ace"));
    	curHand.addCard(new Card(1, "Ace"));
    	curHand.addCard(new Card(12, "Ace"));
    	System.out.println("\nCheck current hand (2,4,Ace,Q): ");
    	curHand.printHand();
    	System.out.println("\nGet current total: " + curHand.getTotal());
    	
    	curHand.clearHand();
    	curHand.addCard(new Card(1, "Ace"));
    	curHand.addCard(new Card(1, "Ace"));
    	curHand.addCard(new Card(1, "Ace"));
    	curHand.addCard(new Card(12, "Ace"));
    	curHand.addCard(new Card(9, "Ace"));
    	System.out.println("\nCheck current hand (Ace,Ace,Ace,Q,9): ");
    	curHand.printHand();
    	System.out.println("\nGet current total: " + curHand.getTotal());
    	
    	curHand.clearHand();
    	System.out.println("\nHand cleared, and now dealing 3. Total is: ");
    	curHand.addCard(new Card(3, "Ace"));
    	System.out.println(curHand.getTotal());
    	System.out.println("Dealing 4. Total is: ");
    	curHand.addCard(new Card(4, "Ace"));
    	System.out.println(curHand.getTotal());
    	System.out.println("Dealing Ace. Total is: ");
    	curHand.addCard(new Card(1, "Ace"));
    	System.out.println(curHand.getTotal());
    	System.out.println("Dealing 4. Total is: ");
    	curHand.addCard(new Card(4, "Ace"));
    	System.out.println(curHand.getTotal());
    	System.out.println("Check current hand (3,4,Ace,4): ");
    	curHand.printHand();
    	curHand.addCard(new Card(8, "Ace"));
    	System.out.println("\nDealing 8. Total is: " + curHand.getTotal());
    	curHand.addCard(new Card(1, "Ace"));
    	System.out.println("Dealing Ace. Total is: " + curHand.getTotal());
    	System.out.println("Final hand:");
    	curHand.printHand();
    	
    	curHand.clearHand();
    	System.out.println("\n\n\nHand cleared, and now dealing 7. Total is: ");
    	curHand.addCard(new Card(7, "Ace"));
    	System.out.println(curHand.getTotal());
    	System.out.println("Dealing 11 (Jack). Total is: ");
    	curHand.addCard(new Card(11, "Ace"));
    	System.out.println(curHand.getTotal());
    	System.out.println("Dealing Ace. Total is: ");
    	curHand.addCard(new Card(1, "Ace"));
    	System.out.println(curHand.getTotal());
    	System.out.println("Dealing 4. Total is: ");
    	curHand.addCard(new Card(4, "Ace"));
    	System.out.println(curHand.getTotal());
    	System.out.println("Check current hand (7, J, A, 4): ");
    	curHand.printHand();
    	
    	curHand.clearHand();
    	System.out.println("\n\n\nHand cleared, and now dealing Q. Total is: ");
    	curHand.addCard(new Card(12, "Ace"));
    	System.out.println(curHand.getTotal());
    	System.out.println("Dealing J. Total is: ");
    	curHand.addCard(new Card(11, "Ace"));
    	System.out.println(curHand.getTotal());
    	System.out.println("Dealing 2. Total is: ");
    	curHand.addCard(new Card(2, "Ace"));
    	System.out.println(curHand.getTotal());
    	System.out.println("Check current hand (Q, J, 2): ");
    	curHand.printHand();
    	
    	curHand.clearHand();
    	System.out.println("\n\n\nHand cleared, and now dealing 8. Total is: ");
    	curHand.addCard(new Card(8, "Ace"));
    	System.out.println(curHand.getTotal());
    	System.out.println("Dealing Ace. Total is: ");
    	curHand.addCard(new Card(1, "Ace"));
    	System.out.println(curHand.getTotal());
    	System.out.println("Dealing Ace. Total is: ");
    	curHand.addCard(new Card(1, "Ace"));
    	System.out.println(curHand.getTotal());
    	System.out.println("Dealing 4. Total is: ");
    	curHand.addCard(new Card(4, "Ace"));
    	System.out.println(curHand.getTotal());
    	System.out.println("Dealing Ace. Total is: ");
    	curHand.addCard(new Card(1, "Ace"));
    	System.out.println(curHand.getTotal());
    	System.out.println("Dealing 5. Total is: ");
    	curHand.addCard(new Card(5, "Ace"));
    	System.out.println(curHand.getTotal());
    	System.out.println("Dealing Ace. Total is: ");
    	curHand.addCard(new Card(1, "Ace"));
    	System.out.println(curHand.getTotal());
    	System.out.println("Check current hand (8, Ace, Ace, 4, Ace, 5, Ace): ");
    	curHand.printHand();
    	
    	curHand.clearHand();
    	curHand.addCard(new Card(5, "Spade"));
    	curHand.addCard(new Card(5, "Spade"));
    	System.out.println("\n\nCur hand is [5 Spade][5 Spade]. Is this splittable? ");
    	System.out.println(curHand.sameValueHand());
    	
      	curHand.clearHand();
    	curHand.addCard(new Card(10, "Spade"));
    	curHand.addCard(new Card(11, "Spade"));
    	System.out.println("\n\nCur hand is [10 Spade][J Spade]. Is this splittable? ");
    	System.out.println(curHand.sameValueHand());
    	
      	curHand.clearHand();
    	curHand.addCard(new Card(13, "Spade"));
    	curHand.addCard(new Card(12, "Hearts"));
    	System.out.println("\n\nCur hand is [K Spade][Q Hearts]. Is this splittable? ");
    	System.out.println(curHand.sameValueHand());
    	
      	curHand.clearHand();
    	curHand.addCard(new Card(10, "Spade"));
    	curHand.addCard(new Card(13, "Hearts"));
    	System.out.println("\n\nCur hand is [10 Spade][K Hearts]. Is this splittable? ");
    	System.out.println(curHand.sameValueHand());
    }


    
 
    
    
    
 }
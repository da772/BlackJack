/**
 * This class will keep track of a hand of cards that will be used by the Player
 * to keep track of the cards that they will have while they are playing the round
 */
package application;
import java.util.ArrayList;
import java.util.List;
 
 public class Hand {
	//Store cards into a hand. Adding a card updates a list of cards in the hand, and their corresponding values. 
	//Get a player's total with getTotal(). getTotal() may need more test cases.
	 
    private ArrayList<Card> hand;
    private ArrayList<ArrayList<Integer>> handValues;
    
    public Hand() {
        this.hand = new ArrayList<Card>();
        this.handValues = new ArrayList<ArrayList<Integer>>();
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
    }
    
    public void clearHand() {
        this.hand = new ArrayList<Card>();
        this.handValues = new ArrayList<ArrayList<Integer>>();
    }
    
    public void printHand() {
    	System.out.print(this.handValues);
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
    	
    	
    }
    
    
  
    
    
    
    /*
    // for adding a card we're going to determine the value based on
    // the total for ace
    // If the total is <= 21 then we'll make an ace be 11 and when total
    // is above that we'll consider it as a 1 whhich now leads me to the
    // problem where when we add a card and the total goes over 10 and we
    // have to go through our hand and see if an ace is in it and then we
    // can change it to one and it might prevent the total from exceeding
    // 21, this means total might get changed when an ace occurs
    
    // this method adds a card to the hand right now
    public void addCard(Card card) {
        cards.add(card);
        // when card is an ace, count as an 11
        if (card.getValue() == 1) total += 11;
        // valid values for cards 2-J where value corresponds with value member
        else if (card.getValue() >= 2 && card.getValue() <= 10) total += card.getValue();
        else (card.getValue() > 10) total += 10;
        
        // now if total exceeds 21, call aceCheck so we can adjust total before
        // returning from this method in Round
        if (total >= 21 && containsUnchangedAce()) {
            total -= 10; // this will change an ace's value to total from 11 -> 1
        }
    }
    
    // this method juat checks if any of player's card is an ace
    // to help 
    private Boolean containsUnchangedAce() {
        // loop through the player's hand of cards
        for (Card card : cards) {
            // when unchanged ace found, set it to changed and then
            // return true!
            if (card.getValue() == 1 && card.beenChanged() == false) {
                card.beenChanged = true;
                return true;
            }
        }
        return false; // no valid ace found (maybe was changed before)
    }
    
    
    
    // this method will be called when a player busts and the dealer takes
    // their cards away
    public ArrayList<Card> returnCards() {
    
        // store the list of cards (hand)
        ArrayList<Card> cardsToReturn = cards;
        
        // reinitialize the Hand's cards to empty list and then
        // put the total back to 0
        cards = new ArrayList<Card>();
        total = 0;
        return cardsToReturn;
    }
    */
    
    
    
    
 }
/**
 * This class will keep track of a hand of cards that will be used by the Player
 * to keep track of the cards that they will have while they are playing the round
 */
 
package application;

import java.util.ArrayList;
import java.util.List;
 
 public class Hand {
    
    // store a list of cards
    private ArrayList<Card> hand;
    private ArrayList<ArrayList<Integer>> handValues;
    
    // constructor just initializes everything!
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
    
    
    public int getTotal() {
    	ArrayList<Integer> totals = new ArrayList<Integer>();
    	totals.add(0);
    	for(int i = 0; i < this.handValues.size(); i++) {
    		ArrayList<Integer> curValue = this.handValues.get(i);
    		if(curValue.size() == 1) {
    			for(int j = 0; j < totals.size(); j++) {
    				totals.set(j, totals.get(j) + curValue.get(0));
    			}
    		}
    		else {
    			ArrayList<Integer> newTotals = new ArrayList<Integer>();
    			for(int j = 0; j < totals.size(); j++) {
    				int curTotal = totals.get(j);
    				newTotals.add(curTotal + 1);
    				newTotals.add(curTotal + 11);
    			}
    			totals = newTotals;
    		}
    	}
    	
    	int minimum = Integer.MAX_VALUE;
    	for(int i = 0; i < totals.size(); i++) {
    		int curTotal = totals.get(i);
    		if(curTotal == 21) {
    			return 21;
    		}
    		else {
    			if(curTotal < minimum) {
    				minimum = curTotal;
    			}
    		}
    	}
    	return minimum;
    	
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
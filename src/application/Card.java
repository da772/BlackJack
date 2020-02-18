package application;
/**
 * This class will handle the creation of cards which will be loaded into our deck
 * and hand
 */

public class Card {
    // private members for the values of the card
    private int value;    
    private String suit;
    
    public Card(int value, String suit) {
        this.value = value;
        this.suit = suit;
    }
    
    public int getValue() { 
    	return this.value; 
    }
    public String getSuit() { 
    	return this.suit; 
    }
    public String getCard() { 
    	return new String(this.getValue() + " " + this.getSuit()); 
    }
    
    public static void main(String args[]) {
    	//test
    }
    
    
}
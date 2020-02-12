/**
 * This class will handle the creation of cards which will be loaded into our deck
 * and hand
 */

package application; 

public class Card {
    // private members for the values of the card
    private int value;    
    private String suit;
    
    public Card(int value, String suit) {
        this.value = value;
        this.suit = suit;
    }
    
    public int getValue() { return value; }
    public int getSuit() { return suit; }
    
}
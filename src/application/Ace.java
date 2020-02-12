// class for an Ace that is just a special card
package application; 
public class Ace extends Card {
    
    // private member that tells when the ace card has converted from 11 to 1!
    private Boolean changed;
    
    // constructor uses parents
    public Ace(int value, String suit) {
        super(value, suit);
        changed = false;
    }
    
    public Boolean beenChanged() {
        return changed;
    }
    
    // IMPORTANT
    // 1) Make sure you declare any aces as Card but use Ace constructor!
    // 2) Make sure to reset beenChanged to false when a user busts and
    //    the card goes back to the deck so that way someone else can change
    //    it again!

}
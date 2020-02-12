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
    
}
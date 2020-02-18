/**
 * This class will keep track of a hand of cards that will be used by the Player
 * to keep track of the cards that they will have while they are playing the round
 */
 
 import java.util.ArrayList;
 
 public class Hand {
    
    // store a list of cards
    private ArrayList<Card> cards;
    private total;
    
    // constructor just initializes everything!
    public Hand() {
        cards = new ArrayList<Card>();
        total = 0;
    }
    
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
    
    
    
    
 }
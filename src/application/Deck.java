
package application; 
import java.util.Collections;
import java.util.Stack;

public class Deck {
	// need two stack which act as Decks and will store Card objects
	private Stack<Card> deck;
	private Stack<Card> usedCards;
	private int INITIAL_COUNT;
	
	
	
	/*
	 *
	 * Use a nested for loop where the outer loop just loops over 4 times
	 * where each time you go through the loop is a different suit that you
	 * are going to give a card.  Then the inner loop will just go over 13 times
	 * so really inside the outer loop but before the nested loop just store a local
	 * variable that has a suit like (0 == "heart", 1 == "spade", etc) and then for
	 * the inner loop just give the card a different value.
	 *
	 * 
	 */
	
	
	// constructor
	public Deck(int decksCount) {
		this.deck = new Stack<Card>();
		this.usedCards = new Stack<Card>();
		this.INITIAL_COUNT = 52 * decksCount;
		
		// Loop over to create the number of decks we want into one big ass deck
		for(int i = 0; i < decksCount; i++) {
		    // now this loop will set the count from 0-3 which will just represent suits
			for(int j = 0; j < 4; j++) {
			    // set suit for each set of 13 cards
				String suit = "";
				if (j == 0) suit = "Diamond";
				else if (j == 1) suit = "Club";
				else if (j == 2) suit = "Heart";
				else if (j == 3) suit = "Spade";
				// loop over 13 cards and create and add to the Deck
				for (int k = 1; k <= 13; k++) {
				    // make a new card and check if ace or not for initialization
				    // then just push onto the stack (Deck)
				    Card newCard;
				    if (k == 1) newCard = new Ace(k, suit);
		            else newCard = new Card(k, suit);
		            deck.push(newCard);
				}
			}
		}
	}
	
	public void printDeck() {
		//print deck contents from top
		int cardsCount = this.deck.size();
		for(int i = cardsCount-1; i >= 0; i--) {
			if(i % 52 == 0) System.out.println();
			System.out.print("[" + this.deck.get(i).getValue() + " " + this.deck.get(i).getSuit() + "] ");
		}
	}
	
	public void printUsedCards() {
		//print used cards
		int count = this.usedCards.size();
		for(int i = count-1; i >= 0; i--) {
			System.out.print("[" + this.usedCards.get(i).getValue() + " " + this.usedCards.get(i).getSuit() + "] ");
		}
	}
	
	public void shuffle() {
		//Shuffle the deck
		Collections.shuffle(deck);
	}
	
	public void addUsedCardsBack() {
		//Add used cards back into the deck
		int usedCardsCount = this.usedCards.size();
		for(int i = 0; i < usedCardsCount; i++) {
			this.deck.add(this.usedCards.pop());
		}
	}
	
	public void checkDeckCount() { //re-add used cards if 80% of the deck has been dealt
		if(this.deck.size() <= 0.80 * INITIAL_COUNT) {
			addUsedCardsBack();
		}
		shuffle();
	}
	
	
	//Draw the top card. Return top Card from Deck.
	public Card drawCard() {
		Card drawnCard = this.deck.pop();
		usedCards.add(drawnCard);
		return drawnCard;
	}
	
	public int getCardCount() {
		return this.deck.size();
	}
	
	public int getUsedCardCount() {
		return this.usedCards.size();
	}
	
	public static void main(String[] args) {
		
		Deck cur = new Deck(1);
		System.out.println("Printing 1 deck:");
		cur.printDeck();
		
		cur = new Deck(2);
		System.out.println("\n\nPrinting 2 decks:");
		cur.printDeck();
		
		cur.shuffle();
		Card draw1 = cur.drawCard();
		Card draw2 = cur.drawCard();
		Card draw3 = cur.drawCard();
		
		System.out.println("\n\nShuffling and drawing 3 cards and printing:");
		System.out.println("[" + draw1.getCard() + "] [" + draw2.getCard() + "] [" + draw3.getCard() + "]");
		System.out.println("Printing used cards (should match last line):");
		cur.printUsedCards();
		System.out.println("\nDeck now has " + cur.getCardCount() + " cards.");
		System.out.print("Readding " + cur.getUsedCardCount());
		cur.addUsedCardsBack();
		System.out.print(" used cards. Deck count is now " + cur.getCardCount());

	
		
	}

	
}
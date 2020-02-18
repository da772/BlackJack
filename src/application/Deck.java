package application;

import java.util.Collections;
import java.util.Stack;

public class Deck {
	
	//create a number code for each card in the deck 
	//0 -> 2 of diamonds
	//1 -> 2 of clubs
	//2 -> 2 of hearts
	//3 -> 2 of spades
	//4 -> 3 of diamonds
	//etc...
	//so every multiple of 4 is a new number in diamond, #%4 = 1 is clubs, #%4 = 2 is hearts, #%4 = 3 is spades
	
	//we can keep track of cards either by keeping them in an array, or jsut keeping track of the count of each
	//card
	
	// need two stack which act as Decks and will store Card objects
	private Stack<Card> deck;
	private Stack<Card> usedCards;
	
	
	
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
		this.deck = new Stack<Integer>();
		this.usedCards = new Stack<Integer>();
		
		// Loop over to create the number of decks we want into one big ass deck
		for(int i = 0; i < decksCount; i++) {
		    // now this loop will set the count from 0-3 which will just represent suits
			for(int j = 0; j < 4; j++) {
			    // set suit for each set of 13 cards
				String suit;
				if (j == 0) suit = "Heart";
				else if (j == 1) suit = "Spade";
				else if (j == 2) suit = "Diamond";
				else if (j == 3) suit = "Club";
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
	
	
	
	
	
	public void printDeckFromBottom(int printType) {
		//Print every card in the deck starting with the bottom card.
		//printType == 0 -> print the integer value of each card
		//printType == 1 -> print the card with suit in string
		int deckCount = this.deck.size();
		if(printType == 0) {
			for(int i = 0; i < deckCount; i++) {
				System.out.print(convertCardToValue(this.deck.get(i)) + " ");
			}
		}
		else if(printType == 1) {
			for(int i = 0; i < deckCount; i++) {
				System.out.print(convertCardToString(this.deck.get(i)) + " ");
			}
		}
		else {
			System.out.println("Invalid print type specified. 0 is for the value, 1 is for the string.");
		}
	}
	
	public void printDeckFromTop(int printType) {
		//Print every card in the deck starting with the top card.
		//printType == 0 -> print the integer value of each card
		//printType == 1 -> print the card with suit in string
		int deckCount = this.deck.size();
		if(printType == 0) {
			for(int i = deckCount - 1; i >= 0; i--) {
				System.out.print(convertCardToValue(this.deck.get(i)) + " ");
			}
		}
		else if(printType == 1) {
			for(int i = deckCount - 1; i >= 0; i--) {
				System.out.print(convertCardToString(this.deck.get(i)) + " ");
			}
		}
		else {
			System.out.println("Invalid print type specified. 0 is for the value, 1 is for the string.");
		}
	}
	
	
	
	public void printUsedCards() {
		int usedDeckCount = this.usedCards.size();
		for(int i = 0; i < usedDeckCount; i++) {
			System.out.print(convertCardToString(this.usedCards.get(i)) + " ");
		}
	}
	
	public void shuffle() {
		//Shuffle the deck
		Collections.shuffle(deck);
	}
	
	public void addUsedCards() {
		//Add used cards back into the deck
		int usedDeckCount = this.usedCards.size();
		for(int i = 0; i < usedDeckCount; i++) {
			this.deck.add(this.usedCards.pop());
		}
	}
	
	
	//Draw the top card. Return top Card from Deck.
	public Card drawCard() {
		return this.deck.pop();
	}
	
	public int getCardCount() {
		return this.deck.size();
	}
	
	public int getUsedCardCount() {
		return this.usedCards.size();
	}
	
	public static void main(String[] args) {
		//Show the basic functionality of the deck
	
		Deck gameDeck = new Deck(1);
		System.out.println("Printing 1 deck: ");
		gameDeck.printDeckFromBottom(0);
		
		System.out.println("\n\nPrinting 1 deck in string: ");
		gameDeck = new Deck(1);
		gameDeck.printDeckFromBottom(1);
		
		System.out.println("\n\nPrinting 2 shuffled decks: ");
		gameDeck = new Deck(2);
		gameDeck.shuffle();
		gameDeck.printDeckFromBottom(0);

		System.out.println("\n\nDraw 2 cards from a shuffled deck: ");
		gameDeck = new Deck(1);
		gameDeck.shuffle();
		System.out.print(gameDeck.convertCardToString(gameDeck.drawCard()) + " " + gameDeck.convertCardToString(gameDeck.drawCard()));
		System.out.println("\nPrint used pile (cards just drawn): ");
		gameDeck.printUsedCards();
		System.out.println("\nPrint current deck: ");
		gameDeck.printDeckFromBottom(0);
		System.out.println("\nNow adding used cards to deck..");
		gameDeck.addUsedCards();
		
		System.out.println("\nPrint readded deck in values and in string: ");
		gameDeck.printDeckFromBottom(0);
		System.out.println();
		gameDeck.printDeckFromBottom(1);
		System.out.println("\nPrint deck from the top card: ");
		gameDeck.printDeckFromTop(1);
		
		
		
		
		
	}
	
}
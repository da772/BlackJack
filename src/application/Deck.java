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
	
	
	private Stack<Integer> deck;
	private Stack<Integer> usedCards;
	
	public Deck(int decksCount) {
		//Initialize the cards based on the number of decks used.
		int CARDS_IN_A_DECK = 52;
		this.deck = new Stack<Integer>();
		this.usedCards = new Stack<Integer>();
		for(int i = 0; i < decksCount; i++) {
			for(int j = 0; j < CARDS_IN_A_DECK; j++) {
				this.deck.add(j);
			}
		}
	}

	public int convertCardToValue(int codedCard) {
		//Convert the card from a coded integer to its real value. Useful for calculation of 21. The coded integer
		//still retains itself for conversion to the real value with the suit, for GUI purpsoes.
		int value = (codedCard / 4) + 2;
		if(value == 11 || value == 12 || value == 13) { //Jack, Queen, King
			return 10;
		}
		else if (value == 14) { //Ace
			return 11; //Note for later: When calculating for 21, if the value is 11, also calculate it as a 1.
		}
		else {
			return value;
		}
	}
	
	public String convertCardToString(int codedCard) {
		//Return coded integer to real value and suit in string.
		int value = (codedCard / 4) + 2; 
		int suitCode = codedCard % 4;
		String suit;
		if(suitCode == 0) {
			suit = "Diamonds";
		}
		else if(suitCode == 1) {
			suit = "Clubs";
		}
		else if(suitCode == 2) {
			suit = "Hearts";
		}
		else {
			suit = "Aces";
		}
		
		if(value > 10) {
			if(value == 11) { 
				return new String("[J " + suit + ']');
			}
			else if(value == 12) {
				return new String("[Q " + suit + ']');
			}
			else if(value== 13) {
				return new String("[K " + suit + ']');
			}
			else {
				return new String("[A " + suit + ']');
			}
		}
		return new String('['+ Integer.toString(value) + ' ' + suit + ']');
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
	
	public int drawCard() {
		//Draw the top card. Commit value to used pile. Return top card value.
		int cardVal = this.deck.pop();
		this.usedCards.add(cardVal);
		return cardVal;
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
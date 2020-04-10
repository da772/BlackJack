package application;

/*
 * Determine how computer should play turn
 */
public class ComputerHitLogic {

	
	public static int getChoice(int cardCount, int dealerCard) {
		
		if (cardCount < 11) {
			return 0;
		}
		
		if (cardCount == 11) {
			return 2;
		}
		if (cardCount == 12) {
			if (dealerCard == 2 || dealerCard == 3 || dealerCard >=7 ) {
				return 0;
			} else {
				return 1;
			}
		}
		
		if (cardCount >= 13 && cardCount <= 16) {
			if (dealerCard <= 6) {
				return 1;
			} else {
				return 0;
			}
		}
		
		if (cardCount >= 17 && cardCount <= 20) {
			return 1;
		}
		
		return 1;
	}
	
	
	
	
	
}

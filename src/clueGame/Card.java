package clueGame;

public class Card {

	
	private String cardName;
	
	private CardType cardType;
	
	
	public CardType getCardType() {
		return cardType;
	}

	public String getCardName() {
		return cardName;
	}
	
	public Card(String cardName, CardType cardType) {
		super();
		this.cardName = cardName;
		this.cardType = cardType;
	}
	



	public Card() {
		super();
	}



	public boolean equals() {
		return false;}
	
	public boolean isNotSolution(String person, String weapon, String room) {
		if(! (this.cardName.equals(person)) ) {
			if((! (this.cardName.equals(weapon)))){
				if((! (this.cardName.equals(room)))) {
					return true;
				}
			}
		}
		return false;
	}

}

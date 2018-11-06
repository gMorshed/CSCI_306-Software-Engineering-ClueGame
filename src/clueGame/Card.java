package clueGame;
/**
*
* @author Abhaya Shrestha, Kirwinlvinodaq S Lawrence, Gazi Mahbub Morshed

* */
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
		return false;
		}

}

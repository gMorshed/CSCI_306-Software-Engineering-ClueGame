package clueGame;

import java.awt.Color;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Player {

	private String playerName;
	
	private int row ;
	
	private int column;
	
	private Color color;
	private boolean human;
	private ArrayList <Card> playersCards;
	
	public ArrayList<Card> getPlayersCards() {
		return playersCards;
	}

	public Player(int row, int column, Color color, String playerName) {
		this.row= row;
		this.color=color;
		this.column=column;
		this.playerName=playerName;
		this.human = false;
		playersCards = new ArrayList<Card>(); 
	}
	
	public void setPlayersCards(ArrayList<Card> playersCards) {
		this.playersCards = playersCards;
	}

	public void receiveCard(Card card) {
		playersCards.add(card);
	}

	public Card disproveSuggestion(Solution suggestion) { 
		for (int i = 0; i < playersCards.size(); i++) {
			int j = (int)(Math.random() *  playersCards.size()); // Get a random index out of 52
			Card temp = playersCards.get(i); // Swap the cards
			playersCards.set(i, playersCards.get(j));
			playersCards.set(j, temp);
		}
		
		for(Card card : playersCards)
		{
			if((suggestion.getPerson().equals(card.getCardName()) ) || (suggestion.getWeapon().equals(card.getCardName())) || (suggestion.getRoom().equals(card.getCardName()) ) ) {
				
				return card;
				
			}
		}
		
		
		return null;}

	public String getPlayerName() {
		return playerName;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
	public void setRow(int row) {
		this.row = row;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public Color getColor() {
		return color;
	}
	
	public boolean isHuman() {
		return human;
	}


}
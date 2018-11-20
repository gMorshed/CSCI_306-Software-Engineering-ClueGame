package clueGame;
/**
*
* @author Abhaya Shrestha, Kirwinlvinodaq S Lawrence, Gazi Mahbub Morshed
*	Player class for the board
*	Describes each player with the components
* */
import java.awt.Color;
import java.awt.Graphics;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Player {

	private static final int WIDTH = 30;

	private String playerName;
	
	private int row ;
	
	private int column;
	
	private Color color;
	protected boolean human;
	private ArrayList <Card> playersCards;

	public boolean hasMoved;
	protected Set<Card> seenCards;
	public ArrayList<Card> getPlayersCards() {
		return playersCards;
	}

	public Player(int row, int column, Color color, String playerName) {
		this.row= row;
		this.color=color;
		this.column=column;
		this.playerName=playerName;
		this.human = false;
		this.hasMoved = false;
		playersCards = new ArrayList<Card>(); 
		seenCards = new HashSet<Card>();
	}
	
	public void setPlayersCards(ArrayList<Card> playersCards) {
		this.playersCards = playersCards;
	}

	public void receiveCard(Card card) {
		playersCards.add(card);
		seenCards.add(card);
	}
/**
 * disprovesuggestion randomizes or shuffles the cards in the players hand and check if the suggestion or accusation matches any one card than 
 * returns that card and if not thn returns null
 * @param suggestion
 * @return
 */
	public Card disproveSuggestion(Solution suggestion) { 
		for (int i = 0; i < playersCards.size(); i++) {
			int j = (int)(Math.random() *  playersCards.size()); // Get a random index out of 52
			Card temp = playersCards.get(i); // Swap the cards
			playersCards.set(i, playersCards.get(j));
			playersCards.set(j, temp);
		}
		
		
		for (Player p : Board.getInstance().getPlayerList()) {
			for(Card card : p.getPlayersCards()) {	
				if((suggestion.getPerson().equals(card.getCardName()) ) || (suggestion.getWeapon().equals(card.getCardName())) || (suggestion.getRoom().equals(card.getCardName()) ) ) {
					
					for(Player p1: Board.getInstance().getPlayerList()) { //I need to show the disapproved card to everyone, 
						//so I add the disapproved card to everyone's seen list except the player who disapproved it, becasue he already have that card on his hand.
							p1.seenCards.add(card);		
					}
					return card;
					
				}
			}
		}
		
		
		
		return null;
		}

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
	
	/**
	 * Sets the location of the player
	 * @param row
	 * @param column
	 */
	public void setLocation(int row, int column) {
		setRow(row);
		setColumn(column);
	}

	/**
	 * Draws the graphics for the player.
	 * Sets the color of the player with the respective color,
	 * and draws the player in x and y coordinate scaled accordingly to the width of the board.
	 * 
	 * @param g
	 */
	public void draw(Graphics g) {
		int y= this.getColumn() * WIDTH;
		int x= this.getRow() * WIDTH;
		g.setColor(this.getColor());
		g.fillOval(y,x, WIDTH, WIDTH);
	}
}
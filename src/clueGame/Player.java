package clueGame;

import java.awt.Color;
import java.io.FileReader;

public class Player {

	private String playerName;
	
	private int row ;
	
	private int column;
	
	private Color color;
	private boolean human;
	
	public Player(int row, int column, Color color, String playerName) {
		this.row= row;
		this.color=color;
		this.column=column;
		this.playerName=playerName;
		this.human = false;
	}
	
	

	public Card disproveSuggestion(Solution suggestion) { return null;}

	public String getPlayerName() {
		return playerName;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public Color getColor() {
		return color;
	}
	
	public boolean isHuman() {
		return human;
	}


}
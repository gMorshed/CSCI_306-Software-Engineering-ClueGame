package clueGame;

import java.awt.Color;
import java.io.FileReader;

public class Player {

	private String playerName;
	
	private int row ;
	
	private int column;
	
	private Color color;
	private boolean human;
	
	public Player() {
		row=0;
		color=null;
		column=0;
		playerName="";
		human = false;
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
		return false;
	}


}
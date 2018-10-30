package clueGame;

import java.awt.Color;
import java.util.Set;

public class ComputerPlayer extends Player {
	
	public BoardCell pickLocation(Set<BoardCell> targets) {
		return null;
	}
	
	public void makeAccusation() {
	}
	
	public void createSugestion() {
	}

	public ComputerPlayer(int row, int column, Color color, String playerName) {
		super(row, column, color, playerName);
	}

}
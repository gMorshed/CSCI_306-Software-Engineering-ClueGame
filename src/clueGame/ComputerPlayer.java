package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Set;

public class ComputerPlayer extends Player {
	private ArrayList <BoardCell> visited_list;
	public BoardCell pickLocation(Set<BoardCell> targets) {
		BoardCell cell= new BoardCell(0,0);
		return cell;
	}
	
	public void makeAccusation() {
	}
	
	public void createSugestion() {
	}

	public ComputerPlayer(int row, int column, Color color, String playerName) {
		super(row, column, color, playerName);
		visited_list=new ArrayList<BoardCell>();
	}

	public ArrayList<BoardCell> getVisited_list() {
		return visited_list;
	}


}
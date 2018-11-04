package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Set;

public class ComputerPlayer extends Player {
	private BoardCell lastRoomVisited=new BoardCell(0, 0);//have to create a boardCell here
	private ArrayList<Card> seenCards;
	public BoardCell pickLocation (Set<BoardCell> targets) {
		ArrayList<BoardCell> listOfCell=new ArrayList<BoardCell>(); //we will add the cells into this list as long as they are not a doorway to room, and pick a random one
		for(BoardCell cell: targets) {
			if(cell.isDoorway()) {
				if(!lastRoomVisited.equals(cell)) {
					lastRoomVisited=cell;
					return cell;
				}
			}
			listOfCell.add(cell);
		}
		int j = (int)(Math.random() *  listOfCell.size());
		return(listOfCell.get(j));
	}
	
	public void makeAccusation() {
	}

	public ComputerPlayer(int row, int column, Color color, String playerName) {
		super(row, column, color, playerName);
	}

	public Solution createSuggestion(ArrayList<Card> playersCard, String roomName) {
		Solution solution=new Solution("abcd", "efgh","ijkf");
		return solution;
	}
	public void addSeenCard(Card c) {
		seenCards.add(c);
	}
	
	public void clearSeenCard() {
		seenCards.clear();
	}
}
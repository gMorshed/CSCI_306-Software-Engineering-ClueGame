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
		seenCards=new ArrayList <Card> ();
	}

	public Solution createSuggestion(ArrayList<Card> playersCard, String roomName) {
		Card selectedPerson = new Card("INVALID", clueGame.CardType.PERSON); //make some dummy cards 
		Card selectedWeapon = new Card("INVALID", clueGame.CardType.WEAPON);
		//randomize the deck
		for (int i = 0; i < playersCard.size(); i++) {
			int j = (int)(Math.random() *  playersCard.size()); // Get a random index out of 52
			Card temp = playersCard.get(i); // Swap the cards
			playersCard.set(i, playersCard.get(j));
			playersCard.set(j, temp);
		}
		for(Card c : playersCard) {
			boolean haveSeen=false;
			for(Card seenC: seenCards) {
				if(c.getCardName().equals(seenC.getCardName())) {
					haveSeen=true;
				}
			}
			
			if(!haveSeen) {
				
				//System.out.println(c.getCardName()+ c.getCardType());
				if(c.getCardType() == clueGame.CardType.PERSON) {
					selectedPerson = c;
				}
				else if(c.getCardType() == clueGame.CardType.WEAPON) {
					selectedWeapon = c;
				}
			}
		}
		Solution lastSolution = new Solution(selectedPerson.getCardName(), roomName, selectedWeapon.getCardName());
		return lastSolution;
	}
	public void addSeenCard(Card c) {
		seenCards.add(c);
	}
	
	public void clearSeenCard() {
		seenCards.clear();
	}
	
}
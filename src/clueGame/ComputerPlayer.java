package clueGame;
/**
*
* @author Abhaya Shrestha, Kirwinlvinodaq S Lawrence, Gazi Mahbub Morshed

* */
import java.awt.Color;


import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ComputerPlayer extends Player {
	private BoardCell lastRoomVisited=new BoardCell(0, 0);//have to create a boardCell here
	private Solution solutionForAccusation=null;
	
	public void setSolutionForAccusation(Solution solutionForAccusation) {
		this.solutionForAccusation = solutionForAccusation;
	}

	/**
	 * pickLocation picks a room to visit if it is within target as long as it is not the last visited one, otherwise choose randomly
	 * @param targets
	 * @return
	 */
	public BoardCell pickLocation (Set<BoardCell> targets) {
		ArrayList<BoardCell> listOfCell=new ArrayList<BoardCell>(); //we will add the cells into this list as long as they are not a doorway to room, and pick a random one
		for(BoardCell cell: targets) {
			if(cell.isDoorway() && cell.getInitial() != lastRoomVisited.getInitial()  ) { //if it is a doorway to a room, that means we will have to visit that room unless the room is the last visited one
				if(!lastRoomVisited.equals(cell)) { //if it not the last visited room
					lastRoomVisited=cell;
					return cell;
				}
			}
			listOfCell.add(cell); //as long as the cells are not a door way to a room or the doorway to the room is the last visited one, add the cells to this list 
			//from which we will pick a random one to pick
		}
		int j = (int)(Math.random() *  listOfCell.size());//getting a randomized index 
		return(listOfCell.get(j));//return a cell randomly
	}
	
	public void makeAccusation() {
		 	//Solution actualAnswer = Board.getInstance().getGameSolution();
			Solution actualAnswer = new Solution("ABCD", "JFBI", "BHF");
			if(solutionForAccusation != null) { //when solution for accusation is not null, that means this solution couldn't be disapporved by anyone
				for(Card card : this.getPlayersCards()) { 
					if(! (card.getCardName().equals(solutionForAccusation.room)) ) {
						if(  (solutionForAccusation.getPerson().equals(actualAnswer.getPerson())) &&  (solutionForAccusation.getRoom().equals(actualAnswer.getRoom())) && (solutionForAccusation.getWeapon().equals(actualAnswer.getWeapon()))   ) {
							JFrame frame = new JFrame();
							String winningMessage = "Computer Player won, the accustion was "+solutionForAccusation.getPerson()+" "+ solutionForAccusation.getRoom()+" "+solutionForAccusation.getWeapon()+".";
							JOptionPane.showMessageDialog(frame, winningMessage,"LOST!!!!", JOptionPane.INFORMATION_MESSAGE);
							System.exit(0);
						}
						else {
							JFrame frame = new JFrame();
							String losingMessage = "Ohh, NOO! That's not correct. The accusation was "+solutionForAccusation.getPerson()+" "+ solutionForAccusation.getRoom()+" "+solutionForAccusation.getWeapon()+".";
							JOptionPane.showMessageDialog(frame,losingMessage , "Sorry", JOptionPane.INFORMATION_MESSAGE);
							for(Player p : Board.getInstance().getPlayerList()) { //letting every computer player know that, the accusation has been rejected so don't make the accusation
								if(!p.isHuman()) {
									ComputerPlayer compPayer = (ComputerPlayer) p;
									compPayer.setSolutionForAccusation(null);
								}
							}
							Board.getInstance().incrementCurrentPlayer();
							Board.getInstance().repaint();
							break;
						}
					}
				}
			}
		
	}

	public ComputerPlayer(int row, int column, Color color, String playerName) {
		super(row, column, color, playerName);
	}
	/**
	 * creating a suggestion with the room that I am in and with a Person and a weapon card as long as I haven't seem them
	 * 
	 */
	public Solution createSuggestion(ArrayList<Card> playersCard, String roomName) {
		Card selectedPerson = new Card("INVALID", clueGame.CardType.PERSON); //make some dummy cards 
		Card selectedWeapon = new Card("INVALID", clueGame.CardType.WEAPON);
		//randomize the deck
		ArrayList<Card> unseenCards=new ArrayList<Card>();
		unseenCards.addAll(Board.getInstance().getAllCards());
		unseenCards.removeAll(seenCards);
		
		int index = 0;
		Random rand = new Random();
		int countSuggestion =0; //I have the room as a suggestion, now I need two more suggestion to make it a package of three
		while(countSuggestion < 2) {
			index = rand.nextInt(unseenCards.size());
			// Adding a person for the solution first
			if (countSuggestion == 0) {
				if (unseenCards.get(index).getCardType() == CardType.PERSON) {
					selectedPerson = unseenCards.get(index);
					unseenCards.remove(index);
					countSuggestion++;
				}	
			}

			// Adding a weapon for the solution second
			else if (countSuggestion == 1) {
				if (unseenCards.get(index).getCardType() == CardType.WEAPON) {
					selectedWeapon = unseenCards.get(index);
					unseenCards.remove(index);
					countSuggestion++;
				}	
			}
		}
		Solution lastSolution = new Solution(selectedPerson.getCardName(), roomName, selectedWeapon.getCardName()); //I need to make a suggestion with a person card, the room that I am in and with a weapon card
		return lastSolution;
	}
	/**
	 * this is for testing 
	 * @param c
	 */
	public void addSeenCard(Card c) {
		seenCards.add(c);
	}
	/**
	 * this is for testing 
	 */
	public void clearSeenCard() {
		seenCards.clear();
	}
	
	/**
	 * Makes the move for the computer player
	 * This method will later implement handling suggestion and accusation
	 * @param board
	 */
	public Solution makeMove(Board board) {
		makeAccusation();
		Solution solution = new Solution("INVALID", "INVALID", "INVALID");
		BoardCell cell = this.pickLocation(board.getTargets());
		((board.getPlayerList()).get(board.currentPlayer)).setLocation(cell.getRow(), cell.getColumn());
		(board.getPlayerList()).get(board.currentPlayer).hasMoved = true;
		if(cell.isRoom()) {
			String str = board.getLegend().get(cell.getInitial());
			Solution soLution = createSuggestion(this.getPlayersCards(), str) ;
			return soLution;
		}
		return solution;
	}
	
}
package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Board;

public class DetectiveNotes extends JDialog {
	//instance variables
	private JComboBox<String> personGuess, roomGuess, weaponGuess;
	/**
	 * Constructor
	 */
	public DetectiveNotes(Board board) {
		setTitle("Detective Notes");
		setSize(700, 700);
		setLayout(new GridLayout(3, 2));
		add(showPeople(board));
		add(showPersonGuess(board));
		add(showRooms());
		add(showRoomGuess(board));
		add(showWeapons());
		add(showWeaponGuess(board));
		setVisible(true);
		//setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
	}
	
	/**
	 * Creates the panel for the people
	 * shown in the diagram
	 * for this dialog class
	 * @return
	 */
	public JPanel showPeople(Board board) {
		JPanel peoplePanel = new JPanel();
		peoplePanel.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		peoplePanel.setLayout(new GridLayout(3, 2));
		for(Card card : board.getAllCards()) {
			if(card.getCardType()==CardType.PERSON)
				peoplePanel.add(new JCheckBox(card.getCardName()));
		}
		return peoplePanel;
	}
	
	/**
	 * Creates the Person Guess panel
	 * shown in the diagram
	 * for this dialog class
	 * @return
	 */
	
	public JPanel showPersonGuess(Board board) {
		personGuess = new JComboBox<String>();
		for(Card card: board.getAllCards()) {
			if(card.getCardType() == CardType.PERSON) {
			personGuess.addItem(card.getCardName());
			}
		}
		personGuess.addItem("Unsure");
		JPanel personGuessPanel = new JPanel();
		personGuessPanel.add(personGuess, BorderLayout.WEST);
		personGuessPanel.setBorder(new TitledBorder(new EtchedBorder(), "Person Guess"));
		personGuessPanel.setLayout(new GridLayout(1, 2));
		return personGuessPanel;
	}
	/**
	 * Creates the Rooms panel 
	 * shown in the diagram
	 * for this dialog class
	 * @return
	 */
	public JPanel showRooms() {
		return new JPanel();
	}
	
	/**
	 * Creates the Room Guess
	 * panel shown in the diagram
	 * for this dialog class
	 * @return
	 */
	public JPanel showRoomGuess(Board board) {
		roomGuess = new JComboBox<String>();
		for(Card card : board.getAllCards()) {
			if(card.getCardType() == CardType.ROOM) {
				roomGuess.addItem(card.getCardName());
			}
		}
		roomGuess.addItem("Unsure");
		JPanel roomsGuessPanel = new JPanel();
		roomsGuessPanel.add(roomGuess);
		roomsGuessPanel.setBorder(new TitledBorder(new EtchedBorder(), "Room Guess"));
		roomsGuessPanel.setLayout(new GridLayout(1, 2));
		return roomsGuessPanel;
	}
	/**
	 * Creates the Weapons
	 * panel shown in the diagram
	 * for this dialog class
	 * @return
	 */
	public JPanel showWeapons() {
		return new JPanel();
	}
	/**
	 * Creates the Weapon Guess
	 * panel shown in the diagram
	 * for this dialog class
	 * @return
	 */
	public JPanel showWeaponGuess(Board board) {
		weaponGuess = new JComboBox<String>();
		for(Card card: board.getAllCards()) {
			if(card.getCardType() == CardType.WEAPON){
				weaponGuess.addItem(card.getCardName());}
		}
		weaponGuess.addItem("Unsure");
		JPanel weaponGuessPanel = new JPanel();
		weaponGuessPanel.add(weaponGuess);
		weaponGuessPanel.setBorder(new TitledBorder(new EtchedBorder(), "Weapon Guess"));
		weaponGuessPanel.setLayout(new GridLayout(1, 2));
		
		return weaponGuessPanel;
	}
	
	/**
	 * Main method to test
	 */
	public static void main(String[] args) {
		Board board;
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("Board_Layout.csv", "ClueRooms.txt");	
		board.setPlayerConfigFile("people.txt");
		board.setWeaponConfigFile("weapon.txt");
		// Initialize will load BOTH config files 
		board.initialize();
		board.dealCards();
		DetectiveNotes detectiveNotes = new DetectiveNotes(board);
	}
	
	
	
	
	
	
	
	
}

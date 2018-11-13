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
/**
 * 
 * @author Abhaya Shrestha, Kirwinlvinodaq S Lawrence, Gazi Mahbub Morshed 
 * 
 * Creates the detective note which is a dialog box that has several panels, such as weapon guess, people guess,
 * etc. show in the diagram for the assignment. This dialog box is later used in the GameControlGUI class. 
 * 
 *
 */
public class DetectiveNotes extends JDialog {
	//instance variables
	private JComboBox<String> personGuess, roomGuess, weaponGuess; // use the respective named combo box for each guess panel
	/**
	 * Constructor
	 * Sets up a dialog box of size 700 by 700, and adds all the panels (weapon guess,
	 * people guess etc.) into the dialog box
	 */
	public DetectiveNotes(Board board) { 
		setTitle("Detective Notes");
		setSize(700, 700);
		
		setLayout(new GridLayout(3, 2));
		add(showPeople(board));
		add(showPersonGuess(board));
		add(showRooms(board));
		add(showRoomGuess(board));
		add(showWeapons(board));
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
		personGuess.addItem("Unsure"); // adding an unsure combo box item for the best guess 
		JPanel personGuessPanel = new JPanel();
		personGuessPanel.add(personGuess);
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
	public JPanel showRooms(Board board) {
		JPanel roomPanel = new JPanel();
		roomPanel.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		roomPanel.setLayout(new GridLayout(5, 2));
		for(Card card : board.getAllCards()) {
			if(card.getCardType()==CardType.ROOM)
				roomPanel.add(new JCheckBox(card.getCardName()));
		}
		return roomPanel;
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
	public JPanel showWeapons(Board board) {
		JPanel weaponPanel = new JPanel();
		weaponPanel.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
		weaponPanel.setLayout(new GridLayout(3, 2));
		for(Card card : board.getAllCards()) {
			if(card.getCardType()==CardType.WEAPON)
				weaponPanel.add(new JCheckBox(card.getCardName()));
		}
		return weaponPanel;
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
}

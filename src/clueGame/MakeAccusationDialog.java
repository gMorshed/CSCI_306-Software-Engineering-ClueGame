package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
/**
 * 
 * @author Abhaya Shrestha, Kirwinlvinodaq S Lawrence, Gazi Mahbub Morshed
 *
 * Makes the accusation Dialog box when it is the players turn. The room, weapons, and person are all selected by the
 * humans. 
 */
public class MakeAccusationDialog extends JDialog{
	private JComboBox<String> person, weapon,room;
	String weapontext;
	String persontext;
	String roomtext;
	private ArrayList <Card> allCards;
	/**
	 * Constructor
	 */
	public MakeAccusationDialog() {
		allCards= Board.getInstance().getAllCards();
		randomizeCards();
		setTitle("Make a Guess");
		setSize(400, 300);
		setLayout(new GridLayout(4, 2));
		add(firstRow());
    	add(secondRow());
    	add(thirdRow());
		add(fourthRow());
		setVisible(true);
	}
	
	/**
	 *  Submit button 
	 * when human player submit's the accusation selected and cross checks with the actual solution
	 * if it is not the right solution, it will show a comment stating it is not the solution.
	 * Else the human wins the game. 
	 * @return
	 */
	public JButton submit() {
		JButton submitButton = new JButton("Submit");
		class SubmitListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				roomtext = (String)room.getSelectedItem();
				persontext = (String)person.getSelectedItem();
				weapontext = (String)weapon.getSelectedItem();
				Solution solution = accusationForHuman(); 
				//this is for the human player
				Solution actualSolution = Board.getInstance().getGameSolution();
				if(  solution.getRoom().equals(actualSolution.getRoom())  &&  solution.getPerson().equals(actualSolution.getPerson()) && solution.getWeapon().equals(actualSolution.getWeapon()) ) {
					JFrame frame = new JFrame();
					JOptionPane.showMessageDialog(frame, "WOW, you win", "Congrats!!!!", JOptionPane.INFORMATION_MESSAGE);
					System.exit(0);
				}
				else {
					JFrame frame = new JFrame();
					JOptionPane.showMessageDialog(frame, "Ohh, NOO! That's not correct", "Sorry", JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}
				Board.getInstance().incrementCurrentPlayer();
				Board.getInstance().repaint();
			}
		}
		submitButton.addActionListener(new SubmitListener());
		return submitButton;
	}
	/**
	 * Cancel button just to cancel an accusation for a human
	 * when they decide not to make a accusation.
	 * @return
	 */
	public JButton cancel() {
		JButton cancelButton = new JButton("Cancel");
		
		class ExitButton implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				dispose();
			}
			
		}
		
		cancelButton.addActionListener(new ExitButton());
		return cancelButton;
	}
	
	/**
	 * First row of the JDialog box will show the 
	 * Your room text, and a JCombo Box for all the rooms
	 * this combo box will be edited once the player selects a room
	 * @return
	 */
	public JPanel firstRow() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 2));
		JLabel roomLabel = new JLabel("Your Room");
		panel.add(roomLabel);
		room = new JComboBox<String>();
		for(Card card: allCards ) {
			if(card.getCardType() == CardType.ROOM) {
				room.addItem(card.getCardName());
			}
		}
		panel.add(room);
		return panel;
	}
	
	/**
	 * Will show the second row of the Dialog 
	 * Will make the accusation for the person
	 * 
	 * @return
	 */
	public JPanel secondRow() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 2));
		JLabel personLabel = new JLabel("Person");
		panel.add(personLabel);
		person = new JComboBox<String>();
		for(Card card: allCards) {
			if(card.getCardType() == CardType.PERSON) {
				person.addItem(card.getCardName());
			}
		}

		panel.add(person);
		return panel;
	}
	/**
	 * Will show the third row of the Dialog
	 * Will make the accusation for the weapon
	 * 
	 * @return
	 */
	public JPanel thirdRow() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 2));
		weapon = new JComboBox<String>();
		JLabel weaponLabel = new JLabel("Weapon");
		panel.add(weaponLabel);
		for(Card card: allCards ) {
			if(card.getCardType() == CardType.WEAPON) {
				weapon.addItem(card.getCardName());
			}
		}
		
		panel.add(weapon);
		return panel;
	}
	/**
	 * Button panel only has the submit and the cancel buttons
	 * 
	 * @return
	 */
	
	public JPanel fourthRow() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 2));
		panel.add(submit());
		panel.add(cancel());
		return panel;
	}
	/**
	 * Accusation for the accusation that the human makes as a solution 
	 * that will be tested to see if it is true or false
	 * @return
	 */
	public Solution accusationForHuman() {
		Solution solution = new Solution(persontext, roomtext, weapontext);
		return solution;
	}
	/**
	 * Used to randomize the combo boxes every time the dialog box is popped.
	 */
	private void  randomizeCards () {
		for (int i = 0; i < allCards.size(); i++) {
			int j = (int) (Math.random() * allCards.size()); // Get a random index out of 52
			Card temp = allCards.get(i); // Swap the cards
			allCards.set(i, allCards.get(j));
			allCards.set(j, temp);
		}
	}

}
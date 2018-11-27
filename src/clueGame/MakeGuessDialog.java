package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MakeGuessDialog extends JDialog{
	private JComboBox<String> person, weapon;
	public JTextField room;
	private String weapontext;
	private String persontext;
	private ArrayList <Card> allCards;
	public MakeGuessDialog() {
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
	
	public JButton submit() {
		JButton submitButton = new JButton("Submit");
		class SubmitListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				persontext = (String)person.getSelectedItem();
				weapontext = (String)weapon.getSelectedItem();
				Solution solution = suggestionForHuman(); 
				//have to move the other person the room of the suggested solution
				
				//this is for the human player
				GameControlGUI.guess.setText(solution.person+" "+solution.room+" "+solution.weapon);
				Card disAppCard = Board.getInstance().getPlayerList().get(Board.getInstance().currentPlayer).disproveSuggestion(solution);
				if(disAppCard != null) {
					GameControlGUI.guessResult.setText(disAppCard.getCardName());
				}
				else {
					GameControlGUI.guessResult.setText("No new Clue");
					for(Player p : Board.getInstance().getPlayerList()) {
						if(!p.isHuman()) {
							ComputerPlayer compPayer = (ComputerPlayer) p;
							compPayer.setSolutionForAccusation(solution);
						}
					}
				}
				dispose();
			}
		}
		submitButton.addActionListener(new SubmitListener());
		return submitButton;
	}
	
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
	 * Your room text, and an empty textField
	 * this textField will be edited once the player is in a room
	 * @return
	 */
	public JPanel firstRow() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 2));
		JLabel personLabel = new JLabel("Your room");
		panel.add(personLabel);
		room = new JTextField(5);
		room.setEditable(false);
		panel.add(room);
		return panel;
		
	}
	
	/**
	 * Will show the second row of the Dialog 
	 * Will make the suggestion for the person
	 *  for Guess panel in GameControl GUI
	 * @return
	 */
	public JPanel secondRow() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 2));
		JLabel personLabel = new JLabel("Person");
		panel.add(personLabel);
		person = new JComboBox<String>();
		for(Card card: (allCards) ) {
			if(card.getCardType() == CardType.PERSON) {
				person.addItem(card.getCardName());
			}
		}
		panel.add(person);
		return panel;
	}
	/**
	 * Will show the third row of the Dialog
	 * Will make the suggestion for the weapon
	 * for Guess panel in the GameControl GUI
	 * @return
	 */
	public JPanel thirdRow() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 2));
		weapon = new JComboBox<String>();
		JLabel personLabel = new JLabel("Weapon");
		panel.add(personLabel);
		for(Card card: (allCards)) {
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
	public Solution suggestionForHuman() {
		Solution solution = new Solution(persontext, room.getText(), weapontext);
		return solution;
	}
	private void  randomizeCards () {
		for (int i = 0; i < allCards.size(); i++) {
			int j = (int) (Math.random() * allCards.size()); // Get a random index out of 52
			Card temp = allCards.get(i); // Swap the cards
			allCards.set(i, allCards.get(j));
			allCards.set(j, temp);
		}
	}
	

}
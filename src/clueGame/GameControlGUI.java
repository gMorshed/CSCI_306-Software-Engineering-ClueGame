package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
/**
*
* @author Abhaya Shrestha, Kirwinlvinodaq S Lawrence, Gazi Mahbub Morshed

* */
public class GameControlGUI extends JPanel {
	private JTextField textField; // used for text field

	public GameControlGUI()
	{
		// Create a layout with 2 rows
		setLayout(new GridLayout(2,0));
		JPanel panel;
		panel = createTurnPanel();
		add(panel);
		panel = createButtonPanel();
		add(panel);
		panel = createRollDiePanel();
		add(panel);
		panel = createGuessPanel();
		add(panel);
		panel = createGuessResultPanel();
		add(panel);
		
	}
	//display of the roll of the die
	 private JPanel createRollDiePanel() {
		 	JPanel panel = new JPanel();
		 	// Use a grid layout, 1 row, 2 elements (label, text)
			panel.setLayout(new GridLayout(1, 2));
		 	JLabel nameLabel = new JLabel("Roll");
			textField = new JTextField(20);
			textField.setEditable(false); // can't edit this text field
			panel.add(nameLabel); // add the nameLabel and the textfield in the panel
			panel.add(textField);
			panel.setBorder(new TitledBorder (new EtchedBorder(), "Die")); // titling the border Die
			panel.setPreferredSize(new Dimension(2, 2));
			return panel;
	}
	 //display of whose turn it is
	 private JPanel createTurnPanel() {
		 JPanel panel = new JPanel();
		 panel.setLayout(new GridLayout(1, 2));
		 JLabel nameLabel = new JLabel("");
		 textField = new JTextField();
		 textField.setEditable(false);
		 
		 panel.add(nameLabel); // this is just going to be a panel rather than having the border as well
		 panel.add(textField);
		 panel.setBorder(new TitledBorder(new EtchedBorder(), "Whose turn?"));
		 return panel;
	 }
	 // display of guesses made by other players
	 private JPanel createGuessPanel() {
		 JPanel panel = new JPanel();
		 panel.setLayout(new GridLayout(2, 2));
		 JLabel nameLabel = new JLabel("Guess");
		 textField = new JTextField(20);
		 textField.setEditable(false);
		 panel.add(nameLabel);
		 panel.add(textField);
		 panel.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
		 return panel;
	 }
	 //display of the result
	 private JPanel createGuessResultPanel() {
		 JPanel panel = new JPanel();
		 panel.setLayout(new GridLayout(2, 2));
		 JLabel nameLabel = new JLabel("Response");
		 textField = new JTextField(20);
		 textField.setEditable(false);
		 panel.add(nameLabel);
		 panel.add(textField);
		 panel.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));
		 return panel;
	 }
	//Creates the buttons Next Player and make an accusation 
	private JPanel createButtonPanel() {
		JButton nextPlayerButton = new JButton("Next Player");
		JButton accusationButton = new JButton("Make an accuasation");
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 2)); // 2 rows 2 elements which are both buttons
		panel.add(nextPlayerButton);
		panel.add(accusationButton);
		return panel;
	}
	//main method to display the panel
	public static void main(String[] args) {
		// Create a JFrame with all the normal functionality
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Clue Game");
		frame.setSize(750, 250);	
		// Create the JPanel and add it to the JFrame
		GameControlGUI gui = new GameControlGUI();
		frame.add(gui, BorderLayout.CENTER);
		// Now let's view it
		frame.setVisible(true);
	}


}

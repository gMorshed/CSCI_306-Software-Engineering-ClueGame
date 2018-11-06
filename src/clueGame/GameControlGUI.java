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
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,2));
		JPanel intermediatePanel = new JPanel();
		intermediatePanel.setLayout(new GridLayout(1,3));
		JButton nextPlayerButton = new JButton("Next Player"); // creates the button
	    JButton accusationButton = new JButton("Make an accuasation");
	
		add(panel);
		add(intermediatePanel);
		panel.add(createTurnPanel());
		panel.add(nextPlayerButton);
		panel.add(accusationButton);
		
		intermediatePanel.add(createRollDiePanel());
		intermediatePanel.add(createGuessPanel());
		intermediatePanel.add(createGuessResultPanel());
		
	}
	//display of the roll of the die
	 private JPanel createRollDiePanel() {
		 	JPanel diePanel = new JPanel();
		 	// diePanel.setLayout(new GridLayout(1,1));
		 	// Use a grid layout, 1 row, 2 elements (label, text)
		 	diePanel.setLayout(new GridLayout(2, 2));
		 	JLabel nameLabel = new JLabel("Roll");
			textField = new JTextField(5);
			nameLabel.setHorizontalAlignment(JLabel.CENTER);
			nameLabel.setVerticalAlignment(JLabel.CENTER);
			diePanel.setBorder(new TitledBorder (new EtchedBorder(), "Die")); // titling the border Die
			textField.setEditable(false); // can't edit this text field
			diePanel.add(nameLabel); // add the nameLabel and the textfield in the panel
			diePanel.add(textField);
			return diePanel;
	}
	 //display of whose turn it is
	 private JPanel createTurnPanel() {
		 JPanel panel = new JPanel();
		 panel.setLayout(new GridLayout(2, 2));
		 JLabel nameLabel = new JLabel("Whose turn?");
		 nameLabel.setHorizontalAlignment(JLabel.CENTER);
		 nameLabel.setVerticalAlignment(JLabel.CENTER);
		 textField = new JTextField(20);
		 textField.setEditable(false);
		 
		 panel.add(nameLabel); // this is just going to be a panel rather than having the border as well
		 panel.add(textField);
		 JPanel panelBackground = new JPanel();
		 panelBackground.add(panel);
		 return panelBackground;
	 }
	 // display of guesses made by other players
	 private JPanel createGuessPanel() {
		 JPanel panel = new JPanel();
		 panel.setLayout(new GridLayout(2, 2));
		 JLabel nameLabel = new JLabel("Guess");
		 textField = new JTextField(5);
		nameLabel.setHorizontalAlignment(JLabel.CENTER);
		nameLabel.setVerticalAlignment(JLabel.CENTER);
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
		 textField = new JTextField(5);
		 nameLabel.setHorizontalAlignment(JLabel.CENTER);
		 nameLabel.setVerticalAlignment(JLabel.CENTER);
		 textField.setEditable(false);
		 panel.add(nameLabel);
		 panel.add(textField);
		 panel.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));
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

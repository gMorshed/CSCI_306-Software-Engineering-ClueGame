package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Abhaya Shrestha, Kirwinlvinodaq S Lawrence, Gazi Mahbub Morshed
 * 
 * Clue game GUI for Game control panel.
 * This class will just let you play the game. 
 * 
 */
public class GameControlGUI extends JPanel {
	private JTextField textField; // used for text field

	/**
	 * The game Control GUI constructor will add all the panels and the buttons
	 */
	public GameControlGUI() {
		
		// Create a layout with 2 rows
		setLayout(new GridLayout(2, 0));
		JPanel panel = new JPanel();
		//JPanel board = boardPanel();
		
		panel.setLayout(new GridLayout(1, 2));
		JPanel intermediatePanel = new JPanel();
		intermediatePanel.setLayout(new GridLayout(1, 3));
		JButton nextPlayerButton = new JButton("Next Player"); // creates the button
		JButton accusationButton = new JButton("Make an accuasation");
		//add(boardPanel()); // adding the board
		add(panel); // adds it to the first row
		add(intermediatePanel);
		panel.add(createTurnPanel());
		panel.add(nextPlayerButton); // add the buttons to this panel
		panel.add(accusationButton);

		intermediatePanel.add(createRollDiePanel());
		intermediatePanel.add(createGuessPanel());
		intermediatePanel.add(createGuessResultPanel());
		

	}
	/**
	 * Creates the menu bar with the options to exit the game
	 * and to see the detective notes
	 * @return
	 */
	public JMenu createFileMenu() {
		JMenu menu = new JMenu("File");
		menu.add(createFileExitItem());
		menu.add(createDetectiveFileItem());
		return menu;
	}
	/**
	 * Creates the exit menu option for the menu bar
	 * This has an action listner for when you click exit,
	 * it will exit the game
	 * @return
	 */
	private JMenuItem createFileExitItem() {
		JMenuItem exit = new JMenuItem("Exit");
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
			
		}
		exit.addActionListener(new MenuItemListener());
		return exit;
	}
	/**
	 * Creates the Show Notes menu option for the menu bar
	 * This has an action listener for when you click Show Notes,
	 * it will show the detective notes for the game
	 * @return
	 */
	private JMenuItem createDetectiveFileItem() {
		JMenuItem detectiveNotes = new JMenuItem("Show Notes");
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
		    Board board = Board.getInstance();
			DetectiveNotes notes = new DetectiveNotes(board);
		}
			
		}
		detectiveNotes.addActionListener(new MenuItemListener());
		return detectiveNotes;
	}


	/**
	 * display of the roll of the die
	 * 
	 * @return
	 */
	private JPanel createRollDiePanel() {
		JPanel diePanel = new JPanel();
		// diePanel.setLayout(new GridLayout(1,1));
		// Use a grid layout, 1 row, 2 elements (label, text)
		diePanel.setLayout(new GridLayout(2, 2));
		JLabel nameLabel = new JLabel("Roll");
		textField = new JTextField(5);
		nameLabel.setHorizontalAlignment(JLabel.CENTER);
		nameLabel.setVerticalAlignment(JLabel.CENTER);
		diePanel.setBorder(new TitledBorder(new EtchedBorder(), "Die")); // titling the border Die
		textField.setEditable(false); // can't edit this text field
		diePanel.add(nameLabel); // add the nameLabel and the textfield in the panel
		diePanel.add(textField);
		return diePanel;
	}

	/**
	 * display of whose turn it is
	 * 
	 * @return
	 */
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

	/**
	 * display of guesses made by other players
	 * 
	 * @return
	 */
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
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
		return panel;
	}

	/**
	 * display of the result
	 * 
	 * @return
	 */
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
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
		return panel;
	}
	/**
	 * Create the board panel
	 * @return
	 */
	private JPanel boardPanel() {
		//JPanel panel = new JPanel();
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
		//panel.add(board);
		return board;
		
	}
	/**
	 * main method to display the panel
	 * This will display the frame with the board, game control gui, and the menu with detective notes. 
	 * @param args
	 */
	public static void main(String[] args) {
		// Create a JFrame with all the normal functionality
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Clue Game");
		frame.setSize(950, 950);
		// Create the JPanel and add it to the JFrame
		GameControlGUI gui = new GameControlGUI();
		frame.add(gui, BorderLayout.SOUTH);
		frame.add(gui.boardPanel(), BorderLayout.CENTER);
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(gui.createFileMenu());
		frame.setJMenuBar(menuBar);

		frame.setVisible(true);
		
	}

}

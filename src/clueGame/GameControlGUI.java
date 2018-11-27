package clueGame;

import clueGame.Board;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Abhaya Shrestha, Kirwinlvinodaq S Lawrence, Gazi Mahbub Morshed
 * 
 * Clue game GUI for Game control panel
 * displays buttons and text that communicate with the human player.
 * When buttons are pressed, it calls the appropriate methods in the Board class for processing.
 * 
 */
public class GameControlGUI extends JPanel {
	protected static JTextField die, turn, guess, guessResult; // used for text field 
	private String humanPlayerName="";
	private Player humanPlayer;
	private  Board board = Board.getInstance();
	public static int roll; // make the roll static because there is only one roll for the whole game
	public static boolean buttonpressed = false; // Board needs to know when the button has been pressed or not, one copy of that variable needed
	private int counter = 0; // counter used to see if we have gone past a first turn

	public String getHumanPlayerName() {
		return humanPlayerName;
	}

	/**
	 * The game Control GUI constructor will add all the panels and the buttons
	 */
	public GameControlGUI() {
		setLayout(new GridLayout(2, 0));
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 2));
		JPanel intermediatePanel = new JPanel();
		intermediatePanel.setLayout(new GridLayout(1, 3));
		add(panel); // adds it to the first row
		add(intermediatePanel);
		panel.add(createTurnPanel());
		panel.add(nextPlayerButton()); // add the buttons to this panel
		panel.add(accusationButton());

		intermediatePanel.add(createRollDiePanel());
		intermediatePanel.add(createGuessPanel());
		intermediatePanel.add(createGuessResultPanel());
		

	}
	
	public static boolean human_made_move=false; // the human should not be making an accussation after he/she clicks a target
	/**
	 * Used to make an accusation Button for the game control GUI
	 * It will make the accusation for the human
	 * @return
	 */
	private JButton accusationButton(){
		JButton accusationButton = new JButton("Make an accuasation");
		
		class AccusationListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				human_made_move=false;
				if (board.getPlayerList().get(board.currentPlayer).isHuman() && human_made_move == false && buttonpressed == true) {		
						MakeAccusationDialog accusation = new MakeAccusationDialog();
				}else{
						JFrame frame = new JFrame();
						JOptionPane.showMessageDialog(frame, "Once you click a target you can't make an accusation", "Error messagge", JOptionPane.INFORMATION_MESSAGE);
				}
			}
				
		
		
	}
		
		accusationButton.addActionListener(new AccusationListener()); 
		return accusationButton;
	}
	/**
	 * Next player button for the GameControlGUI. 
	 * Makes sure that the squares are not highlighted for the player when the game begins.
	 * Updates all the panels for the GameControlGUI depending on whether a player is a computer
	 * or a human
	 * @return
	 */
	private JButton nextPlayerButton(){
		JButton nextPlayerButton = new JButton("Next Player");
		
		class NextPlayerListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				//if there is guess or guess result to show, the strings will get overridden, otherwise this space string means no suggestions. 
				guess.setText(" ");
				guessResult.setText(" ");
				buttonpressed =  true; // the button pressed will signal to paint the targets in the board
				board.repaint();
				
				// only after next player is pressed or when the currentplayer has moved and the counter is not zero
				// that way the player will not be able to roll
				if((board.getPlayerList().get(board.currentPlayer).hasMoved && (counter !=0)) || (counter == 0)) {
					roll = (int)((Math.random()*6) + 1); // roll is random for each player
					die.setText(Integer.toString(roll));
				}
				turn.setText((board.getPlayerList()).get(board.currentPlayer).getPlayerName());
				if(((board.getPlayerList()).get(board.currentPlayer)).isHuman() ) { //if it is a human player
					
					if( board.getPlayerList().get(board.currentPlayer).hasMoved) {
						 board.repaint(); // move the player and move it
						 board.getPlayerList().get(board.currentPlayer).hasMoved = false; // reset has moved so that it can move it the next round
					}
					else if(counter !=0) {
						JFrame frame = new JFrame(); // if the player has not finished the turn print the error message in a Jframe
						JOptionPane.showMessageDialog(frame, "you need to finish your turn","Error message", JOptionPane.INFORMATION_MESSAGE);
					
					}
					
				
				  } 
				  
				else { // else if it is a computer player then just roll the die and move the computer player
					roll = (int)((Math.random()*6) + 1);
					die.setText(Integer.toString(roll));
					int x = ((board.getPlayerList()).get(board.currentPlayer)).getRow();
					int y = ((board.getPlayerList()).get(board.currentPlayer)).getColumn();
					board.calcTargets(x, y, roll);
					ComputerPlayer compPlayer = (ComputerPlayer) (board.getPlayerList().get(board.currentPlayer));
					//have to make a solution if the landing cell is a room
					Solution solution= compPlayer.makeMove(board);
					if(! (solution.person.equals("INVALID") || (solution.room.equals("INVALID")) || (solution.weapon.equals("INVALID")) )  ) {
						String strSolution = solution.person +" " + solution.room +" "+ solution.weapon;
						guess.setText(strSolution); //printed the guess of the player
						//now we will check if anyone could disapprove it
						Card disAppCard = compPlayer.disproveSuggestion(solution);
						if(disAppCard != null) {
							guessResult.setText(disAppCard.getCardName());
						}
						else {
							guessResult.setText("No new Clue");
							compPlayer.setSolutionForAccusation(solution);
						}
					}
					board.repaint();
					board.incrementCurrentPlayer(); 
				}
				counter ++;
				
		}
	}
		
		nextPlayerButton.addActionListener(new NextPlayerListener()); 
		//for this mouse clicker to be activated we need to add NextPlayerListener() 
		
		return nextPlayerButton;
		
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
	 * This has an action listener for when you click exit,
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
	
			DetectiveNotes notes = new DetectiveNotes(board);
		}
			
		}
		detectiveNotes.addActionListener(new MenuItemListener());
		return detectiveNotes;
	}


	/**
	 * display of the roll of the die
	 * @param textField2 
	 * 
	 * @return
	 */
	private JPanel createRollDiePanel() {
		JPanel diePanel = new JPanel();
		diePanel.setLayout(new GridLayout(2, 2));
		JLabel nameLabel = new JLabel("Roll");
		die = new JTextField(5);
		nameLabel.setHorizontalAlignment(JLabel.CENTER);
		nameLabel.setVerticalAlignment(JLabel.CENTER);
		diePanel.setBorder(new TitledBorder(new EtchedBorder(), "Die")); // titling the border Die
		die.setEditable(false); // can't edit this text field
		
		diePanel.add(nameLabel); // add the nameLabel and the textfield in the panel
		diePanel.add(die);
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
		turn = new JTextField(20);
		turn.setEditable(false);

		panel.add(nameLabel); // this is just going to be a panel rather than having the border as well
		panel.add(turn);
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
		guess = new JTextField(5);
		nameLabel.setHorizontalAlignment(JLabel.CENTER);
		nameLabel.setVerticalAlignment(JLabel.CENTER);
		guess.setEditable(false);
		panel.add(nameLabel);
		panel.add(guess);
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
		guessResult = new JTextField(5);
		nameLabel.setHorizontalAlignment(JLabel.CENTER);
		nameLabel.setVerticalAlignment(JLabel.CENTER);
		guessResult.setEditable(false);
		panel.add(nameLabel);
		panel.add(guessResult);
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
		return panel;
	}
	/**
	 * Create the board panel
	 * @return
	 */
	public JPanel boardPanel() {

		board.setConfigFiles("Board_Layout.csv", "ClueRooms.txt");	
		board.setPlayerConfigFile("people.txt");
		board.setWeaponConfigFile("weapon.txt");
		// Initialize will load BOTH config files 
		board.initialize();
		board.dealCards();
		ArrayList<Player>players = board.getPlayerList();
		for(Player p : players) { //i am getting the name of the human player so that 
			//I can print it in the game starting display message
			if(p.isHuman()) {
				humanPlayer = p;
				humanPlayerName = p.getPlayerName();
				break;
			}
		}
		
		return board;
		
	}
	/**
	 * Shows the cards the human player has in a panel
	 * @return
	 */
	public JPanel humanPlayerCardPanel() {
		JPanel cardsPanel = new JPanel();
		cardsPanel.setLayout(new GridLayout(humanPlayer.getPlayersCards().size(), 0));
		cardsPanel.setBorder(new TitledBorder(new EtchedBorder(), "My Cards"));
		for(Card card: (humanPlayer.getPlayersCards() ) ){
			JPanel aCard = new JPanel();
			aCard.setLayout(new GridLayout(1, 1));
			aCard.setBorder(new TitledBorder(new EtchedBorder(),  CardInString(card.getCardType())));
			JTextField text =new JTextField(10);
			text.setText(card.getCardName());
			text.setHorizontalAlignment(JLabel.CENTER);
			text.setEditable(false);
			aCard.add(text);
			cardsPanel.add(aCard);
		}
		return cardsPanel;
	}
	/**
	 * Used for the humanPlayerCardPanel
	 * 
	 * @param card
	 * @return
	 */
	private String CardInString(CardType card) {
		switch(card) {
		case PERSON:
			return "People";
		case WEAPON:
			return "Weapons";
		case ROOM:
			return "Rooms";
		}
		return null; 		
	}

}

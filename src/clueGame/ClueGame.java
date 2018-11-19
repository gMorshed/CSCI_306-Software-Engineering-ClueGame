package clueGame;


import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
/**
 * 
 * @author 
 * This class will display the game, and is used to play the game
 * the ClueGame class provides the main GUI for the game.
 */
public class ClueGame extends JFrame {

	public ClueGame() {
		// Create a JFrame with all the normal functionality
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Clue Game");
		setSize(750, 950);
		// Create the JPanel and add it to the JFrame
		GameControlGUI gui = new GameControlGUI();
		add(gui, BorderLayout.SOUTH);
		add(gui.boardPanel(), BorderLayout.CENTER);
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(gui.createFileMenu());
		setJMenuBar(menuBar);
		add(gui.humanPlayerCardPanel(), BorderLayout.EAST);
		setVisible(true);
		//When the game starts, you should display a message
		JOptionPane.showMessageDialog(this, "You are "+gui.getHumanPlayerName()+ ", press Next Player To begin play", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void main(String args[]) {
		ClueGame game = new ClueGame();		
	}

}

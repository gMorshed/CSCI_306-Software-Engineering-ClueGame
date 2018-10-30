package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Player;

public class gameSetupTests {

	private static Board board;
	@Before
	public void setUp() {
		/**
		 * This test will set up the board  of variety of cards of each type that 
		 * will be required for testing
		 */
		board = Board.getInstance();
		board.setConfigFiles("BoardLayout.csv", "Legends.txt");
		board.setPlayerConfigFile("Players.txt");
		//board.setWeaponConfigFile("Weapons.txt");
		board.initialize();
	}

	@Test
	public void testLoadPeople() {
		ArrayList<Player> playerList = board.getPlayerList(); //get the playerList from board
		
		assertEquals(playerList.get(0).getPlayerName(), "Mr. Stark"); //one computer player and the first player
		assertEquals(playerList.get(3).getPlayerName(), "Steve Rogers"); //one human player and the 3rd player
		assertEquals(playerList.get(5).getPlayerName(), "Thor"); //last player
		
		//now check for color
		assertEquals(playerList.get(0).getColor(), Color.RED); //1st player
		assertEquals(playerList.get(3).getColor(), Color.ORANGE); //3rd player
		assertEquals(playerList.get(5).getColor(), Color.MAGENTA); // last payer
		assertEquals(playerList.get(2).getColor(), Color.BLUE); //4th player
		
		//now check to see if they are human
		assertTrue(playerList.get(0).isHuman());
		assertTrue(playerList.get(3).isHuman());
		assertTrue(playerList.get(5).isHuman());
		//now check some of the row and columns
		assertEquals(playerList.get(0).getRow(), 0);
		assertEquals(playerList.get(1).getColumn(), 12);
		assertEquals(playerList.get(3).getRow(), 6); //human
		assertEquals(playerList.get(3).getColumn(), 2);
		assertEquals(playerList.get(4).getRow(), 16);
	}
	
	

}
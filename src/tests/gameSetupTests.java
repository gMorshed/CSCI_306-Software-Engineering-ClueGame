package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Player;
import clueGame.Card;
import clueGame.CardType;

public class gameSetupTests {

	private static Board board;
	@Before
	public void setUp() {
		/**
		 * This test will set up the board  of variety of cards of each type that 
		 * will be required for testing
		 */
		board = Board.getInstance();
		board.setConfigFiles("BoardLayout.csv", "ClueRooms.txt");
		board.setPlayerConfigFile("people.txt");
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
		assertFalse(playerList.get(0).isHuman()); //computer
		assertTrue(playerList.get(3).isHuman()); //human
		assertFalse(playerList.get(5).isHuman()); //computer
		//now check some of the row and columns
		assertEquals(playerList.get(0).getRow(), 0);
		assertEquals(playerList.get(1).getColumn(), 12);
		assertEquals(playerList.get(3).getRow(), 6); //human
		assertEquals(playerList.get(3).getColumn(), 2);
		assertEquals(playerList.get(4).getRow(), 16);
	}
	
	@Test
	public void testLoadDeckOfCards() {
		// Ensure the deck contains the correct total number of cards 
		ArrayList<Card> testDeck = board.getDeckOfCards();
		assertEquals(21 ,testDeck.size()); // should have 6 people, 6 weapons, and 9 rooms = 21 total
		
		// Ensure the deck contains the correct number of each type of card (room/weapon/person) 
		int countWeapons = 0;
		int countPeople = 0;
		int countRooms = 0;
		for(int i=0; i<testDeck.size(); i++) {
			if((testDeck.get(i)).getCardType() == CardType.WEAPON) {
				countWeapons++;
			}
			if((testDeck.get(i)).getCardType() == CardType.PERSON) {
				countPeople++;
			}
			if((testDeck.get(i)).getCardType() == CardType.ROOM) {
				countRooms++;
			}
			
		}
		
		assert(countPeople == 6);
		assert(countWeapons == 6);
		assert(countRooms == 9);
		
		
		
		//I choose one room, one weapon, and one person, 
		// and ensure the deck contains each of those (to test loading the names). 
	}
	
	

}
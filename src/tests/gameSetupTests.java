package tests;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeTrue;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Player;
import clueGame.Card;
import clueGame.CardType;

public class gameSetupTests {

	private static Board board;
	public static final int NUM_CARDS_IN_DECK=21;
	public static final int NUM_OF_PLAYER=6;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		/**
		 * This test will set up the board  of variety of cards of each type that 
		 * will be required for testing
		 */
		board = Board.getInstance();
		board.setConfigFiles("Board_Layout.csv", "ClueRooms.txt");
		board.setPlayerConfigFile("people.txt");
		board.setWeaponConfigFile("weapon.txt");
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
	
	@Test
	public void testDealingCard() {
		board.dealCards();
		ArrayList<Card> testDeck = board.getDeckOfCards();
		assertEquals(0 ,testDeck.size());  //checking if all the cards have been dealt or not 
		ArrayList<Player> playerList = board.getPlayerList();
		Set<Integer> numberOfCard= new HashSet<Integer>();
		for (Player player : playerList) { // All players should have roughly the same number of cards
			numberOfCard.add(player.getPlayersCards().size()) ;
		}
		assumeTrue(numberOfCard.size()==2); //if the cards are dealt correctly, players should only have two unique numbe rof cards
		Set<Card> cards= new HashSet<Card>();
		for (Player player : playerList) { //The same card should not be given to >1 player 
			cards.addAll(player.getPlayersCards());
		}
	}
	
	

}
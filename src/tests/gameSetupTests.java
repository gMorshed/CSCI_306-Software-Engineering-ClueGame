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

/**
 * 
 * @author Kirwinlvinodaq S Lawrence, Abhaya  Shrestha. Gazi Mahbub Morshed
 *
 *testunit is for testing the methods of loading people, creating deck of cards and dealing the cards
 *
 */


public class gameSetupTests {

	private static Board board;
	public static final int NUM_CARDS_IN_DECK=21;
	public static final int NUM_OF_PLAYER=6;
	
	/**
	 * This test will set up the board  of variety of cards of each type that 
	 * will be required for testing
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	
		board = Board.getInstance();
		board.setConfigFiles("Board_Layout.csv", "ClueRooms.txt");
		board.setPlayerConfigFile("people.txt");
		board.setWeaponConfigFile("weapon.txt");
		board.initialize();
	}
	
	/**
	 * test1LoadPeople tests that the players are being loaded correctly.
	 * This will check if the player has respective color, is human or computer, is in the right coordinate, 
	 * and has the respective name.
	 * 
	 * */
	@Test
	public void test1LoadPeople() {
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
	
	/**
	 * test2LoadDeckOfCards tests the deck is loaded properly, deck has each type of CardType, 
	 * and counting the number of players, weapons, and rooms are the same */
	@Test
	public void test2LoadDeckOfCards() {
		// Ensure the deck contains the correct total number of cards
		ArrayList<Card> testDeck = board.getDeckOfCards();
		assertEquals(NUM_CARDS_IN_DECK ,testDeck.size()); // should have 6 people, 6 weapons, and 9 rooms = 21 total
		
		// Ensure the deck contains the correct number of each type of card (room/weapon/person) 
		int countWeapons = 0;
		int countPeople = 0;
		int countRooms = 0;
		ArrayList<String> CardNames = new ArrayList<String>(); // list to put all the card names that appear
		
		Card person = new Card("Mr. Stark", CardType.PERSON); // ensure the deck contains each one of these
		Card weapon = new Card("Revolver", CardType.WEAPON);
		Card room = new Card("Mancave", CardType.ROOM);
		
		for(int i=0; i<testDeck.size(); i++) {
			if((testDeck.get(i)).getCardType() == CardType.WEAPON) {
				countWeapons++;
				CardNames.add((testDeck.get(i)).getCardName());
			}
			if((testDeck.get(i)).getCardType() == CardType.PERSON) {
				countPeople++;
				CardNames.add((testDeck.get(i)).getCardName());
			}
			if((testDeck.get(i)).getCardType() == CardType.ROOM) {
				countRooms++;
				CardNames.add((testDeck.get(i)).getCardName());
			}
			
		}
		assertTrue(CardNames.contains(person.getCardName())); 
		//I choose one room, one weapon, and one person, 
		// and ensure the deck contains each of those (to test loading the names). 
		assertTrue(CardNames.contains(weapon.getCardName()));
		assertTrue(CardNames.contains(room.getCardName()));
		
		assertEquals( 6, countPeople);  //Ensure the deck contains the correct number of each type of card
		assertEquals(6,countWeapons);
		assertEquals(9,countRooms);
		
		
		
		
		
	}
	/**
	 * test3DealingCard() tests Deal cards (all cards dealt, players have roughly same # of cards, no card dealt twice)
	 * the first assert is to check that all the cards have been distributed, the second assert is to check
	 * that all cards have been somewhat distributed
	 * and the last assert is to check each player has a unique card where card has a list of all cards of
	 *  a player for all players
	 *  
	 */
	@Test
	public void test3DealingCard() {
		board.dealCards();
		ArrayList<Card> testDeck = board.getDeckOfCards();
		assertEquals(0 ,testDeck.size());  //checking if all the cards have been dealt or not 
		
		ArrayList<Player> playerList = board.getPlayerList();
		Set<Integer> numberOfCard= new HashSet<Integer>();
		for (Player player : playerList) { // All players should have roughly the same number of cards
			numberOfCard.add(player.getPlayersCards().size()) ;
		}
		assertTrue(numberOfCard.size() <= 2); //if the cards are dealt correctly, players should only have two unique number of cards
		
		Set<Card> cards= new HashSet<Card>();
		boolean duplicateCard = false;
		for (Player player : playerList) { //The same card should not be given to >1 player 
			ArrayList<Card> playersCard = new ArrayList<Card>();
			playersCard= player.getPlayersCards();
			for( Card card : playersCard) {
				if(cards.contains(card)) { //if the set contains a duplicate card, this statement will be true
					duplicateCard = true;
					break;
				}
				else {
					cards.add(card);
				}
			}
			assertFalse(duplicateCard); //when theres no duplicate card on a player's hand, the boolean variable is false which is set initially
		}
		
		
		
		
	}
	
	

}
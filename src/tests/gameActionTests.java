package tests;

/**
*
* @author Abhaya Shrestha, Kirwinlvinodaq S Lawrence, Gazi Mahbub Morshed


* */
import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;

public class gameActionTests {
	private static Board board;
	private static ArrayList<Card> testDeck;
	private static Solution solution, wrongPersonSolution, wrongWeaponSolution, wrongRoomSolution;
	private static Card cardSolutionPerson, cardSolutionWeapon, cardSolutionRoom, wrongCardSolutionPerson,
			wrongCardSolutionWeapon, wrongCardSolutionRoom;
	private static HumanPlayer Steve;
	private static ComputerPlayer Stark, Natalia, Bruce; // static is one same copy for all instances

	@BeforeClass
	public static void setUpBeforeClass() {
		board = Board.getInstance();
		board.setConfigFiles("Board_Layout.csv", "ClueRooms.txt");
		board.setPlayerConfigFile("people.txt");
		board.setWeaponConfigFile("weapon.txt");
		board.initialize();
		testDeck = new ArrayList<Card>();
		// have to populate the testdeck
		testDeck.add(new Card("Mr. Stark", CardType.PERSON));
		testDeck.add(new Card("Pepper Potts", CardType.PERSON));
		testDeck.add(new Card("Natalia Alianovna Romanova", CardType.PERSON));
		testDeck.add(new Card("Steve Rogers", CardType.PERSON));
		testDeck.add(new Card("Bruce Banner", CardType.PERSON));

		testDeck.add(new Card("Candlestick", CardType.WEAPON));
		testDeck.add(new Card("Dumbell", CardType.WEAPON));
		testDeck.add(new Card("Lead Pipe", CardType.WEAPON));
		testDeck.add(new Card("Revolver", CardType.WEAPON));
		testDeck.add(new Card("Rope", CardType.WEAPON));

		testDeck.add(new Card("Study", CardType.ROOM));
		testDeck.add(new Card("Dining room", CardType.ROOM));
		testDeck.add(new Card("Billiard Room", CardType.ROOM));
		testDeck.add(new Card("Presidential Suite", CardType.ROOM));
		testDeck.add(new Card("Family Room", CardType.ROOM));

	}
	/**
	 * Tests targetLocation
	 * tests if no rooms in the list, select randomly
	 * tests in the list that was not just visited, must select it.
	 * if rooms just visited is in list, each target (including room) selected randomly
	 * 
	 */
	@Test
	public void test1targetLocation() {
		ComputerPlayer player = new ComputerPlayer(17, 0, Color.blue, "Mr. Stark");
		// a location with no rooms in target, just three targets
		board.calcTargets(17, 0, 2);
		boolean loc_15_0 = false;
		boolean loc_17_2 = false;
		boolean loc_16_1 = false;
		// Run the test a large number of times 
		//if room just visited is in list, each target (including room) selected randomly
		for (int i = 0; i < 100; i++) {
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(15, 0))
				loc_15_0 = true;
			else if (selected == board.getCellAt(17, 2))
				loc_17_2 = true;
			else if (selected == board.getCellAt(16, 1))
				loc_16_1 = true;
			else
				fail("Invalid target selected");
		}
		// Ensure each target was selected at least once
		assertTrue(loc_15_0);
		assertTrue(loc_17_2);
		assertTrue(loc_16_1);

		// selecting a cell that has a room in range
		// we make sure if there is a room in range, the room only visited once
		player.setRow(2);
		player.setColumn(5);
		board.calcTargets(2, 5, 2);
		boolean loc_1_4 = false; // rooms coordinate
		boolean loc_1_6 = false;
		boolean loc_4_5 = false;
		boolean loc_3_6 = false;
		boolean loc_0_5 = false;
		int countRoomVisited = 0;
		for (int i = 0; i < 100; i++) {
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(1, 4)) {// rooms coordinate
				loc_1_4 = true;
				countRoomVisited++;
			} else if (selected == board.getCellAt(1, 6))
				loc_1_6 = true;
			else if (selected == board.getCellAt(4, 5))
				loc_4_5 = true;
			else if (selected == board.getCellAt(3, 6))
				loc_3_6 = true;
			else if (selected == board.getCellAt(0, 5))
				loc_0_5 = true;
			else
				fail("Invalid target selected");
		}
		assertTrue(loc_1_4);
		assertTrue(loc_1_6);
		assertTrue(loc_4_5);
		assertTrue(loc_3_6);
		assertTrue(loc_0_5);
		assertTrue(countRoomVisited > 1); // visit the room once if it has been selected once

	}
	/**
	 * Create suggestion testing
	 * Room matches current location
	 * If only one weapon not seen, it's selected
	 * If only one person not seen, it's selected (can be same test as weapon)
	 * If multiple weapons not seen, one of them is randomly selected
	 * If multiple persons not seen, one of them is randomly selected
	 */
	@Test
	public void test2CreateSuggestion() {
		ComputerPlayer computerPlayer = new ComputerPlayer(1, 4, Color.blue, "Mr. Stark");
		computerPlayer.setPlayersCards(testDeck);
		// check if person is choosen randomly
		boolean stark = false; //right now none of them are chosen, so they are all false
		boolean pepper = false;
		boolean natalia = false;
		boolean steve = false;
		boolean banner = false; 

		// Run 100 calculations
		//If only one person not seen, it's selected (can be same test as weapon)
		for (int i = 0; i < 100; i++) {
			Solution suggested = computerPlayer.createSuggestion(testDeck, "Presidential Suite");
			if (suggested.getPerson().equals("Mr. Stark"))
				stark = true;
			else if (suggested.getPerson().equals("Pepper Potts"))
				pepper = true;
			else if (suggested.getPerson().equals("Natalia Alianovna Romanova"))
				natalia = true;
			else if (suggested.getPerson().equals("Steve Rogers"))
				steve = true;
			else if (suggested.getPerson().equals("Bruce Banner"))
				banner = true;
			else
				fail("Invalid target selected");
		}

		// each card should be picked at least once out of 100!
		assertTrue(stark);
		assertTrue(pepper);
		assertTrue(natalia);
		assertTrue(steve);
		assertTrue(banner);
		// now checking random weapon
		boolean candleStick = false;
		boolean dumbell = false;
		boolean leadPipe = false;
		boolean rope = false;
		boolean revolver = false;

		// Run 100 calculations
		for (int i = 0; i < 100; i++) {
			Solution suggestion = computerPlayer.createSuggestion(testDeck, "Study");
			if (suggestion.getWeapon().equals("Candlestick"))
				candleStick = true;
			else if (suggestion.getWeapon().equals("Dumbell"))
				dumbell = true;
			else if (suggestion.getWeapon().equals("Lead Pipe"))
				leadPipe = true;
			else if (suggestion.getWeapon().equals("Revolver"))
				revolver = true;
			else if (suggestion.getWeapon().equals("Rope"))
				rope = true;
			else
				fail("Invalid target selected");
		}

		// Ensure each option was selected once
		assertTrue(candleStick);
		assertTrue(dumbell);
		assertTrue(rope);
		assertTrue(leadPipe);
		assertTrue(revolver);

		// room matches suggestion for current location
		BoardCell currentCell = board.getCellAt(1, 4);
		Map<Character, String> tempLegend = board.getLegend();
		String currentRoom = tempLegend.get(currentCell.getInitial());
		Solution suggestion = computerPlayer.createSuggestion(testDeck, currentRoom);
		assertEquals(currentRoom, suggestion.getRoom());

		// tests for seen cards not be in returned suggestions
		// first for weapon cards
		computerPlayer.clearSeenCard();
		
		computerPlayer.addSeenCard(new Card("Mr. Stark", CardType.PERSON));
		computerPlayer.addSeenCard(new Card("Candlestick", CardType.WEAPON));

		// Weapon is chosen randomly and not the seen Cards
		candleStick = false; // candleStick is in the seen cards list, not it shouldn't show up in the
								// suggeested cards
		dumbell = false;
		leadPipe = false;
		revolver = false;
		rope = false;
		// Run 100 calculations
		for (int i = 0; i < 100; i++) {
			suggestion = computerPlayer.createSuggestion(testDeck, "Study");
			if (suggestion.getWeapon().equals("Candlestick"))
				candleStick = true;
			else if (suggestion.getWeapon().equals("Lead Pipe"))
				leadPipe = true;
			else if (suggestion.getWeapon().equals("Dumbell"))
				dumbell = true;
			else if (suggestion.getWeapon().equals("Revolver"))
				revolver = true;
			else if (suggestion.getWeapon().equals("Rope"))
				rope = true;
			else
				fail("Invalid target selected");
		}

		// Ensure each option was selected once
		assertFalse(candleStick);
		assertTrue(leadPipe);
		assertTrue(revolver);
		assertTrue(dumbell);
		assertTrue(rope);

		// now seen cards tests for Person
		computerPlayer.clearSeenCard();
		;
		computerPlayer.addSeenCard(new Card("Pepper Potts", CardType.PERSON));
		computerPlayer.addSeenCard(new Card("Lead Pipe", CardType.WEAPON));

		// Person is chosen randomly
		boolean potts = false;
		stark = false;
		natalia = false;
		boolean rogers = false;
		banner = false;
		// Run 100 calculations
		for (int i = 0; i < 100; i++) {
			suggestion = computerPlayer.createSuggestion(testDeck, "Study");
			if (suggestion.getPerson().equals("Pepper Potts"))
				potts = true;
			else if (suggestion.getPerson().equals("Mr. Stark"))
				stark = true;
			else if (suggestion.getPerson().equals("Natalia Alianovna Romanova"))
				natalia = true;
			else if (suggestion.getPerson().equals("Steve Rogers"))
				rogers = true;
			else if (suggestion.getPerson().equals("Bruce Banner"))
				banner = true;
			else
				fail("Invalid target selected");
		}

		// Ensure each option was selected once
		assertTrue(rogers);
		assertTrue(stark);
		assertTrue(natalia);
		assertFalse(potts);
	}
	/**
	 * Make an accusation testing
	 * testing if the solution is the correct solution by setting up a solution first then using
	 * check Accusation method with accusation that was made by a player.
	 * This is also repeated for when the solution has one wrong person, one wrong weapon, and one wrong room. 
	 */
	@Test
	public void test3makeAccusation() {
		// setting up a solution
		cardSolutionPerson = new Card("Mr. Stark", CardType.PERSON);
		wrongCardSolutionPerson = new Card("Steve Rogers", CardType.PERSON); 
		cardSolutionWeapon = new Card("Revolver", CardType.WEAPON);
		wrongCardSolutionWeapon = new Card("Dumbell", CardType.WEAPON); 
		cardSolutionRoom = new Card("Mancave", CardType.ROOM);
		wrongCardSolutionRoom = new Card("Study", CardType.ROOM); 

		solution = new Solution(cardSolutionPerson.getCardName(), cardSolutionRoom.getCardName(),
				cardSolutionWeapon.getCardName());

		board.setGameSolution(solution);

		wrongPersonSolution = new Solution(wrongCardSolutionPerson.getCardName(), cardSolutionRoom.getCardName(),
				cardSolutionWeapon.getCardName());
		wrongWeaponSolution = new Solution(cardSolutionPerson.getCardName(), cardSolutionRoom.getCardName(),
				wrongCardSolutionWeapon.getCardName());
		wrongRoomSolution = new Solution(cardSolutionPerson.getCardName(), wrongCardSolutionRoom.getCardName(),
				cardSolutionWeapon.getCardName());

		// solution that is correct
		assertTrue(board.checkAccusation(solution));

		// solution with wrong person
		assertFalse(board.checkAccusation(wrongPersonSolution));

		// solution with wrong weapon
		assertFalse(board.checkAccusation(wrongWeaponSolution));

		// solution with wrong room
		assertFalse(board.checkAccusation(wrongRoomSolution));
		
	}
	
	/**
	 * Testing Disprove suggestion
	 * We first create the players, and add cards for each player
	 * and set up the solution for each condition of one match, multiple matches. 
	 * Then we tested using disproveSuggestion method that player has only one matching card
	 * that card should be returned. 
	 * 
	 * - If player has no matching card null is returned
	 */

	@Test
	public void test4disproveSuggestion() {
		// Setup: Create a small number of players with known cards
		Steve = new HumanPlayer(6, 2, Color.GRAY, "Steve Rogers");
		Stark = new ComputerPlayer(0, 6, Color.RED, "Mr. Stark");
		Natalia = new ComputerPlayer(1, 6, Color.BLUE, "Natalia Alianovna");
		// adding cards for each player
		Steve.receiveCard(new Card("Steve Rogers", CardType.PERSON));
		Steve.receiveCard(new Card("Dumbell", CardType.WEAPON));
		Steve.receiveCard(new Card("Study", CardType.ROOM));
		Stark.receiveCard(new Card("Mr. Stark", CardType.PERSON));
		Stark.receiveCard(new Card("Revolver", CardType.WEAPON));
		Stark.receiveCard(new Card("Mancave", CardType.ROOM));
		Natalia.receiveCard(new Card("Natalia Alianovna Romanova", CardType.PERSON));
		Natalia.receiveCard(new Card("Rope", CardType.WEAPON));
		Natalia.receiveCard(new Card("Dining room", CardType.ROOM));
		//set up the card solutions and wrong solution
		cardSolutionPerson = new Card("Mr. Stark", CardType.PERSON);
		wrongCardSolutionPerson = new Card("Steve Rogers", CardType.PERSON);
		cardSolutionWeapon = new Card("Revolver", CardType.WEAPON);
		wrongCardSolutionWeapon = new Card("Dumbell", CardType.WEAPON);
		cardSolutionRoom = new Card("Mancave", CardType.ROOM);
		wrongCardSolutionRoom = new Card("Study", CardType.ROOM);

		Solution oneMatchSuggestionPerson = new Solution(cardSolutionPerson.getCardName(),
				wrongCardSolutionRoom.getCardName(), wrongCardSolutionWeapon.getCardName());
		Solution oneMatchSuggestionWeapon = new Solution(wrongCardSolutionPerson.getCardName(),
				wrongCardSolutionRoom.getCardName(), cardSolutionWeapon.getCardName());
		Solution oneMatchSuggestionRoom = new Solution(wrongCardSolutionPerson.getCardName(),
				cardSolutionRoom.getCardName(), wrongCardSolutionWeapon.getCardName());
		Solution multipleMatchSuggestion = new Solution(cardSolutionPerson.getCardName(),
				cardSolutionWeapon.getCardName(), cardSolutionRoom.getCardName());
		Solution noMatchSuggestion = new Solution(wrongCardSolutionPerson.getCardName(),
				wrongCardSolutionRoom.getCardName(), wrongCardSolutionWeapon.getCardName());

		// If player has only one matching card it should be returned
		assertEquals(oneMatchSuggestionPerson.getPerson(),
				Stark.disproveSuggestion(oneMatchSuggestionPerson).getCardName());
		assertEquals(oneMatchSuggestionWeapon.getWeapon(),
				Stark.disproveSuggestion(oneMatchSuggestionWeapon).getCardName());
		assertEquals(oneMatchSuggestionRoom.getRoom(), Stark.disproveSuggestion(oneMatchSuggestionRoom).getCardName());

		// If players has >1 matching card, returned card should be chosen randomly
		boolean firstTest = false;
		boolean secondTest = false;
		boolean thirdTest = false;
		boolean allPass = false;
		for (int i = 0; i < 100; i++) {
			if (multipleMatchSuggestion.getPerson()
					.equals(Stark.disproveSuggestion(multipleMatchSuggestion).getCardName()))
				firstTest = true; // tests whether Stark returns a person for matching card
			else if (multipleMatchSuggestion.getWeapon()
					.equals(Stark.disproveSuggestion(multipleMatchSuggestion).getCardName()))
				secondTest = true;
			else if (multipleMatchSuggestion.getRoom()
					.equals(Stark.disproveSuggestion(multipleMatchSuggestion).getCardName()))
				thirdTest = true;
		}
		if (firstTest && secondTest && thirdTest) {
			allPass = true;
		}

		assertTrue(allPass);

		// If player has no matching cards, null is returned
		assertEquals(null, Stark.disproveSuggestion(noMatchSuggestion));
		assertEquals(null, Natalia.disproveSuggestion(noMatchSuggestion));
		assertEquals(null, Steve.disproveSuggestion(multipleMatchSuggestion));

	}
	
	/**
	 * Testing handle Suggestion
	 * it tests how the hadling of a suggestion occurs when no one can disprove it by returning null
	 * test if only an accusing player can disprove it since he/she has all the cards which is same with whn accuser is human as well
	 * a suggestion when a human can disprove is properly returned in random with the dissaproval card
	 * a suggestion tha can be disproved by two players in a list in which the next in line does it whther it be human or computer
	 * 
	 */

	@Test
	public void test5handleSuggestion() {
		// Setup: Create a small number of players with known cards
		Steve = new HumanPlayer(6, 2, Color.GRAY, "Steve Rogers");
		Stark = new ComputerPlayer(20, 0, Color.RED, "Mr. Stark");
		Bruce = new ComputerPlayer(16, 0, Color.GREEN, "Bruce Banner");
		Natalia = new ComputerPlayer(1, 6, Color.BLUE, "Natalia Alianovna");
		Bruce.receiveCard(new Card("Bruce Banner", CardType.PERSON));
		Bruce.receiveCard(new Card("Lead Pipe", CardType.WEAPON));
		Bruce.receiveCard(new Card("Library", CardType.ROOM));
		Steve.receiveCard(new Card("Steve Rogers", CardType.PERSON));
		Steve.receiveCard(new Card("Dumbell", CardType.WEAPON));
		Steve.receiveCard(new Card("Study", CardType.ROOM));
		Stark.receiveCard(new Card("Mr. Stark", CardType.PERSON));
		Stark.receiveCard(new Card("Revolver", CardType.WEAPON));
		Stark.receiveCard(new Card("Mancave", CardType.ROOM));
		Natalia.receiveCard(new Card("Natalia Alianovna Romanova", CardType.PERSON));
		Natalia.receiveCard(new Card("Rope", CardType.WEAPON));
		Natalia.receiveCard(new Card("Dining room", CardType.ROOM));
		ArrayList<Player> tempPlayerList = new ArrayList<Player>();
		tempPlayerList.add(Steve);
		tempPlayerList.add(Bruce);
		tempPlayerList.add(Stark);
		tempPlayerList.add(Natalia);

		board.setPlayerList(tempPlayerList);

		// Suggestion no one can disprove returns null
		Solution disapprovableSuggestion = new Solution("Pepper Potts", "Nursery", "Candlestick");
		Card card = board.handleSuggestion(disapprovableSuggestion, Steve);
		assertEquals(null, card);

		// Suggestion only accusing player can disprove returns null
		Solution fakeCompSuggestion = new Solution("Mr. Stark", "Mancave", "Revolver");
		card = board.handleSuggestion(fakeCompSuggestion, Stark);
		assertEquals(null, card);
		
		// Suggestion only human can disprove returns answer (i.e., card that disproves
		// suggestion)
		Solution humanDisapporveSugg = new Solution("Pepper Potts", "Nursery", "Dumbell");
		card = board.handleSuggestion(humanDisapporveSugg, Stark);
		boolean humanApprove = false;
		for (Card c : Steve.getPlayersCards()) { // only a human has that card so he/she can disprove
			if (c.equals(card)) {
				humanApprove = true;
			}
		}
		assertTrue(humanApprove);

		// Suggestion only human can disprove, but human is accuser, returns null
		Solution onlyHumanDisapporveSugg = new Solution("Steve Rogers", "Study", "Dumbell");
		card = board.handleSuggestion(onlyHumanDisapporveSugg, Steve);
		assertEquals(null, card);

		// Suggestion that two players can disprove, correct player (based on starting
		// with next player in list) returns answer
		Solution multiDisapproveSuggestion = new Solution("Pepper Potts", "Library", "Revolver");
		card = board.handleSuggestion(multiDisapproveSuggestion, tempPlayerList.get(0));
		assertEquals("Library", card.getCardName());
		assertNotEquals("Revolver", card.getCardName()); // ensures players are not asked after one can disprove,
		// we are making sure that player 2 cannot disapprove

		Solution multiDisapproveSuggestionBeforeLast = new Solution("Pepper Potts", "Nursery", "Revolver");
		card = board.handleSuggestion(multiDisapproveSuggestionBeforeLast, tempPlayerList.get(0));
		assertEquals("Revolver", card.getCardName()); // in this test we are checking with player 2 where the player 0
														// is the accuser

		Solution multiDisapproveSuggestionLast = new Solution("Pepper Potts", "Nursery", "Rope");
		card = board.handleSuggestion(multiDisapproveSuggestionLast, tempPlayerList.get(0));
		assertEquals("Rope", card.getCardName()); // in this test we are checking with player 3 where the player 0 is
													// the accuser

		// Suggestion that human and another player can disprove, other player is next
		// in list, ensure other player returns answer
		Solution multiDisapproveSuggestionHumanAndComp = new Solution("Pepper Potts", "Dining room", "Dumbell");
		card = board.handleSuggestion(multiDisapproveSuggestionHumanAndComp, tempPlayerList.get(2));
		assertEquals("Dining room", card.getCardName());
		assertNotEquals("Dumbell", card.getCardName());
		
		
	}

}

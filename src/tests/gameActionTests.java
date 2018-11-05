package tests;

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
import clueGame.Solution;

public class gameActionTests {
	private static Board board;
	private static ArrayList <Card> testDeck;
	private static Solution solution,wrongPersonSolution,wrongWeaponSolution,wrongRoomSolution;
	private static Card cardSolutionPerson, cardSolutionWeapon, cardSolutionRoom,wrongCardSolutionPerson,wrongCardSolutionWeapon,wrongCardSolutionRoom;
	private static HumanPlayer Steve;
	private static ComputerPlayer Stark,Natalia,Bruce; //static is one same copy for all instances

	@BeforeClass
	public static void setUpBeforeClass() {
		board = Board.getInstance();
		board.setConfigFiles("Board_Layout.csv", "ClueRooms.txt");
		board.setPlayerConfigFile("people.txt");
		board.setWeaponConfigFile("weapon.txt");
		board.initialize();
		testDeck= new ArrayList<Card>();
		//have to populate the testdeck
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

	@Test
	public void test1targetLocation() {
		ComputerPlayer player = new ComputerPlayer(17,0, Color.blue, "Mr. Stark");
		//a location with no rooms in target, just three targets
		board.calcTargets(17, 0, 2);
		boolean loc_15_0 = false;
		boolean loc_17_2 = false;
		boolean loc_16_1 = false;
		// Run the test a large number of times
		for (int i=0; i<100; i++)  { 
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(15,0)) 
				loc_15_0 = true; 
			else if (selected == board.getCellAt(17, 2)) 
				loc_17_2 = true;
			else if (selected == board.getCellAt(16, 1)) 
				loc_16_1 = true;
			else fail("Invalid target selected"); 
		}
		// Ensure each target was selected at least once
		assertTrue(loc_15_0); 
		assertTrue(loc_17_2); 
		assertTrue(loc_16_1);

		//selecting a cell that has a room in range
		// we make sure if there is a room in range, the room only visited once
		player.setRow(2);
		player.setColumn(5);
		board.calcTargets(2, 5, 2);
		boolean loc_1_4 = false; //rooms coordinate
		boolean loc_1_6 = false;
		boolean loc_4_5 = false;
		boolean loc_3_6 = false;
		boolean loc_0_5 = false;
		int countRoomVisited=0;
		System.out.println(board.getTargets());
		for (int i=0; i<100; i++)  { 
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(1,4))  {//rooms coordinate
				loc_1_4 = true; 
				countRoomVisited++;
			}
			else if (selected == board.getCellAt(1,6)) 
				loc_1_6 = true;
			else if (selected == board.getCellAt(4,5)) 
				loc_4_5 = true;
			else if (selected == board.getCellAt(3,6)) 
				loc_3_6 = true;
			else if (selected == board.getCellAt(0,5)) 
				loc_0_5 = true;
			else fail("Invalid target selected"); 
		}    
		assertTrue(loc_1_4); 
		assertTrue(loc_1_6); 
		assertTrue(loc_4_5);
		assertTrue(loc_3_6);
		assertTrue(loc_0_5);
		assertTrue(countRoomVisited>1); //visit the room once if it has been selected once

	}
	@Test
	public void test2CreateSuggestion() {
		ComputerPlayer computerPlayer = new ComputerPlayer(1,4,Color.blue, "Mr. Stark"); 
		computerPlayer.setPlayersCards(testDeck);
		//check if person is choosen randomly
		boolean stark = false;
		boolean pepper = false;
		boolean natalia = false;
		boolean steve = false;
		boolean banner = false;

		//Run 100 calculations
		for (int i=0; i<100; i++) {
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

		//each card should be picked at least once out of 100!
		assertTrue(stark);
		assertTrue(pepper);
		assertTrue(natalia);
		assertTrue(steve);
		assertTrue(banner);
		//now checking random weapon
		boolean candleStick = false;
		boolean dumbell = false;
		boolean leadPipe = false;
		boolean rope = false;
		boolean revolver=false;

		//Run 100 calculations
		for (int i=0; i<100; i++) {
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

		//Ensure each option was selected once
		assertTrue(candleStick);
		assertTrue(dumbell);
		assertTrue(rope);
		assertTrue(leadPipe);
		assertTrue(revolver);


		//room matches suggestions
		BoardCell currentCell = board.getCellAt(1,4);
		Map<Character, String> tempLegend = board.getLegend();
		String currentRoom = tempLegend.get(currentCell.getInitial());
		Solution suggestion = computerPlayer.createSuggestion(testDeck, currentRoom);
		assertEquals(currentRoom, suggestion.getRoom());

		//tests for seen cards not be in returned suggestions
		//first for weapon cards
		computerPlayer.clearSeenCard();;
		computerPlayer.addSeenCard(new Card("Mr. Stark", CardType.PERSON));
		computerPlayer.addSeenCard(new Card("Candlestick", CardType.WEAPON));

		//Weapon is chosen randomly and not the seen Cards
		candleStick = false; //candleStick is in the seen cards list, not it shouldn't show up in the suggeested cards
		dumbell = false;
		leadPipe = false;
		revolver = false;
		rope=false;
		//Run 100 calculations
		for (int i=0; i<100; i++) {
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

		//Ensure each option was selected once
		assertFalse(candleStick);
		assertTrue(leadPipe);
		assertTrue(revolver);
		assertTrue(dumbell);
		assertTrue(rope);

		//now seen cards tests for Person
		computerPlayer.clearSeenCard();;
		computerPlayer.addSeenCard(new Card("Pepper Potts", CardType.PERSON));
		computerPlayer.addSeenCard(new Card("Lead Pipe", CardType.WEAPON));

		//Person is chosen randomly
		boolean  potts = false;
		stark = false;
		natalia = false;
		boolean rogers = false;
		banner= false;
		//Run 100 calculations
		for (int i=0; i<100; i++) {
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

		//Ensure each option was selected once
		assertTrue(rogers);
		assertTrue(stark);
		assertTrue(natalia);
		assertFalse(potts);
	}

	@Test
	public void test3makeAccusation() {
		//setting up a solution
		cardSolutionPerson = new Card("Mr. Stark",CardType.PERSON);
		wrongCardSolutionPerson = new Card("Steve Rogers",CardType.PERSON);
		cardSolutionWeapon = new Card("Revolver",CardType.WEAPON);
		wrongCardSolutionWeapon = new Card("Dumbell",CardType.WEAPON);
		cardSolutionRoom = new Card("Mancave",CardType.ROOM);
		wrongCardSolutionRoom = new Card("Study",CardType.ROOM);



		solution = new Solution(cardSolutionPerson.getCardName(),cardSolutionRoom.getCardName(),cardSolutionWeapon.getCardName());

		board.setGameSolution(solution);

		wrongPersonSolution = new Solution(wrongCardSolutionPerson.getCardName(),cardSolutionRoom.getCardName(),cardSolutionWeapon.getCardName());
		wrongWeaponSolution = new Solution(cardSolutionPerson.getCardName(),cardSolutionRoom.getCardName(),wrongCardSolutionWeapon.getCardName());
		wrongRoomSolution = new Solution(cardSolutionPerson.getCardName(),wrongCardSolutionRoom.getCardName(),cardSolutionWeapon.getCardName());


		//solution that is correct
		assertTrue(board.checkAccusation(solution));

		//solution with wrong person
		assertFalse(board.checkAccusation(wrongPersonSolution));

		//solution with wrong weapon
		assertFalse(board.checkAccusation(wrongWeaponSolution));

		//solution with wrong room
		assertFalse(board.checkAccusation(wrongRoomSolution));




	}
	@Test
	public void test4disproveSuggestion() {
		//Setup: Create a small number of players with known cards
		Steve = new HumanPlayer(6,2,Color.GRAY, "Steve Rogers" );
		Stark = new ComputerPlayer( 0, 6, Color.RED, "Mr. Stark");
		Natalia = new ComputerPlayer(1,6,Color.BLUE, "Natalia Alianovna");
		
		Steve.receiveCard(new Card("Steve Rogers", CardType.PERSON));
		Steve.receiveCard(new Card("Dumbell",CardType.WEAPON));
		Steve.receiveCard(new Card("Study",CardType.ROOM));
		Stark.receiveCard(new Card("Mr. Stark", CardType.PERSON));
		Stark.receiveCard(new Card("Revolver",CardType.WEAPON));
		Stark.receiveCard(new Card("Mancave",CardType.ROOM));
		Natalia.receiveCard(new Card("Natalia Alianovna Romanova", CardType.PERSON));
		Natalia.receiveCard(new Card("Rope", CardType.WEAPON));
		Natalia.receiveCard(new Card("Dining room", CardType.ROOM));
		
		cardSolutionPerson = new Card("Mr. Stark",CardType.PERSON);
		wrongCardSolutionPerson = new Card("Steve Rogers",CardType.PERSON);
		cardSolutionWeapon = new Card("Revolver",CardType.WEAPON);
		wrongCardSolutionWeapon = new Card("Dumbell",CardType.WEAPON);
		cardSolutionRoom = new Card("Mancave",CardType.ROOM);
		wrongCardSolutionRoom = new Card("Study",CardType.ROOM);

		Solution oneMatchSuggestionPerson = new Solution(cardSolutionPerson.getCardName(),wrongCardSolutionRoom.getCardName(),wrongCardSolutionWeapon.getCardName());
		Solution oneMatchSuggestionWeapon = new Solution(wrongCardSolutionPerson.getCardName(),wrongCardSolutionRoom.getCardName(),cardSolutionWeapon.getCardName());
		Solution oneMatchSuggestionRoom = new Solution(wrongCardSolutionPerson.getCardName(),cardSolutionRoom.getCardName(),wrongCardSolutionWeapon.getCardName());
		Solution multipleMatchSuggestion = new Solution(cardSolutionPerson.getCardName(),cardSolutionWeapon.getCardName(),cardSolutionRoom.getCardName());
		Solution noMatchSuggestion = new Solution(wrongCardSolutionPerson.getCardName(),wrongCardSolutionRoom.getCardName(),wrongCardSolutionWeapon.getCardName());
		
		
		
		//If player has only one matching card it should be returned
		assertEquals(oneMatchSuggestionPerson.getPerson(), Stark.disproveSuggestion(oneMatchSuggestionPerson).getCardName());
		assertEquals(oneMatchSuggestionWeapon.getWeapon(), Stark.disproveSuggestion(oneMatchSuggestionWeapon).getCardName());
		assertEquals(oneMatchSuggestionRoom.getRoom(), Stark.disproveSuggestion(oneMatchSuggestionRoom).getCardName());
		
		//If players has >1 matching card, returned card should be chosen randomly
		boolean firstTest = false;
		boolean secondTest = false;
		boolean thirdTest = false;
		boolean allPass =false;
		for (int i=0; i<100; i++) {
			if (multipleMatchSuggestion.getPerson().equals(Stark.disproveSuggestion(multipleMatchSuggestion).getCardName()))
				firstTest = true;
			else if (multipleMatchSuggestion.getWeapon().equals(Stark.disproveSuggestion(multipleMatchSuggestion).getCardName()))
				secondTest = true;
			else if (multipleMatchSuggestion.getRoom().equals(Stark.disproveSuggestion(multipleMatchSuggestion).getCardName()))
				thirdTest = true;
		}
		if(firstTest && secondTest && thirdTest  ) {
			allPass = true;
		}
		
		assertTrue(allPass);
		
		//If player has no matching cards, null is returned
		assertEquals(null, Stark.disproveSuggestion(noMatchSuggestion));
		assertEquals(null, Natalia.disproveSuggestion(noMatchSuggestion));
		assertEquals(null, Steve.disproveSuggestion(multipleMatchSuggestion));
		
		

	}
//	@Test
	public void test5handleSuggestion() {
		//Setup: Create a small number of players with known cards
		Steve = new HumanPlayer(6,2,Color.GRAY, "Steve Rogers" );
		Stark = new ComputerPlayer( 0, 6, Color.RED, "Mr. Stark");
		Bruce = new ComputerPlayer( 16, 0, Color.GREEN, "Bruce Banner");
		Natalia = new ComputerPlayer(1,6,Color.BLUE, "Natalia Alianovna");
		Bruce.receiveCard(new Card("Bruce Banner", CardType.PERSON));
		Bruce.receiveCard(new Card("Lead Pipe", CardType.WEAPON));
		Bruce.receiveCard(new Card("Study", CardType.ROOM));
		Steve.receiveCard(new Card("Steve Rogers", CardType.PERSON));
		Steve.receiveCard(new Card("Dumbell",CardType.WEAPON));
		Steve.receiveCard(new Card("Study",CardType.ROOM));
		Stark.receiveCard(new Card("Mr. Stark", CardType.PERSON));
		Stark.receiveCard(new Card("Revolver",CardType.WEAPON));
		Stark.receiveCard(new Card("Mancave",CardType.ROOM));
		Natalia.receiveCard(new Card("Natalia Alianovna Romanova", CardType.PERSON));
		Natalia.receiveCard(new Card("Rope", CardType.WEAPON));
		Natalia.receiveCard(new Card("Dining room", CardType.ROOM));
		



		//Suggestion no one can disprove returns null	
		//Suggestion only accusing player can disprove returns null
		//Suggestion only human can disprove returns answer (i.e., card that disproves suggestion)
		//Suggestion only human can disprove, but human is accuser, returns null
		//Suggestion that two players can disprove, correct player (based on starting with next player in list) returns answer
		//Suggestion that human and another player can disprove, other player is next in list, ensure other player returns answer


	}




}


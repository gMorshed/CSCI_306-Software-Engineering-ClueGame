package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Solution;

public class gameActionTests {
	private static Board board;
	ArrayList <Card> testDeck;
	@Before
	public void setUp() {
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
		testDeck.add(new Card("Bruce Banne", CardType.PERSON));
		
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
			if (suggested.getPerson().equals("Orden"))
				stark = true;
			else if (suggested.getPerson().equals("Tanner"))
				pepper = true;
			else if (suggested.getPerson().equals("McKenna"))
				natalia = true;
			else if (suggested.getPerson().equals("Andreas"))
				steve = true;
			else if (suggested.getPerson().equals("Andreas"))
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
			if (suggestion.getWeapon().equals("CandleStick"))
				candleStick = true;
			else if (suggestion.getWeapon().equals("Dumbell"))
				dumbell = true;
			else if (suggestion.getWeapon().equals("Lead Pipe"))
				leadPipe = true;
			else if (suggestion.getWeapon().equals("Revolver"))
				rope = true;
			else if (suggestion.getWeapon().equals("Rope"))
				revolver = true;
			else
				fail("Invalid target selected");
		}
		
		//Ensure each option was selected once
		assertTrue(candleStick);
		assertTrue(dumbell);
		assertTrue(rope);
		assertTrue(leadPipe);
		assertTrue(revolver);
	}
	
}


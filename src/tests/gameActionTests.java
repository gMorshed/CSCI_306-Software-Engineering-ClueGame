package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.ComputerPlayer;

public class gameActionTests {
	private static Board board;
	@Before
	public void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("Board_Layout.csv", "ClueRooms.txt");
		board.setPlayerConfigFile("people.txt");
		board.setWeaponConfigFile("weapon.txt");
		board.initialize();
	}

	@Test
	public void test1targetLocation() {
		ComputerPlayer player = new ComputerPlayer(5,0, Color.blue, "Mr. Stark");
		//a location with no rooms in target, just three targets
		board.calcTargets(17, 0, 2);
		boolean loc_16_0 = false;
		boolean loc_17_2 = false;
		boolean loc_16_1 = false;
		// Run the test a large number of times
		for (int i=0; i<100; i++)  { 
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(16,0)) 
				loc_16_0 = true; 
			else if (selected == board.getCellAt(17, 2)) 
				loc_17_2 = true;
			else if (selected == board.getCellAt(16, 1)) 
				loc_16_1 = true;
			else fail("Invalid target selected"); 
		}
		// Ensure each target was selected at least once
		assertTrue(loc_16_0); 
		assertTrue(loc_17_2); 
		assertTrue(loc_16_1);
		
		//selecting a cell that has a room in range
		// we make sure if there is a room in range, the room only visited once
		player.setRow(2);
		player.setColumn(5);
		board.calcTargets(2, 5, 1);
		boolean loc_1_5 = false; //rooms coordinate
		boolean loc_4_5 = false;
		boolean loc_2_6 = false;
		int countRoomVisited=0;
		for (int i=0; i<100; i++)  { 
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(1,5))  {//rooms coordinate
				loc_1_5 = true; 
				countRoomVisited++;
			}
			else if (selected == board.getCellAt(4,5)) 
				loc_4_5 = true;
			else if (selected == board.getCellAt(2,6)) 
				loc_2_6 = true;
			else fail("Invalid target selected"); 
		}
		assertTrue(loc_1_5); 
		assertTrue(loc_4_5); 
		assertTrue(loc_2_6);
		assertEquals(countRoomVisited, 1); //visit the room once if it has been selected once
		
	}
	
	
}


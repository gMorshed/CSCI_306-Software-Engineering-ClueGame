package tests;

/**
 * This program tests that adjacencies and targets are calculated correctly
* Team members: Abhaya Shrestha, Gazi Mahbub Morshed, Kirwinl Vinodaq S Lawrence
 * for our excel spreadsheet
 *
 */

import java.util.Set;

//Doing a static import allows me to write assertEquals rather than
//assertEquals
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTests {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("Board_Layout.csv", "ClueRooms.txt");		
		// Initialize will load BOTH config files 
		board.initialize();
	}

	/** ====================================================== Adjacency Testing ====================================================
	 * Tests adjacency within rooms, ensures that the player
	 * does not move around within a room
	 * marked as ORANGE in the spreadsheet
	 * 
	 * - Locations that are within rooms( have empty adjacent list)
	 * 
	 * - Locations that are at the edge of the board
	 * 
	 * 
	 * */
	@Test
	public void testAdjacenciesWithinRooms()
	{	
		/*
		 * Test adjacency at the edges of the board*/
		// Top left edge of the board
		Set<BoardCell> testList = board.getAdjList(0, 0);
		assertEquals(0, testList.size());
		// Bottom right edge of the board
		testList = board.getAdjList(22,16);
		assertEquals(0, testList.size());
		// Bottom left edge of the board
		testList = board.getAdjList(22, 0);
		assertEquals(0, testList.size());
		// Top right edge of the baord
		testList = board.getAdjList(0, 16);
		assertEquals(0, testList.size());
		
		/*
		 * Test Locatiosn that only have walkways as adjacent 
		 * locations*/
		// Test one that has walkway underneath
		testList = board.getAdjList(4,0);
		assertEquals(0, testList.size());
		// Test one that has walkway above
		testList = board.getAdjList(9,0);
		assertEquals(0, testList.size());
		
		// Test one that is in middle of room
		testList = board.getAdjList(19,8);
		assertEquals(0, testList.size());
		
		/*
		 * Test Locations that are adjacent to a doorway.
		 * In this case we are only testing for direction UP*/
		// Location that are adjacent to a doorway 
		// with direction up
		testList = board.getAdjList(12,4);
		assertEquals(0, testList.size());
		
	}

	/**
	 * - Testing adjacency for a cell that only has
	 * walkway as adjacency
	 * - Testing locations that are doorways only. 
	 * 
	 * These are marked as PURPLE on the planning spreadsheet
	 * 
	 * **/
	@Test
	public void testAdjacencyOnlyWalkway()
	{
		// TEST DOORWAY RIGHT 
		Set<BoardCell> testList = board.getAdjList(0,4);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(0,5)));
		// TEST DOORWAY LEFT 
		testList = board.getAdjList(4,14);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(4,13)));
		//TEST DOORWAY DOWN
		testList = board.getAdjList(13,14);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(14,14)));
		//TEST DOORWAY UP
		testList = board.getAdjList(12,5);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(11,5)));
		//TEST DOORWAY RIGHT, WHERE THERE'S A WALKWAY BELOW
		testList = board.getAdjList(18,4);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(18,5)));
		
	}
	
	/**
	 * - Testing Locations that are besides a room cell that is not a doorway
	 * - Testing Locations that are adjacent to a doorway with direction (this includes the doorway itself in the adjacency list)
	 * 
	 * These tests are GREEN in the planning spreadsheet
	 * */
	@Test
	public void testAdjacencyBesidesRoomCell()
	{
		// Test beside a door direction RIGHT
		Set<BoardCell> testList = board.getAdjList(1,5);
		assertTrue(testList.contains(board.getCellAt(1,4))); // this is Doorway RIGHT cell
		assertTrue(testList.contains(board.getCellAt(1,6)));
		assertTrue(testList.contains(board.getCellAt(0,5)));
		assertTrue(testList.contains(board.getCellAt(2,5)));
		assertEquals(4, testList.size());
		// Test beside a door direction DOWN
		testList = board.getAdjList(4,8);
		assertTrue(testList.contains(board.getCellAt(3,8))); // this is Doorway DOWN cell
		assertTrue(testList.contains(board.getCellAt(4,7)));
		assertTrue(testList.contains(board.getCellAt(4,9)));
		assertTrue(testList.contains(board.getCellAt(5,8)));
		assertEquals(4, testList.size());
		// Test beside a door direction LEFT
		testList = board.getAdjList(2,13);
		assertTrue(testList.contains(board.getCellAt(1,13))); 
		assertTrue(testList.contains(board.getCellAt(3,13)));
		assertTrue(testList.contains(board.getCellAt(2,14))); // this is Doorway LEFT cell
		assertTrue(testList.contains(board.getCellAt(2,12)));
		assertEquals(4, testList.size());
		// Test beside a door direction UP
		testList = board.getAdjList(11,5);
		assertTrue(testList.contains(board.getCellAt(12,5))); // this is Doorway UP cell
		assertTrue(testList.contains(board.getCellAt(11,6)));
		assertTrue(testList.contains(board.getCellAt(10,5))); 
		assertEquals(3, testList.size());
		
		//Testing Locations that are besides a room cell that is not a doorway
		testList = board.getAdjList(3, 5);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCellAt(2,5))); // this is Doorway UP cell
		assertTrue(testList.contains(board.getCellAt(3,6)));
		assertTrue(testList.contains(board.getCellAt(4,5)));
	}

	/**Test variety of walkway scenarios
	 * - Tests the bottom edge of the board
	 * These are marked as RED on the spreadsheet
	 * 
	 * 
	 * */
	@Test
	public void testAdjacencyWalkwaysVariety()
	{
		// Test on right edge of board, just one walkway piece
		Set<BoardCell> testList = board.getAdjList(19,16);
		assertTrue(testList.contains(board.getCellAt(19,15)));
		assertEquals(1, testList.size());
		
		// Test on left edge of board, three walkway pieces
		testList = board.getAdjList(7, 0);
		assertTrue(testList.contains(board.getCellAt(6, 0)));
		assertTrue(testList.contains(board.getCellAt(8, 0)));
		assertTrue(testList.contains(board.getCellAt(7, 1)));
		assertEquals(3, testList.size());

		// Test between two rooms, walkways right and left
		testList = board.getAdjList(14,15);
		assertTrue(testList.contains(board.getCellAt(14,14)));
		assertTrue(testList.contains(board.getCellAt(14,16)));
		assertEquals(2, testList.size());

		// Test surrounded by 4 walkways
		testList = board.getAdjList(5,6);
		assertTrue(testList.contains(board.getCellAt(4,6)));
		assertTrue(testList.contains(board.getCellAt(6,6)));
		assertTrue(testList.contains(board.getCellAt(5,5)));
		assertTrue(testList.contains(board.getCellAt(5,7)));
		assertEquals(4, testList.size());
		
		// Test on bottom edge of board, next to 1 room piece
		testList = board.getAdjList(22, 5);
		assertTrue(testList.contains(board.getCellAt(21, 5)));
		assertEquals(1, testList.size());
		
		// Test on walkway next to  door that is not in the needed
		// direction to enter
		testList = board.getAdjList(17,4);
		assertTrue(testList.contains(board.getCellAt(16,4)));
		assertTrue(testList.contains(board.getCellAt(17,3)));
		assertTrue(testList.contains(board.getCellAt(17,5)));
		assertEquals(3, testList.size());
	}
	
	/**
	 * ============================================== Target Testing =============================================================
	 * In the testTargetOneStep() 
	 * 
	 * - Testing target of length 1
	 * - Testing Location with only walkway as adjacent locations
	 * - Locations that are besides a room cell that is not a doorway
	 * - Locations that are at the edge of the board
	 * - Locations that are adjacent to a doorway with directions. We have direction RIGHT
	 * These are PINK on the planning spreadsheet
	 * */
	@Test
	public void testTargetsOneStepAdjEdgeDoorway() {
		// Locations that are besides a room cell that is not a doorway
		board.calcTargets(17,0,1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(17,1)));
		assertTrue(targets.contains(board.getCellAt(16,0))); // only has walkways as adjacent locations
		
		board.calcTargets(14, 6, 1); // besides a room cell that is not a doorway
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(13, 6)));
		assertTrue(targets.contains(board.getCellAt(14, 7)));
		assertTrue(targets.contains(board.getCellAt(15, 6)));
		
		// Test a cell that is adjacent to a doorway with direction RIGHT
		// Locations that are at the edge of the board
		// Allows the user to enter the room
		board.calcTargets(22, 12, 1);
		targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(21, 12)));
		assertTrue(targets.contains(board.getCellAt(22, 11))); 
	}
	
	/**
	 * In testTargetsTwoSteps()
	 * 
	 * I am testing a different distance of size 2
	 *  - Testing locations that are adjacent to doorway, in this case UP and DOWN
	 * These are LIGHT BLUE on the spreadsheet*/
	@Test
	public void testTargetsTwoStepsAdjDoorway() {
		board.calcTargets(19, 14, 2); // different scenario is being tested
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(19, 12)));
		assertTrue(targets.contains(board.getCellAt(19, 16)));
		
		// Adjacent to door direction DOWN
		board.calcTargets(14, 14, 2);
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(14, 16)));
		assertTrue(targets.contains(board.getCellAt(14, 12)));
		assertTrue(targets.contains(board.getCellAt(13, 14))); 
		
		// Adjacent to door direction UP
		board.calcTargets(12, 6, 2);
		targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(10, 6)));
		assertTrue(targets.contains(board.getCellAt(14, 6)));
		assertTrue(targets.contains(board.getCellAt(11, 5)));
	}
	
	/**
	 * Testing a step size of 4
	 *  - Locations that are adjacent to a doorway with needed directions. This time we have LEFT and RIGHT, so
	 *  now we have all 4 locations
	 *  
	 *  This is DARK BLUE on the spreadsheet
	 * */
	@Test
	public void testTargetsFourStepsAdjDoorway() {
		board.calcTargets(21, 12, 4); // Left and right doorway are both are adjacent to this coordinate
		Set<BoardCell> targets= board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCellAt(17, 12)));
		assertTrue(targets.contains(board.getCellAt(19, 14)));
		assertTrue(targets.contains(board.getCellAt(20, 13)));
		assertTrue(targets.contains(board.getCellAt(21, 13)));
		assertTrue(targets.contains(board.getCellAt(21, 11))); // points not traveling the full distance
		assertTrue(targets.contains(board.getCellAt(22, 11)));
		
	}	
	

	/**
	 * Testing getting into rooms
	 * 
	 * These tests are in LIGHT PURLE on the spreadsheet
	 * */

	@Test 
	public void testTargetsIntoRoom()
	{
		// One room is exactly 2 away
		board.calcTargets(19, 12, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(5, targets.size()); // make sure we have 5 targets from calcTargets
		//checking the doorway
		assertTrue(targets.contains(board.getCellAt(20,13))); // we should be able to get into this doorway as one of the targets
		assertTrue(targets.contains(board.getCellAt(21,12)));
		assertTrue(targets.contains(board.getCellAt(19,14)));
		assertTrue(targets.contains(board.getCellAt(17,12)));
		assertTrue(targets.contains(board.getCellAt(18,11)));
		// Different distance
		board.calcTargets(4, 10, 3);
		targets= board.getTargets();
		assertEquals(13, targets.size());
		assertTrue(targets.contains(board.getCellAt(2, 11)));
		assertTrue(targets.contains(board.getCellAt(3, 12)));
		assertTrue(targets.contains(board.getCellAt(6, 11)));
		assertTrue(targets.contains(board.getCellAt(5, 8)));
		assertTrue(targets.contains(board.getCellAt(5, 12)));
		assertTrue(targets.contains(board.getCellAt(6, 9)));
		assertTrue(targets.contains(board.getCellAt(4, 7)));
		assertTrue(targets.contains(board.getCellAt(4, 13)));
		assertTrue(targets.contains(board.getCellAt(3, 9))); // these two doorways are not the same length, and we have to get into them
		assertTrue(targets.contains(board.getCellAt(3, 8))); // door direction is DOWN
		assertTrue(targets.contains(board.getCellAt(4,9)));
		assertTrue(targets.contains(board.getCellAt(5,10)));
		assertTrue(targets.contains(board.getCellAt(4,11)));
		
		
		
	}

/**
 * 
 * Testing here when we are exiting the room
 * - Testing locations here are doorways
 * These are marked as LIGHT GREEN on the testing
 * */
	@Test
	public void testRoomExit()
	{
		
		// Exiting from a room; door direction DOWN
		board.calcTargets(3, 9, 2);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(4, 10)));
		assertTrue(targets.contains(board.getCellAt(4, 8)));
		assertTrue(targets.contains(board.getCellAt(5, 9)));
		
		// Exiting from a room; door direction RIGHT
		board.calcTargets(19, 4, 2);
		targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(20, 5)));
		assertTrue(targets.contains(board.getCellAt(18, 5)));	
	}
	/**
	 * testing target within a room, which should be zero
	 */
	@Test
	public void testTargetWithinARoom() {
		board.calcTargets(10,0,2); // Locations that are besides a room cell that is not a doorway
		Set<BoardCell> targets= board.getTargets();
		assertEquals(0, targets.size());
		board.calcTargets(20,8,5);
		targets= board.getTargets();
		assertEquals(0, targets.size());
	}
}



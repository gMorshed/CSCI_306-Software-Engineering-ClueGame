package tests;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

/**
 *
 * @author Abhaya Shrestha, Kirwinlvinodaq S Lawrence, Gazi Mahbub Morshed
 *         testcase class,tests our config files and if all the methods work
 *         testroom to test if it matches legend, testboarddimensions is for
 *         testing the board dimension, testdoordirection is making sure each
 *         doorway has a enumerator type
 * 
 */

public class Testcase {
	// Constants that we will use to test whether the file was loaded correctly
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 23;
	public static final int NUM_COLUMNS = 17;

	private static Board board;

	/**
	 * setUp(): setup test files
	 */
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("Board_Layout.csv", "ClueRooms.txt");
		// Initialize will load BOTH config files
		board.initialize();
	}

	/**
	 * testRooms(): Testing whether the initials match for each room from the .txt
	 * file
	 */
	@Test
	public void testRooms() {
		// Get the map of initial => room
		Map<Character, String> legend = board.getLegend();
		// Ensure we read the correct number of rooms
		assertEquals(LEGEND_SIZE, legend.size());
		// To ensure data is correctly loaded, test retrieving a few rooms
		// from the hash, including the first and last in the file and a few others
		assertEquals("Study", legend.get('S'));
		assertEquals("Dining room", legend.get('D'));
		assertEquals("Billiard Room", legend.get('B'));
		assertEquals("Library", legend.get('L'));
		assertEquals("Presidential Suite", legend.get('P'));
		assertEquals("Family Room", legend.get('F'));
		assertEquals("Home Office", legend.get('H'));
		assertEquals("Nursery", legend.get('N'));
		assertEquals("Mancave", legend.get('M'));
		assertEquals("Closet", legend.get('X'));
		assertEquals("Walkway", legend.get('W'));

	}

	/**
	 * testBoardDimensions(): Testing whether the rows and columns from the files
	 * match the expected rows and columns
	 */
	@Test
	public void testBoardDimensions() {
		// Ensure we have the proper number of rows and columns
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());
	}

	/**
	 * FourDoorDirections(): Testing whether we can obtain at least one enumerated
	 * type of DoorDirection and also to check if a room is or is not a doorway
	 */
	@Test
	public void FourDoorDirections() {
		BoardCell room = board.getCellAt(0, 4); // we are testing the door way for the billiard room which has a right
												// direction.
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection()); // at least one of each enumarated type is tested
																	// for the cooresponding boardcell in our file
		room = board.getCellAt(14, 1);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.DOWN, room.getDoorDirection());
		room = board.getCellAt(20, 13);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.LEFT, room.getDoorDirection());
		room = board.getCellAt(12, 5);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.UP, room.getDoorDirection());
		// Test that room pieces that aren't doors know it
		room = board.getCellAt(12, 4);
		assertFalse(room.isDoorway());
		// Test that walkways are not doors
		BoardCell cell = board.getCellAt(15, 0);
		assertFalse(cell.isDoorway());

	}

	/**
	 * testNumberOfDoorways(): There are 20 doorways in our file, and we will see if
	 * the number of doorways we counted is equivalent to what the program counts.
	 */
	@Test
	public void testNumberOfDoorways() {
		int numDoors = 0;
		for (int row = 0; row < board.getNumRows(); row++)
			for (int col = 0; col < board.getNumColumns(); col++) {
				BoardCell cell = board.getCellAt(row, col);
				if (cell.isDoorway())
					numDoors++;
			}
		Assert.assertEquals(20, numDoors); // testing to see if the number of doorways counted match up with what
		// should be expected from the file
	}

	/**
	 * testRoomInitials(): Testing if the initials that the file read matches with
	 * what we put the initial as in the file.
	 */
	@Test
	public void testRoomInitials() {
		// Test first cell in room
		assertEquals('P', board.getCellAt(0, 0).getInitial());
		assertEquals('L', board.getCellAt(0, 7).getInitial());
		assertEquals('S', board.getCellAt(0, 14).getInitial());
		// Test last cell in room
		assertEquals('B', board.getCellAt(9, 0).getInitial());
		assertEquals('F', board.getCellAt(11, 13).getInitial());
		assertEquals('M', board.getCellAt(18, 0).getInitial());
		assertEquals('D', board.getCellAt(18, 6).getInitial());
		assertEquals('H', board.getCellAt(20, 14).getInitial());
		// test the closet and walkway
		assertEquals('X', board.getCellAt(10, 7).getInitial());
		assertEquals('W', board.getCellAt(15, 0).getInitial());

	}

}

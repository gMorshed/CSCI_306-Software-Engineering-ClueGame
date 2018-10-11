package tests;
/**
 * Team members: Abhaya Shrestha, Gazi Mahbub Morshed, Kirwinl Vinodaq S Lawrence
 * */

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Before;
import org.junit.Test;

import experiment.BoardCell;
import experiment.IntBoard;

public class IntBoardTests {
	private IntBoard board; // first we have to declare
							// this instance variable for
							// this JUnit testing to work

	@Before
	public void beforeAll() {
		board = new IntBoard(); // constructor should call calcAdjacencies() so you can test them
		board.calcAdjacencies();
	}

	/**
	 * Methods to test adjacency
	 */
	@Test
	public void testAdjTopLeftCorner() {
		/**
		 * Since we are at the top corner, we can only be adjacent to two squares right
		 * and down
		 */
		BoardCell cell = board.getCell(0, 0); // testing for top left corner
		Set<BoardCell> testList = board.getAdjList(cell);
		// testing AdjList
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertEquals(2, testList.size());

	}

	@Test
	public void testAdjBottomRightCorner() {
		/**
		 * Since we are are the bottom square we can only be adjacent to two squares
		 * left and up
		 */

		BoardCell cell = board.getCell(3, 3); // testing for bottom right corner
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(2, 3))); // left
		assertTrue(testList.contains(board.getCell(3, 2))); // up
		assertEquals(2, testList.size());

	}

	@Test
	public void testAdjRightEdge() {
		/**
		 * Since we are at right edge we can be adjacent to 3 squares right, left, and
		 * up
		 */
		BoardCell cell = board.getCell(1, 3); // testing for right
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(2, 3))); // right
		assertTrue(testList.contains(board.getCell(0, 3))); // left
		assertTrue(testList.contains(board.getCell(1, 2))); // up
		assertEquals(3, testList.size());

	}

	@Test
	public void testAdjLeftEdge() {
		/**
		 * Since we are at left edge we can be adjacent to 2 squares left, and down
		 */

		BoardCell cell = board.getCell(3, 0); // testing for left
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(2, 0))); // left
		assertTrue(testList.contains(board.getCell(3, 1))); // down
		assertEquals(2, testList.size());

	}

	@Test
	public void testSecondColMiddle() {
		/**
		 * Since we are in the middle column we have choices
		 */

		BoardCell cell = board.getCell(1, 1); // testing second column
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(2, 1))); // right
		assertTrue(testList.contains(board.getCell(1, 2))); // down
		assertTrue(testList.contains(board.getCell(0, 1))); // left
		assertTrue(testList.contains(board.getCell(1, 0))); // up
		assertEquals(4, testList.size());

	}

	@Test
	public void testSecondLastColMiddle() {
		/**
		 * Second last column in the middle also has choices
		 */
		BoardCell cell = board.getCell(2, 2); // testing the middle of grid
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(3, 2))); // right
		assertTrue(testList.contains(board.getCell(2, 3))); // down
		assertTrue(testList.contains(board.getCell(1, 2))); // left
		assertTrue(testList.contains(board.getCell(2, 1))); // up
		assertEquals(4, testList.size());

	}

	/**
	 * 6 Test calcTargets method
	 */
	@Test
	public void testTargetRoll1() {
		/**
		 * If we roll a 1
		 */
		BoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(1, 0))); // we can only go to these 2
		assertTrue(targets.contains(board.getCell(0, 1))); // places

	}

	@Test
	public void testTargetRoll2() {
		/**
		 * if we roll a 2
		 */
		BoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 2);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(0, 2)));

	}

	@Test
	public void testTargetRoll3() {
		/**
		 * If we roll a 3
		 */
		BoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 3);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(6, targets.size());
		// by looking at the diagram we can calculate where we can move
		assertTrue(targets.contains(board.getCell(3, 0)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(0, 3)));
		assertTrue(targets.contains(board.getCell(1, 0)));

	}

	@Test
	public void testTargetRoll4() {
		/**
		 * If we roll a 4
		 */
		BoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 4);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(1, 3)));
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(2, 2)));
		assertTrue(targets.contains(board.getCell(3, 1)));

	}

	@Test
	public void testTargetRoll5() {
		/**
		 * If we roll a 5 on a different point
		 */

		BoardCell cell = board.getCell(2, 2);
		board.calcTargets(cell, 5);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(8, targets.size());
		// all the possible squares
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(0, 3)));
		assertTrue(targets.contains(board.getCell(1, 0)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(2, 3)));
		assertTrue(targets.contains(board.getCell(3, 2)));
		assertTrue(targets.contains(board.getCell(3, 0)));

	}

	@Test
	public void testTargetRoll6() {
		/**
		 * If we roll a 6 with a different point
		 */

		BoardCell cell = board.getCell(2, 2);
		board.calcTargets(cell, 4);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(0, 0)));
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(1, 3)));
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(3, 1)));
		assertTrue(targets.contains(board.getCell(3, 3)));

	}

}

package tests;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Before;
import org.junit.Test;

import experiment.BoardCell;
import experiment.IntBoard;

public class IntBoardTests {
	private IntBoard board;
	@Before
    public void beforeAll() {
      board = new IntBoard();  // constructor should call calcAdjacencies() so you can test them
    }

	
	@Test
	public void testTopLeftCorner() {

		BoardCell cell = board.getCell(0,0); // testing for top left corner
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(1, 0)));
        assertTrue(testList.contains(board.getCell(0, 1)));
        assertEquals(2, testList.size());
		
	}
	@Test
	public void testBottomRightCorner() {
		BoardCell cell = board.getCell(3,3); // testing for bottom right corner
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(4, 3))); // 
        assertTrue(testList.contains(board.getCell(3, 4)));
        assertEquals(2, testList.size());
		
	}
	@Test
	public void testRightEdge() {
		BoardCell cell = board.getCell(1,3); // testing for right
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(2, 3)));
        assertTrue(testList.contains(board.getCell(1, 4)));
        assertEquals(2, testList.size());
		
	}
	@Test
	public void testLeftEdge() {
		BoardCell cell = board.getCell(3,0); // testing for left
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(4, 0)));
        assertTrue(testList.contains(board.getCell(3, 1)));
        assertEquals(2, testList.size());
		
	}
	/*
	 * Catching off by one errors test*/
	@Test
	public void testSecondColMiddle() {
		BoardCell cell = board.getCell(1,1); // testing second column
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(2, 1)));
        assertTrue(testList.contains(board.getCell(1, 2)));
        assertEquals(2, testList.size());
		
	}
	@Test
	public void testSecondLastColMiddle() {
		BoardCell cell = board.getCell(2,2); // testing the middle of grid
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(3, 2)));
        assertTrue(testList.contains(board.getCell(2, 3)));
        assertEquals(2, testList.size());
		
	}
	
	
	
	@Test
	public void testTopRightCorner() {
		
	}

	
	
	

}

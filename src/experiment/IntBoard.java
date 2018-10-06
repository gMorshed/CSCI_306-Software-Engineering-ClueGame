package experiment;

import java.util.*;

/*contains grid and adjacency list
 * includes a data structure to contain the board cells
 * A constructor
 * */
public class IntBoard {

	private Map<BoardCell, Set<BoardCell>> adjMtx; // A hashmap to store
													// adjacency list
	private Set<BoardCell> visited; // visited list
	private Set<BoardCell> targets; // targets list
	private BoardCell[][] grid; // board grid
	
	

	public IntBoard() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BoardCell getCell(int row, int column) {
		return grid[row][column];
	}
	/*
	 * Functions
	 * 
	 */

	/*
	 * calcAdjacencies(): Calculates the adjacency list for each gird cell and
	 * stores the result as a Map in an instance variable
	 */
	public void calcAdjacencies() {

	}

	
	/*
	 * 
	 * calcTargets(): Calculates the targets that are pathLength 
	 * distance
	 * from the startCell.
	 * The list of targets will be stored as a Set in an
	 * instance variable
	 * 
	 * */
	public Set<BoardCell> calcTargets(BoardCell startCell, double pathLength){
		//TODO
		/*Algorithm:
	 * 
	 * Set up for recursive
	 * visited list is used to avoid backtracking. Set to empty list.
	 * targets will ultimately contain the list of cells initially set to empty list
	 * add the start location to the visited list (so no cycle through this cell)
	 * call recursive function*/
		return null;
	}
	
	/*
	 * getTargets(): returns the list of targets as a set. Type
	 * is Set<BoardCell>*/
	public Set<BoardCell> getTargets(){
		//TODO
		return null;
	}
	
	/*
	 * getAdjList(): Returns the adjacency list for one cell
	 * 
	 * use a HashSet
	 */
	public Set<BoardCell> getAdjList(BoardCell cell) {
		//TODO 
		return null;

	}
	

	
}

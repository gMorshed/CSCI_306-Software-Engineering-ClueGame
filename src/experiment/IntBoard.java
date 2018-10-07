/*
 * Team members: Abhaya Shrestha, Gazi Mahbub Morshed, Kirwinl Vinodaq S Lawrence
 * */
package experiment;

import java.util.*;

/*contains grid and adjacency list
 * includes a data structure to contain the board cells
 * A constructor
 * */
public class IntBoard {
	private static final int BOARD_X_DIMENSION = 4;
	private static final int BOARD_Y_DIMENSION = 4;
	private Map<BoardCell, Set<BoardCell>> adjMtx; // A hashmap to store
													// adjacency list
	private Set<BoardCell> visited; // visited list
	private Set<BoardCell> targets; // targets list
	private BoardCell[][] grid; // board grid

	public IntBoard() {
		super();
		// declare visited and targets
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		// declare adjacent matrix
		adjMtx = new HashMap<BoardCell, Set<BoardCell>>();
		// initialize grid
		grid = new BoardCell[BOARD_X_DIMENSION][BOARD_Y_DIMENSION];
		for (int i = 0; i < BOARD_X_DIMENSION; i++) {
			for (int j = 0; j < BOARD_Y_DIMENSION; j++) {
				grid[i][j] = new BoardCell(i, j);
			}
		}
		// TODO Auto-generated constructor stub
	}

	/*
	 * used to get the grid cell
	 */
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
		// iterate through the grid and put each BoardCell as key,
		// and each board cell adjacent values and a set
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				Set<BoardCell> adjacentPoints = new HashSet<BoardCell>();
				if ((i + 1) < 4) {
					adjacentPoints.add(getCell(i + 1, j)); // these if statements check if
				} // these points are in the gird
				if ((i - 1) >= 0) {
					adjacentPoints.add(getCell(i - 1, j));
				}
				if ((j + 1) < 4) {
					adjacentPoints.add(getCell(i, j + 1));
				}
				if ((j - 1) >= 0) {
					adjacentPoints.add(getCell(i, j - 1));
				}

				adjMtx.put(getCell(i, j), adjacentPoints);

			}
		}
		//System.out.println(adjMtx);
		//System.out.println(getAdjList(getCell(0, 0)).contains(getCell(1, 0)));

	}

	/*
	 * 
	 * calcTargets(): Calculates the targets that are pathLength distance from the
	 * startCell. The list of targets will be stored as a Set in an instance
	 * variable
	 * 
	 */
	public void calcTargets(BoardCell startCell, int pathLength) {
		// TODO
		/*
		 * Algorithm:
		 * 
		 * Set up for recursive visited list is used to avoid backtracking. Set to empty
		 * list. targets will ultimately contain the list of cells initially set to
		 * empty list add the start location to the visited list (so no cycle through
		 * this cell) call recursive function
		 */
		/* Psuedocode
		 * 
		 * for each adjCell in adjacentCell
		 * -- if already in visited list, skip rest of this
		 * 		--add adjCell to visited list
		 * 		--if pathLength == 1, add adjCell to Targets
		 * 		--else call calcTargets(adjCell, pathLength-1)
		 * 		
		 * 		--remove adjCell from visited list
		 * */
		visited.add(startCell); // we always add our startCell for visited list first
		for(BoardCell adjCell: adjMtx.get(startCell)) { 
			
			if(!(visited.contains(adjCell))) {
				
				visited.add(adjCell); // add adjCell to visited list
				if(pathLength == 1) {
					targets.add(adjCell); // where targets is initialized
				}else {
				// else we recursively call calcTargets again passing in adjacent cell
				calcTargets(adjCell, pathLength-1);
				}
				
				visited.remove(adjCell);
			}
			
			
		}

	}

	/*
	 * getTargets(): returns the list of targets as a set. Type is Set<BoardCell>
	 */
	public Set<BoardCell> getTargets() {
		// TODO
		return targets;
	}

	/*
	 * getAdjList(): Returns the adjacency list for one cell
	 * 
	 * use a HashSet
	 */
	public Set<BoardCell> getAdjList(BoardCell cell) {
		// TODO
		return adjMtx.get(cell);

	}
/*	public static void main(String args[]) {
		IntBoard test = new IntBoard();
		test.calcAdjacencies();
		
	}*/

}

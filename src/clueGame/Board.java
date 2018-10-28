package clueGame;

import java.io.FileNotFoundException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 *
 * @author Abhaya Shrestha, Kirwinlvinodaq S Lawrence, Gazi Mahbub Morshed
 * 
 *         Board class will give us all the methods for the board. We will
 *         mostly focus on the initialize(); initializes the board based on the
 *         files .csv and .txt, setConfigFiles(); sets the instance variable to
 *         particular file name , loadRoomConfig() and loadBoardConfig() methods
 *         which read the .csv and .txt files respectively.
 * 
 */
public class Board {
	// public constants
	public static final int MAX_BOARD_SIZE = 50; // we do not know how big the grid might be
	public static final int MIN_BOARD_SIZE = 0; // we do not know what is the minimum a grid can go
	/*
	 * Instance variables
	 *
	 */
	private int numRows; // number of rows we have for our board
	private int numColumns; // number of columns we have for our board
	private BoardCell[][] grid; // board representation in a grid
	private Map<Character, String> legend; // keeps track of what each room is
	private Map<BoardCell, Set<BoardCell>> adjMatrix; // keeps track of all adjacent squares for each boardcell
	private Set<BoardCell> targets; // used after calculating targets
	private String boardConfigFile; // the file name for the csv file which represents the baord
	private String roomConfigFile; // the file name for the txt file which stores what the initial is for each room
	private static Board theInstance = new Board(); // since there is only one Board we make it static
	private Set<BoardCell> visited; // the visited list that gets changed every time a square is visited

	/** Getters for NumRows and NumColumns */
	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	/** Getters for Legend */
	public Map<Character, String> getLegend() {
		return legend;
	}

	/** Returns a set of adjacency list for all the points */

	public Set<BoardCell> getAdjList(int row, int column) {
		return adjMatrix.get(getCellAt(row, column));

	}
	// variable used for singleton pattern

	/*
	 * Methods
	 */
	/** constructor is private to ensure only one can be created */

	private Board() { // to avoid null pointer exceptions, we allocated space for the board here
		numColumns = 0;
		numRows = 0;
		grid = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
		legend = new HashMap<>();
		adjMatrix = new HashMap<>();
		targets = new HashSet<>();
		visited = new HashSet<>();
		boardConfigFile = "";
		roomConfigFile = "";
	}
	// this method returns the only Board

	public static Board getInstance() {
		return theInstance;
	}

	/**
	 * getCellAt returns the BoardCell from the board given row and column
	 * coordinates
	 */
	public BoardCell getCellAt(int row, int column) {
		return grid[row][column];
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}

	/**
	 * setConfigFiles(): Used for setting the files
	 */
	public void setConfigFiles(String boardConfigFile, String roomConfigFile) {
		// set the file names to use my config files
		this.boardConfigFile = boardConfigFile;
		this.roomConfigFile = roomConfigFile;
	}

	public void initialize() {
		// set the file names to use my config files

		try {
			loadRoomConfig(); // all we are doing here is initializing files
			loadBoardConfig();
			calcAdjacencies();// then calculating our adjacencies for our matrix
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}

	}

	/**
	 * loadRoomConfig() use the TXT file to load into Legend map <char, string>
	 * Where character represents the initial for room type (Ex: 'X' for closet)
	 * String represents the room type (Library, closet etc.)
	 * 
	 */
	public void loadRoomConfig() throws BadConfigFormatException {

		FileReader reader;
		try {
			reader = new FileReader(roomConfigFile);

			Scanner in = new Scanner(reader);

			while (in.hasNextLine()) {
				String[] line = in.nextLine().split(", "); // splitting the line by every occurance of comma
				char key = line[0].charAt(0);
				legend.put(key, line[1]);
				if ((!line[2].equals("Card")) && (!line[2].equals("Other"))) { // throw our BadConfigFormatException if
					// it is anything else other than 'Card'
					// and 'Other'
					throw new BadConfigFormatException(
							"Invalid type for the kind of room. It has to be either Card or Other");
				}
			}
			in.close();
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage() + "Error in roomconfigfile");
		} catch (IOException e) { // this is to catch the resource leak
			System.out.println(e.getMessage());
		}
	}

	private void setDirectionGrid(BoardCell cell, char a, DoorDirection direction) { // helper function
		if (a == (direction.getValue())) { // setting the cell direction respectively to what a char
			cell.setDoorDirection(direction); // might be (EX: 'U' is UP, 'D' is DOWN etc. Look more in enum)
		} // else do nothing
	}

	/**
	 * loadBoardConfig() we are loading into the board which is a 2*2 array of type
	 * BoardCell
	 */
	public void loadBoardConfig() throws BadConfigFormatException {
		Set<Integer> count = new HashSet<>();
		numRows = 0;
		try {
			FileReader reader = new FileReader(boardConfigFile);
			Scanner in = new Scanner(reader);
			while (in.hasNextLine()) {
				String[] line = in.nextLine().split(",");
				numColumns = line.length;
				count.add(numColumns); // we put the number of columns; each time should be expected number of cell
				// if it is not then the set's length will be greater than 1
				for (int i = 0; i < numColumns; i++) {
					// in the two dimensional array, we create a new object, and initialize it's
					// direction based on
					// the length of the square's initial.
					grid[numRows][i] = new BoardCell(numRows, i);
					grid[numRows][i].setInitial(line[i].charAt(0));
					if (line[i].length() > 1) { // check to see if the character represents any of the 4 directions
						setDirectionGrid(grid[numRows][i], line[i].charAt(1), DoorDirection.RIGHT);
						setDirectionGrid(grid[numRows][i], line[i].charAt(1), DoorDirection.LEFT);
						setDirectionGrid(grid[numRows][i], line[i].charAt(1), DoorDirection.DOWN);
						setDirectionGrid(grid[numRows][i], line[i].charAt(1), DoorDirection.UP);
						setDirectionGrid(grid[numRows][i], line[i].charAt(1), DoorDirection.NONE); // else nothing if
						// it is none of the 4 direction, example is a boardCell with 2 characters
						// ending with 'N'
					} else {
						grid[numRows][i].setDoorDirection(DoorDirection.NONE);
					}
				}
				numRows++;
			}
			in.close();
			reader.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		if (count.size() > 1) { // check if the size is consistent for each row
			throw new BadConfigFormatException("For some row, there is a mismatched coloumns");
		}
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				if (!legend.containsKey(grid[i][j].getInitial())) {
					throw new BadConfigFormatException("There is a room in the board that is not in legend list");
				}
			}
		}
	}

	/** Helper functions for calcAdjacencies() Method */
	private void calcAdjHelper(BoardCell cell, Set<BoardCell> adjList, DoorDirection direction) { // checks if an
																									// adjacent board
																									// cell is valid
		if (cell.isWalkway()) { // if our cell that we are testing for adjacencies is a walkway we just add
			adjList.add(cell);
		}
		if ((cell.isDoorway()) && (cell.getDoorDirection() == direction)) {
			adjList.add(cell); // else if we are a doorway and we can go in the right direction we add
		}
	}

	private void checkAllFourDirectionsWalkway(int i, int j, Set<BoardCell> adjList) {
		// adding 1 to the x coordinate, looking DOWN for adjacency
		if ((i + 1) < numRows) { // if the adjacency is within board length
			calcAdjHelper(grid[i + 1][j], adjList, DoorDirection.UP);
		}

		// adding one to the y coordinate, looking RIGHT for adjacency
		if ((j + 1) < numColumns) {
			calcAdjHelper(grid[i][j + 1], adjList, DoorDirection.LEFT);
		}

		// subtracting 1 to the x coordinate, looking UP for adjacency

		if ((i - 1) >= MIN_BOARD_SIZE) {
			calcAdjHelper(grid[i - 1][j], adjList, DoorDirection.DOWN);

		}
		// subtracting 1 to the y coordinate, looking LEFT for adjacency

		if ((j - 1) >= MIN_BOARD_SIZE) {
			calcAdjHelper(grid[i][j - 1], adjList, DoorDirection.RIGHT);

		}
	}

	private void checkDirectionsDoorway(int i, int j, Set<BoardCell> adjList) {
		switch (grid[i][j].getDoorDirection()) {
		case RIGHT: // we can only look one direction if we are a doorway for adjacencies.
			if ((j + 1) < numColumns) { // if we can only look right we add 1 to the y direction and see
				calcAdjHelper(grid[i][j + 1], adjList, DoorDirection.LEFT); // if we have a valid boardcell
			}
			break;
		case LEFT:
			if ((j - 1) >= MIN_BOARD_SIZE) {
				calcAdjHelper(grid[i][j - 1], adjList, DoorDirection.RIGHT);
			}
			break;
		case DOWN:
			if ((i + 1) < numRows) {
				calcAdjHelper(grid[i + 1][j], adjList, DoorDirection.UP);
			}
			break;
		case UP:
			if ((i - 1) >= MIN_BOARD_SIZE) {
				calcAdjHelper(grid[i - 1][j], adjList, DoorDirection.DOWN);
			}
			break;
		case NONE:
			// do nothing
			break;
		}
	}

	/** initialize the adjMatrix 
	 * So that we know all the adjacencies for each cell in the grid*/
	public void calcAdjacencies() {

		for (int i = 0; i < numRows; i++) {

			for (int j = 0; j < numColumns; j++) {
				Set<BoardCell> adjacencies = new HashSet<>(); // temporary set to add things in

				if (grid[i][j].isWalkway()) { // if our coordinate is a walkway
					checkAllFourDirectionsWalkway(i, j, adjacencies);
					adjMatrix.put(grid[i][j], adjacencies);// put all the adjacencies into the adjMatrix
				}

				if (grid[i][j].isDoorway()) { // if our coordinate is a doorway then we can only look for adjacencies in
												// door direction
					checkDirectionsDoorway(i, j, adjacencies);
					adjMatrix.put(grid[i][j], adjacencies);
				}

				else { // if it is neither a walkway or a doorway then we don't need to do anything
						// else
					adjMatrix.put(grid[i][j], adjacencies);
				}

			}
		}
	}
	/**
	 * This method will calculate all the targets within a pathLength for the game*/
	public void calcTargets(int row, int column, int pathLength) {
		targets.clear(); // we have to clear our targets and visited every time we call the recursive
							// method
		visited.clear(); // this is because it will remember what the targets and visited list was which
							// will give
		// incorrect target boardcells.
		calcTargetsHelper(row, column, pathLength);

	}

	private void calcTargetsHelper(int row, int column, int pathLength) {
		visited.add(getCellAt(row, column)); // we always add our startCell for visited list first
		for (BoardCell adjCell : adjMatrix.get(getCellAt(row, column))) {

			if (!(visited.contains(adjCell))) {

				visited.add(adjCell); // add adjCell to visited list
				if (pathLength == 1) {
					targets.add(adjCell); // where targets is initialized
				} else {
					// else we recursively call calcTargets again passing in adjacent cell
					if (adjCell.isDoorway()) {
						targets.add(adjCell);
					}
					calcTargetsHelper(adjCell.getRow(), adjCell.getColumn(), pathLength - 1);
				}
				visited.remove(adjCell);
			}

		}

	}

}
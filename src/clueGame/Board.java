package clueGame;

import java.io.FileNotFoundException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import clueGame.BadConfigFormatException;
import clueGame.BoardCell;

/**
 *
 * @author Abhaya Shrestha, Kirwinlvinodaq S Lawrence, Gazi Mahbub Morshed 
 * 
 * 		    Board
 *         class will give us all the methods for the board. We will mostly
 *         focus on the initialize(); initializes the board based on the files
 *         .csv and .txt, setConfigFiles(); sets the instance variable to
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
	
	/** Returns a set of adjacency list for all the points*/

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
		legend = new HashMap<Character, String>();
		adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
		targets = new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();
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
		} catch (IOException e) { //this is to catch the resource leak
			System.out.println(e.getMessage());
		}
	}

	/**
	 * loadBoardConfig() we are loading into the board which is a 2*2 array of type
	 * BoardCell
	 */
	public void loadBoardConfig() throws BadConfigFormatException {
		Set<Integer> count = new HashSet<Integer>();
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
					if (line[i].length() > 1) { // setting the direction based on the hardcoded directions on enum
						if (line[i].charAt(1) == (DoorDirection.RIGHT).getValue()) // that is being read from the file
						{	grid[numRows][i].setDoorDirection(DoorDirection.RIGHT);
						}
						else if(line[i].charAt(1) == (DoorDirection.LEFT).getValue()) {
							grid[numRows][i].setDoorDirection(DoorDirection.LEFT);
						}
						else if(line[i].charAt(1) == (DoorDirection.DOWN).getValue()) {
							grid[numRows][i].setDoorDirection(DoorDirection.DOWN);
						}
						else if(line[i].charAt(1) == (DoorDirection.UP).getValue()) {
							grid[numRows][i].setDoorDirection(DoorDirection.UP);
						}
						else {
							grid[numRows][i].setDoorDirection(DoorDirection.NONE);
						}
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

	/** initialize the adjMatrix */
	public void calcAdjacencies() {

		for (int i = 0; i < numRows; i++) {

			for (int j = 0; j < numColumns; j++) {
				Set<BoardCell> adjacencies = new HashSet<BoardCell>(); // temporary set to add things in
				
				if(grid[i][j].isWalkway()) { // if our coordinate is a walkway
					// adding 1 to the x coordinate, looking DOWN for adjacency
					if ((i + 1) < numRows) { // if the adjacency is within board length

						if ((grid[i + 1][j].isWalkway())) { // if it is a walkway we add
							adjacencies.add(grid[i + 1][j]);
						}

						if ((grid[i + 1][j].isDoorway())) { // if it is a room cell but a doorway, then we can add
							if ((grid[i + 1][j]).getDoorDirection() == DoorDirection.UP) {// in the appropriate
								// direction
								adjacencies.add(grid[i + 1][j]);
							}
						}

					}

					// adding one to the y coordinate, looking RIGHT for adjacency
					if ((j + 1) < numColumns) {

						if ((grid[i][j + 1].isWalkway())) {
							adjacencies.add(grid[i][j + 1]);
						}

						if ( grid[i][j + 1].isDoorway() && grid[i][j + 1].getDoorDirection() == DoorDirection.LEFT ) {
								adjacencies.add(grid[i][j + 1]);
						}

					}

					// subtracting 1 to the x coordinate, looking UP for adjacency

					if ((i - 1) >= MIN_BOARD_SIZE) {

						if ((grid[i - 1][j].isWalkway())) {
							adjacencies.add(grid[i - 1][j]);
						}

						if ((grid[i - 1][j].isDoorway())) {
							if ((grid[i - 1][j]).getDoorDirection() == DoorDirection.DOWN) {
								adjacencies.add(grid[i - 1][j]);
							}
						}

					}
					// subtracting 1 to the y coordinate, looking LEFT for adjacency

					if ((j - 1) >= MIN_BOARD_SIZE) {

						if ((grid[i][j - 1].isWalkway())) {
							adjacencies.add(grid[i][j - 1]);
						}

						if ( grid[i][j - 1].isDoorway() && grid[i][j - 1].getDoorDirection() == DoorDirection.RIGHT ) {
								adjacencies.add(grid[i][j - 1]);
						}

					} 

					adjMatrix.put(grid[i][j], adjacencies);// put all the adjacencies into the adjMatrix
				}

				if(grid[i][j].isDoorway()) { // if our coordinate is a doorway then we can only look for adjacencies in door direction
					switch(grid[i][j].getDoorDirection()) {
					case RIGHT:
						if((j + 1) < numColumns) {
							if(grid[i][j + 1].isWalkway()) {
								adjacencies.add(grid[i][j + 1]);
							}
							if(grid[i][j + 1].isDoorway()) {
								if(grid[i][j + 1].getDoorDirection() == DoorDirection.LEFT) {
									adjacencies.add(grid[i][j + 1]);
								}
							}
						}
						break;
					case LEFT:
						if((j - 1) >= MIN_BOARD_SIZE) {
							if(grid[i][j - 1].isWalkway()) {
								adjacencies.add(grid[i][j - 1]);
							}
							if(grid[i][j - 1].isDoorway()) {
								if(grid[i][j - 1].getDoorDirection() == DoorDirection.RIGHT) {
									adjacencies.add(grid[i][j - 1]);
								}
							}
						}
						break;
					case DOWN:
						if((i + 1) < numRows) {
							if(grid[i + 1][j].isWalkway()) {
								adjacencies.add(grid[i + 1][j]);
							}
							if(grid[i + 1][j].isDoorway()) {
								if(grid[i + 1][j].getDoorDirection() == DoorDirection.UP) {
									adjacencies.add(grid[i + 1][j]);
								}
							}
						}
						break;
					case UP:
						if((i - 1) >= MIN_BOARD_SIZE) {
							if(grid[i - 1][j].isWalkway()) {
								adjacencies.add(grid[i - 1][j]);
							}
							if(grid[i - 1][j].isDoorway()) {
								if(grid[i - 1][j].getDoorDirection() == DoorDirection.DOWN) {
									adjacencies.add(grid[i - 1][j]);
								}
							}
						}
						break;
					case NONE:
						// do nothing
						break;
					}
					adjMatrix.put(grid[i][j], adjacencies);					 
				}

				else { // if it is neither a walkway or a doorway then we don't need to do anything else
					adjMatrix.put(grid[i][j], adjacencies);
				}



			}
		}
	}

	public void calcTargets(int row, int column, int pathLength) {
		targets.clear();
		visited.clear();
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
					if(adjCell.isDoorway()) {
						targets.add(adjCell);
					}
					calcTargetsHelper(adjCell.getRow(), adjCell.getColumn(), pathLength - 1);
				}
				visited.remove(adjCell);
			}

		}
		
	}

}
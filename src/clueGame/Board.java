package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;

import java.util.*;
import clueGame.BadConfigFormatException;

/**
 *
 * @author Abhaya Shrestha, Kirwinlvinodaq S Lawrence, Gazi Mahbub Morshed Board
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
	/*
	 * Instance variables
	 */
	private int numRows = 0;
	private int numColumns = 0;
	private BoardCell[][] board;
	private Map<Character, String> legend;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private Set<BoardCell> targets;
	private String boardConfigFile;
	private String roomConfigFile;
	private static Board theInstance = new Board();

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

	// variable used for singleton pattern

	/*
	 * Methods
	 */
	/** constructor is private to ensure only one can be created */

	private Board() {
		numColumns = 0;
		numRows = 0;
		board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
		legend = new HashMap<Character, String>();
		adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
		targets = new HashSet<BoardCell>();
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
		return board[row][column];
	}

	public void initialize() {
		// set the file names to use my config files

		try {
			loadRoomConfig();
			loadBoardConfig();
		} catch (Exception e) {
			e.getMessage();
		}

		// TODO implement this method using CTest_FileInitTest.java
	}

	/**
	 * loadRoomConfig() use the TXT file to load into Legend map <char, string>
	 * 
	 */
	public void loadRoomConfig() throws BadConfigFormatException {

		FileReader reader;
		try {
			reader = new FileReader(roomConfigFile);

			@SuppressWarnings("resource")
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
		} catch (FileNotFoundException e) {
			e.printStackTrace();
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
					board[numRows][i] = new BoardCell(numRows, i);
					board[numRows][i].setInitial(line[i].charAt(0));
					if (line[i].length() > 1) {
						switch (line[i].charAt(1)) {
						case 'R':
							board[numRows][i].setDoorDirection(DoorDirection.RIGHT);
							break;
						case 'L':
							board[numRows][i].setDoorDirection(DoorDirection.LEFT);
							break;
						case 'D':
							board[numRows][i].setDoorDirection(DoorDirection.DOWN);
							break;
						case 'U':
							board[numRows][i].setDoorDirection(DoorDirection.UP);
							break;
						default:
							board[numRows][i].setDoorDirection(DoorDirection.NONE);
							break;
						}
					} else {
						board[numRows][i].setDoorDirection(DoorDirection.NONE);
					}
				}
				numRows++;
			}
			in.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		if (count.size() > 1) { // check if the size is consistent for each row
			throw new BadConfigFormatException("For some row, there is a mismatched coloumns");
		}
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				if (!legend.containsKey(board[i][j].getInitial())) {
					throw new BadConfigFormatException("There is a room in the board that is not in legend list");
				}
			}
		}
	}

	public void calcAdjacencies() {
		// TODO implement this method using CTest_FileInitTest.java
	}

	public void calcTargets(BoardCell cell, int pathLength) {
		// TODO implement this method using CTest_FileInitTest.java
	}

	/**
	 * setConfigFiles(): Used for setting the files
	 */
	public void setConfigFiles(String _boardConfigFile, String _roomConfigFile) {
		// TODO implement this method using CTest_FileInitTest.java
		// set the file names to use my config files
		this.boardConfigFile = _boardConfigFile;
		this.roomConfigFile = _roomConfigFile;
	}
}
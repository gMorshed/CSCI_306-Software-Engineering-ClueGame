package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;

import java.util.*;
import clueGame.BadConfigFormatException;

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

	/* Getters for NumRows and NumColumns */
	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	/* Getters for Legend */
	public Map<Character, String> getLegend() {
		return legend;
	}

	// variable used for singleton pattern

	/*
	 * Methods
	 */
	// constructor is private to ensure only one can be created

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

	/*
	 * returns the BoardCell from the board
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
	 * loadRoomConfig() use the TXT file We're loading into Legend
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
				if ((!line[2].equals("Card")) && (!line[2].equals("Other"))) {
					throw new BadConfigFormatException(
							"Invalid type for the kind of room. It has to be either Card or Other");
				}
			}
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadBoardConfig() throws BadConfigFormatException {
		Set<Integer> count = new HashSet<Integer>();
		numRows = 0;
		try {
			FileReader reader = new FileReader(boardConfigFile);
			Scanner in = new Scanner(reader);
			while (in.hasNextLine()) {
				String[] line = in.nextLine().split(",");
				numColumns = line.length;
				count.add(numColumns); // so we put into a set to see if each row has an equivalent number of columns
				for (int i = 0; i < numColumns; i++) {
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

		if (count.size() > 1) {
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

	/*
	 * Used for setting the files
	 */
	public void setConfigFiles(String _boardConfigFile, String _roomConfigFile) {
		// TODO implement this method using CTest_FileInitTest.java
		// set the file names to use my config files
		this.boardConfigFile = _boardConfigFile;
		this.roomConfigFile = _roomConfigFile;
	}
}
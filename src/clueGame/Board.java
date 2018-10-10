package clueGame;

import java.io.FileReader;
import java.util.*;

public class Board {
	// public constants
	public static final int MAX_BOARD_SIZE = 50; // we do not know how big the grid might be
	/*
	 * Instance variables*/
	private int numRows=0;
	private int numColumns=0;
	private BoardCell[][] board;
	private Map<Character, String> legend;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private Set<BoardCell> targets;
	private String boardConfigFile;
	private String roomConfigFile;
	private static Board theInstance = new Board();

	/*Getters for NumRows and NumColumns*/
	public int getNumRows() {
		return numRows;
	}
	
	
	public int getNumColumns() {
		return numColumns;
	}

	/*Getters for Legend*/
	public Map<Character, String> getLegend() {
		return legend;
	}
	
	// variable used for singleton pattern
	
	
	/*
	 * Methods
	 * */
	// constructor is private to ensure only one can be created
	
	private Board() {
		numColumns=0;
		numRows=0;
		board= new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
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
	 * */
	public BoardCell getCellAt(int row, int column) {
		return board[row][column];
	}
	public void initialize() {
		// set the file names to use my config files
		loadRoomConfig();
		loadBoardConfig();
		//TODO implement this method using CTest_FileInitTest.java
	}
	/**
	 * loadRoomConfig() use the TXT file
	 * We're loading into Legend
	 * 
	 */
	public void loadRoomConfig() {
		try {
			FileReader reader = new FileReader(roomConfigFile);
			Scanner in = new Scanner(reader);
			while(in.hasNextLine()) {
				String[] line=in.nextLine().split(", "); //splitting the line by every occurance of comma
				char key= line[0].charAt(0);
				legend.put(key, line[1]);
			}
			in.close();

		} catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		
		
		
	}
	
	public void loadBoardConfig() {
		try {
			FileReader reader = new FileReader(boardConfigFile);
			Scanner in = new Scanner(reader);
			while(in.hasNextLine()) {
				String[] line=in.nextLine().split(",");
				numColumns = line.length;
				for(int i=0; i<numColumns; i++) {
					board[numRows][i]= new BoardCell(numRows, i);
					board[numRows][i].setInitial(line[i].charAt(0));
					if(line[i].length() > 1) {
						switch(line[i].charAt(1)) {
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
					}
					else {
						board[numRows][i].setDoorDirection(DoorDirection.NONE);
					}
				}
				numRows++;
			}
			in.close();

		} catch(Exception e){
			System.out.println(e.getMessage());
		}

	}
	
	public void calcAdjacencies() {
		//TODO implement this method using CTest_FileInitTest.java
	}
	
	public void calcTargets(BoardCell cell, int pathLength) {
		//TODO implement this method using CTest_FileInitTest.java
	}
	
	/*
	 * Used for setting the files
	 * */
	public void setConfigFiles(String _boardConfigFile, String _roomConfigFile) {
		//TODO implement this method using CTest_FileInitTest.java
		// set the file names to use my config files
		this.boardConfigFile = _boardConfigFile;
		this.roomConfigFile = _roomConfigFile;
	}
}
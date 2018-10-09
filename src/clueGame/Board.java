
package clueGame;

import java.util.*;

public class Board {
	// public constants
	public static final int MAX_BOARD_SIZE = 50; // we do not know how big the grid might be
	/*
	 * Instance variables*/
	private int numRows;
	private int numColumns;
	private BoardCell[][] board;
	private Map<Character, String> legend;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private Set<BoardCell> targets;
	private String boardConfigFile;
	private String roomConfigFile;
	private static Board theInstance = new Board();

	/*Getters for NumRows and NumColumns*/
	public int getNumRows() {
		return 0;
	}
	
	
	public int getNumColumns() {
		return 0;
	}

	/*Getters for Legend*/
	public Map<Character, String> getLegend() {
		return null;
	}
	
	// variable used for singleton pattern
	
	
	/*
	 * Methods
	 * */
	// constructor is private to ensure only one can be created
	
	private Board() {}
	// this method returns the only Board
	
	public static Board getInstance() {
		return theInstance;
	}
	/*
	 * returns the BoardCell from the board
	 * */
	public BoardCell getCellAt(int row, int column) {
		return null;
	}
	public void initialize() {
		//TODO implement this method using CTest_FileInitTest.java
	}
	
	public void loadRoomConfig() {
		//TODO implement this method using CTest_FileInitTest.java
	}
	
	public void loadBoardConfig() {
		//TODO implement this method using CTest_FileInitTest.java
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
	}
	
}

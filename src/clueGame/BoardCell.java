/*
 * Team members: Abhaya Shrestha, Gazi Mahbub Morshed, Kirwinl Vinodaq S Lawrence
 * */
package clueGame;

/*represents one cell in your grid
 * two instance variables of type int to represent
 * row and column
 * -- more fields will be added later
 * */
public class BoardCell {

	/*
	 * Instance variables and constructors
	 * */
	private int row;

	private int column;
	
	private char initial; 

	private DoorDirection doorDirection;
	/*
	 * Constructor*/
	public BoardCell(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}
	
	/*Getters and setters for doorDirection
	 * */
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	public void setDoorDirection(DoorDirection doorDirection) {
		this.doorDirection = doorDirection;
	}
	
	/*
	 * getters and setters for row*/
	public int getRow() {
		return row;
	}


	public void setRow(int row) {
		this.row = row;
	}

	/*
	 * getters and setters for column*/
	public int getColumn() {
		return column;
	}


	public void setColumn(int column) {
		this.column = column;
	}

	/*
	 * getters and setters for initial*/
	public char getInitial() {
		return initial;
	}


	public void setInitial(char initial) {
		this.initial = initial;
	}


	/*
	 * Checks if the BoardCell is a walkway*/
	public boolean isWalkway() {
		//TODO implement this method using hints from CTest_FileInitTests.java
		return false;
	}
	
	/*
	 * Checks if the BoardCell is a room*/
	public boolean isRoom() {
		//TODO implement this method using hints from CTest_FileInitTests.java
		return false;
	}
	
	/*
	 * Checks if the BoardCell is a Doorway*/
	public boolean isDoorway() {
		//TODO implement this method using hints from CTest_FileInitTests.java
		return false; 
	}

}

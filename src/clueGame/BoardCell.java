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

	private int row;

	private int column;
	
	private char initial; 
	/**
	 * comment that i added to check
	 * @param row
	 * @param column
	 */
	public BoardCell(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}
	
	/*
	 * Checks if the BoardCell is a walkway*/
	public boolean isWalkway() {
		return false;
	}
	
	/*
	 * Checks if the BoardCell is a room*/
	public boolean isRoom() {
		return false;
	}
	
	/*
	 * Checks if the BoardCell is a Doorway*/
	public boolean isDoorway() {
		return false; 
	}

}

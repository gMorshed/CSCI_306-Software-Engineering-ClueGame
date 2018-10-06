package experiment;

/*represents one cell in your grid
 * two instance variables of type int to represent
 * row and column
 * -- more fields will be added later
 * */
public class BoardCell {

	private int row;

	private int column;

	public BoardCell(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}
	@Override
	public String toString() {
		return "( " + this.row + " , " + this.column + " )"; 
	} // to string method is used for testing

}

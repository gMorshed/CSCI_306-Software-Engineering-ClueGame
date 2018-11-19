
package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Set;

import javax.swing.JLabel;

/**
*
* @author Abhaya Shrestha, Kirwinlvinodaq S Lawrence, Gazi Mahbub Morshed
* 
*/

/**
 * class BoardCell represents one cell in your grid two instance variables of
 * type int to represent row and column -- more fields will be added later
 */
public class BoardCell {

	/**
	 * Instance variables and constructors
	 */
	private int row;

	private int column;

	private char initial;

	private DoorDirection doorDirection;
	

	
	//private Color color; // color of the board cell

	// Constants variables used for the draw method
	public static final int WIDTH = 30;
	public static final int DOOR_LENGTH = 5;
	public BoardCell(int row, int column) {
		super();
		this.row = row;
		this.column = column;
		
	}

	/**
	 * Getters and setters for doorDirection
	 */
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	public void setDoorDirection(DoorDirection doorDirection) {
		this.doorDirection = doorDirection;
	}

	/**
	 * getters and setters for row
	 */
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	/**
	 * getters and setters for column
	 */
	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	/**
	 * getters and setters for initial
	 */
	public char getInitial() {
		return initial;
	}

	public void setInitial(char initial) {
		this.initial = initial;
	}

	/**
	 * Checks if the BoardCell is a walkway
	 */
	public boolean isWalkway() {
		return (initial == 'W');
	}


	/**
	 * Checks if the BoardCell is a room
	 */
	public boolean isRoom() {
		return (initial != 'W' || initial != 'X');
	}

	/**
	 * Checks if the BoardCell is a Doorway
	 */
	public boolean isDoorway() {
		return(this.doorDirection != DoorDirection.NONE);
	}

	@Override
	public String toString() {
		return "( " + row + ", " + column + ", " + initial + " )";
	}
	
	/**
	 * Using the graphics draws the BoardCell object
	 * Colors the walkway cells yellow, the room cells light gray, and then doorway a mix of light gray and blue
	 * where blue is color of the door direction. 
	 * @param g
	 */
	public void draw(Graphics g) {
		int x = row * WIDTH;
		int y = column*WIDTH;
		
		if(this.isWalkway()) {
			g.setColor(Color.YELLOW); // if it is a walkway then we just color it yellow
			g.fillRect(y, x, WIDTH, WIDTH);
			g.setColor(Color.BLACK);
			g.drawRect(y, x, WIDTH, WIDTH);
		}
		else if(this.isDoorway()) {
			g.setColor(Color.LIGHT_GRAY); // if it is a doorway we color it light gray first
			g.fillRect(y, x, WIDTH, WIDTH);
			g.setColor(Color.BLUE);
			if(this.doorDirection == DoorDirection.RIGHT) {
				g.drawRect(y+WIDTH-DOOR_LENGTH, x, DOOR_LENGTH, WIDTH ); // with respective coordinates, we color the door length rectangle
				g.fillRect(y+WIDTH-DOOR_LENGTH, x, DOOR_LENGTH, WIDTH );
			}
			else if(this.doorDirection == DoorDirection.LEFT) {
				g.drawRect(y, x, DOOR_LENGTH,WIDTH);
				g.fillRect(y, x, DOOR_LENGTH,WIDTH);
			}
			else if(this.doorDirection == DoorDirection.DOWN) {
				g.drawRect(y, x+WIDTH-DOOR_LENGTH, WIDTH, DOOR_LENGTH);
				g.fillRect(y, x+WIDTH-DOOR_LENGTH, WIDTH, DOOR_LENGTH);
			}
			else if(this.doorDirection == DoorDirection.UP) {
				g.drawRect(y, x, WIDTH, DOOR_LENGTH);
				g.fillRect(y, x, WIDTH, DOOR_LENGTH);
			}
		}
		
		else {
			g.setColor(Color.LIGHT_GRAY); // else just color it light gray
			g.fillRect(y, x, WIDTH, WIDTH);
		}	
	}


	/**
	 * Names the room in the board
	 * @param g
	 * @param string
	 */
	public void drawName(Graphics g, String string) {
		// TODO Auto-generated method stub
		int y = this.getRow()*WIDTH;
		int x = this.getColumn()*WIDTH;
		g.setColor(Color.BLACK);
		g.drawString(string, x, y);
		
	}
	
	/**
	 * Method used to paint the BoardCell when selecting targets
	 * 
	 * @param g
	 */
	public void reDraw(Graphics g) {
		int x = this.getRow()*WIDTH;
		int y = this.getColumn()*WIDTH;
		g.setColor(Color.CYAN); // if it is a walkway then we just color it yellow
		g.fillRect(y, x, WIDTH, WIDTH);
		g.setColor(Color.BLACK);
		g.drawRect(y, x, WIDTH, WIDTH);
	}
	
}
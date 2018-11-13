package clueGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.io.FileNotFoundException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Field;
import java.util.*;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;



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
public class Board extends JPanel{
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
	private String playerConfigFile; 
	private String weaponConfigFile;
	private static Board theInstance = new Board(); // since there is only one Board we make it static
	private Set<BoardCell> visited; // the visited list that gets changed every time a square is visited
	private ArrayList <Player> playerList; // list of players in the game
	private ArrayList<Card> deckOfCards, allCards; // deck of cards, and allCards is the copy
	private Solution gameSolution; // Solution for the game
	
	private ArrayList<BoardCell> roomNamesCoordinate;
	
	public Solution getGameSolution() {
		return gameSolution;
	}
	
	public void setGameSolution(Solution answer) {
		gameSolution = answer;
	}
	
	public void setPlayerList(ArrayList<Player> playerList) {
		this.playerList = playerList;
	}

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
		playerList = new ArrayList<Player>();
		deckOfCards = new ArrayList<Card>();
		gameSolution = new Solution("", "", "");
		allCards = new ArrayList<Card>();
		roomNamesCoordinate = new ArrayList <BoardCell>();
		setVisible(true);
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
			loadPeoplecConfigFile();
			loadDeckOfCards();
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		roomNamesCoordinate.add(grid[3][1]); // coordinates represent the place where we want to name the rooms
		roomNamesCoordinate.add(grid[2][8]);
		roomNamesCoordinate.add(grid[3][15]);
		roomNamesCoordinate.add(grid[12][1]);
		roomNamesCoordinate.add(grid[12][14]);
		roomNamesCoordinate.add(grid[20][1]);
		roomNamesCoordinate.add(grid[20][7]);
		roomNamesCoordinate.add(grid[16][15]);
		roomNamesCoordinate.add(grid[21][14]);
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

	public void setPlayerConfigFile(String playerConfigFile) {
		this.playerConfigFile = playerConfigFile;
	}
	/**
	 * 
	 * @throws BadConfigFormatException
	 * the loadpeopleconfigfile method reads in the playercongfig file and parses accordingly based on the format we decided
	 * into human and computer player and their starting locations
	 */
	public void loadPeoplecConfigFile() throws BadConfigFormatException {
		try {
			FileReader reader= new FileReader(playerConfigFile);
			Scanner in = new Scanner(reader);
			while(in.hasNextLine()) {
				String tempStrArray[] = in.nextLine().split(", ");
				if(tempStrArray.length != 5) { //the length of each line is 5
					throw new BadConfigFormatException("bad format in " + playerConfigFile);
				}
				Color color;
				color= convertColor(tempStrArray[1]);
				int playerRow = Integer.parseInt(tempStrArray[3]);
				int playerCol = Integer.parseInt(tempStrArray[4]);
				if(tempStrArray[2].equals("Human")) {
					HumanPlayer hplayer= new HumanPlayer(playerRow, playerCol, color, tempStrArray[0]);
					playerList.add(hplayer);
				}
				else {
					ComputerPlayer cPlayer= new ComputerPlayer(playerRow, playerCol, color, tempStrArray[0]);
					playerList.add(cPlayer);
				}
			}
			in.close();
		} catch (FileNotFoundException e) {
			
			e.getMessage();
		}
	}
	public ArrayList<Player> getPlayerList() {
		return playerList;
	}
	/**
	 *  convertcolor uses reflection with the in built color class for each players piece color
	 * @param strColor
	 * @return
	 */
	
	private Color convertColor(String strColor) {
		Color color;
		try {
			Field field = Class.forName("java.awt.Color").getField(strColor);
			color =(Color)field.get(null);
		} catch (Exception e) {
			color=null;
		}
		return color;
	}

	public ArrayList<Card> getDeckOfCards() {
		// TODO Auto-generated method stub
		return deckOfCards;
	}
	/**
	 * set the weapon config file
	 * @param weaponConfigFile
	 */
	public void setWeaponConfigFile(String weaponConfigFile) {
		this.weaponConfigFile = weaponConfigFile;
	}
	/**
	 * loaddeckofcards method ensures the deck is loaded with all the different types of cards from three different atrributes
	 * althuogh the rooms were made to remove walkway and closet
	 */
	public void loadDeckOfCards() { 
		Set<Character> keys = legend.keySet();
		keys.remove('W');
		keys.remove('X');
		for(Character character : keys) {
			Card card= new Card(legend.get(character), CardType.ROOM);
			deckOfCards.add(card);
		}
		for(Player player : playerList) {
			Card card= new Card(player.getPlayerName(), CardType.PERSON);
			deckOfCards.add(card);
		}
		try {
			FileReader reader= new FileReader(weaponConfigFile);
			Scanner in = new Scanner(reader);
			while(in.hasNextLine()) {
				Card card = new Card(in.nextLine(), CardType.WEAPON);
				deckOfCards.add(card);
			}
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		 
	}
	/**
	 * dealcards method just deals the card in random order to simulate shuffling and distributes them to
	 *  each player until deck is empty
	 */
	public void dealCards(){
		for (int i = 0; i < deckOfCards.size(); i++) {
			int j = (int)(Math.random() *  deckOfCards.size()); // Get a random index out of 52
			Card temp = deckOfCards.get(i); // Swap the cards
			deckOfCards.set(i, deckOfCards.get(j));
			deckOfCards.set(j, temp);
		}
		
		//Solution must be placed into list 
		
		for(Card card : deckOfCards) { // must place a weapon into solution
			
			if(card.getCardType() == CardType.WEAPON) {
				gameSolution.setWeapon(card.getCardName());
				allCards.add(card); // copy all the cards
				deckOfCards.remove(card);
				break;
			}
		}
		for(Card card : deckOfCards) { // must place a person into solution
			if(card.getCardType() == CardType.PERSON) {
				gameSolution.setPerson(card.getCardName());
				allCards.add(card);
				deckOfCards.remove(card);
				break;
			}
		}
		for(Card card : deckOfCards) { // must place a room into solution
			if(card.getCardType() == CardType.ROOM) {
				gameSolution.setRoom(card.getCardName());
				allCards.add(card);
				deckOfCards.remove(card);
				break;
			}
		}
		
		// dealing the remaining deck of cards to the players
		while(!deckOfCards.isEmpty()) {
			for(Player player : playerList)  {	
				if(!deckOfCards.isEmpty() ) {
						
				player.receiveCard(deckOfCards.get(0));
				allCards.add(deckOfCards.get(0)); 
				deckOfCards.remove(deckOfCards.get(0));	
				}
				
				}
			}
		

	}
	
	
	/**
	 * checkAccusation method checks a an accusation passed in as solution type and if accusation matches all three
	 * conditions then its a pass if not then false
	 * @param accusation
	 * @return
	 */
	

	public boolean checkAccusation(Solution accusation) {
		
		
		if((gameSolution.getPerson().equals(accusation.person)) && (gameSolution.getWeapon().equals(accusation.weapon)))  {
			if((gameSolution.getRoom().equals(accusation.room))) {
				return true;
			}
		}
		
		return false;

		
	}
	/**
	 * handlesuggestion method cross check the suggestion made by th eplayer with every other player in the list 
	 * and returns the dissaprovable card if possible and if not then it will return null
	 * first for loop is from next to player to end and next is from begiining to before player
	 * @param suggested
	 * @param player
	 * @return
	 */

	public Card handleSuggestion(Solution suggested, Player player) {
		Card card =null;
		for(int i=playerList.indexOf(player)+1; i<playerList.size(); i++) { //start after the player
			card = (playerList.get(i)).disproveSuggestion(suggested);
			if(card != null) {
				return card;
			}
		}
		for(int i=0; i<playerList.indexOf(player); i++) { //start after the player
			card = (playerList.get(i)).disproveSuggestion(suggested);
			if(card != null) {
				return card;
			}
		}
		return card;
	}
	/**
	 * getter for allCards instance variable
	 * @return
	 */
	public ArrayList<Card> getAllCards() {
		return allCards;
	}
	
	/**
	 * paint component paints the gird using the BoardCell draw method
	 * for each cell in the grid. It also draws the players using the draw
	 * player method.
	 * It also names the rooms respectively. 
	 * 
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(int i = 0 ; i < numRows; i++) {
			for(int j = 0 ; j < numColumns; j++) {
				grid[i][j].draw(g);
			}
			
		}
		//naming the rooms with the set color and drawstring method
		for(BoardCell cell : roomNamesCoordinate) {
			cell.drawName(g, legend.get(cell.getInitial()));
		}
		// drawing location for the player
		for(Player p : playerList) {
			p.draw(g);
		}
	}
	


}
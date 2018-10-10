package clueGame;

public class BadConfigFormatException extends Exception{

	/*
	 * Just create a skeleton BadConfigFormatException
	 * with no attributes just constructors
	 * 
	 * Write Exception:
	 * 
	 * - board config refers to a room that is not in the file
	 * - board config file does not have the same number of columns in every row.
	 * - an entry in the room config file does not have the proper format.
	 * */
	public BadConfigFormatException() {
		super("The file is not valid becuase of imporper file configuration format.");
		// TODO Auto-generated constructor stub
	}
	
	public BadConfigFormatException(String message) {
		super("The file is not valid becuase of imporper file configuration format." + message);
		// TODO Auto-generated constructor stub
	}
	
	
	

}

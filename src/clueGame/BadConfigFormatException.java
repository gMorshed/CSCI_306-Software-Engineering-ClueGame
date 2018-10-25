package clueGame;

import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.FileOutputStream;

/**
 * @author Abhaya Shrestha, Kirwinlvinodaq S Lawrence, Gazi Mahbub Morshed
 *         custom exception for throwing out custom exception messages for when
 *         there is a badroom,badconfig file
 */

public class BadConfigFormatException extends Exception {

	/**
	 * Just create a skeleton BadConfigFormatException with no attributes just
	 * constructors
	 * 
	 * Write Exception:
	 * 
	 * board config refers to a room that is not in the file - board config file
	 * does not have the same number of columns in every row. - an entry in the room
	 * config file does not have the proper format.
	 * 
	 * Extra credit is done in this file
	 **/
	public BadConfigFormatException() { // first constructor just prints the default message
		super("The file is not valid because of imporper file configuration format.");
	
	}

	public BadConfigFormatException(String message) { // second constructor will print the specific message.
		super("The file is not valid because of imporper file configuration format." + message);
		// Extra credit: Writing the error message to a file
		try {
			PrintWriter pw = new PrintWriter(new FileOutputStream(new File("log.txt"), true));
			pw.println("The file is not valid because of imporper file configuration format." + message);
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

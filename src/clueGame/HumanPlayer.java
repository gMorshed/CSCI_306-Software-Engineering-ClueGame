package clueGame;
/**
*
* @author Abhaya Shrestha, Kirwinlvinodaq S Lawrence, Gazi Mahbub Morshed

* */
import java.awt.Color;

public class HumanPlayer extends Player {

	public HumanPlayer(int row, int column, Color color, String playerName) {
		super(row, column, color, playerName);
	}
	@Override
	public boolean isHuman() {
		super.human = true;
		return human;
	}
}
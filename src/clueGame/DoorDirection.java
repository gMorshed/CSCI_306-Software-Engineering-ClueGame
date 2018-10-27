package clueGame;

/**
 * Enumerator DoorDirection has attributes UP, DOWN, LEFT, RIGHT, NONE
 * These are the directions for the doorway
 */
public enum DoorDirection {

	UP('U'), DOWN('D'), LEFT('L'), RIGHT('R'), NONE('N');
	private final char value;
	DoorDirection(char value){
		this.value = value;
	}
	public char getValue() {
		return this.value;
	}
	
}

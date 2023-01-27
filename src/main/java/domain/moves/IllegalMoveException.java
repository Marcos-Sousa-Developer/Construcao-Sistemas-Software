package domain.moves;

public class IllegalMoveException extends Exception { 
	public IllegalMoveException() {
		super("Illegal move");
	}

	public IllegalMoveException(String message) {
		super(message);
	}

}

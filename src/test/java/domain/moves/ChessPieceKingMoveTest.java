package domain.moves;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import domain.ChessBoard;
import domain.ChessPiece;
import domain.ChessPieceKind;
import domain.ChessPosition;
import domain.Color;

class ChessPieceKingMoveTest {

    private static ChessBoard board;
    private static ChessPieceKingMove kingMove;

	@BeforeAll
	public static void beforeAll() {
		ChessPiece king = new ChessPiece(ChessPieceKind.KING, Color.BLACK);
		kingMove = (ChessPieceKingMove) king.getMoveStrategy();
		
		board = new ChessBoard();
		
        // Pieces for testing
        board.set(4, 3, new ChessPiece(ChessPieceKind.KING, Color.BLACK));
	} 
	
	@Test
	@Order(1)
	public void isMoveValid() { 
        final ChessPosition from = new ChessPosition(4, 3);

		// King 5d to 5f; false because the move is two squares away and not a castle
        assertThrows(IllegalMoveException.class, () -> kingMove.isMoveValid(from, new ChessPosition(4, 5), board));

		//King 5d to 5e; true because the move is one square away
		try {
			assertTrue(kingMove.isMoveValid(from, new ChessPosition(4, 4), board));
		} catch (IllegalMoveException e) {
			// This should not happen
		}
	}
}

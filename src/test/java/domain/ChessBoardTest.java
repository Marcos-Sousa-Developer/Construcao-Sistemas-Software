package domain;

import static org.junit.jupiter.api.Assertions.*;

import com.mysql.cj.jdbc.SuspendableXAConnection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import domain.moves.IllegalMoveException;

class ChessBoardTest {
	
	private static ChessBoard board;
	
	@BeforeAll
	static void beforeAll() {
		board = new ChessBoard();
	}
    
    @Test
	@Order(1)
	public void get() {
		ChessPiece piece;
				
		int row = 0;
		int col = 0; 
		piece = board.get(row, col);
		
		assertEquals(piece.getColor(), Color.WHITE);  
		assertEquals(piece.getChessPieceKind(), ChessPieceKind.ROOK); 
		
		row = 7;
		col = 7; 
		piece = board.get(row, col);
		
		assertEquals(piece.getColor(), Color.BLACK); 
		assertEquals(piece.getChessPieceKind(), ChessPieceKind.ROOK);
		
		row = 7;
		col = 7; 
		piece = board.get(row, col);
		
		assertEquals(piece.getColor(), Color.BLACK); 
		assertEquals(piece.getChessPieceKind(), ChessPieceKind.ROOK);
		
		row = 6;
		col = 5; 
		piece = board.get(row, col);
		
		assertEquals(piece.getColor(), Color.BLACK); 
		assertEquals(piece.getChessPieceKind(), ChessPieceKind.PAWN);
		
		row = 3;
		col = 5; 
		piece = board.get(row, col);

		assertNull(piece);
	}
    
    @Test
	@Order(2)
	public void update() throws IllegalMoveException {
		ChessPiece piecePawn = board.get(6, 0); 
		ChessPosition positionFrom = new ChessPosition(6, 0);
		ChessPosition positionTo = new ChessPosition(5, 0);
		
		ChessMove move = new ChessMove(piecePawn, positionFrom, positionTo);
		System.out.print(board);
		System.out.println(move);

		try {
			board.update(move);
		} catch (IllegalMoveException e) {
			// Do nothing, this should not happen
			System.out.println("Illegal move");
		}

		assertNull(board.get(6, 0));
		assertEquals(board.get(5, 0), piecePawn);  
	}
    
    @Test
	@Order(3)
	public void updateMoveException() throws IllegalMoveException {  
		Assertions.assertThrows(IllegalMoveException.class, () -> { 
			ChessPiece piecePawn = board.get(6, 0);
			ChessPosition positionFrom = new ChessPosition(6, 0);
			ChessPosition positionTo = new ChessPosition(7, 0);
			ChessMove move = new ChessMove(piecePawn, positionFrom, positionTo ); 
			board.update(move);
		});
	}
}

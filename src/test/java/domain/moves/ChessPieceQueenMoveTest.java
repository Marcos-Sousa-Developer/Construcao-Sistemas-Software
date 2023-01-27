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

class ChessPieceQueenMoveTest { 
    private static ChessBoard board;
    private static ChessPieceQueenMove queenMove;

	@BeforeAll
	public static void beforeAll() {
		ChessPiece queen = new ChessPiece(ChessPieceKind.QUEEN, Color.BLACK);
		queenMove = (ChessPieceQueenMove) queen.getMoveStrategy();
		
		board = new ChessBoard();
		
        // Pieces for testing
        board.set(4, 4, new ChessPiece(ChessPieceKind.QUEEN, Color.BLACK));
	} 
	
	@Test
	@Order(1)
	public void isMoveValid() { 
        final ChessPosition from = new ChessPosition(4, 4);

		//Queen 5e to 6f; true 
        ChessPosition to = new ChessPosition(5, 5);
        try {
            assertTrue(queenMove.isMoveValid(from, to, board));
        } catch (IllegalMoveException e) {
            // This should not happen
        }

        //Queen 5e to 3d; true
        to = new ChessPosition(5, 3);
        try {
            assertTrue(queenMove.isMoveValid(from, to, board));
        } catch (IllegalMoveException e) {
            // This should not happen
        }

        //Queen 5e to 3c; true
        to = new ChessPosition(2, 2);
        try {
            assertTrue(queenMove.isMoveValid(from, to, board));
        } catch (IllegalMoveException e) {
            // This should not happen
        }

        //Queen 5e to 5a; true
        to = new ChessPosition(2, 6);
        try {
            assertTrue(queenMove.isMoveValid(from, to, board));
        } catch (IllegalMoveException e) {
            // This should not happen
        }

        //Queen 5e to 5h; true
        to = new ChessPosition(4, 7);
        try {
            assertTrue(queenMove.isMoveValid(from, to, board));
        } catch (IllegalMoveException e) {
            // This should not happen
        }

        //Queen 5e to 5a; true
        to = new ChessPosition(4, 0);
        try {
            assertTrue(queenMove.isMoveValid(from, to, board));
        } catch (IllegalMoveException e) {
            // This should not happen
        }

        //Queen 5e to 6e; true
        to = new ChessPosition(5, 4);
        try {
            assertTrue(queenMove.isMoveValid(from, to, board));
        } catch (IllegalMoveException e) {
            // This should not happen
        }

        //Queen 5e to 3e; true
        to = new ChessPosition(2, 4);
        try {
            assertTrue(queenMove.isMoveValid(from, to, board));
        } catch (IllegalMoveException e) {
            // This should not happen
        }

        //Queen 5e to 2h; true because only see if move is valid
        to = new ChessPosition(1, 7);
        try {
            assertTrue(queenMove.isMoveValid(from, to, board));
        } catch (IllegalMoveException e) {
            // This should not happen
        }

        //Queen 5e to 1h; false
        assertThrows(IllegalMoveException.class, () -> queenMove.isMoveValid(from, new ChessPosition(0, 7), board));
        
		//Queen 5e to 1h; false
        assertThrows(IllegalMoveException.class, () -> queenMove.isMoveValid(from, new ChessPosition(0, 7), board));
        
		//Queen 5e to 1b; true
        to = new ChessPosition(1, 1);
        try {
            assertTrue(queenMove.isMoveValid(from, to, board));
        } catch (IllegalMoveException e) {
            // This should not happen
        }

        //Queen 5e to 1a; true
        assertThrows(IllegalMoveException.class, () -> queenMove.isMoveValid(from, new ChessPosition(1, 0), board));
	} 
	
	@Test
	@Order(2)
	public void isPathClear() { 
        final ChessPosition from = new ChessPosition(4, 4);

		//Queen 5e to 6f; true 
        ChessPosition to = new ChessPosition(5, 5);
        try {
            assertTrue(queenMove.isPathClear(from, to, board));
        } catch (IllegalMoveException e) {
            // This should not happen
        }

        //Queen 5e to 2h; true because only see the path not the position which want
        to = new ChessPosition(1, 7);
        try {
            assertTrue(queenMove.isPathClear(from, to, board));
        } catch (IllegalMoveException e) {
            // This should not happen
        }

        //Queen 5e to 2h; false
        board.set(2, 6, new ChessPiece(ChessPieceKind.BISHOP, Color.BLACK));        
        assertThrows(IllegalMoveException.class, () -> queenMove.isPathClear(from, new ChessPosition(1, 7), board));
	} 
}

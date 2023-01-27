package domain.moves;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.ChessBoard;
import domain.ChessPiece;
import domain.ChessPieceKind;
import domain.ChessPosition;
import domain.Color;

import static org.junit.jupiter.api.Assertions.*;

public class ChessPieceKnightMoveTest {
    private ChessPieceKnightMove knightMove;
    private ChessBoard board;

    @BeforeEach
    void setUp() {
        ChessPiece knight = new ChessPiece(ChessPieceKind.KNIGHT, Color.WHITE);
        knightMove = (ChessPieceKnightMove) knight.getMoveStrategy();

        board = new ChessBoard();

        // Pieces for testing
        board.set(4, 5, new ChessPiece(ChessPieceKind.ROOK, Color.WHITE));
        board.set(2, 7, new ChessPiece(ChessPieceKind.ROOK, Color.WHITE));

        // The knight
        board.set(2, 2, knight);
    }

    @Test
    void isMoveValid() {
        // knight c3 to d4; false because the move is diagonal
        final ChessPosition from = new ChessPosition(2, 2);
        assertThrows(IllegalMoveException.class, () -> knightMove.isMoveValid(from, new ChessPosition(3, 3), board));

        // knight c3 to c2; false because the move is vertical
        assertThrows(IllegalMoveException.class, () -> knightMove.isMoveValid(from, new ChessPosition(1, 2), board));

        // knight c3 to c2; false because the move is horizontal
        assertThrows(IllegalMoveException.class, () -> knightMove.isMoveValid(from, new ChessPosition(2, 0), board));

        // knight c3 to a4; true because the move is an "L" shape -> 1 jump then 2
        try {
            assertTrue(knightMove.isMoveValid(from, new ChessPosition(3, 0), board));
        } catch (IllegalMoveException e) {
            // This should not happen
        }

        // knight c3 to d5; true because the move is an "L" shape -> 1 jump then 2
        try {
            assertTrue(knightMove.isMoveValid(from, new ChessPosition(4, 3), board));
        } catch (IllegalMoveException e) {
            // This should not happen
        }

        // knight c3 to e4; true because the move is an "L" shape -> 2 jumps then 1
        try {
            assertTrue(knightMove.isMoveValid(from, new ChessPosition(3, 4), board));
        } catch (IllegalMoveException e) {
            // This should not happen
        }
    }

    @Test
    void isPathClear() {
        // This method requires that the move is valid, so we don't need to test that here.
    	
    	//Test results are always true because to knight paths are always clear
        // knight g5 to f3; true because the move is "L" shape (1 jump then 2) AND there is no piece in the way
        ChessPosition from = new ChessPosition(4, 6);
        ChessPosition to = new ChessPosition(2, 5);
        assertTrue(knightMove.isPathClear(from, to, board));

        // knight g5 to e6; true because the move is "L" shape (2 jumps then 1) 
        to = new ChessPosition(5, 4);
        assertTrue(knightMove.isPathClear(from, to, board)); 


        // knight g5 to h3; false because the move is "L" shape (1 jump then 2) 
        // there is a piece in the "to" square but this method only tests the path between origin and destiny
        to = new ChessPosition(2, 7);
        assertTrue(knightMove.isPathClear(from, to, board));
    }
}

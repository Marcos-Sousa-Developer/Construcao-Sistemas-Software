package domain.moves;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.ChessBoard;
import domain.ChessPiece;
import domain.ChessPieceKind;
import domain.ChessPosition;
import domain.Color;

import static org.junit.jupiter.api.Assertions.*;

public class ChessPiecePawnMoveTest {
    private ChessPiecePawnMove pawnMove;
    private ChessBoard board;

    @BeforeEach
    public void setUp() {
        ChessPiece pawn = new ChessPiece(ChessPieceKind.PAWN, Color.WHITE);
        pawnMove = (ChessPiecePawnMove) pawn.getMoveStrategy();

        board = new ChessBoard();

        // Pieces for testing
        board.set(2, 2, new ChessPiece(ChessPieceKind.ROOK, Color.BLACK));
        board.set(2, 4, new ChessPiece(ChessPieceKind.ROOK, Color.BLACK));
        
        
        // The pawn
        board.set(1, 1, pawn);
    }

    @Test
    public void isMoveValid() {
        // pawn b2 to a3; false - can't move in diagonal because there isn't any piece to eat
        ChessPosition from = new ChessPosition(1, 1);
        assertThrows(IllegalMoveException.class, () -> pawnMove.isMoveValid(from, new ChessPosition(2, 0), board));

        // pawn b2 to c3; true because there is a piece to eat
        try {
            assertTrue(pawnMove.isMoveValid(from, new ChessPosition(2, 2), board));
        } catch (IllegalMoveException e) {
            // This should not happen
        }

        // pawn b2 to c2; false because the move is horizontal
        assertThrows(IllegalMoveException.class, () -> pawnMove.isMoveValid(from, new ChessPosition(1, 2), board));
    }

    @Test
    public void isPathClear() {
        // This method requires that the move is valid, so we don't need to test that here.

        // pawn e2 to e3; true because there is no piece in the "to" square but this method only tests the path between origin and destiny
        final ChessPosition from = new ChessPosition(1, 4);
        try {
            assertTrue(pawnMove.isPathClear(from, new ChessPosition(2, 4), board));
        } catch (IllegalMoveException e) {
            // This should not happen
        }

        // pawn e2 to e4; false because there is a piece in the way
        assertThrows(IllegalMoveException.class, () -> pawnMove.isPathClear(from, new ChessPosition(3, 4), board));

        

    }
}

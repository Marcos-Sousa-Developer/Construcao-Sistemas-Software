package domain.moves;

import domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChessPieceBishopMoveTest {
    private ChessPieceBishopMove bishopMove;
    private ChessBoard board;

    @BeforeEach
    public void setUp() {
        ChessPiece bishop = new ChessPiece(ChessPieceKind.BISHOP, Color.WHITE);
        bishopMove = (ChessPieceBishopMove) bishop.getMoveStrategy();

        board = new ChessBoard();
        board.clear();

        // Pieces for testing
        board.set(7, 0, new ChessPiece(ChessPieceKind.KING, Color.BLACK));
        board.set(7, 7, new ChessPiece(ChessPieceKind.KING, Color.WHITE));
        board.set(6, 1, new ChessPiece(ChessPieceKind.QUEEN, Color.BLACK));
        board.set(3, 2, new ChessPiece(ChessPieceKind.QUEEN, Color.BLACK));
        board.set(1, 3, new ChessPiece(ChessPieceKind.QUEEN, Color.BLACK));
        board.set(0, 5, new ChessPiece(ChessPieceKind.QUEEN, Color.BLACK));
        board.set(5, 6, new ChessPiece(ChessPieceKind.QUEEN, Color.BLACK));

        // The bishop
        board.set(3, 4, bishop);
    }

    @Test
    public void isMoveValid() {
        // Bishop e4 to g7; false because the move is diagonal (but imperfect)
        final ChessPosition from = new ChessPosition(3, 4);
        // assert exception
        assertThrows(IllegalMoveException.class, () -> bishopMove.isMoveValid(from, new ChessPosition(6, 6), board));

        // Bishop e4 to e8; false because the move is vertical
        assertThrows(IllegalMoveException.class, () -> bishopMove.isMoveValid(from, new ChessPosition(7, 4), board));

        // Bishop e4 to a4; false because the move is horizontal.
        assertThrows(IllegalMoveException.class, () -> bishopMove.isMoveValid(from, new ChessPosition(3, 0), board));

        // Bishop e4 to b7; true because the move is diagonal and perfect.
        try {
            assertTrue(bishopMove.isMoveValid(from, new ChessPosition(6, 1), board));
        } catch (IllegalMoveException e) {
            // This should not happen
        }

        // Bishop e4 to d3; true because the move is diagonal and perfect.
        try {
            assertTrue(bishopMove.isMoveValid(from, new ChessPosition(2, 3), board));
        } catch (IllegalMoveException e) {
            // This should not happen
        }
    }

    @Test
    public void isPathClear() {
        // This method requires that the move is valid, so we don't need to test that here.

        // Bishop e4 to g6; true because there is no piece in the way
        final ChessPosition from = new ChessPosition(3, 4);
        try {
            assertTrue(bishopMove.isPathClear(from, new ChessPosition(5, 6), board));
        } catch (IllegalMoveException e) {
            // This should not happen
        }

        // Bishop e4 to a8; false because the move is diagonal and perfect BUT there is a queen in the way
        assertThrows(IllegalMoveException.class, () -> bishopMove.isPathClear(from, new ChessPosition(7, 0), board));

        // Rook e4 to b6; true because the move is diagonal and perfect AND there is no piece in the way
        try {
            assertTrue(bishopMove.isPathClear(from, new ChessPosition(6, 1), board));
        } catch (IllegalMoveException e) {
            // This should not happen
        }
    }
}

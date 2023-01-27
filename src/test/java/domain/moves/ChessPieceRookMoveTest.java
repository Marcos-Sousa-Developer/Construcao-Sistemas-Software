package domain.moves;

import domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChessPieceRookMoveTest {
    private ChessPieceRookMove rookMove;
    private ChessBoard board;

    @BeforeEach
    public void setUp() {
        ChessPiece rook = new ChessPiece(ChessPieceKind.ROOK, Color.WHITE);
        rookMove = (ChessPieceRookMove) rook.getMoveStrategy();

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

        // The rook
        board.set(3, 4, rook);
    }

    @Test
    void isMoveValid() {
        // Rook e4 to g7; false because the move is diagonal
        final ChessPosition from = new ChessPosition(3, 4);

        assertThrows(IllegalMoveException.class, () -> rookMove.isMoveValid(from, new ChessPosition(6, 6), board));

        // Rook e4 to e8; true because the move is vertical
        ChessPosition to = new ChessPosition(7, 4);
        try {
            assertTrue(rookMove.isMoveValid(from, to, board));
        } catch (IllegalMoveException e) {
            // This should not happen
        }

        // Rook e4 to a4; true because the move is horizontal.
        //  There is a piece in the way but this method only checks if the move is valid.
        to = new ChessPosition(3, 0);
        try {
            assertTrue(rookMove.isMoveValid(from, to, board));
        } catch (IllegalMoveException e) {
            // This should not happen
        }
    }

    @Test
    void isPathClear() {
        // This method requires that the move is valid, so we don't need to test that here.

        // Rook e4 to e8; true because there is no piece in the way
        final ChessPosition from = new ChessPosition(3, 4);
        ChessPosition to = new ChessPosition(7, 4);
        try {
            assertTrue(rookMove.isPathClear(from, to, board));
        } catch (IllegalMoveException e) {
            // This should not happen
        }

        // Rook e4 to a4; false because the move is horizontal BUT there is a queen in the way
        assertThrows(IllegalMoveException.class, () -> rookMove.isPathClear(from, new ChessPosition(3, 0), board));

        // Rook e4 to c4; true because the move is horizontal AND there is no piece in the way
        to = new ChessPosition(3, 2);
        try {
            assertTrue(rookMove.isPathClear(from, to, board));
        } catch (IllegalMoveException e) {
            // This should not happen
        }
    }
}

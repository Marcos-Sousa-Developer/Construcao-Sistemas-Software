package domain.moves;

import domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChessPieceMoveStrategyTest {
    private ChessPieceRookMove rookMove;
    private ChessBoard board;
    private ChessPieceBishopMove bishopMove;


    @BeforeEach
    public void setUp() {
        ChessPiece rook = new ChessPiece(ChessPieceKind.ROOK, Color.WHITE);
        rookMove = (ChessPieceRookMove) rook.getMoveStrategy();

        ChessPiece bishop = new ChessPiece(ChessPieceKind.BISHOP, Color.WHITE);
        bishopMove = (ChessPieceBishopMove) bishop.getMoveStrategy();

        board = new ChessBoard();
        board.clear();

        // Pieces for testing
        board.set(7, 0, new ChessPiece(ChessPieceKind.KING, Color.BLACK));
        board.set(7, 7, new ChessPiece(ChessPieceKind.KING, Color.WHITE));
        board.set(7, 1, new ChessPiece(ChessPieceKind.QUEEN, Color.BLACK));
        board.set(3, 2, new ChessPiece(ChessPieceKind.QUEEN, Color.BLACK));
        board.set(1, 3, new ChessPiece(ChessPieceKind.QUEEN, Color.BLACK));
        board.set(0, 5, new ChessPiece(ChessPieceKind.QUEEN, Color.BLACK));
        board.set(5, 6, new ChessPiece(ChessPieceKind.QUEEN, Color.BLACK));

        // The rook
        board.set(3, 4, rook);

        // The bishop
        board.set(7, 4, bishop);
        System.out.println(board);
    }

    @Test
    public void isMoveValid() {
        // Rook e4 to e8; false because the move eats a piece of the same color
        final ChessPosition from = new ChessPosition(3, 4);
        assertThrows(IllegalMoveException.class, () -> rookMove.isMoveValid(from, new ChessPosition(7, 4), board));

        // Rook e4 to e4; false because the move is to itself
        assertThrows(IllegalMoveException.class, () -> rookMove.isMoveValid(from, new ChessPosition(3, 4), board));

        // Rook e4 to k9; false because the move is out of bounds
        assertThrows(IllegalMoveException.class, () -> rookMove.isMoveValid(from, new ChessPosition(10, 11), board));

        // Rook e4 to g4; true because the move is horizontal.
        try {
            assertTrue(rookMove.isMoveValid(from, new ChessPosition(3, 6), board));
        } catch (IllegalMoveException e) {
            // This should not happen
        }
    }

    @Test
    public void isMoveSafe() {
        // This method requires that the move is valid and that the path is clear, so we don't need to test that here.

        // Bishop e8 to b5; true because the move will allow queen b8 to check the king
        try {
            assertTrue(bishopMove.isMoveSafe(new ChessPosition(7, 4), new ChessPosition(4, 1), board));
        } catch (IllegalMoveException e) {
            // This should not happen
        }

        // Rook e4 to g4; true because the move doesn't allow any piece to check the king
        try {
            assertTrue(rookMove.isMoveSafe(new ChessPosition(3, 4), new ChessPosition(3, 6), board));
        } catch (IllegalMoveException e) {
            // This should not happen
        }
    }
    
}

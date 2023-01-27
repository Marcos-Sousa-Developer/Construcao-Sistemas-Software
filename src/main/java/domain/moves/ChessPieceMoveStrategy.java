package domain.moves;

import domain.ChessBoard;
import domain.ChessMove;
import domain.ChessPiece;
import domain.ChessPosition;

/**
 * A strategy for moving a chess piece.
 */
public abstract class ChessPieceMoveStrategy {
    /**
     * Checks if the move is valid for the piece.
     * @param from The position of the piece.
     * @param to The position to move to.
     * @param board The board.
     * @return True if the move is valid, false otherwise.
     */
    public boolean isMoveValid(ChessPosition from, ChessPosition to, ChessBoard board) throws IllegalMoveException {
        // Check if the move is within the board
        if (!from.isValid(board) || !to.isValid(board)) {
            throw new IllegalMoveException("Move is outside the board");
        }

		// Check if the move is not to the same position
		if (from.equals(to)) {
			throw new IllegalMoveException("Move is to the same position");
		}

        // Check if the move will eat a piece of the same color
        ChessPiece fromPiece = board.get(from.getRow(), from.getCol());
        ChessPiece toPiece = board.get(to.getRow(), to.getCol());

        if (toPiece != null && fromPiece.getColor() == toPiece.getColor())
            throw new IllegalMoveException("Move is not to an empty position nor it is a capture");

        return true;
    }

    /**
     * Whether the move does not put the own king in check
     * @param from The position of the piece to move
     * @param to The position to move to
     * @param board The board
     * @requires isMoveValid(from, to, board)
     * @return Whether the move does not put the own king in check
     */
    public final boolean isMoveSafe(ChessPosition from, ChessPosition to, ChessBoard board) throws IllegalMoveException {
        ChessPiece fromPiece = board.get(from.getRow(), from.getCol());
        ChessPiece toPiece = board.get(to.getRow(), to.getCol());

        ChessMove move = new ChessMove(fromPiece, from, to);
        move.setTakenPiece(toPiece);
        board.move(move);

        boolean isInCheck = board.isCheck(fromPiece.getColor());
        board.undoMove(move);

        if (isInCheck)
            throw new IllegalMoveException("Move is not safe as it puts the king in check");

        return true;
    }

    /**
     * Whether the path between the from and to position is clear
     * @param from The position of the piece to move
     * @param to The position to move to
     * @param board The board
     * @requires isMoveValid(from, to, board)
     * @return Whether the path between the from and to position is clear
     */
    public abstract boolean isPathClear(ChessPosition from, ChessPosition to, ChessBoard board) throws IllegalMoveException;
}

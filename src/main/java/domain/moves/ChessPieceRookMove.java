package domain.moves;

import domain.ChessBoard;
import domain.ChessPosition;

/**
 * A strategy for moving a chess rook.
 */
public class ChessPieceRookMove extends ChessPieceMoveStrategy {
    /**
     * Checks if the move is valid for the piece.
     * For the rook, the move is valid if the move is horizontal or vertical.
     * @param from The position of the piece.
     * @param to The position to move to.
     * @param board The board.
     * @return True if the move is valid, false otherwise.
     */
    @Override
    public boolean isMoveValid(ChessPosition from, ChessPosition to, ChessBoard board) throws IllegalMoveException {
        super.isMoveValid(from, to, board);

        if (from.getRow() != to.getRow() && from.getCol() != to.getCol())
            throw new IllegalMoveException("Move is not horizontal nor vertical");

        return true;
    }

    @Override
    public boolean isPathClear(ChessPosition from, ChessPosition to, ChessBoard board) throws IllegalMoveException {
        if (from.getRow() == to.getRow()) {
            // horizontal move
            int colStep = from.getCol() < to.getCol() ? 1 : -1;
            int col = from.getCol() + colStep;

            while (col != to.getCol()) {
                if (board.get(from.getRow(), col) != null) {
                    throw new IllegalMoveException("There is a piece in the way on " + new ChessPosition(from.getRow(), col));
                }

                col += colStep;
            }
        } else {
            // vertical move
            int rowStep = from.getRow() < to.getRow() ? 1 : -1;
            int row = from.getRow() + rowStep;

            while (row != to.getRow()) {
                if (board.get(row, from.getCol()) != null) {
                    throw new IllegalMoveException("There is a piece in the way on " + new ChessPosition(row, from.getCol()));
                }

                row += rowStep;
            }
        }

        return true;
    }
}

package domain.moves;

import domain.ChessBoard;
import domain.ChessPosition;

public class ChessPieceBishopMove extends ChessPieceMoveStrategy {
    @Override
    public boolean isMoveValid(ChessPosition from, ChessPosition to, ChessBoard board) throws IllegalMoveException {
        super.isMoveValid(from, to, board);


        if (Math.abs(from.getRow() - to.getRow()) != Math.abs(from.getCol() - to.getCol()))
            throw new IllegalMoveException("Move is not diagonal");

        return true;
    }

    @Override
    public boolean isPathClear(ChessPosition from, ChessPosition to, ChessBoard board) throws IllegalMoveException {
        int rowStep = from.getRow() < to.getRow() ? 1 : -1;
        int colStep = from.getCol() < to.getCol() ? 1 : -1;

        int row = from.getRow() + rowStep;
        int col = from.getCol() + colStep;

        while (row != to.getRow() && col != to.getCol()) {
            if (board.get(row, col) != null) {
                throw new IllegalMoveException("There is a piece in the way on " + new ChessPosition(row, col));
            }

            row += rowStep;
            col += colStep;
        }

        return true;
    }
}

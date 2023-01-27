package domain.moves;

import domain.ChessBoard;
import domain.ChessPosition;

public class ChessPieceKnightMove extends ChessPieceMoveStrategy {
    @Override
    public boolean isMoveValid(ChessPosition from, ChessPosition to, ChessBoard board) throws IllegalMoveException {
        super.isMoveValid(from, to, board);

            // jumps 2 then 1
        if (!(Math.abs(from.getRow() - to.getRow()) == 2 && Math.abs(from.getCol() - to.getCol()) == 1
            // jumps 1 then 2
            || Math.abs(from.getRow() - to.getRow()) == 1 && Math.abs(from.getCol() - to.getCol()) == 2))
            throw new IllegalMoveException("Move is not L-shaped");

        return true;
    }

    @Override
    public boolean isPathClear(ChessPosition from, ChessPosition to, ChessBoard board) {
        return true;
    }
}

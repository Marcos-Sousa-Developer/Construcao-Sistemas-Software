package domain.moves;

import domain.ChessBoard;
import domain.ChessPosition;
import domain.Color;

public class ChessPiecePawnMove extends ChessPieceMoveStrategy {
    @Override
    public boolean isMoveValid(ChessPosition from, ChessPosition to, ChessBoard board) throws IllegalMoveException {
        super.isMoveValid(from, to, board);

    	int moveSign = board.get(from.getRow(), from.getCol()).getColor() == Color.WHITE ? +1 : -1;

        // normal case: advances one
        boolean oneStep = from.getCol() == to.getCol() && to.getRow()-from.getRow() == 1*moveSign;
        // initial place: can advance two
        boolean initialTwoStep = (from.getRow() == 1 || from.getRow() == 6) && from.getCol() == to.getCol() && to.getRow()-from.getRow() == 2*moveSign;
        // eat move: diagonal
        boolean capture = Math.abs(from.getCol() - to.getCol()) == 1 && to.getRow()-from.getRow() == 1*moveSign && board.get(to.getRow(), to.getCol()) != null;

        if (!(oneStep || initialTwoStep || capture))
            throw new IllegalMoveException("Move is not one step forward, two steps forward (if first move), nor one step diagonal (a capture)");

        if (!capture && board.get(to.getRow(), to.getCol()) != null)
            throw new IllegalMoveException("Cannot move forward if there is a piece in the way");

        return true;
    }

    @Override
    public boolean isPathClear(ChessPosition from, ChessPosition to, ChessBoard board) throws IllegalMoveException {
    	// if pawn tries to move 2, check if something is in the middle
    	if(Math.abs(from.getRow()-to.getRow()) == 2) {
    		int row = Math.abs((from.getRow()+to.getRow())/2);

            if (board.get(row, from.getCol()) != null) {
                throw new IllegalMoveException("There is a piece in the way on " + new ChessPosition(row, from.getCol()));
            }
    	}

    	return true;
    }
   
}

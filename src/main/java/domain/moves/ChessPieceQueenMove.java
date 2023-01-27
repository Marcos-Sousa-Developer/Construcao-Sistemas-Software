package domain.moves;

import domain.ChessBoard;
import domain.ChessPosition;

public class ChessPieceQueenMove extends ChessPieceMoveStrategy {

	@Override
	public boolean isMoveValid(ChessPosition from, ChessPosition to, ChessBoard board) throws IllegalMoveException {
		super.isMoveValid(from, to, board);

        boolean isHorizontal = from.getRow() == to.getRow() && from.getCol() != to.getCol();
        boolean isVertical = from.getRow() != to.getRow() && from.getCol() == to.getCol();
        boolean isDiagonal = Math.abs(from.getRow() - to.getRow()) == Math.abs(from.getCol() - to.getCol());

        if (!(isHorizontal || isVertical || isDiagonal))
            throw new IllegalMoveException("Move is not horizontal, vertical, nor diagonal");

        return true;
	}

	@Override
	public boolean isPathClear(ChessPosition from, ChessPosition to, ChessBoard board) throws IllegalMoveException {
        //horizontal move
    	if(from.getRow() == to.getRow()) {
            int colStep = from.getCol() < to.getCol() ? 1 : -1;
            int col = from.getCol() + colStep; 
            
            while (col != to.getCol()) {
                if (board.get(from.getRow(), col) != null) {
                    throw new IllegalMoveException("There is a piece in the way on " + new ChessPosition(from.getRow(), col));
                }

                col += colStep;
            }
    		
    	} else if(from.getCol() == to.getCol()) {
            //vertical move
    		int rowStep = from.getRow() < to.getRow() ? 1 : -1;
            int row = from.getRow() + rowStep;

            while (row != to.getRow()) {
                if (board.get(row, from.getCol()) != null) {
                    throw new IllegalMoveException("There is a piece in the way on " + new ChessPosition(row, from.getCol()));
                }

                row += rowStep;
            }
    		
    	} else { 
            // diagonal move
    		int colStep = from.getCol() < to.getCol() ? 1 : -1; 
       		int rowStep = from.getRow() < to.getRow() ? 1 : -1;
            int row = from.getRow() + rowStep;
            int col = from.getCol() + colStep; 
            
            while (row != to.getRow() && col != to.getCol()) {
                if (board.get(row, col) != null) {
                    throw new IllegalMoveException("There is a piece in the way on " + new ChessPosition(row, col));
                }

                row += rowStep;
                col += colStep;
            }
    	}
    	
        return true;
    }
}

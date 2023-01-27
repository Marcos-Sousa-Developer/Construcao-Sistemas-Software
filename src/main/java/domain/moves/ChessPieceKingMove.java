package domain.moves;

import domain.ChessBoard;
import domain.ChessMove;
import domain.ChessPiece;
import domain.ChessPieceKind;
import domain.ChessPosition;

public class ChessPieceKingMove extends ChessPieceMoveStrategy {
	@Override
	public boolean isMoveValid(ChessPosition from, ChessPosition to, ChessBoard board) throws IllegalMoveException {
		super.isMoveValid(from, to, board);

		// isOneStepMove || isCastling
		//  but we need to handle isCastling exceptions
		try {
			if (from.getRow() == to.getRow() && Math.abs(from.getCol() - to.getCol()) == 2)
				return isCastling(from, to, board);
			else
				return isOneStepMove(from, to);
		} catch (IllegalMoveException e) {
			if (isOneStepMove(from, to))
				return true;
			else
				throw e;
		}
	}

	@Override
	public boolean isPathClear(ChessPosition from, ChessPosition to, ChessBoard board) {
		return true;
	}

	private boolean isOneStepMove(ChessPosition from, ChessPosition to) {
		return Math.abs(from.getRow() - to.getRow()) <= 1
				&& Math.abs(from.getCol() - to.getCol()) <= 1;
	}

	private boolean isCastling(ChessPosition from, ChessPosition to, ChessBoard board) throws IllegalMoveException {
		ChessPiece king = board.get(from.getRow(), from.getCol());
		// A king can only castle if it has not moved yet
		if (king == null || king.getChessPieceKind() != ChessPieceKind.KING || king.getMoveCount() > 0) {
			throw new IllegalMoveException("King can only castle if it has not moved yet");
		}

		// Get the rook that will be castling
		int rookCol = from.getCol() < to.getCol() ? board.getSize() - 1 : 0;
		ChessPosition rookPosition = new ChessPosition(from.getRow(), rookCol);
		ChessPiece rook = board.get(rookPosition.getRow(), rookPosition.getCol());

		if (rook == null || rook.getChessPieceKind() != ChessPieceKind.ROOK || rook.getMoveCount() > 0) {
			throw new IllegalMoveException("Rook can only castle if it has not moved yet");
		}

		// Check if the path is clear
		//  aka if the rook can move next to the king's position
		int colStep = from.getCol() < to.getCol() ? 1 : -1;
		ChessPosition positionNextToKing = new ChessPosition(from.getRow(), from.getCol() + colStep);
		if (!rook.getMoveStrategy().isPathClear(rookPosition, positionNextToKing, board)) {
			throw new IllegalMoveException("The rook can not move next to the king's position: a piece is on the way");
		}

		// Check if the king will be in check
		//  There is no advantage in mixing the above instructions with these because of Time Complexity being
		//  negligible, the fact that the code is more readable this way, and because the *check* checking is not
		//  made in every square as the rook's path is checked
		int startCol = Math.min(from.getCol(), to.getCol());
		int endCol = Math.max(from.getCol(), to.getCol());
		for (int col = startCol; col <= endCol; col++) {
			ChessMove move = new ChessMove(king, from, new ChessPosition(from.getRow(), col));
			board.move(move);

			if (board.isCheck(king.getColor())) {
				board.undoMove(move);
				throw new IllegalMoveException("The king will be in check in the process of castling");
			}
		}

		return true;
	}
}

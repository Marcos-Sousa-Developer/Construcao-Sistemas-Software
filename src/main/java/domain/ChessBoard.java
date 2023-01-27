package domain;

import java.util.ArrayList;
import java.util.List;

import domain.moves.ChessPieceMoveStrategy;
import domain.moves.IllegalMoveException;

public class ChessBoard {
	private final int size;
	
	private final ChessPiece[][] board;
	
	private void initBoard() { 
		int l0 = 0;
		int l1 = 1;
		int l6 = this.board[0].length -2;
		int l7 = this.board[0].length -1;
			
		for(int c=0; c<this.board[0].length; c++) {
			this.board[l1][c] = new ChessPiece(ChessPieceKind.PAWN, Color.WHITE);
			this.board[l6][c] = new ChessPiece(ChessPieceKind.PAWN, Color.BLACK); 
			
			if(c == 0 || c == 7) {
				this.board[l0][c] = new ChessPiece(ChessPieceKind.ROOK, Color.WHITE);
				this.board[l7][c] = new ChessPiece(ChessPieceKind.ROOK, Color.BLACK);
			} else if(c == 1 || c == 6) {	
				this.board[l0][c] = new ChessPiece(ChessPieceKind.KNIGHT, Color.WHITE);
				this.board[l7][c] = new ChessPiece(ChessPieceKind.KNIGHT, Color.BLACK);
			} else if(c == 2 || c == 5) {	
				this.board[l0][c] = new ChessPiece(ChessPieceKind.BISHOP, Color.WHITE);
				this.board[l7][c] = new ChessPiece(ChessPieceKind.BISHOP, Color.BLACK);
			} else if(c == 3) {	
				this.board[l0][c] = new ChessPiece(ChessPieceKind.QUEEN, Color.WHITE);
				this.board[l7][c] = new ChessPiece(ChessPieceKind.QUEEN, Color.BLACK);
			} else {	
				this.board[l0][c] = new ChessPiece(ChessPieceKind.KING, Color.WHITE);
				this.board[l7][c] = new ChessPiece(ChessPieceKind.KING, Color.BLACK);
			}
		}
	}

	public ChessBoard() {
		this.size = 8;
		this.board = new ChessPiece[this.size][this.size];

		initBoard();
	}
	
	//Updates based on a list of moves
	public ChessBoard(List<ChessMove> moves) {
		this.size = 8;
		this.board = new ChessPiece[this.size][this.size];

		initBoard();

		for(ChessMove move : moves) {
			this.move(move);
		}
	}

	/**
	 * Clears the board.
	 */
	public void clear() {
		for(int row = 0; row < this.size; row++) {
			for(int col = 0; col < this.size; col++) {
				this.board[row][col] = null;
			}
		}	
	}
	
	
	public ChessPiece get(int row, int col) {
		return this.board[row][col];
	}

	/**
	 * Sets the chess piece at the given position.
	 * @param row the row
	 * @param col the column
	 * @param piece the chess piece
	 * @return the chess piece that was previously at the given position
	 */
	public ChessPiece set(int row, int col, ChessPiece piece) {
		ChessPiece oldPiece = this.board[row][col];
		this.board[row][col] = piece;
		return oldPiece;
	}

	public int getSize() {
		return this.size;
	}

	/**
	 * Moves a chess piece from one position to another.
	 * @param move the move
	 * @throws IllegalMoveException if the move is not valid
	 * @requires the game not to be over
	 */
	public void update(ChessMove move) throws IllegalMoveException {
		ChessPieceMoveStrategy moveStrategy = move.getPiece().getMoveStrategy();

		ChessPosition from = move.getFrom();
		ChessPosition to = move.getTo();

		if(moveStrategy.isMoveValid(from, to, this)
				&& moveStrategy.isPathClear(from, to, this)
				&& moveStrategy.isMoveSafe(from, to, this)) {
			// Execute the move
			this.move(move);
		}
		else {
			throw new IllegalMoveException("Move is not valid");
		}
	}

	/**
	 * Executes the move
	 * @param move the move to execute
	 * @requires the move is valid
	 */
	public void move(ChessMove move) {
		ChessPosition from = move.getFrom();
		ChessPosition to = move.getTo();

		// Move the piece. This can't be done in the end
		//  because some special moves (like promotion)
		//  will override the destination position.
		set(from.getRow(), from.getCol(), null);
		set(to.getRow(), to.getCol(), move.getPiece());

		move.getPiece().move();

		if (move.getTakenPiece() != null) {
			move.getTakenPiece().setAlive(false);
		}

		// Pawn promotion
		if (move.isPromotion()) {
			// Assume that the promotion is always to a queen
			ChessPiece queen = new ChessPiece(ChessPieceKind.QUEEN, move.getPiece().getColor());
			move.promote(queen);
			set(to.getRow(), to.getCol(), queen);
		}

		// Castling
		if (move.isCastle()) {
			Pair<ChessPosition, ChessPosition> rookPositions = move.rookCastlePositions();
			ChessPosition rookFrom = rookPositions.getFirst();
			ChessPosition rookTo = rookPositions.getSecond();

			ChessPiece rook = this.get(rookFrom.getRow(), rookFrom.getCol());
			this.set(rookFrom.getRow(), rookFrom.getCol(), null);
			this.set(rookTo.getRow(), rookTo.getCol(), rook);
			rook.move();
		}
	}

	/**
	 * Undos the move
	 * @param move the move to undo
	 */
	public void undoMove(ChessMove move) {
		ChessPosition from = move.getFrom();
		ChessPosition to = move.getTo();

		set(to.getRow(), to.getCol(), null);
		set(from.getRow(), from.getCol(), move.getPiece());

		move.getPiece().undoMove();

		if(move.getTakenPiece() != null) {
			move.getTakenPiece().setAlive(true);
			set(to.getRow(), to.getCol(), move.getTakenPiece());
		}

		// Pawn promotion 
		if (move.isPromotion()) {
			// Pawn is already set on the board by previous lines
			move.promote(null);
		}

		// Castling
		if (move.isCastle()) {
			Pair<ChessPosition, ChessPosition> rookPositions = move.rookCastlePositions();
			ChessPosition rookFrom = rookPositions.getFirst();
			ChessPosition rookTo = rookPositions.getSecond();

			ChessPiece rook = this.get(rookTo.getRow(), rookTo.getCol());
			this.set(rookTo.getRow(), rookTo.getCol(), null);
			this.set(rookFrom.getRow(), rookFrom.getCol(), rook);
			rook.undoMove();
		}
	}

	/**
	 * Returns the king position of the given color.
	 * @param color the color
	 * @return the king position
	 */
	public ChessPosition findKing(Color color) {
		List<Pair<ChessPiece, ChessPosition>> pieces = alivePiecesPositionList(color);
		ChessPosition kingPosition = null;

		for (Pair<ChessPiece, ChessPosition> piecePosition : pieces) {
			if (piecePosition.getFirst().getChessPieceKind() == ChessPieceKind.KING) {
				kingPosition = piecePosition.getSecond();

				// It is safe to break because there is only one king per color
				break;
			}
		}
		
		return kingPosition;
	}

	/**
	 * Calculate if the color can attack a given position.
	 * @param position the position to check
	 * @param color the color to check
	 * @return true if the color can attack the position, false otherwise
	 */
	public boolean isAttacked(ChessPosition position, Color color) {
		boolean isAttacked = false;

		for(int r=0; r<this.size; r++) {
			for(int c=0; c<this.size; c++) {
				if(this.board[r][c] != null
						&& this.board[r][c].getColor() != color) {
					ChessPieceMoveStrategy moveStrategy = this.board[r][c].getMoveStrategy();
					ChessPosition from = new ChessPosition(r, c);

					try {
						if(moveStrategy.isMoveValid(from, position, this)
								&& moveStrategy.isPathClear(from, position, this)
								&& moveStrategy.isMoveSafe(from, position, this)) {
							isAttacked = true;
							break;
						}
					} catch (IllegalMoveException e) {
						// Do nothing
					}
				}
			}

			if (isAttacked) {
				break;
			}
		}

		return isAttacked;
	}

	/**
	 * Calculate whether the color's king is in check.
	 * @param color the color to check
	 * @return true if the king is in check, false otherwise
	 */
	public boolean isCheck(Color color) {
		ChessPosition kingPosition = this.findKing(color);

		return this.isAttacked(kingPosition, color);
	}

	/**
	 * Creates list of all alive pieces of a given color.
	 * @param color the color to check
	 * @return list of alive pieces of the given color
	 */
	public List<Pair<ChessPiece, ChessPosition>> alivePiecesPositionList(Color color) {
		List<Pair<ChessPiece, ChessPosition>> alivePieces = new ArrayList<>();

		for(int r=0; r<this.size; r++) {
			for(int c=0; c<this.size; c++) {
				if(this.board[r][c] != null && this.board[r][c].getColor() == color) {
					alivePieces.add(new Pair<>(this.board[r][c], new ChessPosition(r, c)));
				}
			}
		}

		return alivePieces;
	}


	/**
	 * Calculate whether the color player has moves remaining.
	 * @param color the color to check
	 * @return true if it has moves remaining, false otherwise
	 */
	public boolean hasMoves(Color color) {
		List<Pair<ChessPiece, ChessPosition>> pieces = alivePiecesPositionList(color);

		for(Pair<ChessPiece, ChessPosition> piece : pieces) {
			ChessPieceMoveStrategy moveStrategy = piece.getFirst().getMoveStrategy();
			ChessPosition from = piece.getSecond();
			for(int r=0; r<this.size; r++) {
				for(int c=0; c<this.size; c++) {
					ChessPosition to = new ChessPosition(r, c);
					try {
						if(moveStrategy.isMoveValid(from, to, this)
								&& moveStrategy.isPathClear(from, to, this)
								&& moveStrategy.isMoveSafe(from, to, this)) {
							return true;
						}
					} catch (IllegalMoveException e) {
						// Do nothing
					}
				}
			}
		}

		return false;
	}

	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("  ");
        for (int i = 0; i < this.size; i++) {
            sb.append("  ").append((char) ('a' + i)).append("  ");
        }
        sb.append("\n  +");
        for (int i = 0; i < this.size; i++) {
            sb.append("----+");
        }
        sb.append("\n");
        for (int i = 7; i >= 0; i--) {
            sb.append("").append(i+1).append(" ");
            for (int j = 0; j < this.size; j++) {
                if (get(i, j) != null) {
                    sb.append("| ").append(get(i, j)).append(" ");
                } else {
                    sb.append("|    ");
                }
            }
            sb.append("|\n  +");
            for (int k = 0; k < this.size; k++) {
                sb.append("----+");
            }
            sb.append("\n");
        }
        return sb.toString();
	}

	@Override
	public ChessBoard clone() {
		ChessBoard clone = new ChessBoard();

		for(int r=0; r<this.size; r++) {
			clone.board[r] = this.board[r].clone();
		}

		return clone;
	}
}

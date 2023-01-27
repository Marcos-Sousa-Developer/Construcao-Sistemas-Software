package domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class ChessMove {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

	@ManyToOne
	@Embedded
    private ChessPiece piece;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="chessPieceKind", column=@Column(name="takenPieceChessPieceKind")),
		@AttributeOverride(name="color", column=@Column(name="takenPieceColor")),
		@AttributeOverride(name="alive", column=@Column(name="takenPieceAlive")),
		@AttributeOverride(name="moveCount", column=@Column(name="takenPieceMoveCount"))
	})
    private ChessPiece takenPiece;
	
	@OneToOne
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="chessPieceKind", column=@Column(name="promotedPieceChessPieceKind")),
		@AttributeOverride(name="color", column=@Column(name="promotedPieceColor")),
		@AttributeOverride(name="alive", column=@Column(name="promotedPieceAlive")),
		@AttributeOverride(name="moveCount", column=@Column(name="promotedPieceMoveCount"))
	})
    private ChessPiece promotedPiece;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="row", column=@Column(name="fromRow")),
		@AttributeOverride(name="col", column=@Column(name="fromCol")),
	})
    private ChessPosition from;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="row", column=@Column(name="toRow")),
		@AttributeOverride(name="col", column=@Column(name="toCol")),
	})
    private ChessPosition to;

	
	@Column(nullable = false)
    private int duration;
	
	@Enumerated
    private CheckType checkType;

	
	public ChessMove() {}

    public ChessMove(ChessPiece piece, ChessPosition from, ChessPosition to) {
        this.piece = piece;
        this.from = from;
        this.to = to;
    }

    public int getId() {
        return id;
    }

    public ChessPiece getPiece() {
        return piece;
    }

    public ChessPosition getFrom() {
        return from;
    }

    public ChessPosition getTo() {
        return to;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public ChessPiece getTakenPiece() {
        return takenPiece;
    }

    public void setTakenPiece(ChessPiece takenPiece) {
        this.takenPiece = takenPiece;
    }

    public void setCheckType(CheckType checkType) {
        this.checkType = checkType;
    }

    //region Special Moves
    /**
     * Whether this move is a promotion
     * @return true if this move is a promotion
     */
    public boolean isPromotion() {
        // If the piece is a pawn, or it is on the last rank
        return piece.getChessPieceKind() == ChessPieceKind.PAWN &&
                (to.getRow() == 7 || to.getRow() == 0);
    }

    /**
     * Promote a pawn to a piece.
     * @requires isPromotion()
     */
    public void promote(ChessPiece piece) {
        this.promotedPiece = piece;
    }

    public ChessPiece getPromotedPiece() {
        return promotedPiece;
    }

    /**
     * Whether this move is a castling
     * @return true if this move is a castling
     */
    public boolean isCastle() {
        // If the piece is a king, and the move is two squares
        return piece.getChessPieceKind() == ChessPieceKind.KING &&
                Math.abs(to.getCol() - from.getCol()) == 2;
    }

    /**
     * Calculate the before and after positions of the rook for a castling move.
     * @requires isCastle()
     * @return the before and after positions of the rook for a castling move.
     */
    public Pair<ChessPosition, ChessPosition> rookCastlePositions() {
        ChessPosition from = null;
        ChessPosition to = null;

        // We are viewing the board from the perspective of the white player
        //  so if the king is black, we have to flip it
        int multiplier = piece.getColor() == Color.WHITE ? 1 : -1;

        if (this.getCastleType() == CastleType.KING_SIDE) {
            from = new ChessPosition(this.from.getRow(), this.from.getCol() + 3 * multiplier);
            to = new ChessPosition(this.from.getRow(), this.from.getCol() + multiplier);
        } else {
            from = new ChessPosition(this.from.getRow(), this.from.getCol() - 4 * multiplier);
            to = new ChessPosition(this.from.getRow(), this.from.getCol() - multiplier);
        }

        return new Pair<>(from, to);
    }

    /**
     * Calculate the type of castling move this is.
     * @requires isCastle()
     * @return the type of castling move this is.
     */
    public CastleType getCastleType() {
        // We are viewing the board from the perspective of the white player
        //  so if the king is black, we have to flip it
        int multiplier = piece.getColor() == Color.WHITE ? 1 : -1;

        // King FROM position is at the left of the TO position?
        if (from.getCol() < to.getCol() * multiplier) {
            return CastleType.KING_SIDE;
        } else {
            return CastleType.QUEEN_SIDE;
        }
    }
    //endregion

    /**
     * The string representation of this move
     * <a href="https://en.wikipedia.org/wiki/Algebraic_notation_(chess)#Notation_for_moves">Notation for Moves</a>
     * @return the long algebraic notation of this move
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // If the move is a castle, return the castle notation
        CastleType castleType = getCastleType();
        if (isCastle() && castleType != null) {
            return castleType == CastleType.KING_SIDE ? "O-O" : "O-O-O";
        }

        // Only non-pawns have a piece name
        if (piece.getChessPieceKind() != ChessPieceKind.PAWN) {
            sb.append(piece.chessPieceName());

            // Long algebraic notation
            sb.append(from);
        } else if (takenPiece != null) {
            // When a pawn takes a piece, the column of the pawn is added
            sb.append(from.colToString());
        }

        // If the move is a capture, add an 'x'
        if (takenPiece != null) {
            sb.append('x');
        }

        // The coordinates of the destination square
        sb.append(to);

        // If the move is a promotion, add the promoted piece
        if (promotedPiece != null) {
            sb.append("=").append(promotedPiece.chessPieceName());
        }

        // If the move is a check, add the check type
        if (checkType != null) {
            sb.append(checkType == CheckType.CHECKMATE ? '#' : '+');
        }

        return sb.toString();
    }
}

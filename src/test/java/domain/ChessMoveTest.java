package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChessMoveTest {
    @Test
    void testToString() {
        ChessPiece piece = new ChessPiece(ChessPieceKind.PAWN, Color.WHITE);
        ChessPiece takenPiece = new ChessPiece(ChessPieceKind.QUEEN, Color.BLACK);

        // Pawn e2 to e4
        ChessPosition from = new ChessPosition(1, 4);
        assertEquals("e2", from.toString());
        ChessPosition to = new ChessPosition(3, 4);
        assertEquals("e4", to.toString());
        ChessMove move = new ChessMove(piece, from, to);
        assertEquals("e4", move.toString());

        // Pawn d3 eating c4
        from = new ChessPosition(2, 3);
        assertEquals("d3", from.toString());
        to = new ChessPosition(3, 2);
        assertEquals("c4", to.toString());
        move = new ChessMove(piece, from, to);
        move.setTakenPiece(takenPiece);
        assertEquals("dxc4", move.toString());

        // Pawn e7 eating f8, promotion to queen
        from = new ChessPosition(6, 4);
        assertEquals("e7", from.toString());
        to = new ChessPosition(7, 5);
        assertEquals("f8", to.toString());
        move = new ChessMove(piece, from, to);
        move.setTakenPiece(takenPiece);
        move.promote(new ChessPiece(ChessPieceKind.QUEEN, piece.getColor()));
        assertEquals("exf8=Q", move.toString());

        // Knight e4 to d6 check
        from = new ChessPosition(3, 4);
        assertEquals("e4", from.toString());
        to = new ChessPosition(5, 3);
        assertEquals("d6", to.toString());
        move = new ChessMove(new ChessPiece(ChessPieceKind.KNIGHT, piece.getColor()), from, to);
        move.setCheckType(CheckType.CHECK);
        assertEquals("ke4d6+", move.toString());

        // Pawn c5 to c6 checkmate
        from = new ChessPosition(4, 2);
        assertEquals("c5", from.toString());
        to = new ChessPosition(5, 2);
        assertEquals("c6", to.toString());
        move = new ChessMove(piece, from, to);
        move.setCheckType(CheckType.CHECKMATE);
        assertEquals("c6#", move.toString());

        // King-side castling (e1 to g1)
        from = new ChessPosition(0, 4);
        assertEquals("e1", from.toString());
        to = new ChessPosition(0, 6);
        assertEquals("g1", to.toString());
        move = new ChessMove(new ChessPiece(ChessPieceKind.KING, piece.getColor()), from, to);
        assertEquals("O-O", move.toString());

        // Queen-side castling (e1 to c1)
        from = new ChessPosition(0, 4);
        assertEquals("e1", from.toString());
        to = new ChessPosition(0, 2);
        assertEquals("c1", to.toString());
        move = new ChessMove(new ChessPiece(ChessPieceKind.KING, piece.getColor()), from, to);
        assertEquals("O-O-O", move.toString());
    }
}

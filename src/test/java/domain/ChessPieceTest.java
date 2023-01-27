package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

public class ChessPieceTest {
    private static ChessPiece pawn;
    private static ChessPiece king;
    private static ChessPiece queen;
    private static ChessPiece bishop;
    private static ChessPiece knight;
    private static ChessPiece rook;

	@BeforeAll
	static void beforeAll() {

        pawn = new ChessPiece(ChessPieceKind.PAWN, Color.WHITE);
        king = new ChessPiece(ChessPieceKind.KING, Color.BLACK);
        queen = new ChessPiece(ChessPieceKind.QUEEN, Color.WHITE);
        bishop = new ChessPiece(ChessPieceKind.BISHOP, Color.BLACK);
        knight = new ChessPiece(ChessPieceKind.KNIGHT, Color.WHITE);
        rook = new ChessPiece(ChessPieceKind.ROOK, Color.BLACK);
        
	}


    @Test 
	@Order(1) 
	public void chessPieceNameTest() {   
        assertEquals("P", pawn.chessPieceName()); 
        assertEquals("K", king.chessPieceName()); 
        assertEquals("Q", queen.chessPieceName()); 
        assertEquals("B", bishop.chessPieceName()); 
        assertEquals("k", knight.chessPieceName()); 
        assertEquals("R", rook.chessPieceName()); 
	}


    @Test 
	@Order(2) 
	public void chessPieceColorTest() {   
        assertEquals("w", pawn.chessPieceColor()); 
        assertEquals("b", king.chessPieceColor()); 
        assertEquals("w", queen.chessPieceColor()); 
        assertEquals("b", bishop.chessPieceColor()); 
        assertEquals("w", knight.chessPieceColor()); 
        assertEquals("b", rook.chessPieceColor()); 
		
	}


    @Test 
	@Order(3) 
	public void toStringTest() {   
        assertEquals("Pw", pawn.toString()); 
        assertEquals("Kb", king.toString()); 
        assertEquals("Qw", queen.toString()); 
        assertEquals("Bb", bishop.toString()); 
        assertEquals("kw", knight.toString()); 
        assertEquals("Rb", rook.toString()); 
		
	}

}

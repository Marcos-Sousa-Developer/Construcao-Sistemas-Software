package domain;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import domain.moves.IllegalMoveException;

public class ChessGameTest {
    private static ChessGame game;


	@BeforeEach
    public void setUp() {
        ChessPlayer player1 = new ChessPlayer("Joao", "joao@alunos.fc.ul.pt");
        ChessPlayer player2 = new ChessPlayer("Maria", "maria@alunos.fc.ul.pt");
		game = new ChessGame(player1, player2, new Date());
        game.loadBoard();
	}

    @Test 
	@Order(1) 
	public void movesTest() throws IllegalMoveException {  
        // tests addMove, getLastMove and undoMove
        ChessPiece piece = game.getBoard().get(1, 0);
        ChessMove move = new ChessMove(piece, new ChessPosition(1, 0), new ChessPosition(3, 0));
        game.addMove(move);
		assertEquals(move, game.getLastMove()); 
        game.undoMove();
		assertNotEquals(move, game.getLastMove());
	}

    @Test
    @Order(2)
    public void checkmate() throws IllegalMoveException {
        /// Fool's Mate
        // white f2 f3
        ChessPiece piece = game.getBoard().get(1, 5);
        ChessMove move = new ChessMove(piece, new ChessPosition(1, 5), new ChessPosition(2, 5));
        game.addMove(move);
        // black e7 e5
        piece = game.getBoard().get(6, 4);
        move = new ChessMove(piece, new ChessPosition(6, 4), new ChessPosition(4, 4));
        game.addMove(move);
        // white g2 g4
        piece = game.getBoard().get(1, 6);
        move = new ChessMove(piece, new ChessPosition(1, 6), new ChessPosition(3, 6));
        game.addMove(move);
        // black d8 h4
        piece = game.getBoard().get(7, 3);
        move = new ChessMove(piece, new ChessPosition(7, 3), new ChessPosition(3, 7));
        game.addMove(move);

        /// Assertions
        // Is check
        assertTrue(game.getBoard().isCheck(Color.WHITE));
        // does not have moves (i.e. is checkmate)
        assertFalse(game.getBoard().hasMoves(Color.WHITE));
        // Checkmate ends the game
        assertTrue(game.isOver());

        // Make sure we can't move after the game is over
        assertThrows(IllegalMoveException.class, () -> {
            // white a2 a3
            ChessPiece p = game.getBoard().get(1, 0);
            ChessMove m = new ChessMove(p, new ChessPosition(1, 0), new ChessPosition(2, 0));
            game.addMove(m);
        });
    }

    @Test
    @Order(3)
    public void stalemate() throws IllegalMoveException {
        /// Sam Loyd composition
        // white e2 e3
        ChessPiece piece = game.getBoard().get(1, 4);
        ChessMove move = new ChessMove(piece, new ChessPosition(1, 4), new ChessPosition(2, 4));
        game.addMove(move);
        // black a7 a5
        piece = game.getBoard().get(6, 0);
        move = new ChessMove(piece, new ChessPosition(6, 0), new ChessPosition(4, 0));
        game.addMove(move);
        // white d1 h5
        piece = game.getBoard().get(0, 3);
        move = new ChessMove(piece, new ChessPosition(0, 3), new ChessPosition(4, 7));
        game.addMove(move);
        // black a8 a6
        piece = game.getBoard().get(7, 0);
        move = new ChessMove(piece, new ChessPosition(7, 0), new ChessPosition(5, 0));
        game.addMove(move);
        // white h5 a5
        piece = game.getBoard().get(4, 7);
        move = new ChessMove(piece, new ChessPosition(4, 7), new ChessPosition(4, 0));
        game.addMove(move);
        // black h7 h5
        piece = game.getBoard().get(6, 7);
        move = new ChessMove(piece, new ChessPosition(6, 7), new ChessPosition(4, 7));
        game.addMove(move);
        // white a5 c7
        piece = game.getBoard().get(4, 0);
        move = new ChessMove(piece, new ChessPosition(4, 0), new ChessPosition(6, 2));
        game.addMove(move);
        // black a6 h6
        piece = game.getBoard().get(5, 0);
        move = new ChessMove(piece, new ChessPosition(5, 0), new ChessPosition(5, 7));
        game.addMove(move);
        // white h2 h4
        piece = game.getBoard().get(1, 7);
        move = new ChessMove(piece, new ChessPosition(1, 7), new ChessPosition(3, 7));
        game.addMove(move);
        // black f7 f6
        piece = game.getBoard().get(6, 5);
        move = new ChessMove(piece, new ChessPosition(6, 5), new ChessPosition(5, 5));
        game.addMove(move);
        // white c7 d7
        piece = game.getBoard().get(6, 2);
        move = new ChessMove(piece, new ChessPosition(6, 2), new ChessPosition(6, 3));
        game.addMove(move);
        // black e8 f7
        piece = game.getBoard().get(7, 4);
        move = new ChessMove(piece, new ChessPosition(7, 4), new ChessPosition(6, 5));
        game.addMove(move);
        // white d7 b7
        piece = game.getBoard().get(6, 3);
        move = new ChessMove(piece, new ChessPosition(6, 3), new ChessPosition(6, 1));
        game.addMove(move);
        // black d8 d3
        piece = game.getBoard().get(7, 3);
        move = new ChessMove(piece, new ChessPosition(7, 3), new ChessPosition(2, 3));
        game.addMove(move);
        // white b7 b8
        piece = game.getBoard().get(6, 1);
        move = new ChessMove(piece, new ChessPosition(6, 1), new ChessPosition(7, 1));
        game.addMove(move);
        // black d3 h7
        piece = game.getBoard().get(2, 3);
        move = new ChessMove(piece, new ChessPosition(2, 3), new ChessPosition(6, 7));
        game.addMove(move);
        // white b8 c8
        piece = game.getBoard().get(7, 1);
        move = new ChessMove(piece, new ChessPosition(7, 1), new ChessPosition(7, 2));
        game.addMove(move);
        // black f7 g6
        piece = game.getBoard().get(6, 5);
        move = new ChessMove(piece, new ChessPosition(6, 5), new ChessPosition(5, 6));
        game.addMove(move);
        // white c8 e6
        piece = game.getBoard().get(7, 2);
        move = new ChessMove(piece, new ChessPosition(7, 2), new ChessPosition(5, 4));
        game.addMove(move);

        /// Assertions
        // Is not check
        assertFalse(game.getBoard().isCheck(Color.BLACK));
        // does not have moves (i.e. is stalemate)
        assertFalse(game.getBoard().hasMoves(Color.BLACK));
        // Stalemate ends the game
        assertTrue(game.isOver());

        // Make sure we can't move after the game is over
        assertThrows(IllegalMoveException.class, () -> {
            // a7 to a6
            ChessPiece p = game.getBoard().get(6, 0);
            ChessMove m = new ChessMove(p, new ChessPosition(6, 0), new ChessPosition(5, 0));
            game.addMove(m);
        });
    }

    // totalTime() is not considered in this test because time is hard to predict
}

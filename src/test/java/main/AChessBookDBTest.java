package main;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import domain.ChessGame;
import domain.ChessMove;
import domain.ChessPiece;
import domain.ChessPieceKind;
import domain.ChessPlayer;
import domain.ChessPosition;
import domain.Color;
import domain.moves.IllegalMoveException;
import persist.ChessGameDM;
import persist.ChessPlayerDM;

class AChessBookDBTest {

	private static ChessPlayerDM cpdm;
	private static ChessGameDM cgdm;
	private static ChessPlayer karpov;
	private static ChessPlayer nakamura;
	private static ChessGame game1;
	private static ChessGame game2; 
	private static int idGame1; 
	private static int idGame2; 

	@BeforeAll
	static void beforeAll() {
		try {
			Main.emf = Persistence.createEntityManagerFactory("chessbookderbydb");
		} catch (Exception e) {
			System.out.println("An error occured while creating the EntityManagerFactory");
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		cgdm = ChessGameDM.getInstance(); 
		cgdm.deleteAllGames();
		
		cpdm = ChessPlayerDM.getInstance();
		cpdm.deleteAllPlayers();
		
        karpov   = new ChessPlayer("Karpov", "karpov@chess.org");
        nakamura = new ChessPlayer("Nakamura", "Nakamura@chess.org");  
        
        cpdm.insert(karpov);
        cpdm.insert(nakamura);  
        
        game1 = new ChessGame(nakamura, karpov, new Date());
		game1.loadBoard();
        game2 = new ChessGame(karpov, nakamura, new Date());
		game2.loadBoard();
        idGame1 = cgdm.insert(game1);
        idGame2 = cgdm.insert(game2);
	}

	@Test
	@Order(1)
	void testGetInstancePlayer() { 
		ChessPlayerDM cpdm2 = ChessPlayerDM.getInstance();
		assertTrue(cpdm == cpdm2);
	}
	
	@Test
	@Order(2)
	void testGetInstanceGame() { 
		ChessGameDM cgdm2 = ChessGameDM.getInstance();
		assertTrue(cgdm == cgdm2);
	}
	
	@Test
	@Order(3)
	void testPlayerGetID() { 
		int karpovId = cpdm.getID(karpov);
		int nakamuraId = cpdm.getID(nakamura);
		assertTrue(karpovId == karpov.getId());
		assertTrue(nakamuraId == nakamura.getId()); 
		
	}
	
	@Test
	@Order(4)
	void testGameGetID() { 
		int game1Id = cgdm.getID(game1);
		int game2Id = cgdm.getID(game2);
		assertTrue(game1Id == game1.getId());
		assertTrue(game2Id == game2.getId()); 
	} 
	
	@Test
	@Order(5)
	void testFindByName() { 
		List<ChessPlayer> playersNakamura = cpdm.findByName("Nakamura");
		assertTrue(playersNakamura.size() == 1);
		List<ChessPlayer> playersNull = cpdm.findByName("Test");
		assertTrue(playersNull.size() == 0);
	}
	
	@Test
	@Order(6)
	void testFindByPlayerName() { 
		
		List<ChessGame> gamesNakamura = cgdm.findByPlayerName("Nakamura");
		assertTrue(gamesNakamura.size() == 2);
		
		List<ChessGame> gameskarpov = cgdm.findByPlayerName("Karpov");
		assertTrue(gameskarpov.size() == 2);
		
		List<ChessGame> gamesNull = cgdm.findByPlayerName("Test");
		assertTrue(gamesNull.size() == 0);
	}
	
	@Test
	@Order(7)
	void testFindByEmail() { 
		
		Optional<ChessPlayer> nakamuraByEmail = cpdm.findByEmail("Nakamura@chess.org");
		assertTrue(nakamuraByEmail.get().getEmail().equals(nakamura.getEmail()));
		
		Optional<ChessPlayer> karpovByEmail = cpdm.findByEmail("karpov@chess.org");
		assertTrue(karpovByEmail.get().getEmail().equals(karpov.getEmail()));
		
	}
	
	@Test
	@Order(8)
	void testFindByPlayerEmail() { 
		
		List<ChessGame> gamesNakamura = cgdm.findByPlayerEmail("Nakamura@chess.org");
		assertTrue(gamesNakamura.size() == 2);
		
		List<ChessGame> gameskarpov = cgdm.findByPlayerEmail("karpov@chess.org");
		assertTrue(gameskarpov.size() == 2);
		
		List<ChessGame> gamesNull = cgdm.findByPlayerEmail("Test");
		assertTrue(gamesNull.size() == 0);
	} 
	
	@Test
	@Order(9)
	void testChessPlayersList() { 
		List<ChessPlayer> players = cpdm.chessPlayersList();
		assertTrue(players.size() == 2);
	}
	
	@Test
	@Order(10)
	void testChessGamesList() { 
		List<ChessGame> players = cgdm.chessGamesList();
		assertEquals(players.size(),2);
	}  
	
	
	@Test
	@Order(11)
	void testFind() { 
		Optional<ChessPlayer> player1 = cpdm.find(karpov.getId());
		assertEquals(player1.get().getEmail(), karpov.getEmail()); 
		
		Optional<ChessPlayer> player2 = cpdm.find(nakamura.getId()); 	
		assertEquals(player2.get().getEmail(), nakamura.getEmail()); 
		
		Optional<ChessGame> gameN1 = cgdm.find(idGame1); 	
		assertEquals(gameN1.get().getId(), game1.getId()); 
		
		Optional<ChessGame> gameN2 = cgdm.find(idGame2); 	
		assertEquals(gameN2.get().getId(), game2.getId()); 
		
		
	}
	@Test
	@Order(12)
    void testUpdate() throws IllegalMoveException {
				
    	ChessPosition from = new ChessPosition(1, 0);
    	ChessPosition to = new ChessPosition(2, 0);
    	ChessPiece piece = new ChessPiece(ChessPieceKind.PAWN, Color.WHITE);
    	
    	ChessMove mv = new ChessMove(piece, from, to);
		try {
			game1.addMove(mv);
		} catch (IllegalMoveException e) {
			// Do nothing, this should happen
		}
        
        
    	from = new ChessPosition(2, 0);
    	to = new ChessPosition(3, 0);
    	piece = new ChessPiece(ChessPieceKind.PAWN, Color.WHITE);
    	
    	mv = new ChessMove(piece, from, to);
		try {
			game1.addMove(mv);
		} catch (IllegalMoveException e) {
			// Do nothing, this should happen
		}
        
        cgdm.update(game1);
        
    }
	
    @AfterAll
    static void afterAll() {
		cgdm.deleteAllGames();
		cpdm.deleteAllPlayers();
    }
}

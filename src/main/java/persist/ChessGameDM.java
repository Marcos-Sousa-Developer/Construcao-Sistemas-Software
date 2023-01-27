package persist;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import domain.ChessGame;
import main.Main;

public class ChessGameDM extends ADataMapper<ChessGame> {
	private static ChessGameDM instance;

	private ChessGameDM() {
		super();
	}
	
	public static ChessGameDM getInstance() {
		if (instance == null) {
			instance = new ChessGameDM();
		}

		return instance;
	}

	
	@Override
	public int getID(ChessGame player) {
		return player.getId();
	}

	public List<ChessGame> findByPlayerName(String name) {

		EntityManager em = null;
        try {
        	em = Main.emf.createEntityManager();
			TypedQuery<ChessGame> query = 
					em.createQuery("SELECT g FROM ChessGame g, ChessPlayer p WHERE p.name = ?1 AND (g.white = p OR g.black = p)",ChessGame.class);
			query.setParameter(1, name);
			return query.getResultList(); // Obter a lista de resultados da query
		} finally {
			em.close();
		}
	}


	public List<ChessGame> findByPlayerEmail(String email) {
		EntityManager em = null;
        try {
        	em = Main.emf.createEntityManager();
			TypedQuery<ChessGame> query = em.createQuery("SELECT g FROM ChessGame g, ChessPlayer p WHERE p.email = ?1 AND (g.white = p OR g.black = p)",
					ChessGame.class);
			query.setParameter(1, email);
			return query.getResultList(); // Obter a lista de resultados da query
		} finally {
			em.close();
		}
	}

	public List<ChessGame> chessGamesList() {
		EntityManager em = null;
        try {
        	em = Main.emf.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<ChessGame> query = em.createQuery("SELECT a FROM ChessGame a", ChessGame.class);
			em.getTransaction().commit();
			return query.getResultList(); // Obter a lista de resultados da query
		} finally {
			em.close();
		}
	}

	public void deleteAllGames() {
		List<ChessGame> games = chessGamesList();

		for (ChessGame game : games) { 
			super.remove(game);			
		}
	}
}


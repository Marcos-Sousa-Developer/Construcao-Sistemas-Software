package persist;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import domain.ChessPlayer;
import main.Main;

public class ChessPlayerDM extends ADataMapper<ChessPlayer> {
	private static ChessPlayerDM instance;
	
	private ChessPlayerDM() {
		super(); 
	}
	
	public static ChessPlayerDM getInstance() {
        if (instance == null) {
            instance = new ChessPlayerDM();
		}

		return instance;
	}
	
	public int getID(ChessPlayer player) {
		return player.getId(); // este getter deve existir na classe ChessPlayer
	}

	public ChessPlayer findByID(int id) {
		EntityManager em = null;
        try {
        	em = Main.emf.createEntityManager();
        	TypedQuery<ChessPlayer> query = 
                    em.createQuery("SELECT a FROM ChessPlayer a WHERE a.id = ?1", ChessPlayer.class);
            query.setParameter(1, id); 
            return query.getSingleResult(); // Obter a lista de resultados da query 
        } finally {
            if (em != null)
                em.close();
        }
	}
	
	public List<ChessPlayer> findByName(String name){
		EntityManager em = null;
        try {
        	em = Main.emf.createEntityManager();
        	TypedQuery<ChessPlayer> query = 
                    em.createQuery("SELECT a FROM ChessPlayer a WHERE a.name = ?1", ChessPlayer.class);
            query.setParameter(1, name); 
            return query.getResultList(); // Obter a lista de resultados da query 
        } finally {
            if (em != null)
                em.close();
        }		
	}	
	
	public Optional<ChessPlayer> findByEmail(String email){
		EntityManager em = null;
        try {
        	em = Main.emf.createEntityManager();
            TypedQuery<ChessPlayer> query = 
                     em.createQuery("SELECT a FROM ChessPlayer a WHERE a.email = ?1", ChessPlayer.class);
			query.setParameter(1, email); 
			return Optional.of(query.getSingleResult()); // Obter a lista de resultados da query 
			
        } catch (NoResultException e) {
        	
        	return Optional.empty();
        	
         } finally {
			if (em != null)
				em.close();
         }
        
       
		
	}
	
	public List<ChessPlayer> chessPlayersList(){
		EntityManager em = null;
        try {
        	em = Main.emf.createEntityManager();
            TypedQuery<ChessPlayer> query = em.createQuery("SELECT a FROM ChessPlayer a", ChessPlayer.class);
            return query.getResultList(); // Obter a lista de resultados da query 
         } finally {
			if (em != null)
				em.close();
         }
		
	}
	
	public void deleteAllPlayers(){
		
		List<ChessPlayer> players = chessPlayersList();
		for (ChessPlayer player : players) {
			super.remove(player);			
    	}
    }
}

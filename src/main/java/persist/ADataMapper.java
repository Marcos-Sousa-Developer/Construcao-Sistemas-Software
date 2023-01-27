package persist;

import java.util.Optional;

import javax.persistence.EntityManager;

import domain.ChessGame;
import domain.ChessPlayer;
import main.Main;

public abstract class ADataMapper<E> implements IDataMapper<E> { 
    
    private Class<E> className;
    
    protected ADataMapper() {
    	this.className = this.getClass().equals(ChessGameDM.class) ? (Class<E>) ChessGame.class : (Class<E>) ChessPlayer.class;
    }
	
    public Optional<E> find(int id) {	
		EntityManager em = null;
		
        try {
        	em = Main.emf.createEntityManager();
    		E result = em.find(this.className, id); 
    		return Optional.of(result);
    		
    	} catch (NullPointerException e) {	
    		return Optional.empty();
    		
    	} finally {
            if (em != null)
                em.close();
    	}
    }

    public int insert(E entity) {
		EntityManager em = null;
        try {
        	em = Main.emf.createEntityManager();
    		em.getTransaction().begin(); 
    		em.persist(entity);
    		em.getTransaction().commit(); 
    		//(int) emf.getPersistenceUnitUtil().getIdentifier(entity)
    		return getID(entity);
    	
    	} finally {
            if (em != null)
                em.close();
    	}
   }

    public void update(E entity) {
		EntityManager em = null;
        try {
        	em = Main.emf.createEntityManager();
        	em.getTransaction().begin();
        	em.merge(entity);
        	em.getTransaction().commit();
    	} finally {
            if (em != null)
                em.close();
    	}
    }

    public void remove(E entity) { 
		EntityManager em = null;
        try {
        	em = Main.emf.createEntityManager();
    		em.getTransaction().begin();
            em.remove(em.merge(entity));
            em.getTransaction().commit();
    	} finally {
            if (em != null)
                em.close();
    	}
    } 
}

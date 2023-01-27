package main;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
	
	public static EntityManagerFactory emf =
					Persistence.createEntityManagerFactory("chessbookderbydb"); 
	

}

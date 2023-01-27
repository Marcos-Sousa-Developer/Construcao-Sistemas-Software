package domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

class ChessPlayerTest {
	private static ChessPlayer player;
	
	@BeforeAll
	static void beforeAll() {
		player = new ChessPlayer("Joao", "aluno1@alunos.fc.ul.pt");
	}
	
	@Test 
	@Order(1) 
	public void getName() {   
		assertEquals("Joao", player.getName());
	}
	
	@Test 
	@Order(2) 
	public void getEmail() {   
		assertEquals("aluno1@alunos.fc.ul.pt",player.getEmail()); 
	}
}

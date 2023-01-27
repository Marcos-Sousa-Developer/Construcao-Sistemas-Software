package domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ChessPlayer { 
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(nullable = false)
	private String name; 
	
	@Column(nullable = false, unique=true)
	private String email; 
	
	public ChessPlayer() {
		
	}
		
	public ChessPlayer(String name, String email) {
		this.name = name;
		this.email = email;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public Integer getId() {
		return this.id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj == this) {
			return true;
		}

		if (obj.getClass() != getClass()) {
			return false;
		}

		ChessPlayer rhs = (ChessPlayer) obj;
		return this.id == rhs.id;
	}

	@Override
	public int hashCode() {
		return this.getId().hashCode();
	}

	@Override
	public String toString() {
		return name;
	}
}

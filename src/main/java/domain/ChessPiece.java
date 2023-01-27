package domain;


import javax.persistence.Embeddable;
import javax.persistence.Transient;

import domain.moves.*;

@Embeddable
public class ChessPiece {
	private ChessPieceKind chessPieceKind;
	private Color color; 
	private boolean alive;
	private int moveCount;
	
	@Transient
	private ChessPieceMoveStrategy moveStrategy;
	
	public ChessPiece() {
	}

	public ChessPiece(ChessPieceKind chessPieceKind, Color color) {
		this.chessPieceKind = chessPieceKind;
		this.color = color;
		this.alive = true;
	}
	
	public ChessPieceKind getChessPieceKind(){
		return this.chessPieceKind;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public boolean isAlive() {
		return this.alive;
	}
	
	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public ChessPieceMoveStrategy getMoveStrategy() {
		if (this.moveStrategy == null) {
			this.setMoveStrategy();
		}

		return this.moveStrategy;
	}

	private void setMoveStrategy() {
		switch (chessPieceKind) {
			case BISHOP:
				this.moveStrategy = new ChessPieceBishopMove();
				break;
			case KING:
				this.moveStrategy = new ChessPieceKingMove();
				break;
			case KNIGHT:
				this.moveStrategy = new ChessPieceKnightMove();
				break;
			case PAWN:
				this.moveStrategy = new ChessPiecePawnMove();
				break;
			case QUEEN:
				this.moveStrategy = new ChessPieceQueenMove();
				break;
			case ROOK:
				this.moveStrategy = new ChessPieceRookMove();
				break;
		}
	}

	public int getMoveCount() {
		return this.moveCount;
	}

	public void move() {
		this.moveCount++;
	}

	public void undoMove() {
		this.moveCount--;
	}

	public String chessPieceName() {
		String cpk = chessPieceKind.toString();

		switch(cpk) {
			case "PAWN": return "P";
			case "BISHOP": return "B";
			case "KNIGHT": return "k";
			case "ROOK": return "R";
			case "QUEEN": return "Q";
		}

		return "K";
	}
	
	public String chessPieceColor() {
		return color.toString().split("")[0].toLowerCase();
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		
		if(this.isAlive()) {
			sb.append(chessPieceName() + chessPieceColor());
		}
		
		return sb.toString();
	}




}

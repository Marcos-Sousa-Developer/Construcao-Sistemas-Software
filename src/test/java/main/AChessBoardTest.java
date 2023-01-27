package main;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import domain.ChessGame;
import domain.ChessMove;
import domain.ChessPiece;
import domain.ChessPlayer;
import domain.ChessPosition;
import domain.moves.IllegalMoveException;

class AChessBoardTest {
	
    @Test
    public void moveRookKO_1() throws IllegalMoveException { 
    	ChessPlayer white = new ChessPlayer("Joao", "aluno1@alunos.fc.ul.pt");
    	ChessPlayer black = new ChessPlayer("Joana", "aluno2@alunos.fc.ul.pt");

        ChessGame cg = new ChessGame(white, black, new Date());
        cg.loadBoard();
        
		// obter a peça que está na posição 0,0. Num tabuleiro inicial 
        // deve-se encontrar nesta posição uma torre.  
        ChessPiece cp = cg.getBoard().get(0, 0);
        
        // Criar duas posições:
        ChessPosition from = new ChessPosition(0, 0);
        ChessPosition to = new ChessPosition(2, 0);
        
        // verificar que na posição 0,0 encontra-se a peça cp
        assertEquals(cg.getBoard().get(0, 0), cp, "The piece " + cp
                + " should be in position " + from);
        
        // Verificar que o movimento é illegal (a torre não pode passar por cima do peão)
        // Espera-se que uma exceção de tipo IllegalMoveException é lançada
        IllegalMoveException assertThrows = Assertions.assertThrows(IllegalMoveException.class,
                // quando se adiciona o movimento.
                () -> cg.addMove(new ChessMove(cp, from, to)));
        System.out.println(assertThrows.getMessage());
        System.out.println(cg.getBoard());
    }

}

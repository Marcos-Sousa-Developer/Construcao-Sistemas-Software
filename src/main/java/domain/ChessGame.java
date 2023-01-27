package domain;

import domain.moves.IllegalMoveException;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
public class ChessGame{
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
	
	@ManyToOne
    @JoinColumn(nullable = false)
	private ChessPlayer white; 
	
	@ManyToOne
    @JoinColumn(nullable = false)
	private ChessPlayer black;
    
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate; 
    
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastDate; 
    
	//not part of serialization
    @Transient
	private ChessBoard board;
	
	@OneToMany
	@JoinColumn
	private List<ChessMove> moves = new ArrayList<ChessMove>();

	@Column
	private GameOutcome outcome;

	@Column
	private Color winnerColor;

	private Color resignColor;

	public ChessGame() {
		this.lastDate = null;
		this.board = new ChessBoard();
	}
	
	public ChessGame(ChessPlayer white, ChessPlayer black, Date startDate) {
		this();
		this.white = white;
		this.black = black;
		this.startDate = startDate;
	}

	public void loadBoard() {
		if (this.moves.isEmpty()) {
			this.board = new ChessBoard();
		} else {
			this.board = new ChessBoard(this.moves);
		}
	}

	// if the turn is pair then it's white's turn and if it's odd then it's black's turn
	public Color getCurrentColor() {
		return this.moves.size() % 2 == 0 ? Color.WHITE : Color.BLACK;
	}
	
	public ChessPlayer getWhite() {
		return this.white;
	}
	
	public ChessPlayer getBlack() {
		return this.black;
	}
	
	public ChessBoard getBoard() {
		return this.board;
	}

	public void resign() {
		this.outcome = GameOutcome.RESIGN;
		this.winnerColor = getCurrentColor().opposite();
	}

	public void addMove(ChessMove move) throws IllegalMoveException {
		Color currentColor = getCurrentColor();
		Color adversary = currentColor.opposite();

		if (isOver()) {
			throw new IllegalMoveException("The game has already ended");
		}

		if (move.getPiece() == null) {
			throw new IllegalMoveException("No piece selected to move");
		}

		if (move.getPiece().getColor() != getCurrentColor()) {
			throw new IllegalMoveException("You can't move your opponent's pieces");
		}

		this.board.update(move);
		moves.add(move);

		if (this.outcome != GameOutcome.REPLAYING) {
			Date start = lastDate == null ? startDate : lastDate;
			Date now = new Date();

			int time = (int) Duration.between(start.toInstant(), now.toInstant()).getSeconds();
			move.setDuration(time);

			// Check ending
			boolean isCheck = this.board.isCheck(adversary);
			boolean hasMoves = this.board.hasMoves(adversary);

			if (isCheck && hasMoves) {
				move.setCheckType(CheckType.CHECK);
			}
			if (isCheck && !hasMoves) {
				move.setCheckType(CheckType.CHECKMATE);
				this.outcome = GameOutcome.CHECKMATE;
				this.winnerColor = currentColor;
			} else if (!isCheck && !hasMoves) {
				this.outcome = GameOutcome.STALEMATE;
				this.winnerColor = null;
			}
		}
	}

	public void undoMove() throws IllegalMoveException {
		try {
			ChessMove move = moves.remove(moves.size() - 1);
			this.board.undoMove(move);
		} catch (IndexOutOfBoundsException e) {
			throw new IllegalMoveException("No moves to undo");
		}
	}

	public void replayGame() {
		this.outcome = GameOutcome.REPLAYING;
	}

	public int totalTime(Color c) {
		int tt = 0;

		for(ChessMove move: moves) {
			if(move.getPiece().getColor().equals(c))
				tt += move.getDuration();
		}

		return tt;
	}

	public String totalTimeStr(Color c) {
		int tt = totalTime(c);
		return String.format("%02d:%02d:%02d", tt/3600, (tt%3600)/60, tt%60);
	}
	
	public Date getStartDate() {
		return this.startDate;
	}

	public Date getLastDate() {
		return this.lastDate;
	}
	
	public List<ChessMove> getMoves() {
		return moves;
	}
	
	public ChessMove getLastMove() {
		if (moves.isEmpty()) {
			return null;
		}

		return moves.get(moves.size()-1);
	}
	
	public int getId() {
		return this.id;
	}
	
	/**
	 * One of the players quits the game.
	 * @param color the color of the player who quits
	 * @return true if the player who quits is the winner, false otherwise
	 */
	public void quitGame(Color color) {
		this.resignColor = color;
	}

	// get resign color
	public Color getResignColor() {
		return this.resignColor;
	}


	/**
	 * Whether the game is over
	 * @return true if the game is over, false otherwise
	 */
	public boolean isOver() {
		return this.outcome != null && this.outcome != GameOutcome.REPLAYING;
	}

	/**
	 * Winner color
	 * @return winner color
	 */
	public Color getWinnerColor() {
		return this.winnerColor;
	}

	/**
	 * Game outcome
	 * @return null if still running, otherwise the outcome
	 */
	public GameOutcome getOutcome() {
		return this.outcome;
	}

	public ChessPlayer getPlayerFromColor(Color color) {
		if (color == Color.WHITE) {
			return this.white;
		} else {
			return this.black;
		}
	}

	public Color getColorFromPlayer(ChessPlayer player) {
		if (player.equals(this.white)) {
			return Color.WHITE;
		} else if (player.equals(this.black)) {
			return Color.BLACK;
		} else {
			return null;
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ChessGame chessGame = (ChessGame) o;

		return id == chessGame.id;
	}

	@Override
	public int hashCode() {
		return id;
	}
}

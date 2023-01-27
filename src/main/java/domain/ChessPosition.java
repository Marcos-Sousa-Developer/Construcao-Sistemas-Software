package domain;

import javax.persistence.Embeddable;

@Embeddable
public class ChessPosition {
    private int row;
    private int col;
	
    public ChessPosition() {}
    
    public ChessPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public boolean isValid(ChessBoard board) {
        return row >= 0 && row < board.getSize() && col >= 0 && col < board.getSize();
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public String rowToString() {
    	return Integer.toString(row + 1);
    }

    public String colToString() {
    	return Character.toString((char) ('a' + col));
    }

    @Override
    public String toString() {
    	return colToString() + rowToString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChessPosition that = (ChessPosition) o;

        if (getRow() != that.getRow()) return false;
        return getCol() == that.getCol();
    }

    @Override
    public int hashCode() {
        int result = getRow();
        result = 31 * result + getCol();
        return result;
    }
}

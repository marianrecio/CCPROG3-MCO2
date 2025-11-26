package model;

public class Position {
    private int row, col;

    public Position(int r, int c) {
        row = r;
        col = c;
        }
    public int getRow() {
        return row; 
        }
    public int getCol() { 
        return col; 
        }
    public void setRow(int r) { 
        row = r; 
        }
    public void setCol(int c) {
        col = c; 
        }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Position)) return false;
        Position other = (Position) o;
        return row == other.row && col == other.col;
    }
}

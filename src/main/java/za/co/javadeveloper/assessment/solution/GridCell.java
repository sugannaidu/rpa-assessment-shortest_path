package za.co.javadeveloper.assessment.solution;

public class GridCell {
    int x;
    int y;
    GridCell previousCell;

    public GridCell(int x, int y) {
        this.x = x;
        this.y = y;
        this.previousCell = null;
    }

    public GridCell(int x, int y, GridCell previousCell) {
        this.x = x;
        this.y = y;
        this.previousCell = previousCell;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    GridCell getPreviousCell() {
        return previousCell;
    }
}
package za.co.javadeveloper.assessment.solution;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Grid {
    private static final int CLEAR_PATH = 0;
    private static final int WALL = 1;
    private static final int START = 2;
    private static final int END = 3;
    private static final int PATH_TAKEN = 4;
    private static final int VISITED = 5;

    private int[][] grid;
    private int[][] originalGrid;
    public boolean[][] distance;
    private boolean[][] visitedCell;
    private GridCell startCell;
    private GridCell endCell;
    private boolean showSteps;

//    public Grid(File grid) {
//        String fileText = "";
//        try (Scanner input = new Scanner(grid)) {
//            while (input.hasNextLine()) {
//                fileText += input.nextLine().replaceAll("\\s", "") + "\n";
//            }
//        }
//
//        initializeMap(fileText);
//    }

    public Grid(File grid) throws Exception {
        String fileText = "";
        try (Scanner input = new Scanner(grid)) {
            while (input.hasNextLine()) {
                fileText += input.nextLine().replaceAll("\\s", "") + "\n";
            }
        }

        try {
            initializeMap(fileText);
        } catch (IllegalArgumentException e) {
            throw new Exception("Grid data invalid: ", e);

        }
    }

    public Grid(List<String> stringList) throws Exception {
        String text = "";
        for (String line : stringList) {
            text += line.replaceAll("\\s", "") + "\n";
        }
        try {
            initializeMap(text.toString());
        } catch (IllegalArgumentException e) {
            throw new Exception("Grid data invalid: ", e);

        }

    }

    private void initializeMap(String text) {
        if (text == null || (text = text.trim()).length() == 0) {
            throw new IllegalArgumentException("empty lines data");
        }

        String[] lines = text.split("[\r]?\n");
        grid = new int[lines.length][lines[0].length()];
        visitedCell = new boolean[lines.length][lines[0].length()];

        for (int row = 0; row < getRowCount(); row++) {
            if (lines[row].length() != getColCount()) {
//                throw new Exception("line " + (row + 1) + " wrong length (was " + lines[row].length() + " but should be " + getColCount() + ")");
                throw new IllegalArgumentException("line " + (row + 1) + " wrong length (was " + lines[row].length() + " but should be " + getColCount() + ")");
            }

            for (int col = 0; col < getColCount(); col++) {
                if (lines[row].charAt(col) == 'W')
                    grid[row][col] = WALL;
                else if (lines[row].charAt(col) == 'S') {
                    grid[row][col] = START;
                    startCell = new GridCell(row, col);
                } else if (lines[row].charAt(col) == 'E') {
                    grid[row][col] = END;
                    endCell = new GridCell(row, col);
                } else
                    grid[row][col] = CLEAR_PATH;
            }
        }

        if (startCell==null)
          throw new IllegalArgumentException("Start cell not provided.");

        if (endCell==null)
          throw new IllegalArgumentException("End cell not provided.");

        originalGrid = Arrays.stream(grid)
                .map(int[]::clone)
                .toArray(int[][]::new);
    }

    public int getRowCount() {
        return grid.length;
    }

    public int getColCount() {
        return grid[0].length;
    }

    public GridCell getStart() {
        return startCell;
    }

    public GridCell getEnd() {
        return endCell;
    }

    public boolean isEnd(int x, int y) {
        return x == endCell.getX() && y == endCell.getY();
    }

    public boolean isAdjacentToEnd(int x, int y) {
        return (isValidCell(endCell.getX()-1, endCell.getY()-1) && ((endCell.getX()-1==x) && ((endCell.getY()-1==y))))||
        (isValidCell(endCell.getX()+1, endCell.getY()+1) && ((endCell.getX()+1==x) && ((endCell.getY()+1==y))))||
        (isValidCell(endCell.getX()+1, endCell.getY()-1) && ((endCell.getX()+1==x) && ((endCell.getY()-1==y))))||
        (isValidCell(endCell.getX()-1, endCell.getY()+1) && ((endCell.getX()-1==x) && ((endCell.getY()+1==y))));

    }

    public boolean isStart(int x, int y) {
        return x == startCell.getX() && y == startCell.getY();
    }

    public boolean isVisited(int row, int col) {
        return visitedCell[row][col];
    }

    public boolean isWall(int row, int col) {
        return grid[row][col] == WALL;
    }

    public void setVisited(int row, int col, boolean value) {
        visitedCell[row][col] = value;
        if (grid[row][col] == CLEAR_PATH)
            grid[row][col] = VISITED;
    }

    public boolean isValidCell(int row, int col) {
        if (row < 0 || row >= getRowCount() || col < 0 || col >= getColCount()) {
            return false;
        }
        return true;
    }

    public void printInterimMap() {
        System.out.println("Original Map:");
        System.out.println(toString(originalGrid));
        System.out.println();
    }

    public void printInterimMap(GridCell gCell) {
        if (this.showSteps) {
            System.out.println("Interim Map: (x: " + gCell.x + ", y: " + gCell.y + ") Value: " + grid[gCell.x][gCell.y]);
            System.out.println(toString(grid));
            System.out.println();
        }
    }

    public void printPath(List<GridCell> path) {
        System.out.println("Solution Map:");
        int[][] tempMap = Arrays.stream(grid)
                .map(int[]::clone)
                .toArray(int[][]::new);
        int count = 0;
        for (GridCell gridCell : path) {
            if (isStart(gridCell.getX(), gridCell.getY()) || isEnd(gridCell.getX(), gridCell.getY())) {
                continue;
            }
            tempMap[gridCell.getX()][gridCell.getY()] = PATH_TAKEN;
            ++count;
        }
        System.out.println(toString(tempMap));
        System.out.println("Path length = " + count);
        System.out.println("");
    }

    public String toString(int[][] map) {
        StringBuilder result = new StringBuilder(getColCount() * (getRowCount() + 1));
        for (int row = 0; row < getRowCount(); row++) {
            for (int col = 0; col < getColCount(); col++) {
                if (map[row][col] == CLEAR_PATH) {
                    result.append('.').append(' ');
                } else if (map[row][col] == WALL) {
                    result.append('W').append(' ');
                } else if (map[row][col] == START) {
                    result.append('S').append(' ');
                } else if (map[row][col] == END) {
                    result.append('E').append(' ');
                } else if (map[row][col] == VISITED) {
                    result.append('"').append(' ');
                } else {
                    result.append('*').append(' ');
                }
            }
            result.append('\n');
        }
        return result.toString();
    }

    public void reset() {
        for (int i = 0; i < visitedCell.length; i++)
            Arrays.fill(visitedCell[i], false);
    }

    public boolean isShowSteps() {
        return showSteps;
    }

    public void setShowSteps(boolean showSteps) {
        this.showSteps = showSteps;
    }
}
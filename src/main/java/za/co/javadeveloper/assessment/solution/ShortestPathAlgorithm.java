package za.co.javadeveloper.assessment.solution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

class ShortestPathAlgorithm {
    private static final int[][] ADJACENT_CELLS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    List<GridCell> solve(Grid grid) {
        LinkedList<GridCell> nextCell = new LinkedList<>();
        GridCell start = grid.getStart();
        nextCell.add(start);

        while (!nextCell.isEmpty()) {
            GridCell selectedCell = nextCell.remove();

            if (!grid.isValidCell(selectedCell.getX(), selectedCell.getY()) || grid.isVisited(selectedCell.getX(), selectedCell.getY())) {
                continue;
            }

            if (grid.isWall(selectedCell.getX(), selectedCell.getY())) {
                grid.setVisited(selectedCell.getX(), selectedCell.getY(), true);
                continue;
            }

            if (grid.isEnd(selectedCell.getX(), selectedCell.getY())) {
                return pathTaken(selectedCell);
            }

            for (int[] adjCell : ADJACENT_CELLS) {
                GridCell gridCell = new GridCell(selectedCell.getX() + adjCell[0], selectedCell.getY() + adjCell[1], selectedCell);
                if (grid.isValidCell(gridCell.getX(), gridCell.getY()) && !(grid.isWall(gridCell.getX(), gridCell.getY()))) {
                    if (!grid.isEnd(gridCell.x, gridCell.y))
                        nextCell.add(gridCell);
                    else
                      nextCell.addFirst(gridCell);

                    if ((grid.isEnd(gridCell.x, gridCell.y))||(grid.isAdjacentToEnd(gridCell.x, gridCell.y) /** Is adjacent to End cell, includes diagonals only to prioritise cell as nextCell to select **/))
                        nextCell.addFirst(gridCell);
                    else
                       nextCell.add(gridCell);
                }
            }
            grid.setVisited(selectedCell.getX(), selectedCell.getY(), true);
            grid.printInterimMap(selectedCell);
        }
        return Collections.emptyList();
    }


    private List<GridCell> pathTaken(GridCell currentGridCell) {
        List<GridCell> path = new ArrayList<>();
        return findPathTaken(currentGridCell, path);
    }

    private List<GridCell> findPathTaken(GridCell currentGridCell, List<GridCell> path) {
        if (currentGridCell == null) {
            return path;
        }
        path.add(currentGridCell);
        findPathTaken(currentGridCell.getPreviousCell(), path);
        return path;
    }

//    private List<GridCell> pathTakenIterative(GridCell currentGridCell) {
//        List<GridCell> path = new ArrayList<>();
//        GridCell linkedCell = currentGridCell;
//
//        while (linkedCell != null) {
//            path.add(linkedCell);
//            linkedCell = linkedCell.getPreviousCell();
//        }
//        return path;
//    }

}

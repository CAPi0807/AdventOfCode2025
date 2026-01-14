package software.aoc.day12.model;

public class Grid {
    private final boolean[][] grid;
    private final int rows;
    private final int cols;

    public Grid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new boolean[rows][cols];
    }

    public boolean canPlace(Shape shape, int r, int c) {
        // Chequeo de límites
        if (r + shape.getHeight() > rows || c + shape.getWidth() > cols) {
            return false;
        }

        // Chequeo de colisión celda por celda
        for (Coordinate cell : shape.getCells()) {
            if (grid[r + cell.r()][c + cell.c()]) {
                return false; // Colisión detectada
            }
        }
        return true;
    }

    public void place(Shape shape, int r, int c) {
        updateGrid(shape, r, c, true);
    }

    public void remove(Shape shape, int r, int c) {
        updateGrid(shape, r, c, false);
    }

    private void updateGrid(Shape shape, int r, int c, boolean state) {
        for (Coordinate cell : shape.getCells()) {
            grid[r + cell.r()][c + cell.c()] = state;
        }
    }
}
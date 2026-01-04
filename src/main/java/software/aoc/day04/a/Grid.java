package software.aoc.day04.a;

import java.util.List;

public class Grid {
    private final char[][] data;
    private final int rows;
    private final int cols;

    public Grid(List<String> lines) {
        this.rows = lines.size();
        this.cols = lines.isEmpty() ? 0 : lines.getFirst().length();
        this.data = parseLines(lines);
    }

    private char[][] parseLines(List<String> lines) {
        char[][] grid = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            grid[i] = lines.get(i).toCharArray();
        }
        return grid;
    }

    public boolean isValid(Position p) {
        return p.row() >= 0 && p.row() < rows &&
                p.col() >= 0 && p.col() < cols;
    }

    public char get(Position p) {
        if (!isValid(p)) {
            throw new IndexOutOfBoundsException("Invalid position: " + p);
        }
        return data[p.row()][p.col()];
    }

    public int rows() { return rows; }
    public int cols() { return cols; }
}
package software.aoc.day07.b;

import java.util.List;

public class GridMap {

    private final List<String> grid;
    private final int height;
    private final int width;

    public GridMap(List<String> grid) {
        this.grid = grid;
        this.height = grid.size();
        this.width = grid.isEmpty() ? 0 : grid.get(0).length();
    }

    public char getCell(int row, int col) {
        if (row < 0 || row >= height) return ' ';
        String line = grid.get(row);
        if (col < 0 || col >= line.length()) return ' ';
        return line.charAt(col);
    }

    public int findStartIndex() {
        for (int r = 0; r < height; r++) {
            int index = grid.get(r).indexOf('S');
            if (index != -1) return index;
        }
        return -1;
    }

    public boolean isInsideBounds(int col) {
        return col >= 0 && col < width;
    }

    public int getHeight() {
        return height;
    }
}
package software.aoc.day07.a;

import java.util.List;

public class Grid {
    private final List<String> lines;
    private final int height;
    private final int width;

    public Grid(List<String> lines) {
        this.lines = lines;
        this.height = lines.size();
        this.width = lines.isEmpty() ? 0 : lines.get(0).length();
    }

    public char getCell(int row, int col) {
        if (isOutOfBounds(row, col)) {
            return ' '; // Valor nulo seguro
        }
        return lines.get(row).charAt(col);
    }

    public boolean isOutOfBounds(int row, int col) {
        return row < 0 || row >= height || col < 0 || col >= width;
    }

    public int findStartColumn() {
        // Buscamos 'S' en todo el grid
        for (String line : lines) {
            int index = line.indexOf('S');
            if (index != -1) {
                return index;
            }
        }
        throw new IllegalStateException("Start point 'S' not found in grid");
    }

    public int height() { return height; }
}
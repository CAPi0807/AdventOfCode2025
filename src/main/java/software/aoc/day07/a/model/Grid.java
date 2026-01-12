package software.aoc.day07.a.model;

import java.util.List;

public class Grid {
    private final List<String> lines;
    private final int height;

    public Grid(List<String> lines) {
        this.lines = lines;
        this.height = lines.size();
    }

    public char getCell(int row, int col) {
        return lines.get(row).charAt(col);
    }


    public int findStartColumn() {
        // Buscamos S
        return lines.get(0).indexOf('S');
    }

    public int height() { return height; }
}
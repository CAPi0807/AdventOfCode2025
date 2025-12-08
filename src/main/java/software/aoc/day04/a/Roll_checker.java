package software.aoc.day04.a;

import java.util.List;

public class Roll_checker {

    private final char[][] grid;
    private final int rows;
    private final int cols;

    // direcciones de los 8 vecinos
    private static final int[] DR = {-1,-1,-1, 0, 0, 1, 1, 1};
    private static final int[] DC = {-1, 0, 1,-1, 1,-1, 0, 1};

    public Roll_checker(List<String> lines) {
        this.rows = lines.size();
        this.cols = lines.getFirst().length();
        this.grid = parseGrid(lines);
    }

    public long countSelectableRolls() {
        long count = 0;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (isSelectable(r, c)) {
                    count++;
                }
            }
        }
        return count;
    }

    private boolean isSelectable(int r, int c) {
        if (grid[r][c] != '@') return false;
        return countAdjacentRolls(r, c) < 4;
    }

    private int countAdjacentRolls(int r, int c) {
        int count = 0;

        for (int i = 0; i < 8; i++) {
            int nr = r + DR[i];
            int nc = c + DC[i];

            if (isInside(nr, nc) && grid[nr][nc] == '@') {
                count++;
            }
        }
        return count;
    }

    private boolean isInside(int r, int c) {
        return r >= 0 && r < rows && c >= 0 && c < cols;
    }

    private char[][] parseGrid(List<String> lines) {
        char[][] g = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            g[i] = lines.get(i).toCharArray();
        }
        return g;
    }
}

package software.aoc.day04.b;

import java.util.ArrayList;
import java.util.List;

public class Roll_checker {

    private char[][] grid;
    private final int rows;
    private final int cols;

    private static final int[] DR = {-1,-1,-1, 0, 0, 1, 1, 1};
    private static final int[] DC = {-1, 0, 1,-1, 1,-1, 0, 1};

    public Roll_checker(List<String> lines) {
        this.rows = lines.size();
        this.cols = lines.getFirst().length();
        this.grid = parseGrid(lines);
    }

    /* =======================
       PARTE A
       ======================= */

    public long countSelectableRolls() {
        return findSelectablePositions().size();
    }

    /* =======================
       PARTE B
       ======================= */

    /**
     * Ejecuta la simulación completa eliminando rollos
     * hasta que el sistema sea estable.
     *
     * @return número total de rollos eliminados
     */
    public long removeUntilStable() {
        long removedTotal = 0;

        while (true) {
            List<Position> toRemove = findSelectablePositions();
            if (toRemove.isEmpty()) break;

            removedTotal += toRemove.size();
            removePositions(toRemove);
        }
        return removedTotal;
    }

    /**
     * Devuelve el grid final como lista de strings
     */
    public List<String> getGrid() {
        List<String> result = new ArrayList<>();
        for (char[] row : grid) {
            result.add(new String(row));
        }
        return result;
    }

    /* =======================
       LÓGICA INTERNA
       ======================= */

    private List<Position> findSelectablePositions() {
        List<Position> positions = new ArrayList<>();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (isSelectable(r, c)) {
                    positions.add(new Position(r, c));
                }
            }
        }
        return positions;
    }

    private boolean isSelectable(int r, int c) {
        return grid[r][c] == '@' && countAdjacentRolls(r, c) < 4;
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

    private void removePositions(List<Position> positions) {
        for (Position p : positions) {
            grid[p.r()][p.c()] = '.';
        }
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

    /* =======================
       RECORD AUXILIAR
       ======================= */

    private record Position(int r, int c) {}
}

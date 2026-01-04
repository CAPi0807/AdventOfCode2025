package software.aoc.day04.b;

import java.util.List;

public class UnstableRollRule implements SelectionRule {

    private static final char ROLL = '@';
    private static final int STABILITY_THRESHOLD = 4;

    @Override
    public List<Position> findMatches(Grid grid) {
        return grid.streamAllPositions()
                .filter(p -> isUnstable(grid, p))
                .toList();
    }

    private boolean isUnstable(Grid grid, Position p) {
        // Solo verificamos si es un rollo
        if (grid.get(p) != ROLL) return false;

        // Contamos vecinos que TAMBIÉN sean rollos
        long neighborsCount = p.neighbors().stream()
                .filter(grid::isValid)
                .filter(n -> grid.get(n) == ROLL)
                .count();

        return neighborsCount < STABILITY_THRESHOLD;
    }
}
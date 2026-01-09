package software.aoc.day04.b.model;

import software.aoc.day04.a.model.Grid; // Importamos de A
import software.aoc.day04.a.model.Position;

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
        if (grid.get(p) != ROLL) return false;

        long neighborsCount = p.neighbors().stream()
                .filter(grid::isValid)
                .filter(n -> grid.get(n) == ROLL)
                .count();

        return neighborsCount < STABILITY_THRESHOLD;
    }
}
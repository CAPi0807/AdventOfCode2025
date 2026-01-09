package software.aoc.day04.a.rule;

import software.aoc.day04.a.model.Grid;
import software.aoc.day04.a.model.Position;
import software.aoc.day04.a.model.SelectionRule;

public class RollSelectionRule implements SelectionRule {

    private static final char ROLL_SYMBOL = '@';
    private static final int MAX_NEIGHBORS = 4;

    @Override
    public boolean matches(Grid grid, Position position) {
        // 1. Debe ser un rollo
        if (grid.get(position) != ROLL_SYMBOL) {
            return false;
        }

        // 2. Contar vecinos que sean rollos
        long adjacentRolls = position.neighbors().stream()
                .filter(grid::isValid)
                .filter(neighbor -> grid.get(neighbor) == ROLL_SYMBOL)
                .count();

        // 3. Condición de soledad (< 4)
        return adjacentRolls < MAX_NEIGHBORS;
    }
}
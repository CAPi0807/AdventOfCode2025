package software.aoc.day04.a.rule;

import software.aoc.day04.a.model.Grid;
import software.aoc.day04.a.model.Position;
import software.aoc.day04.a.model.SelectionRule;

public class RollSelectionRule implements SelectionRule {

    private static final char roll = '@';
    private static final int neighbors = 4;

    @Override
    public boolean matches(Grid grid, Position position) {

        if (grid.get(position) != roll) {
            return false;
        }

        long adjacentRolls = position.neighbors().stream()
                .filter(grid::isValid)
                .filter(neighbor -> grid.get(neighbor) == roll)
                .count();

        return adjacentRolls < neighbors;
    }
}
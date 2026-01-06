package software.aoc.day04.b;

import software.aoc.day04.a.Grid; // Importamos de A
import software.aoc.day04.a.Position; // Importamos de A
import java.util.List;

public class WarehouseSimulator {

    private final SelectionRule rule;

    public WarehouseSimulator(SelectionRule rule) {
        this.rule = rule;
    }

    public SimulationResult runUntilStable(Grid initialGrid) {
        Grid currentGrid = initialGrid;
        long totalRemoved = 0;

        while (true) {
            List<Position> toRemove = rule.findMatches(currentGrid);

            if (toRemove.isEmpty()) {
                break;
            }

            totalRemoved += toRemove.size();
            currentGrid = currentGrid.without(toRemove);
        }

        return new SimulationResult(totalRemoved, currentGrid);
    }
}
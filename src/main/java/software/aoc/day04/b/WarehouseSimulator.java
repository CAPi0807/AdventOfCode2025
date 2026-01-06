package software.aoc.day04.b;

import software.aoc.day04.a.Position;

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
            // 1. Detectar candidatos a eliminar con la regla actual
            List<Position> toRemove = rule.findMatches(currentGrid);

            // 2. Condición de parada: Estabilidad (nadie se elimina)
            if (toRemove.isEmpty()) {
                break;
            }

            // 3. Acumular contador
            totalRemoved += toRemove.size();

            // 4. Evolucionar estado (Crear nuevo Grid inmutable)
            currentGrid = currentGrid.without(toRemove);
        }

        return new SimulationResult(totalRemoved, currentGrid);
    }
}
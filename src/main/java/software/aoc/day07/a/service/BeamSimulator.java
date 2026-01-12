package software.aoc.day07.a.service;

import software.aoc.day07.a.model.Grid;
import software.aoc.day07.a.model.InteractionResult;
import software.aoc.day07.a.physics.RayPhysics;

import java.util.HashSet;
import java.util.Set;

public class BeamSimulator {

    private final Grid grid;
    private final RayPhysics physics;

    public BeamSimulator(Grid grid, RayPhysics physics) {
        this.grid = grid;
        this.physics = physics;
    }

    public long simulateAndCountSplits() {

        int startCol = grid.findStartColumn();
        Set<Integer> activeColumns = Set.of(startCol);

        long totalSplits = 0;

        // Bucle de Gravedad (Fila a Fila)
        for (int row = 0; row < grid.height(); row++) {

            Set<Integer> nextColumns = new HashSet<>();

            for (int col : activeColumns) {
                // Obtener celda
                char cell = grid.getCell(row, col);

                InteractionResult result = physics.interact(cell);

                if (result.isSplit()) {
                    totalSplits++;
                }

                // Calcular siguientes posiciones
                for (int offset : result.nextColumnOffsets()) {
                    int nextCol = col + offset;
                    nextColumns.add(nextCol);
                }
            }

            activeColumns = nextColumns;

        }
        return totalSplits;
    }
}
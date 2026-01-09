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
        // 1. Estado Inicial
        int startCol = grid.findStartColumn();
        Set<Integer> activeColumns = Set.of(startCol);

        long totalSplits = 0;

        // 2. Bucle de Gravedad (Fila a Fila)
        for (int row = 0; row < grid.height(); row++) {

            Set<Integer> nextColumns = new HashSet<>();

            for (int col : activeColumns) {
                // a. Obtener celda
                char cell = grid.getCell(row, col);

                // b. Calcular física
                InteractionResult result = physics.interact(cell);

                // c. Actualizar contadores
                if (result.isSplit()) {
                    totalSplits++;
                }

                // d. Calcular siguientes posiciones
                for (int offset : result.nextColumnOffsets()) {
                    int nextCol = col + offset;
                    // Solo añadimos si cae dentro del mapa (Validación de límites)
                    if (!grid.isOutOfBounds(row + 1, nextCol)) {
                        nextColumns.add(nextCol);
                    }
                }
            }

            // e. Evolucionar estado
            activeColumns = nextColumns;

            // Optimización
            if (activeColumns.isEmpty()) break;
        }

        return totalSplits;
    }
}
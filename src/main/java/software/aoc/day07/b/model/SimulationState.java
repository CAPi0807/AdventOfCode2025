package software.aoc.day07.b.model;

import software.aoc.day07.b.physics.InteractionRule;

import java.util.HashMap;
import java.util.Map;

public class SimulationState {

    // Mapa: Columna -> Número de caminos que llegan a ella
    private final Map<Integer, Long> paths;

    // Constructor privado para inmutabilidad
    private SimulationState(Map<Integer, Long> paths) {
        this.paths = Map.copyOf(paths);
    }

    // Factory method para el estado inicial
    public static SimulationState initial(int startCol) {
        return new SimulationState(Map.of(startCol, 1L));
    }

    public boolean isEmpty() {
        return paths.isEmpty();
    }

    public long totalPaths() {
        return paths.values().stream().mapToLong(Long::longValue).sum();
    }

    /**
     * Genera el estado de la siguiente fila aplicando las reglas de física.
     * Aquí reside la lógica delicada de acumulación.
     */
    public SimulationState evolve(Grid grid, int currentRowIndex, InteractionRule physics) {
        Map<Integer, Long> nextPaths = new HashMap<>();

        for (Map.Entry<Integer, Long> entry : this.paths.entrySet()) {
            int col = entry.getKey();
            long incomingPaths = entry.getValue();

            // 1. Consultar qué hay en el mapa en la posición actual
            char cell = grid.getCell(currentRowIndex, col);

            // 2. Obtener offsets de movimiento (Física)
            // Ejemplo: Si es '^', offsets serán [-1, 1]
            var offsets = physics.getNextOffsets(cell);

            // 3. Distribuir los caminos
            for (int offset : offsets) {
                int targetCol = col + offset;

                // Solo si cae dentro del mapa, acumulamos los caminos
                if (!grid.isOutOfBounds(targetCol)) {
                    // LÓGICA CLAVE: merge con Long::sum
                    // Si dos rayos convergen en 'targetCol', sus caminos se suman.
                    nextPaths.merge(targetCol, incomingPaths, Long::sum);
                }
            }
        }

        return new SimulationState(nextPaths);
    }
}
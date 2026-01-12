package software.aoc.day07.b.model;

import software.aoc.day07.b.physics.InteractionRule;
import software.aoc.day07.a.model.Grid;

import java.util.HashMap;
import java.util.Map;

public class SimulationState {

    // Mapa: Columna -> Número de caminos que llegan a ella
    private final Map<Integer, Long> paths;

    private SimulationState(Map<Integer, Long> paths) {
        this.paths = Map.copyOf(paths);
    }

    // Factory method para el estado inicial
    public static SimulationState initial(int startCol) {
        return new SimulationState(Map.of(startCol, 1L));
    }

    public long totalPaths() {
        return paths.values().stream().mapToLong(Long::longValue).sum();
    }

    public SimulationState evolve(Grid grid, int currentRowIndex, InteractionRule physics) {
        Map<Integer, Long> nextPaths = new HashMap<>();

        for (Map.Entry<Integer, Long> entry : this.paths.entrySet()) {
            int col = entry.getKey();
            long incomingPaths = entry.getValue();

            // Consultar qué hay en el mapa en la posición actual
            char cell = grid.getCell(currentRowIndex, col);

            // Obtener offsets de movimiento (Física)
            // Ejemplo: Si es '^', offsets serán [-1, 1]
            var offsets = physics.getNextOffsets(cell);

            // 3. Distribuir los caminos
            for (int offset : offsets) {
                int targetCol = col + offset;

                // LÓGICA CLAVE: merge con Long::sum
                // Si dos rayos convergen en 'targetCol', sus caminos se suman.
                nextPaths.merge(targetCol, incomingPaths, Long::sum);

            }
        }

        return new SimulationState(nextPaths);
    }
}
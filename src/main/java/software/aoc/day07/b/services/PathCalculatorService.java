package software.aoc.day07.b.services;

import software.aoc.day07.b.physics.InteractionRule;
import software.aoc.day07.b.model.SimulationState;
import software.aoc.day07.a.model.Grid;

public class PathCalculatorService {

    private final InteractionRule physics;

    public PathCalculatorService(InteractionRule physics) {
        this.physics = physics;
    }

    public long calculateTotalPaths(Grid grid) {
        int startCol = grid.findStartColumn();

        // Estado inicial: 1 camino en la 'S'
        SimulationState currentState = SimulationState.initial(startCol);

        for (int row = 0; row < grid.height(); row++) {
            // Calculamos el estado de la fila siguiente basándonos en la actual
            currentState = currentState.evolve(grid, row, physics);

        }
        return currentState.totalPaths();
    }
}
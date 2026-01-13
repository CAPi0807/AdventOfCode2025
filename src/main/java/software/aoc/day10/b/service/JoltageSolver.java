package software.aoc.day10.b.service;

import java.util.List;
import software.aoc.day10.a.model.Machine;

public class JoltageSolver {

    private final LinearOptimizer optimizer;

    public JoltageSolver() {
        this.optimizer = new LinearOptimizer();
    }

    public long solveTotalVoltagePresses(List<Machine> machines) {
        return machines.stream()
                .mapToLong(this::solveMachine)
                .sum();
    }

    private long solveMachine(Machine machine) {
        int rows = machine.voltageCount();
        int cols = machine.buttonCount();

        double[][] matrix = new double[rows][cols];
        double[] target = new double[rows];

        // Rellenar vector objetivo (Voltajes)
        for (int i = 0; i < rows; i++) {
            target[i] = machine.voltageTargets().get(i);
        }

        // Rellenar matriz (1.0 si el botón incrementa ese voltaje)
        for (int j = 0; j < cols; j++) {
            List<Integer> affectedIndices = machine.buttons().get(j);
            for (Integer voltageIdx : affectedIndices) {
                if (voltageIdx < rows) {
                    matrix[voltageIdx][j] = 1.0;
                }
            }
        }

        long result = optimizer.solveMinPresses(matrix, target);
        return result == -1 ? 0 : result;
    }
}

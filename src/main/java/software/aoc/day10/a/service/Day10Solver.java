package software.aoc.day10.a.service;

import software.aoc.day10.a.model.Machine;

import java.util.List;

public class Day10Solver {

    private final GaussianSolver mathSolver;

    public Day10Solver() {
        this.mathSolver = new GaussianSolver();
    }

    public long solveTotalPresses(List<Machine> machines) {
        return machines.stream()
                .mapToLong(this::solveMachine)
                .filter(presses -> presses >= 0) // Filtramos máquinas sin solución
                .sum();
    }

    private int solveMachine(Machine machine) {
        // Construir matriz: Filas = Luces, Columnas = Botones
        int rows = machine.lightCount();
        int cols = machine.buttonCount();

        int[][] matrix = new int[rows][cols];
        int[] target = new int[rows];

        // Rellenar vector objetivo
        for (int i = 0; i < rows; i++) {
            target[i] = machine.targetState().get(i);
        }

        // Rellenar matriz de coeficientes
        // Si el botón j afecta a la luz i, matrix[i][j] = 1
        for (int j = 0; j < cols; j++) {
            List<Integer> affectedLights = machine.buttons().get(j);
            for (Integer lightIndex : affectedLights) {
                if (lightIndex < rows) {
                    matrix[lightIndex][j] = 1;
                }
            }
        }

        return mathSolver.solveMinimumPresses(matrix, target);
    }
}
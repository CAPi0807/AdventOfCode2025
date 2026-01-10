package Day10Test.b;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.aoc.day10.a.model.Machine;
import software.aoc.day10.b.service.Day10BSolver;
import software.aoc.day10.b.service.LinearOptimizer;

import java.util.List;

class Day10BTest {

    private static final double EPSILON = 1e-9;

    /**
     * Test Unidad: LinearOptimizer
     * Caso: Sistema simple con solución única entera.
     * 2x = 10 -> x = 5.
     */
    @Test
    void testSimpleIntegerSolution() {
        LinearOptimizer optimizer = new LinearOptimizer();
        double[][] matrix = {{2.0}};
        double[] target = {10.0};

        long result = optimizer.solveMinPresses(matrix, target);
        Assertions.assertEquals(5, result);
    }

    /**
     * Test Unidad: LinearOptimizer
     * Caso: Solución fraccionaria (debe fallar).
     * 2x = 5 -> x = 2.5 (No es válido, necesitamos pulsaciones enteras).
     */
    @Test
    void testFractionalSolutionFails() {
        LinearOptimizer optimizer = new LinearOptimizer();
        double[][] matrix = {{2.0}};
        double[] target = {5.0};

        long result = optimizer.solveMinPresses(matrix, target);
        // El optimizador devuelve -1 si no encuentra solución entera
        Assertions.assertEquals(-1, result);
    }

    /**
     * Test Unidad: LinearOptimizer
     * Caso: Variables Libres y Minimización.
     * Ecuación: 1x + 2y = 10. Minimizar x + y.
     * Posibles soluciones enteras no negativas:
     * - y=0, x=10 -> Suma = 10
     * - y=1, x=8  -> Suma = 9
     * - y=2, x=6  -> Suma = 8
     * - y=3, x=4  -> Suma = 7
     * - y=4, x=2  -> Suma = 6  <-- Mínimo esperado
     * - y=5, x=0  -> Suma = 5  <-- Espera, x+y = 5 es menor que 6.
     *
     * Revisemos:
     * Si y=5, 1x + 10 = 10 -> x=0. Suma = 5.
     * El optimizador debería encontrar 5.
     */
    @Test
    void testFreeVariableOptimization() {
        LinearOptimizer optimizer = new LinearOptimizer();
        double[][] matrix = {{1.0, 2.0}}; // Coeficientes para x, y
        double[] target = {10.0};

        long result = optimizer.solveMinPresses(matrix, target);
        Assertions.assertEquals(5, result);
    }


    /**
     * Test Integración: Caso Imposible
     * Btn 0 suma 2 al voltaje. Target es 3. Imposible en enteros.
     */
    @Test
    void testSolverImpossibleCase() {
        List<List<Integer>> buttons = List.of(List.of(0));
        List<Integer> voltageTargets = List.of(3);
        // Simulamos que el botón 0 aumenta el voltaje 0. Pero la matriz se construye como 1.0.
        // Espera, el parser dice que matrix[i][j] = 1.0.
        // Si el target es 3, y el coeficiente es 1, entonces 1*x = 3 -> x=3. ES POSIBLE.

        // Vamos a crear un caso realmente imposible con coeficientes 1.0 (que es lo que hace el solver actual).
        // Ecuaciones:
        // x = 10
        // x = 20
        // Imposible satisfacer ambas con una sola variable.

        List<List<Integer>> buttonsConflict = List.of(
                List.of(0, 1) // Btn 0 afecta voltajes 0 y 1
        );
        List<Integer> voltageTargetsConflict = List.of(10, 20);
        // Sistema:
        // 1*x = 10
        // 1*x = 20 -> Contradicción.

        Machine machine = new Machine(List.of(), buttonsConflict, voltageTargetsConflict);
        Day10BSolver solver = new Day10BSolver();

        // solveTotalVoltagePresses filtra los -1 y suma 0
        long result = solver.solveTotalVoltagePresses(List.of(machine));

        Assertions.assertEquals(0, result);
    }
}
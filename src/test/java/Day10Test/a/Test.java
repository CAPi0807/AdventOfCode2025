package Day10Test.a;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.aoc.day10.a.model.Machine;
import software.aoc.day10.a.parser.MachineParser;
import software.aoc.day10.a.service.MachineSolver;
import software.aoc.day10.a.service.GaussianSolver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class Day10Test {

    /**
     * Test de Unidad: MachineParser
     * Verifica que las Regex extraigan correctamente la estructura compleja.
     */
    @Test
    void testParser() {
        // Input ejemplo: Luces target [.##], Botones (0,1) y (1), Voltaje {5}
        // '#' = 1, '.' = 0
        String input = "Wait: [.##] Buttons: (0,1) (1) Voltage: {5}";

        MachineParser parser = new MachineParser();
        Machine machine = parser.parse(input);

        // Verificar Luces (Target State)
        // .## -> 0, 1, 1
        Assertions.assertEquals(3, machine.lightCount());
        Assertions.assertEquals(List.of(0, 1, 1), machine.targetState());

        // Verificar Botones
        // Botón 0 afecta a luces 0 y 1
        // Botón 1 afecta a luz 1
        Assertions.assertEquals(2, machine.buttonCount());
        Assertions.assertEquals(List.of(0, 1), machine.buttons().get(0));
        Assertions.assertEquals(List.of(1), machine.buttons().get(1));

        // Verificar Voltaje
        Assertions.assertEquals(1, machine.voltageCount());
        Assertions.assertEquals(5, machine.voltageTargets().get(0));
    }

    /**
     * Test de Unidad: GaussianSolver (Matemática Pura)
     * Caso 1: Sistema con solución única trivial.
     * Matriz Identidad:
     * 1 0 | 1  (Botón 0 enciende luz 0, queremos luz 0 encendida)
     * 0 1 | 1  (Botón 1 enciende luz 1, queremos luz 1 encendida)
     * Solución: Pulsar ambos (peso 2).
     */
    @Test
    void testGaussianSolverUniqueSolution() {
        GaussianSolver solver = new GaussianSolver();
        int[][] matrix = {
                {1, 0},
                {0, 1}
        };
        int[] target = {1, 1};

        int result = solver.solveMinimumPresses(matrix, target);
        Assertions.assertEquals(2, result);
    }

    /**
     * Test de Unidad: GaussianSolver
     * Caso 2: Sistema Imposible.
     * 0 0 | 1 (Ningún botón afecta a la luz 0, pero queremos encenderla)
     */
    @Test
    void testGaussianSolverImpossible() {
        GaussianSolver solver = new GaussianSolver();
        int[][] matrix = {
                {0, 0},
                {1, 1}
        };
        int[] target = {1, 0}; // Imposible cumplir la primera fila

        int result = solver.solveMinimumPresses(matrix, target);
        Assertions.assertEquals(-1, result);
    }

    /**
     * Test de Unidad: GaussianSolver
     * Caso 3: Variables Libres (Múltiples soluciones, buscar mínima).
     * Luz 0: Afectada por Botón A y Botón B. Target: 0 (Apagada).
     * Soluciones posibles:
     * 1. No pulsar nada (A=0, B=0) -> Luz 0 apagada. Peso = 0.
     * 2. Pulsar ambos (A=1, B=1) -> 1 XOR 1 = 0. Luz 0 apagada. Peso = 2.
     * El solver debe devolver 0.
     */
    @Test
    void testGaussianSolverFreeVariablesMinimization() {
        GaussianSolver solver = new GaussianSolver();
        int[][] matrix = {
                {1, 1} // Luz 0 afectada por btn 0 y btn 1
        };
        int[] target = {0}; // Queremos que esté apagada

        int result = solver.solveMinimumPresses(matrix, target);
        Assertions.assertEquals(0, result);
    }

    /**
     * Test de Integración: Day10Solver
     * Verifica la conversión de Machine a Matriz y la solución.
     */
    @Test
    void testDay10SolverIntegration() {
        // Escenario:
        // Luz 0 (Target 1). Botón 0 afecta a Luz 0.
        // Luz 1 (Target 1). Botón 1 afecta a Luz 1.
        // Luz 2 (Target 0). Botón 0 afecta a Luz 2.

        // Matriz resultante:
        // 1 0 (Luz 0)
        // 0 1 (Luz 1)
        // 1 0 (Luz 2)

        // Target: [1, 1, 0]

        // Fila 0 dice: btn0 = 1
        // Fila 1 dice: btn1 = 1
        // Fila 2 dice: btn0 = 0
        // CONTRADICCIÓN en btn0 (debe ser 1 y 0 a la vez). Debe retornar 0 (filtrado) o manejado.
        // El metodo solveTotalPresses suma los >= 0.

        List<Integer> targetState = List.of(1, 1, 0);
        List<List<Integer>> buttons = List.of(
                List.of(0, 2), // Btn 0 afecta luz 0 y 2
                List.of(1)     // Btn 1 afecta luz 1
        );

        Machine impossibleMachine = new Machine(targetState, buttons, List.of());

        MachineSolver solver = new MachineSolver();

        // Como es una lista de 1 máquina y es imposible, el resultado de la suma debe ser 0
        // (ya que filter elimina los -1).
        long result = solver.solveTotalPresses(List.of(impossibleMachine));
        Assertions.assertEquals(0, result);
    }

    /**
     * Test de Integración Completa con Archivo
     */
    @Test
    void testFullIntegration() throws IOException {
        // Simulamos un archivo con 2 máquinas
        // Maquina 1 (Coste 1): [.#] Btn(0) -> Btn0 enciende Luz1. Target Luz1=ON. Sol: Pulsar Btn0.
        // Maquina 2 (Coste 2): [##] Btn(0) Btn(1) -> Btn0->Luz0, Btn1->Luz1. Sol: Pulsar ambos.

        String content = """
                Machine 1: [.#] Buttons: (1) Voltage: {0}
                Machine 2: [##] Buttons: (0) (1) Voltage: {0}
                """;

        Path tempPath = Path.of("src/test/resources/Day10/Machines.txt");
        Files.createDirectories(tempPath.getParent());
        Files.writeString(tempPath, content);

        MachineParser parser = new MachineParser();
        MachineSolver solver = new MachineSolver();

        try (var lines = Files.lines(tempPath)) {
            List<Machine> machines = lines
                    .filter(line -> !line.isBlank())
                    .map(parser::parse)
                    .toList();

            long total = solver.solveTotalPresses(machines);

            // Maquina 1: 1 pulsación
            // Maquina 2: 2 pulsaciones
            // Total: 3
            Assertions.assertEquals(3, total);
        }

        // Limpieza (Opcional)
        Files.deleteIfExists(tempPath);
    }
}
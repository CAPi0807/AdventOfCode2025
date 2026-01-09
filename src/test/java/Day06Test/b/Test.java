package Day06Test.a; // Mismo paquete de test solicitado

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.aoc.day06.b.model.NumberBlock;
import software.aoc.day06.b.model.Operation;
import software.aoc.day06.b.parser.GridScanner;
import software.aoc.day06.b.service.ProblemSolver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class Day06BTest {

    @Test
    void testOperationLogic() {
        // Si hay un asterisco, es multiplicación
        Assertions.assertEquals(Operation.MULTIPLY, Operation.determineFromSignature("  * "));
        Assertions.assertEquals(Operation.MULTIPLY, Operation.determineFromSignature("**"));

        // Si no, es suma
        Assertions.assertEquals(Operation.SUM, Operation.determineFromSignature("  + "));
        Assertions.assertEquals(Operation.SUM, Operation.determineFromSignature("   "));
    }

    @Test
    void testBlockCalculation() {
        // Bloque Suma: 10 + 20 = 30
        NumberBlock sumBlock = new NumberBlock(List.of(10L, 20L), Operation.SUM);
        Assertions.assertEquals(30, sumBlock.calculateResult());

        // Bloque Multi: 5 * 5 = 25
        NumberBlock multBlock = new NumberBlock(List.of(5L, 5L), Operation.MULTIPLY);
        Assertions.assertEquals(25, multBlock.calculateResult());
    }

    @Test
    void testGridScanningVerticalAndGaps() {
        // Simulación:
        // Col 0: '1', '1' -> 11
        // Col 1: ' ', ' ' -> Espacio (Rompe bloque)
        // Col 2: '2', ' ' -> 2
        // Col 3: ' ', '2' -> 2 (Contiguo al anterior)
        // Ops:   ' ', ' ', '*', ' '

        // Bloque 1: [11]. Op: ' ' -> SUM. Res: 11.
        // Bloque 2: [2, 2]. Op: '*' -> MULT. Res: 4.
        // Total: 15.

        List<String> data = List.of(
                "1 2 ",
                "1  2"
        );
        String ops = "  * "; // El * está bajo la primera col del segundo bloque (índice 2)

        GridScanner scanner = new GridScanner();
        List<NumberBlock> blocks = scanner.scanBlocks(data, ops);

        Assertions.assertEquals(2, blocks.size());

        // Validar Bloque 1
        Assertions.assertEquals(11L, blocks.get(0).numbers().get(0));
        Assertions.assertEquals(Operation.SUM, blocks.get(0).operation());

        // Validar Bloque 2
        Assertions.assertEquals(2L, blocks.get(1).numbers().get(0));
        Assertions.assertEquals(2L, blocks.get(1).numbers().get(1));
        Assertions.assertEquals(Operation.MULTIPLY, blocks.get(1).operation());
    }

    @Test
    void testIntegrationFromFile() throws IOException {
        Path path = Path.of("src/test/resources/Day06/Problems.txt");
        List<String> lines = Files.readAllLines(path);

        ProblemSolver solver = new ProblemSolver();
        long result = solver.solve(lines);

        // Verificación
        System.out.println("Integration Result Day06 B: " + result);
        Assertions.assertEquals(3263827, result);
    }
}
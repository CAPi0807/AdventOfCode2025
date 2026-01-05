package Day06Test.a;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.aoc.day06.a.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class Day06Test {

    @Test
    void testOperatorLogic() {
        // Suma
        Assertions.assertEquals(10, Operator.ADD.apply(5, 5));
        Assertions.assertEquals(0, Operator.ADD.identity());

        // Multiplicación
        Assertions.assertEquals(25, Operator.MULTIPLY.apply(5, 5));
        Assertions.assertEquals(1, Operator.MULTIPLY.identity());
    }

    @Test
    void testColumnCalculation() {
        // Matriz:
        // 1  2
        // 3  4
        // Ops: + *

        // Col 0 (+): 0 + 1 + 3 = 4
        // Col 1 (*): 1 * 2 * 4 = 8
        // Total esperado: 12

        Matrix matrix = new Matrix(List.of(
                List.of(1L, 2L),
                List.of(3L, 4L)
        ));
        List<Operator> ops = List.of(Operator.ADD, Operator.MULTIPLY);
        ProblemSchema schema = new ProblemSchema(matrix, ops);

        ColumnCalculationService service = new ColumnCalculationService();
        long result = service.solve(schema);

        Assertions.assertEquals(12, result);
    }

    @Test
    void testParserValidation() {
        ProblemParser parser = new ProblemParser();
        // Matriz de 2 columnas pero solo 1 operador
        List<String> input = List.of(
                "1 2",
                "+"
        );

        // Debe lanzar excepción por mismatch
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            parser.parse(input);
        });
    }

    @Test
    void testIntegrationFromFile() throws IOException {
        Path path = Path.of("src/test/resources/Day06/Problems.txt");
        List<String> lines = Files.readAllLines(path);

        ProblemParser parser = new ProblemParser();
        ProblemSchema schema = parser.parse(lines);

        ColumnCalculationService service = new ColumnCalculationService();
        long result = service.solve(schema);

        // Verificación
        System.out.println("Integration Result Day06: " + result);
        Assertions.assertEquals(4277556, result);
    }
}
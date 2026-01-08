package Day11Test.a;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.aoc.day11.a.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

class Day11ATest {

    @Test
    void testParserWithComplexLine() {
        // Probamos una línea con múltiples destinos y espacios
        List<String> input = List.of("aaa: you hhh");
        GraphParser parser = new GraphParser();
        Graph graph = parser.parse(input);

        List<String> neighbors = graph.getNeighbors("aaa");
        Assertions.assertEquals(2, neighbors.size());
        Assertions.assertTrue(neighbors.contains("you"));
        Assertions.assertTrue(neighbors.contains("hhh"));
    }

    @Test
    void testMemoizationEfficiency() {
        // Estructura Diamante Convergente (Similar a bbb/ccc convergiendo en ddd/eee)
        // you -> bbb, ccc
        // bbb -> target
        // ccc -> target
        // target -> out
        // Paths: you-bbb-target-out, you-ccc-target-out.
        // La ruta 'target -> out' debe calcularse una sola vez y reutilizarse.

        Graph graph = new Graph(Map.of(
                "you", List.of("bbb", "ccc"),
                "bbb", List.of("target"),
                "ccc", List.of("target"),
                "target", List.of("out")
        ));

        PathCounter counter = new PathCounter(graph);
        long paths = counter.countPaths("you", "out");

        Assertions.assertEquals(2, paths);
    }

    @Test
    void testUnreachableNodesIgnored() {
        // aaa -> you -> out
        // Si empezamos en 'you', 'aaa' no cuenta.
        Graph graph = new Graph(Map.of(
                "aaa", List.of("you"),
                "you", List.of("out")
        ));

        PathCounter counter = new PathCounter(graph);
        long paths = counter.countPaths("you", "out"); // Ignora aaa

        Assertions.assertEquals(1, paths);
    }

    /**
     * TEST DE INTEGRACIÓN PRINCIPAL
     * Usa el contenido exacto de Connections.txt provisto.
     */
    @Test
    void testIntegrationFromProvidedFile() throws IOException {
        Path path = Path.of("src/test/resources/Day11/Connections.txt");
        List<String> lines = Files.readAllLines(path);

        Day11Solver solver = new Day11Solver();
        long result = solver.solve(lines);

        // Desglose de caminos esperados (5):
        // 1. you -> bbb -> ddd -> ggg -> out
        // 2. you -> bbb -> eee -> out
        // 3. you -> ccc -> ddd -> ggg -> out
        // 4. you -> ccc -> eee -> out
        // 5. you -> ccc -> fff -> out

        System.out.println("Integration Result Day11: " + result);
        Assertions.assertEquals(5, result);
    }
}
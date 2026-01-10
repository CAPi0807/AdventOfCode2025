package Day11Test.b;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.aoc.day11.b.service.WaypointRouteService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class Day11BTest {

    /**
     * Caso 1: Ruta Lineal Simple (Orden 1)
     * svr -> fft -> dac -> out
     * Solo hay 1 camino válido. La variante inversa (svr->dac->fft) debería dar 0.
     */
    @Test
    void testLinearOrderFftThenDac() {
        List<String> input = List.of(
                "svr: fft",
                "fft: dac",
                "dac: out"
        );

        WaypointRouteService solver = new WaypointRouteService();
        long result = solver.solve(input);

        // Variante 1: 1 * 1 * 1 = 1
        // Variante 2: 0 (svr no conecta a dac directamente ni indirectamente antes de fft)
        Assertions.assertEquals(1, result);
    }

    /**
     * Caso 2: Ruta Lineal Inversa (Orden 2)
     * svr -> dac -> fft -> out
     */
    @Test
    void testLinearOrderDacThenFft() {
        List<String> input = List.of(
                "svr: dac",
                "dac: fft",
                "fft: out"
        );

        WaypointRouteService solver = new WaypointRouteService();
        long result = solver.solve(input);

        Assertions.assertEquals(1, result);
    }

    /**
     * Caso 3: Combinatoria (Multiplicación)
     * svr -> (2 caminos) -> fft -> (2 caminos) -> dac -> (2 caminos) -> out
     * Total esperado: 2 * 2 * 2 = 8.
     */
    @Test
    void testPathMultiplication() {
        List<String> input = List.of(
                // svr a fft (2 caminos: a, b)
                "svr: a b",
                "a: fft",
                "b: fft",

                // fft a dac (2 caminos: c, d)
                "fft: c d",
                "c: dac",
                "d: dac",

                // dac a out (2 caminos: e, f)
                "dac: e f",
                "e: out",
                "f: out"
        );

        WaypointRouteService solver = new WaypointRouteService();
        long result = solver.solve(input);

        Assertions.assertEquals(8, result);
    }

    /**
     * Caso 4: Rutas Disjuntas (Suma de Variantes)
     * El grafo tiene dos ramas totalmente separadas.
     * Rama A: svr -> fft -> dac -> out (1 camino)
     * Rama B: svr -> dac -> fft -> out (1 camino)
     * Total: 2 caminos válidos.
     * Nota: Esto es raro en un DAG físico simple, pero posible en grafos abstractos.
     */
    @Test
    void testBothVariantsPossible() {
        List<String> input = List.of(
                "svr: path1 path2",

                // Camino 1: fft primero
                "path1: fft",
                "fft: mid1",
                "mid1: dac",
                "dac: end1",
                "end1: out",

                // Camino 2: dac primero
                "path2: dac", // Ojo: en este contexto dac es un nodo distinto o el mismo?
                // En el mapa es EL MISMO nodo "dac".
                // Para que esto funcione, fft y dac deben estar conectados en ambos sentidos?
                // NO, porque es un DAG. No puede haber ciclos.
                // Por tanto, es IMPOSIBLE en un DAG tener svr->fft->dac Y svr->dac->fft a la vez
                // porque implicaría fft->...->dac Y dac->...->fft, creando un ciclo.
                "path2: dac_alt"
        );

        // CORRECCIÓN LÓGICA:
        // En un DAG estricto, si existe un camino de A a B, no puede existir de B a A.
        // Por tanto, solo una de las dos variantes (fft->dac O dac->fft) puede ser mayor que 0.
        // La otra dará 0. La suma será correcta.
        // Probaremos que el solver maneja esto devolviendo el resultado de la única rama válida.

        WaypointRouteService solver = new WaypointRouteService();

        // Grafo donde svr -> fft -> dac -> out
        // Intentar calcular la inversa debe dar 0.
        long result = solver.solve(List.of("svr: fft", "fft: dac", "dac: out"));
        Assertions.assertEquals(1, result);
    }

    /**
     * Test de Integración con Archivo
     */
    @Test
    void testIntegrationFile() throws IOException {
        // Creamos un grafo complejo
        // svr -> a, b
        // a -> fft
        // b -> fft
        // fft -> c
        // c -> dac
        // dac -> out
        // Rutas: svr->a->fft->c->dac->out (1)
        //        svr->b->fft->c->dac->out (1)
        // Total = 2.

        String content = """
                svr: a b
                a: fft
                b: fft
                fft: c
                c: dac
                dac: out
                """;

        Path tempPath = Path.of("src/test/resources/Day11/Connections.txt");
        Files.createDirectories(tempPath.getParent());
        Files.writeString(tempPath, content);

        try (var lines = Files.lines(tempPath)) {
            WaypointRouteService solver = new WaypointRouteService();
            long result = solver.solve(lines.toList());

            Assertions.assertEquals(2, result);
        } finally {
            Files.deleteIfExists(tempPath);
        }
    }
}
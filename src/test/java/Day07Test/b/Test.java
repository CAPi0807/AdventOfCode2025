package Day07Test.a; // Mismo paquete de test solicitado

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.aoc.day07.b.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class Day07BTest {

    @Test
    void testSimplePathNoSplits() {
        // .S.
        // ...
        // ...
        // 1 camino baja recto. Total 1.
        List<String> lines = List.of(".S.", "...", "...");
        Grid grid = new Grid(lines);
        PathCalculatorService service = new PathCalculatorService(new InteractionRule());

        Assertions.assertEquals(1, service.calculateTotalPaths(grid));
    }

    @Test
    void testSingleSplitDoublesPaths() {
        // .S. (1 camino)
        // .^. (Se divide en 2)
        // ... (Bajan 2 caminos separados)
        List<String> lines = List.of(
                ".S.",
                ".^.",
                "..."
        );
        Grid grid = new Grid(lines);
        PathCalculatorService service = new PathCalculatorService(new InteractionRule());

        Assertions.assertEquals(2, service.calculateTotalPaths(grid));
    }

    @Test
    void testDiamondConvergence() {
        // Este es el test crítico para la lógica delicada.
        // Fila 0:  . S .   -> Estado: {1: 1} (Col 1 tiene 1 camino)
        // Fila 1:  . ^ .   -> El ^ está en col 1.
        //                     Divide a col 0 y col 2.
        //                     Estado siguiente (Fila 2): {0: 1, 2: 1}
        // Fila 2:  . . .   -> En col 0 y 2 hay '.', bajan recto.
        //
        // Vamos a forzar una convergencia real.
        //   S
        //   ^   (División -> L, R)
        //  ^ ^  (División de nuevo)

        // Mapa estilo Triángulo de Pascal:
        //    S      (1)
        //    ^      (1 -> Split -> 1, 1)
        //   . .     (Bajan 1 y 1)

        // Convergencia forzada:
        //  S    (Col 1) -> 1
        //  ^    (Col 1) -> Split a 0 y 2. (Caminos: 1 en col 0, 1 en col 2)
        // ^ ^
        // Imaginemos un mapa que fuerza a unirse.
        // Es difícil forzar unión con solo offsets -1, +1 en un grid cuadrado sin paredes diagonales,
        // pero podemos probar la explosión exponencial.

        //   S
        //   ^
        //  ^ ^
        // ^ ^ ^
        // 1 -> 2 -> 4 -> 8.

        List<String> lines = List.of(
                "..S..", // 1
                "..^..", // Split -> 2
                ".^.^.", // Split x2 -> 4
                "....."  // 4 caminos finales
        );

        Grid grid = new Grid(lines);
        PathCalculatorService service = new PathCalculatorService(new InteractionRule());

        Assertions.assertEquals(4, service.calculateTotalPaths(grid));
    }

    @Test
    void testOutOfBoundsAreLost() {
        // S en el borde, ^ manda uno fuera y otro dentro.
        // S
        // ^
        // El de la izquierda cae fuera (-1). El de la derecha sigue (1).
        // Total esperado: 1.
        List<String> lines = List.of(
                "S.",
                "^.",
                ".."
        );
        Grid grid = new Grid(lines);
        PathCalculatorService service = new PathCalculatorService(new InteractionRule());

        Assertions.assertEquals(1, service.calculateTotalPaths(grid));
    }

    @Test
    void testIntegrationFromFile() throws IOException {
        Path path = Path.of("src/test/resources/Day07/Input.txt");
        List<String> lines = Files.readAllLines(path);

        Grid grid = new Grid(lines);
        PathCalculatorService service = new PathCalculatorService(new InteractionRule());

        long result = service.calculateTotalPaths(grid);


        System.out.println("Integration Result Day07 B: " + result);
        Assertions.assertEquals(40, result);
    }
}
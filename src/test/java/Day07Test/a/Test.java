package Day07Test.a;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.aoc.day07.a.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class Day07Test {

    @Test
    void testPhysicsSplit() {
        RayPhysics physics = new RayPhysics();
        InteractionResult result = physics.interact('^');

        Assertions.assertTrue(result.isSplit());
        Assertions.assertEquals(List.of(-1, 1), result.nextColumnOffsets());
    }

    @Test
    void testPhysicsPassThrough() {
        RayPhysics physics = new RayPhysics();

        // Caso 'S'
        InteractionResult resS = physics.interact('S');
        Assertions.assertFalse(resS.isSplit());
        Assertions.assertEquals(List.of(0), resS.nextColumnOffsets());

        // Caso '.'
        InteractionResult resDot = physics.interact('.');
        Assertions.assertFalse(resDot.isSplit());
    }

    @Test
    void testGridFindStart() {
        List<String> lines = List.of(
                "..^..",
                ".S..."
        );
        Grid grid = new Grid(lines);

        // 'S' está en índice 1 de la segunda fila. findStartColumn busca la S globalmente
        Assertions.assertEquals(1, grid.findStartColumn());
    }

    @Test
    void testSimulationSimpleSplit() {
        // Mapa:
        // .S.  (Row 0: S -> Next {1})
        // .^.  (Row 1: ^ en col 1 -> Split. Count=1. Next {0, 2})
        // ...  (Row 2: . en 0, . en 2 -> Pass. Next {0, 2})

        List<String> lines = List.of(
                ".S.",
                ".^.",
                "..."
        );
        Grid grid = new Grid(lines);
        BeamSimulator simulator = new BeamSimulator(grid, new RayPhysics());

        long splits = simulator.simulateAndCountSplits();

        Assertions.assertEquals(1, splits);
    }

    @Test
    void testSimulationCascade() {
        // Cascada:
        //  S    (0)
        //  ^    (1) -> Split en col 2. Next {1, 3}
        // ^ ^   (2) -> ^ en 1 (Split), ^ en 3 (Split). Count += 2.
        // ....
        // Total splits = 1 (fila 1) + 2 (fila 2) = 3.

        List<String> lines = List.of(
                "..S..",
                "..^..",
                ".^.^."
        );

        Grid grid = new Grid(lines);
        BeamSimulator simulator = new BeamSimulator(grid, new RayPhysics());

        Assertions.assertEquals(3, simulator.simulateAndCountSplits());
    }

    @Test
    void testIntegrationFromFile() throws IOException {
        Path path = Path.of("src/test/resources/Day07/Input.txt");
        List<String> lines = Files.readAllLines(path);

        Grid grid = new Grid(lines);
        BeamSimulator simulator = new BeamSimulator(grid, new RayPhysics());

        long result = simulator.simulateAndCountSplits();

        System.out.println("Integration Result Day07: " + result);
        Assertions.assertEquals(21, result);
    }
}
package Day04Test.b;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.aoc.day04.a.Position;
import software.aoc.day04.b.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class Day04BTest {

    @Test
    void testGridImmutability() {
        // Verificar que 'without' devuelve una instancia nueva y no toca la vieja
        List<String> input = List.of("@");
        Grid original = new Grid(input);

        Grid modified = original.without(List.of(new Position(0,0)));

        Assertions.assertEquals('@', original.get(new Position(0,0))); // Original intacto
        Assertions.assertEquals('.', modified.get(new Position(0,0))); // Nuevo modificado
    }

    @Test
    void testSingleStepRemoval() {
        // @ @ @
        // @ @ @
        // @ @ @
        // Las esquinas tienen 3 vecinos -> Se eliminan.
        // Los centros de borde tienen 5 -> Se quedan.
        // El centro tiene 8 -> Se queda.
        List<String> input = List.of("@@@", "@@@", "@@@");
        Grid grid = new Grid(input);
        SelectionRule rule = new UnstableRollRule();

        List<Position> matches = rule.findMatches(grid);

        // Esperamos 4 esquinas eliminadas: (0,0), (0,2), (2,0), (2,2)
        Assertions.assertEquals(4, matches.size());
        Assertions.assertTrue(matches.contains(new Position(0,0)));
    }

    @Test
    void testSimulationCollapse() {
        // Escenario que colapsa totalmente en varios pasos:
        // Paso 0:
        // @@@
        // @.@ (Centro vacío para reducir vecinos de los bordes)
        // @@@

        // (0,0) vecinos: (0,1), (1,0). Total 2 (<4) -> Muere
        // (0,1) vecinos: (0,0), (0,2), (1,0), (1,2). Total 4 (>=4) -> VIVE (por ahora)

        List<String> input = List.of(
                "@@@",
                "@.@",
                "@@@"
        );

        Grid grid = new Grid(input);
        WarehouseSimulator simulator = new WarehouseSimulator(new UnstableRollRule());

        SimulationResult result = simulator.runUntilStable(grid);

        // En este config, las esquinas mueren primero. Eso deja expuestos a los lados.
        // Al final todos deberían morir porque pierden soporte.
        Assertions.assertEquals(8, result.removedCount());

        // Verificamos que todo esté vacío
        result.finalGrid().streamAllPositions().forEach(p ->
                Assertions.assertEquals('.', result.finalGrid().get(p))
        );
    }

    @Test
    void testIntegrationFromFile() throws IOException {
        Path path = Path.of("src/test/resources/Day04/Rolls.txt");
        List<String> lines = Files.readAllLines(path);

        Grid grid = new Grid(lines);
        WarehouseSimulator simulator = new WarehouseSimulator(new UnstableRollRule());

        SimulationResult result = simulator.runUntilStable(grid);

        System.out.println("Integration Result Day04 B: " + result.removedCount());
        Assertions.assertEquals(43, result.removedCount());
    }
}
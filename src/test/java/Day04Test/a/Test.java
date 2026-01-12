package Day04Test.a;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.aoc.day04.a.model.Grid;
import software.aoc.day04.a.model.GridParser;
import software.aoc.day04.a.model.Position;
import software.aoc.day04.a.rule.RollSelectionRule;
import software.aoc.day04.a.model.SelectionRule;
import software.aoc.day04.a.service.WarehouseService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class Day04Test {

    @Test
    void testPositionNeighbors() {
        Position p = new Position(5, 5);
        List<Position> neighbors = p.neighbors();

        Assertions.assertEquals(8, neighbors.size());
        // Verificar que contiene (4,4) y (6,6)
        Assertions.assertTrue(neighbors.contains(new Position(4, 4)));
        Assertions.assertTrue(neighbors.contains(new Position(6, 6)));
        // Verificar que NO se contiene a sí mismo
        Assertions.assertFalse(neighbors.contains(p));
    }

    @Test
    void testRuleIsolatedRoll() {
        // Grid 3x3 con un solo rollo en el centro
        List<String> input = List.of(
                "...",
                ".@.",
                "..."
        );
        Grid grid = GridParser.parse(input);
        SelectionRule rule = new RollSelectionRule();

        // El centro tiene 0 vecinos, es seleccionable
        Assertions.assertTrue(rule.matches(grid, new Position(1, 1)));
    }

    @Test
    void testRuleCrowdedRoll() {
        // Grid 3x3 donde el centro está rodeado por 4 rollos (Cruz)
        // . @ .
        // @ @ @
        // . @ .
        List<String> input = List.of(
                ".@.",
                "@@@",
                ".@."
        );
        Grid grid = GridParser.parse(input);
        SelectionRule rule = new RollSelectionRule();

        Position center = new Position(1, 1);

        // El centro tiene 4 vecinos ('@' arriba, abajo, izq, der).
        // La regla dice < 4. Por tanto, 4 NO es seleccionable.
        Assertions.assertFalse(rule.matches(grid, center));
    }

    @Test
    void testRuleEmptySpace() {
        List<String> input = List.of("...");
        Grid grid = GridParser.parse(input);
        SelectionRule rule = new RollSelectionRule();

        // Un espacio vacío nunca es seleccionable
        Assertions.assertFalse(rule.matches(grid, new Position(0, 0)));
    }

    @Test
    void testServiceCount() {
        // Grid mixto:
        // @ @  (2 vecinos c/u -> OK)
        // . .
        List<String> input = List.of(
                "@@",
                ".."
        );
        Grid grid = GridParser.parse(input);
        SelectionRule rule = new RollSelectionRule();
        WarehouseService service = new WarehouseService(rule);

        // Los dos @ tienen 1 vecino cada uno. Ambos < 4. Total 2.
        Assertions.assertEquals(2, service.countSafeRolls(grid));
    }

    @Test
    void testIntegrationFromFile() throws IOException {
        Path path = Path.of("src/test/resources/Day04/Rolls.txt");
        // Asegúrate de crear este archivo con contenido de prueba
        List<String> lines = Files.readAllLines(path);

        Grid grid = GridParser.parse(lines);
        WarehouseService service = new WarehouseService(new RollSelectionRule());

        long result = service.countSafeRolls(grid);

        System.out.println("Integration Result Day04: " + result);
        Assertions.assertEquals(13, result);
    }
}
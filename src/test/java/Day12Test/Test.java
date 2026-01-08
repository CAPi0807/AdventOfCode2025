package Day12Test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.aoc.day12.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

class Day12Test {

    /**
     * Test Unidad: Geometría de Shape
     * Verifica normalización y dimensiones.
     */
    @Test
    void testShapeNormalization() {
        // Figura ".." en coordenadas (10,10) y (10,11)
        Set<Coordinate> raw = Set.of(
                new Coordinate(10, 10),
                new Coordinate(10, 11)
        );
        Shape shape = Shape.create(1, raw);

        // Debe normalizarse a (0,0) y (0,1)
        Set<Coordinate> expected = Set.of(
                new Coordinate(0, 0),
                new Coordinate(0, 1)
        );

        Assertions.assertEquals(expected, shape.getCells());
        Assertions.assertEquals(1, shape.getHeight());
        Assertions.assertEquals(2, shape.getWidth());
    }

    /**
     * Test Unidad: Rotación
     * Verifica que una barra horizontal se convierta en vertical.
     */
    @Test
    void testRotation() {
        // Barra horizontal: ##
        Shape bar = Shape.create(1, Set.of(new Coordinate(0, 0), new Coordinate(0, 1)));

        List<Shape> rotations = bar.getUniqueRotations();

        // Una barra tiene 2 estados únicos: Horizontal y Vertical.
        // (La rotación 180 es igual a la 0, la 270 igual a la 90)
        Assertions.assertEquals(2, rotations.size());

        Shape vertical = rotations.stream().filter(s -> s.getHeight() == 2).findFirst().orElseThrow();
        Assertions.assertEquals(1, vertical.getWidth());
    }

    /**
     * Test Unidad: Grid Collision
     */
    @Test
    void testGridCollision() {
        Grid grid = new Grid(2, 2);
        Shape dot = Shape.create(1, Set.of(new Coordinate(0, 0))); // 1x1

        // Colocar en 0,0
        Assertions.assertTrue(grid.canPlace(dot, 0, 0));
        grid.place(dot, 0, 0);

        // Intentar colocar otra vez en 0,0 -> Colisión
        Assertions.assertFalse(grid.canPlace(dot, 0, 0));

        // Colocar en 0,1 -> OK
        Assertions.assertTrue(grid.canPlace(dot, 0, 1));
    }

    /**
     * Test Algoritmo: Solver Simple
     * Tablero 2x2. Llenar con 4 cuadrados 1x1.
     */
    @Test
    void testSolverTrivialFit() {
        Shape dot = Shape.create(1, Set.of(new Coordinate(0, 0)));
        List<Shape> shapes = List.of(dot, dot, dot, dot); // 4 puntos

        PuzzleSolver solver = new PuzzleSolver();
        // Cabe en 2x2
        Assertions.assertTrue(solver.canFitAll(2, 2, shapes));
        // No cabe en 2x1 (Área insuficiente)
        Assertions.assertFalse(solver.canFitAll(2, 1, shapes));
    }

    /**
     * Test Algoritmo: Puzzle Tetris
     * Tablero 3x3.
     * Pieza L (tamaño 3)
     * Pieza I (tamaño 3)
     * Pieza I (tamaño 3)
     * Total área 9.
     */
    @Test
    void testSolverTetrisLike() {
        // L shape:
        // #
        // ##
        Shape L = Shape.create(1, Set.of(new Coordinate(0,0), new Coordinate(1,0), new Coordinate(1,1)));

        // I shape (length 3): ###
        Shape I = Shape.create(2, Set.of(new Coordinate(0,0), new Coordinate(0,1), new Coordinate(0,2)));

        // Solver
        PuzzleSolver solver = new PuzzleSolver();

        // Caso: 3x3 con 3 "I"s (###, ###, ###). Debe caber apiladas.
        Assertions.assertTrue(solver.canFitAll(3, 3, List.of(I, I, I)));

        // Caso: 2x3 con 2 "L"s.
        // L1: (0,0)(1,0)(1,1). L2 (rotada 180): (0,1)(0,2)(1,2)?
        // ## #
        // # ##
        // Sí, dos L de 3 bloques forman un rectángulo 2x3.
        Assertions.assertTrue(solver.canFitAll(2, 3, List.of(L, L)));
    }

    /**
     * Test Integración: Parser + Solver con Archivo Temporal
     */
    @Test
    void testIntegration() throws IOException {
        String content = """
            0:
            #
            
            1:
            ##
            
            # Test Case: 2x1 con pieza 1 (##)
            2x1: 0 1
            
            # Test Case: 1x1 con pieza 0 (#)
            1x1: 1 0
            
            # Test Case: 1x1 con pieza 1 (##) -> Fallo por área
            1x1: 0 1
            """;

        Path tempPath = Path.of("src/test/resources/Day12/Presents_Test.txt");
        Files.createDirectories(tempPath.getParent());
        Files.writeString(tempPath, content);

        try {
            InputParser parser = new InputParser();
            InputParser.InputData data = parser.parse(tempPath.toString());
            PuzzleSolver solver = new PuzzleSolver();

            List<InputParser.TestCase> cases = data.testCases();
            Assertions.assertEquals(3, cases.size());

            // Caso 1: 2x1 con una barra (##). OK.
            Assertions.assertTrue(solver.canFitAll(cases.get(0).rows(), cases.get(0).cols(), cases.get(0).shapesToFit()));

            // Caso 2: 1x1 con un punto (#). OK.
            Assertions.assertTrue(solver.canFitAll(cases.get(1).rows(), cases.get(1).cols(), cases.get(1).shapesToFit()));

            // Caso 3: 1x1 con una barra (##). FALLO.
            Assertions.assertFalse(solver.canFitAll(cases.get(2).rows(), cases.get(2).cols(), cases.get(2).shapesToFit()));

        } finally {
            Files.deleteIfExists(tempPath);
        }
    }
}
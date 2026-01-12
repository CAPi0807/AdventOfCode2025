package Day09Test.b; // Mismo paquete de test

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.aoc.day09.a.model.BoundingBox;
import software.aoc.day09.a.model.Point;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import software.aoc.day09.a.parser.PointParser; // Necesario importar parser de A
import software.aoc.day09.b.model.CompressedGrid;
import software.aoc.day09.b.model.RayCaster;
import software.aoc.day09.b.service.ConstrainedSolver;

class Day09BTest {

    @Test
    void testRayCasterSimpleSquare() {
        RayCaster caster = new RayCaster();
        List<Point> square = List.of(
                new Point(0, 0), new Point(10, 0), new Point(10, 10), new Point(0, 10));

        Assertions.assertTrue(caster.isPointInside(5, 5, square)); // Centro
        Assertions.assertFalse(caster.isPointInside(15, 5, square)); // Fuera
        Assertions.assertFalse(caster.isPointInside(5, 15, square)); // Fuera
    }

    @Test
    void testCompressedGridLShape() {
        // Forma de L:
        // (0,10) -- (5,10)
        // | |
        // | (5,5) -- (10,5)
        // | |
        // (0,0) ------------ (10,0)
        List<Point> lShape = List.of(
                new Point(0, 0), new Point(10, 0), new Point(10, 5),
                new Point(5, 5), new Point(5, 10), new Point(0, 10));

        RayCaster caster = new RayCaster();
        CompressedGrid grid = new CompressedGrid(lShape, caster);

        // Rectángulo válido en la parte inferior ancha
        BoundingBox validBottom = new BoundingBox(new Point(0, 0), new Point(10, 5));
        Assertions.assertTrue(grid.fullyCovers(validBottom));

        // Rectángulo válido en la parte vertical izquierda
        BoundingBox validLeft = new BoundingBox(new Point(0, 0), new Point(5, 10));
        Assertions.assertTrue(grid.fullyCovers(validLeft));

        // Rectángulo inválido (ocupa el hueco de la L en 5,5 -> 10,10)
        BoundingBox invalid = new BoundingBox(new Point(0, 0), new Point(10, 10));
        Assertions.assertFalse(grid.fullyCovers(invalid));
    }

    @Test
    void testIntegrationFromFile() throws IOException {
        PointParser parser = new PointParser();
        Path path = Path.of("src/test/resources/Day09/Points.txt");
        List<Point> points = parser.parse(Files.readAllLines(path));

        ConstrainedSolver solver = new ConstrainedSolver();
        BoundingBox result = solver.solve(points);

        System.out.println("Integration Result B: " + result.calculateInclusiveArea());
        Assertions.assertEquals(24, result.calculateInclusiveArea());
    }
}
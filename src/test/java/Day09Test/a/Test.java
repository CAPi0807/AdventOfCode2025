package Day09Test.a;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.aoc.day09.a.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class Day09ATest {

    @Test
    void testBoundingBoxInclusiveArea() {
        // Caso: Puntos en la misma posición (un solo pixel)
        // Distancia 0, pero área 1x1 = 1
        BoundingBox boxSame = new BoundingBox(new Point(0,0), new Point(0,0));
        Assertions.assertEquals(1, boxSame.calculateInclusiveArea());

        // Caso: Rectángulo 2x2
        // (0,0) a (1,1). Delta X = 1, Delta Y = 1.
        // Width = 1+1=2. Height = 1+1=2. Area = 4.
        BoundingBox box2x2 = new BoundingBox(new Point(0,0), new Point(1,1));
        Assertions.assertEquals(4, box2x2.calculateInclusiveArea());

        // Caso: Coordenadas negativas
        // (-1, -1) a (1, 1). Delta = 2. Size = 3. Area = 9.
        BoundingBox boxNeg = new BoundingBox(new Point(-1,-1), new Point(1,1));
        Assertions.assertEquals(9, boxNeg.calculateInclusiveArea());
    }

    @Test
    void testBruteForceStrategy() {
        AreaStrategy strategy = new BruteForcePairsStrategy();

        List<Point> points = List.of(
                new Point(0, 0),
                new Point(2, 2),  // Con (0,0) forma área (2+1)*(2+1) = 9
                new Point(0, 10)  // Con (0,0) forma área (0+1)*(10+1) = 11
                // Con (2,2) forma área (2+1)*(8+1) = 3*9 = 27 (Ganador)
        );

        long maxArea = strategy.findMaxArea(points);
        Assertions.assertEquals(27, maxArea);
    }

    @Test
    void testIntegrationFromFile() throws IOException {
        Path path = Path.of("src/test/resources/Day09/Points.txt");
        List<String> lines = Files.readAllLines(path);

        PointParser parser = new PointParser();
        List<Point> points = parser.parse(lines);

        GeometryService service = new GeometryService(new BruteForcePairsStrategy());
        long result = service.calculateLargestRectangle(points);

        // Basado en el archivo de recursos de abajo:
        // Puntos extremos: (0,0) y (10,10) -> Area 11*11 = 121.
        System.out.println("Integration Result Day09: " + result);
        Assertions.assertEquals(50, result);
    }
}
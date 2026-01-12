package Day08Test.b; // Mismo paquete de test solicitado

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.aoc.day08.a.parser.CoordinateParser;
import software.aoc.day08.a.strategies.EuclideanStrategy;
import software.aoc.day08.a.model.Point3D;
import software.aoc.day08.b.service.CircuitCompletionService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class Day08BTest {

    @Test
    void testSimpleLineConnection() {
        // A(1,0,0) --- B(2,0,0) --- C(3,0,0)
        // 1. A-B (dist 1) -> Se unen. Quedan 2 grupos {A,B} y {C}.
        // 2. B-C (dist 1) -> Se unen. Queda 1 grupo {A,B,C}. Fin.
        // Última conexión: B y C. Resultado: B.x * C.x = 2 * 3 = 6.

        List<Point3D> points = List.of(
                new Point3D(1, 0, 0),
                new Point3D(2, 0, 0),
                new Point3D(3, 0, 0)
        );

        CircuitCompletionService service = new CircuitCompletionService(new EuclideanStrategy());
        long result = service.findCriticalConnectionValue(points);

        Assertions.assertEquals(6, result);
    }

    @Test
    void testTwoClustersMerging() {
        // Cluster 1: A(10,0,0), B(11,0,0) -> Se unen rápido.
        // Cluster 2: C(20,0,0), D(21,0,0) -> Se unen rápido.
        // Último paso: Unir Cluster 1 con Cluster 2.
        // Arista más corta entre clusters: B(11) y C(20). Distancia 9^2 = 81.
        // Resultado: 11 * 20 = 220.

        List<Point3D> points = List.of(
                new Point3D(10, 0, 0),
                new Point3D(11, 0, 0),
                new Point3D(20, 0, 0),
                new Point3D(21, 0, 0)
        );

        CircuitCompletionService service = new CircuitCompletionService(new EuclideanStrategy());
        long result = service.findCriticalConnectionValue(points);

        Assertions.assertEquals(220, result);
    }

    @Test
    void testIntegrationFromFile() throws IOException {
        Path path = Path.of("src/test/resources/Day08/Coordinates.txt");
        List<String> lines = Files.readAllLines(path);

        CoordinateParser parser = new CoordinateParser();
        List<Point3D> points = parser.parse(lines);

        CircuitCompletionService service = new CircuitCompletionService(new EuclideanStrategy());
        long result = service.findCriticalConnectionValue(points);


        System.out.println("Integration Result Day08 B: " + result);
        Assertions.assertEquals(25272, result);
    }
}
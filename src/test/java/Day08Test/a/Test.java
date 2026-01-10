package Day08Test.a;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.aoc.day08.a.model.DisjointSet;
import software.aoc.day08.a.model.DistanceStrategy;
import software.aoc.day08.a.model.Point3D;
import software.aoc.day08.a.parser.CoordinateParser;
import software.aoc.day08.a.service.NetworkService;
import software.aoc.day08.a.strategies.EuclideanStrategy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class Day08ATest {

    @Test
    void testEuclideanDistance() {
        DistanceStrategy strategy = new EuclideanStrategy();
        Point3D p1 = new Point3D(0, 0, 0);
        Point3D p2 = new Point3D(3, 4, 0); // Triángulo 3-4-5

        // Distancia euclidiana normal = 5. Al cuadrado = 25.
        Assertions.assertEquals(25.0, strategy.calculate(p1, p2));
    }

    @Test
    void testDisjointSetLogic() {
        DisjointSet dsu = new DisjointSet(5);

        // Unir 0 y 1 -> Tamaño 2
        dsu.union(0, 1);
        Assertions.assertEquals(2, dsu.getComponentSizes().stream().max(Integer::compareTo).get());

        // Unir 2 y 3 -> Tamaño 2
        dsu.union(2, 3);

        // Unir (0,1) con (2,3) -> Tamaño 4
        dsu.union(1, 2);

        List<Integer> sizes = dsu.getComponentSizes();
        // Deberíamos tener un componente de tamaño 4 y uno de tamaño 1 (el nodo 4 que quedó solo)
        Assertions.assertTrue(sizes.contains(4));
        Assertions.assertTrue(sizes.contains(1));
    }

    @Test
    void testNetworkLogicSmallScale() {
        // Crear 4 puntos: 3 muy cerca entre sí y 1 muy lejos.
        // A(0,0,0), B(1,0,0), C(2,0,0) -> Muy cerca.
        // D(100,0,0) -> Lejos.

        List<Point3D> points = List.of(
                new Point3D(0,0,0),
                new Point3D(1,0,0),
                new Point3D(2,0,0),
                new Point3D(100,0,0)
        );

        // Si limitamos a 2 conexiones:
        // 1. A-B (dist 1) -> Se unen.
        // 2. B-C (dist 1) -> Se unen.
        // El D queda solo porque su conexión más cercana (C-D) es dist 98^2, y ya gastamos las 2 conexiones.
        // Resultado: Grupo {A,B,C} size 3. Grupo {D} size 1.
        // Score: 3 * 1 = 3.

        NetworkService service = new NetworkService(new EuclideanStrategy());
        long score = service.calculateCircuitScore(points, 2);

        Assertions.assertEquals(3, score);
    }

    @Test
    void testIntegrationFromFile() throws IOException {
        Path path = Path.of("src/test/resources/Day08/Coordinates.txt");
        List<String> lines = Files.readAllLines(path);

        CoordinateParser parser = new CoordinateParser();
        List<Point3D> points = parser.parse(lines);

        NetworkService service = new NetworkService(new EuclideanStrategy());
        long result = service.calculateCircuitScore(points, 10);

        System.out.println("Integration Result Day08: " + result);
        Assertions.assertEquals(40, result);
    }
}
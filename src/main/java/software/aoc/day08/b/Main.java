package software.aoc.day08.b;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import software.aoc.day08.a.EuclideanStrategy;
import software.aoc.day08.a.Point3D;
import software.aoc.day08.a.CoordinateParser;

public class Main {

    public static void main(String[] args) throws IOException {
        Path inputPath = Path.of("src/main/resources/Day08/Coordinates.txt");
        List<String> lines = Files.readAllLines(inputPath);

        CoordinateParser parser = new CoordinateParser();
        List<Point3D> points = parser.parse(lines);

        CircuitCompletionService service = new CircuitCompletionService(new EuclideanStrategy());
        long result = service.findCriticalConnectionValue(points);

        System.out.println("Resultado (X1 * X2 de la última conexión): " + result);
    }
}
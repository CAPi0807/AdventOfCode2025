package software.aoc.day08.a;

import software.aoc.day08.a.model.Point3D;
import software.aoc.day08.a.parser.CoordinateParser;
import software.aoc.day08.a.service.NetworkService;
import software.aoc.day08.a.strategies.EuclideanStrategy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    private static final int MAX_CONNECTIONS = 1000;

    public static void main(String[] args) throws IOException {

        Path inputPath = Path.of("src/main/resources/Day08/Coordinates.txt");
        List<String> lines = Files.readAllLines(inputPath);

        CoordinateParser parser = new CoordinateParser();
        List<Point3D> points = parser.parse(lines);

        NetworkService service = new NetworkService(new EuclideanStrategy());
        long result = service.calculateCircuitScore(points, MAX_CONNECTIONS);

        System.out.println("El tamaño multiplicado de los 3 circuitos más grandes es: " + result);
    }
}
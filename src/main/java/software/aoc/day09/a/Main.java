package software.aoc.day09.a;

import software.aoc.day09.a.model.Point;
import software.aoc.day09.a.parser.PointParser;
import software.aoc.day09.a.service.GeometryService;
import software.aoc.day09.a.strategy.BruteForcePairsStrategy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        Path path = Path.of("src/main/resources/Day09/Points.txt");
        List<String> lines = Files.readAllLines(path);

        PointParser parser = new PointParser();
        List<Point> points = parser.parse(lines);

        GeometryService service = new GeometryService(new BruteForcePairsStrategy());
        long maxArea = service.calculateLargestRectangle(points);

        System.out.println("El área del rectángulo más grande es: " + maxArea);
    }
}
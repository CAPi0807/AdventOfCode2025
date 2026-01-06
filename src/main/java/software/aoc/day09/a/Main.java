package software.aoc.day09.a;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        // 1. IO
        Path path = Path.of("src/main/resources/Day09/Points.txt");
        List<String> lines = Files.readAllLines(path);

        // 2. Parsing
        PointParser parser = new PointParser();
        List<Point> points = parser.parse(lines);

        // 3. Lógica (Inyección de la estrategia de fuerza bruta)
        GeometryService service = new GeometryService(new BruteForcePairsStrategy());
        long maxArea = service.calculateLargestRectangle(points);

        // 4. Salida
        System.out.println("El área del rectángulo más grande es: " + maxArea);
    }
}
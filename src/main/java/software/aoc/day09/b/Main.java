package software.aoc.day09.b;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        InputParser parser = new InputParser();
        RectangleSolver solver = new RectangleSolver();

        List<String> lines = Files.readAllLines(Path.of("src/main/resources/Day09/Points.txt"));
        List<Point> points = parser.parse(lines);

        long start = System.currentTimeMillis();

        // Obtenemos el objeto Rectangle en lugar de solo el long
        Rectangle result = solver.solve(points);

        long time = System.currentTimeMillis() - start;

        System.out.println("--- RESULTADOS ---");
        System.out.println("Área máxima: " + result.area());
        System.out.printf("Esquinas elegidas: %s y %s%n", result.p1(), result.p2());
        System.out.println("Tiempo cálculo: " + time + "ms");
    }
}
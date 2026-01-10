package software.aoc.day09.b;

import software.aoc.day09.a.model.BoundingBox;
import software.aoc.day09.a.model.Point;
import software.aoc.day09.a.parser.PointParser;
import software.aoc.day09.b.service.ConstrainedSolver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        // 1. Reutilización de Parser de Parte A
        PointParser parser = new PointParser();
        List<String> lines = Files.readAllLines(Path.of("src/main/resources/Day09/Points.txt"));
        List<Point> points = parser.parse(lines);

        // 2. Lógica Parte B
        ConstrainedSolver solver = new ConstrainedSolver();

        long start = System.currentTimeMillis();
        BoundingBox result = solver.solve(points);
        long time = System.currentTimeMillis() - start;

        // 3. Salida
        System.out.println("--- RESULTADOS ---");
        System.out.println("Área máxima: " + result.calculateInclusiveArea());
        System.out.printf("Esquinas: %s y %s%n", result.p1(), result.p2());
        System.out.println("Tiempo cálculo: " + time + "ms");
    }
}
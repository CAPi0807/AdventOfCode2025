package software.aoc.day09.a;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        List<String> lines = Files.readAllLines(
                Path.of("src/main/resources/Day09/Points.txt")
        );

        RectangleSolver solver = new RectangleSolver();
        long maxArea = solver.findLargestRectangleArea(lines);

        System.out.println("El área del rectángulo más grande es: " + maxArea);
    }
}
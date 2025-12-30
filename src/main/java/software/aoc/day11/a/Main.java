package software.aoc.day11.a;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        Path path = Path.of("src/main/resources/Day11/Connections.txt");

        // Lectura segura
        try (var stream = Files.lines(path)) {
            List<String> lines = stream.toList();

            Day11Solver solver = new Day11Solver();
            long totalPaths = solver.solve(lines);

            System.out.println("--- DÍA 11 ---");
            System.out.println("Total de caminos desde 'you' hasta 'out': " + totalPaths);
        }
    }
}
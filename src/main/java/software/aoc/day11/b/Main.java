package software.aoc.day11.b;

import software.aoc.day11.b.service.WaypointRouteService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        Path path = Path.of("src/main/resources/Day11/Connections.txt");

        try (var linesStream = Files.lines(path)) {
            List<String> lines = linesStream.toList();

            WaypointRouteService solver = new WaypointRouteService();
            long result = solver.solve(lines);

            System.out.println("--- DÍA 11 PARTE B ---");
            System.out.printf("Caminos desde '%s' hasta '%s' pasando por 'fft' y 'dac': %d%n",
                    "srv", "out", result);
        }
    }
}
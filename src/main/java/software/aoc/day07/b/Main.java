package software.aoc.day07.b;

import software.aoc.day07.b.model.Grid;
import software.aoc.day07.b.physics.InteractionRule;
import software.aoc.day07.b.services.PathCalculatorService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        // 1. IO
        Path inputPath = Path.of("src/main/resources/Day07/Input.txt");
        List<String> lines = Files.readAllLines(inputPath);

        // 2. Wiring
        Grid grid = new Grid(lines);
        InteractionRule physics = new InteractionRule();
        PathCalculatorService calculator = new PathCalculatorService(physics);

        // 3. Ejecución
        long totalPaths = calculator.calculateTotalPaths(grid);

        // 4. Salida
        System.out.println("Total de caminos posibles: " + totalPaths);
    }
}
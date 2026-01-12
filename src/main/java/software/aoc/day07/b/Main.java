package software.aoc.day07.b;

import software.aoc.day07.b.physics.InteractionRule;
import software.aoc.day07.b.services.PathCalculatorService;
import software.aoc.day07.a.model.Grid;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        Path inputPath = Path.of("src/main/resources/Day07/Input.txt");
        List<String> lines = Files.readAllLines(inputPath);

        Grid grid = new Grid(lines);
        InteractionRule physics = new InteractionRule();
        PathCalculatorService calculator = new PathCalculatorService(physics);

        long totalPaths = calculator.calculateTotalPaths(grid);

        System.out.println("Total de caminos posibles: " + totalPaths);
    }
}
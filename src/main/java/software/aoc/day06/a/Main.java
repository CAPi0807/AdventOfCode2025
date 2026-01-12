package software.aoc.day06.a;

import software.aoc.day06.a.model.ProblemSchema;
import software.aoc.day06.a.parser.ProblemParser;
import software.aoc.day06.a.service.ColumnCalculationService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        Path path = Path.of("src/main/resources/Day06/Problems.txt");
        List<String> lines = Files.readAllLines(path);

        ProblemParser parser = new ProblemParser();
        ProblemSchema schema = parser.parse(lines);

        ColumnCalculationService solver = new ColumnCalculationService();
        long result = solver.solve(schema);

        System.out.println("Resultado total = " + result);
    }
}
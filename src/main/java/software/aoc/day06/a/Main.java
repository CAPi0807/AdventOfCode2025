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
        // 1. IO
        Path path = Path.of("src/main/resources/Day06/Problems.txt");
        List<String> lines = Files.readAllLines(path);

        // 2. Parsing
        ProblemParser parser = new ProblemParser();
        ProblemSchema schema = parser.parse(lines);

        // 3. Lógica
        ColumnCalculationService solver = new ColumnCalculationService();
        long result = solver.solve(schema);

        // 4. Salida
        System.out.println("Resultado total = " + result);
    }
}
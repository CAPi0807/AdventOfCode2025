package software.aoc.day06.b;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        Path path = Path.of("src/main/resources/Day06/Problems.txt");
        List<String> lines = Files.readAllLines(path);

        ProblemSolver solver = new ProblemSolver();
        long result = solver.solve(lines);

        System.out.println("Resultado total = " + result);
    }
}
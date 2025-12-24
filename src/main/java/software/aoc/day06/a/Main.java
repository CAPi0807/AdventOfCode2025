package software.aoc.day06.a;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        List<String> lines = Files.readAllLines(
                Path.of("src/main/resources/Day06/Problems.txt")
        );

        ProblemSolver solver = new ProblemSolver();
        long result = solver.solve(lines);

        System.out.println("Resultado total = " + result);
    }
}

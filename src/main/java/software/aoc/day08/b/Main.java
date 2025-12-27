package software.aoc.day08.b;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        Path inputPath = Path.of("src/main/resources/Day08/Coordinates.txt");
        List<String> lines = Files.readAllLines(inputPath);

        CircuitSolver solver = new CircuitSolver();
        long result = solver.solveFullCircuit(lines);

        System.out.println("Resultado (X1 * X2 de la última conexión): " + result);
    }
}
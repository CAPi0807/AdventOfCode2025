package software.aoc.day10.b;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        MachineParser parser = new MachineParser();
        Day10BSolver solver = new Day10BSolver();

        Path path = Path.of("src/main/resources/Day10/Machines.txt");

        try (var lines = Files.lines(path)) {
            List<Machine> machines = lines
                    .filter(line -> !line.isBlank())
                    .map(parser::parse)
                    .toList();

            long totalPresses = solver.solveTotalVoltagePresses(machines);

            System.out.println("--- RESULTADOS DÍA 10 PARTE B ---");
            System.out.println("Mínimo de pulsaciones para voltaje: " + totalPresses);
        }
    }
}

//18898 es muy baja

package software.aoc.day10.a;

import software.aoc.day10.a.model.Machine;
import software.aoc.day10.a.parser.MachineParser;
import software.aoc.day10.a.service.Day10Solver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        MachineParser parser = new MachineParser();
        Day10Solver solver = new Day10Solver();

        Path path = Path.of("src/main/resources/Day10/Machines.txt");

        try (var lines = Files.lines(path)) {
            List<Machine> machines = lines
                    .filter(line -> !line.isBlank())
                    .map(parser::parse)
                    .toList();

            long totalPresses = solver.solveTotalPresses(machines);

            System.out.println("--- RESULTADOS DÍA 10 ---");
            System.out.println("Suma total de pulsaciones mínimas: " + totalPresses);
        }
    }
}
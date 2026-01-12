package software.aoc.day04.b;

import software.aoc.day04.a.model.Grid;
import software.aoc.day04.a.parser.GridParser;
import software.aoc.day04.b.model.SelectionRule;
import software.aoc.day04.b.model.SimulationResult;
import software.aoc.day04.b.model.UnstableRollRule;
import software.aoc.day04.b.service.WarehouseSimulator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        Path path = Path.of("src/main/resources/Day04/Rolls.txt");
        List<String> lines = Files.readAllLines(path);

        Grid initialGrid = GridParser.parse(lines);

        SelectionRule rule = new UnstableRollRule();
        WarehouseSimulator simulator = new WarehouseSimulator(rule);

        SimulationResult result = simulator.runUntilStable(initialGrid);

        System.out.println("Rollos eliminados = " + result.removedCount());
        System.out.println("Grid final:");
        result.finalGrid().print().forEach(System.out::println);
    }
}

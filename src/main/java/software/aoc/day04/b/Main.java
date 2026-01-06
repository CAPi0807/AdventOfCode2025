package software.aoc.day04.b;

import software.aoc.day04.a.Grid; // Importamos de A

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        Path path = Path.of("src/main/resources/Day04/Rolls.txt");
        List<String> lines = Files.readAllLines(path);

        // Usamos el Grid unificado del paquete A
        Grid initialGrid = new Grid(lines);

        SelectionRule rule = new UnstableRollRule();
        WarehouseSimulator simulator = new WarehouseSimulator(rule);

        SimulationResult result = simulator.runUntilStable(initialGrid);

        System.out.println("Rollos eliminados = " + result.removedCount());
        System.out.println("Grid final:");
        result.finalGrid().print().forEach(System.out::println);
    }
}
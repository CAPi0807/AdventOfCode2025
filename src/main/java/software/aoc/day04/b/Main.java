package software.aoc.day04.b;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        // 1. Configuración
        Path path = Path.of("src/main/resources/Day04/Rolls.txt");
        List<String> lines = Files.readAllLines(path);

        // 2. Construcción de objetos
        Grid initialGrid = new Grid(lines);
        SelectionRule rule = new UnstableRollRule();
        WarehouseSimulator simulator = new WarehouseSimulator(rule);

        // 3. Ejecución
        SimulationResult result = simulator.runUntilStable(initialGrid);

        // 4. Salida
        System.out.println("Rollos eliminados = " + result.removedCount());
        System.out.println("Grid final:");
        result.finalGrid().print().forEach(System.out::println);
    }
}
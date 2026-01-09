package software.aoc.day07.a;

import software.aoc.day07.a.model.Grid;
import software.aoc.day07.a.physics.RayPhysics;
import software.aoc.day07.a.service.BeamSimulator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        // 1. I/O
        Path inputPath = Path.of("src/main/resources/Day07/Input.txt");
        List<String> lines = Files.readAllLines(inputPath);

        // 2. Wiring (Inyección)
        Grid map = new Grid(lines);
        RayPhysics physics = new RayPhysics();
        BeamSimulator simulator = new BeamSimulator(map, physics);

        // 3. Ejecución
        long totalSplits = simulator.simulateAndCountSplits();

        // 4. Salida
        System.out.println("El rayo se dividió un total de: " + totalSplits + " veces.");
    }
}
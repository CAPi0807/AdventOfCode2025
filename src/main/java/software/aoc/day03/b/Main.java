package software.aoc.day03.b;

import software.aoc.day03.a.model.Battery;
import software.aoc.day03.a.parser.BatteryParser;
import software.aoc.day03.a.service.JoltageService;
import software.aoc.day03.a.model.JoltageStrategy;
import software.aoc.day03.b.strategy.GreedySelectionStrategy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        // 1. Configuración
        BatteryParser parser = new BatteryParser();
        JoltageStrategy strategy = new GreedySelectionStrategy();
        JoltageService service = new JoltageService(strategy);

        // 2. IO
        Path path = Path.of("src/main/resources/Day03/Batteries.txt");
        List<String> rawLines = Files.readAllLines(path);

        // 3. Proceso
        List<Battery> batteries = parser.parse(rawLines);
        long totalJoltage = service.calculateTotalLoad(batteries);

        // 4. Salida
        System.out.println("Total Joltage = " + totalJoltage);
    }
}
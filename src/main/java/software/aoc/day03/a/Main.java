package software.aoc.day03.a;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        // 1. Configuración de dependencias
        BatteryParser parser = new BatteryParser();
        JoltageStrategy strategy = new HighestOrderedPairStrategy();
        JoltageService service = new JoltageService(strategy);

        // 2. Lectura
        Path path = Path.of("src/main/resources/Day03/Batteries.txt");
        List<String> rawLines = Files.readAllLines(path);

        // 3. Transformación
        List<Battery> batteries = parser.parse(rawLines);

        // 4. Ejecución
        Long totalJoltage = service.calculateTotalLoad(batteries);

        // 5. Salida
        System.out.println("Total Joltage = " + totalJoltage);
    }
}
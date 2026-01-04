package software.aoc.day04.a;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        // 1. Lectura
        Path path = Path.of("src/main/resources/Day04/Rolls.txt");
        List<String> lines = Files.readAllLines(path);

        // 2. Construcción de Dominio
        Grid warehouse = new Grid(lines);
        SelectionRule rule = new RollSelectionRule();
        WarehouseService service = new WarehouseService(rule);

        // 3. Ejecución
        long count = service.countSafeRolls(warehouse);

        // 4. Salida
        System.out.println("Rollos seleccionables = " + count);
    }
}
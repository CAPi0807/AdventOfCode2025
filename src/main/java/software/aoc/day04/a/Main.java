package software.aoc.day04.a;

import software.aoc.day04.a.model.Grid;
import software.aoc.day04.a.parser.GridParser;
import software.aoc.day04.a.rule.RollSelectionRule;
import software.aoc.day04.a.model.SelectionRule;
import software.aoc.day04.a.service.WarehouseService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        Path path = Path.of("src/main/resources/Day04/Rolls.txt");
        List<String> lines = Files.readAllLines(path);

        Grid warehouse = GridParser.parse(lines);

        SelectionRule rule = new RollSelectionRule();
        WarehouseService service = new WarehouseService(rule);

        long count = service.countSafeRolls(warehouse);

        System.out.println("Rollos seleccionables = " + count);
    }
}

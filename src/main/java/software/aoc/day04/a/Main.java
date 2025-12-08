package software.aoc.day04.a;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        List<String> grid = Files.readAllLines(
                Path.of("src/main/resources/Day04/Rolls.txt")
        );

        Roll_checker checker = new Roll_checker(grid);

        long selectableRolls = checker.countSelectableRolls();

        System.out.println("Rollos seleccionables = " + selectableRolls);
    }
}

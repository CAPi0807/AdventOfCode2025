package software.aoc.day03.a;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        // 1. Leemos las ristras de números del archivo
        List<String> batteries = Files.readAllLines(
                Path.of("src/main/resources/Day03/Batteries.txt")
        );

        // 2. Calculamos el total de joltage
        int TotalJoltage = Joltage_Meter.JoltajeDeterminator(batteries);

        // 3. Mostramos el resultado
        System.out.println("Total Joltage = " + TotalJoltage);
    }
}

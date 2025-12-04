package software.aoc.day01.b;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        List<String> orders = Files.readAllLines(
                Path.of("src/main/resources/Day01/Orders.txt")
        );

        DialB dial = new DialB(50);

        int password = dial.applyOrdersB(
                orders.stream()
                        .map(String::trim)
                        .filter(l -> !l.isEmpty())
                        .toList()
        );

        System.out.println("Contraseña = " + password);
    }
}

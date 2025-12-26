package software.aoc.day07.b;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        List<String> lines = Files.readAllLines(
                Path.of("src/main/resources/Day07/Input.txt")
        );

        // Reutilizamos el GridMap (Principio DRY - Don't Repeat Yourself)
        GridMap map = new GridMap(lines);

        // Usamos el nuevo PathCounter para la lógica de la parte B
        PathCounter counter = new PathCounter(map);

        long totalPaths = counter.calculateTotalPaths();

        System.out.println("Total de caminos posibles: " + totalPaths);
    }
}
package software.aoc.day05.b;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        List<String> lines = Files.readAllLines(
                Path.of("src/main/resources/Day05/ID_ranges.txt")
        );

        IDChecker checker = new IDChecker();

        long total = checker.countValidIDsInRanges(
                lines.stream()
                        .filter(l -> !l.isBlank())
                        .toList()
        );

        System.out.println("Total valid IDs available: " + total);
    }
}

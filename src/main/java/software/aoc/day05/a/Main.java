package software.aoc.day05.a;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        List<String> lines = Files.readAllLines(
                Path.of("src/main/resources/Day05/IDs.txt")
        );

        // Separación por línea vacía
        int separatorIndex = lines.indexOf("");

        List<String> ranges = lines.subList(0, separatorIndex);
        List<String> ids = lines.subList(separatorIndex + 1, lines.size());

        IDChecker checker = new IDChecker();
        checker.loadRanges(ranges);

        long totalValid = checker.countValidIDs(ids);

        System.out.println("Total valid products: " + totalValid);
    }
}

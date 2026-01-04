package software.aoc.day05.b;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        // 1. IO
        Path path = Path.of("src/main/resources/Day05/ID_ranges.txt");
        List<String> lines = Files.readAllLines(path);

        // 2. Configuración
        RangeParser parser = new RangeParser();
        RangeMerger merger = new RangeMerger();
        AvailabilityService service = new AvailabilityService(merger);

        // 3. Conversión
        List<NumericRange> rawRanges = parser.parse(lines);

        // 4. Ejecución
        long total = service.calculateTotalAvailableIDs(rawRanges);

        // 5. Salida
        System.out.println("Total valid IDs available: " + total);
    }
}
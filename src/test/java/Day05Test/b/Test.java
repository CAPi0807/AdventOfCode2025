package Day05Test.b; // Mismo paquete de test

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.aoc.day05.b.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class Day05BTest {

    @Test
    void testNumericRangeSize() {
        // 10, 11, 12 -> Size 3
        Assertions.assertEquals(3, new NumericRange(10, 12).size());
        // 5, 5 -> Size 1
        Assertions.assertEquals(1, new NumericRange(5, 5).size());
    }

    @Test
    void testOverlapsOrTouches() {
        NumericRange r1 = new NumericRange(1, 5);

        // Solape normal
        Assertions.assertTrue(r1.overlapsOrTouches(new NumericRange(3, 7)));
        // Adyacente (Touch) -> [1-5] y [6-10] deben fusionarse
        Assertions.assertTrue(r1.overlapsOrTouches(new NumericRange(6, 10)));
        // Contenido
        Assertions.assertTrue(r1.overlapsOrTouches(new NumericRange(2, 4)));
        // Disjunto
        Assertions.assertFalse(r1.overlapsOrTouches(new NumericRange(7, 10)));
    }

    @Test
    void testMergerLogic() {
        RangeMerger merger = new RangeMerger();

        List<NumericRange> input = List.of(
                new NumericRange(1, 5),   // 1,2,3,4,5
                new NumericRange(4, 8),   // 4,5,6,7,8 (solapa)
                new NumericRange(10, 10), // 10 (disjunto)
                new NumericRange(11, 12)  // 11,12 (adyacente al 10)
        );

        // Esperamos: [1-8] y [10-12]
        List<NumericRange> result = merger.merge(input);

        Assertions.assertEquals(2, result.size());

        Assertions.assertEquals(1, result.get(0).start());
        Assertions.assertEquals(8, result.get(0).end()); // 1-8 size 8

        Assertions.assertEquals(10, result.get(1).start());
        Assertions.assertEquals(12, result.get(1).end()); // 10-12 size 3
    }

    @Test
    void testIntegrationFromFile() throws IOException {
        Path path = Path.of("src/test/resources/Day05/ID_ranges.txt");
        List<String> lines = Files.readAllLines(path);

        RangeParser parser = new RangeParser();
        RangeMerger merger = new RangeMerger();
        AvailabilityService service = new AvailabilityService(merger);

        List<NumericRange> raw = parser.parse(lines);
        long result = service.calculateTotalAvailableIDs(raw);

        System.out.println("Integration Result Day05 B: " + result);
        Assertions.assertEquals(14, result);
    }
}
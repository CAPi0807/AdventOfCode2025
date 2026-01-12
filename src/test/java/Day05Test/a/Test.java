package Day05Test.a;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.aoc.day05.a.model.InputData;
import software.aoc.day05.a.model.NumericRange;
import software.aoc.day05.a.model.ValidationPolicy;
import software.aoc.day05.a.parser.InputParser;
import software.aoc.day05.a.policies.AllowedRangesPolicy;
import software.aoc.day05.a.service.ProductService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class Day05Test {

    @Test
    void testNumericRangeLogic() {
        NumericRange range = new NumericRange(10, 20);

        Assertions.assertTrue(range.contains(10)); // Límite inferior
        Assertions.assertTrue(range.contains(20)); // Límite superior
        Assertions.assertTrue(range.contains(15)); // Dentro

        Assertions.assertFalse(range.contains(9));  // Fuera (bajo)
        Assertions.assertFalse(range.contains(21)); // Fuera (alto)
    }

    @Test
    void testParserSplitsCorrectly() {
        List<String> mockFile = List.of(
                "1-5",
                "10-15",
                "",       // Separador
                "3",
                "12",
                "20"
        );

        InputParser parser = new InputParser();
        InputData data = parser.parse(mockFile);

        // Verificar Rangos
        Assertions.assertEquals(2, data.ranges().size());
        Assertions.assertEquals(new NumericRange(1, 5), data.ranges().get(0));

        // Verificar IDs
        Assertions.assertEquals(3, data.ids().size());
        Assertions.assertEquals(3L, data.ids().get(0));
    }

    @Test
    void testPolicyLogic() {
        // Rangos: 1-5 y 10-15
        List<NumericRange> ranges = List.of(
                new NumericRange(1, 5),
                new NumericRange(10, 15)
        );
        ValidationPolicy policy = new AllowedRangesPolicy(ranges);

        Assertions.assertTrue(policy.isValid(3));  // En rango 1
        Assertions.assertTrue(policy.isValid(12)); // En rango 2
        Assertions.assertFalse(policy.isValid(7)); // En el hueco
    }

    @Test
    void testIntegrationFromFile() throws IOException {
        Path path = Path.of("src/test/resources/Day05/IDs.txt");
        List<String> lines = Files.readAllLines(path);

        InputParser parser = new InputParser();
        InputData data = parser.parse(lines);

        ValidationPolicy policy = new AllowedRangesPolicy(data.ranges());
        ProductService service = new ProductService();

        long result = service.countValidProducts(data.ids(), policy);

        System.out.println("Integration Result Day05: " + result);
        Assertions.assertEquals(3, result);
    }
}
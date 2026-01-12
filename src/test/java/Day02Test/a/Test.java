package Day02Test.a;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.aoc.day02.a.model.NumberPredicate;
import software.aoc.day02.a.model.NumericRange;
import software.aoc.day02.a.model.RepetitiveNumberPredicate;
import software.aoc.day02.a.parser.RangeParser;
import software.aoc.day02.a.service.RangeSumService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class RangeTest {

    @Test
    void testRepetitivePredicate() {
        NumberPredicate predicate = new RepetitiveNumberPredicate();

        // Casos True
        Assertions.assertTrue(predicate.test(1212));
        Assertions.assertTrue(predicate.test(1010));
        Assertions.assertTrue(predicate.test(55)); // 5 y 5

        // Casos False
        Assertions.assertFalse(predicate.test(123));   // Impar
        Assertions.assertFalse(predicate.test(1234));  // 12 != 34
        Assertions.assertFalse(predicate.test(5));     // Un dígito
        Assertions.assertFalse(predicate.test(1213));
    }

    @Test
    void testNumericRangeStream() {
        // Rango 10-12 debe generar 10, 11, 12
        NumericRange range = new NumericRange(10, 12);
        long count = range.stream().count();
        long sum = range.stream().sum();

        Assertions.assertEquals(3, count);
        Assertions.assertEquals(33, sum);
    }

    @Test
    void testParser() {
        RangeParser parser = new RangeParser();
        String input = "1-3,10-10";

        List<NumericRange> ranges = parser.parse(input);

        Assertions.assertEquals(2, ranges.size());
        Assertions.assertEquals(1, ranges.get(0).start());
        Assertions.assertEquals(3, ranges.get(0).end());
        Assertions.assertEquals(10, ranges.get(1).start());
    }

    @Test
    void testIntegrationLogic() {
        // Setup completo
        NumberPredicate predicate = new RepetitiveNumberPredicate();
        RangeSumService service = new RangeSumService(predicate);

        // Rango 10-13: 10, 11, 12, 13
        // Rango 11-11: 11
        // Repetitivos válidos:
        // 10 (No, len 2, 1!=0)
        // 11 (Si, 1==1)
        // 12 (No)
        // 13 (No)
        // Distinct: Solo el 11 cuenta una vez. Suma = 11.

        List<NumericRange> ranges = List.of(
                new NumericRange(10, 13),
                new NumericRange(11, 11)
        );

        long sum = service.calculateUniqueSum(ranges);

        Assertions.assertEquals(11, sum);
    }

    @Test
    void testIntegrationFromFile() throws IOException {
        // Lectura del archivo de test generado
        Path path = Path.of("src/test/resources/Day02/Ranges.txt");
        String content = Files.readString(path);

        RangeParser parser = new RangeParser();
        NumberPredicate predicate = new RepetitiveNumberPredicate();
        RangeSumService service = new RangeSumService(predicate);

        List<NumericRange> ranges = parser.parse(content);
        long result = service.calculateUniqueSum(ranges);

        System.out.println("Integration Test Result Day02: " + result);
        Assertions.assertEquals(1227775554, result);
    }
}
package Day02Test.b; // Mismo paquete de test solicitado

import org.junit.jupiter.api.Assertions;
import software.aoc.day02.a.NumberPredicate;
import software.aoc.day02.a.NumericRange;
import software.aoc.day02.a.RangeParser;
import software.aoc.day02.a.RangeSumService;
import software.aoc.day02.b.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class Test {

    @org.junit.jupiter.api.Test
    void testPatternRepetitivePredicate() {
        NumberPredicate predicate = new PatternRepetitivePredicate();

        // Casos que ya funcionaban en la Parte A (Patrón mitad)
        Assertions.assertTrue(predicate.test(1212), "12-12 debería ser true");

        // Casos NUEVOS de la Parte B
        // Patrón "1" repetido 3 veces (Impar, fallaría en A, pasa en B)
        Assertions.assertTrue(predicate.test(111), "1-1-1 debería ser true");

        // Patrón "12" repetido 3 veces ("12"-"12"-"12")
        Assertions.assertTrue(predicate.test(121212), "12-12-12 debería ser true");

        // Patrón "123" repetido 2 veces
        Assertions.assertTrue(predicate.test(123123), "123-123 debería ser true");

        // Patrón "8" repetido 4 veces
        Assertions.assertTrue(predicate.test(8888), "8-8-8-8 debería ser true");

        // Casos Falsos
        Assertions.assertFalse(predicate.test(1234));
        Assertions.assertFalse(predicate.test(121)); // Casi, pero no
        Assertions.assertFalse(predicate.test(5));   // Un dígito
    }

    @org.junit.jupiter.api.Test
    void testIntegrationLogic() {
        NumberPredicate predicate = new PatternRepetitivePredicate();
        RangeSumService service = new RangeSumService(predicate);

        // Rango 10-15: 10, 11, 12, 13, 14, 15
        // Repetitivos (Patrón):
        // 11 (patrón '1', OK)
        // Resto no.
        // Rango 111-111: 111 (patrón '1', OK)

        // Suma esperada: 11 + 111 = 122
        List<NumericRange> ranges = List.of(
                new NumericRange(10, 15),
                new NumericRange(111, 111)
        );

        long sum = service.calculateUniqueSum(ranges);

        Assertions.assertEquals(122, sum);
    }

    @org.junit.jupiter.api.Test
    void testIntegrationFromFile() throws IOException {
        // Usa el archivo generado anteriormente
        Path path = Path.of("src/test/resources/Day02/Ranges.txt");
        String content = Files.readString(path);

        RangeParser parser = new RangeParser();
        NumberPredicate predicate = new PatternRepetitivePredicate();
        RangeSumService service = new RangeSumService(predicate);

        List<NumericRange> ranges = parser.parse(content);

        long result = service.calculateUniqueSum(ranges);

        // Verificación básica
        System.out.println("Integration Test Result Day02 B: " + result);
        Assertions.assertTrue(result > 0);
    }
}
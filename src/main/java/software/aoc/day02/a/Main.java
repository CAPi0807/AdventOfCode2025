package software.aoc.day02.a;

import software.aoc.day02.a.model.NumberPredicate;
import software.aoc.day02.a.model.NumericRange;
import software.aoc.day02.a.model.RepetitiveNumberPredicate;
import software.aoc.day02.a.parser.RangeParser;
import software.aoc.day02.a.service.RangeSumService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            // 1. Configuración (Wiring)
            RangeParser parser = new RangeParser();
            NumberPredicate predicate = new RepetitiveNumberPredicate();
            RangeSumService service = new RangeSumService(predicate);

            // 2. IO
            String content = Files.readString(Path.of("src/main/resources/Day02/Ranges.txt"));

            // 3. Parsing
            List<NumericRange> ranges = parser.parse(content);

            // 4. Ejecución
            long result = service.calculateUniqueSum(ranges);

            // 5. Salida
            System.out.println(result);

        } catch (IOException e) {
            System.err.println("Error de lectura: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error de formato en los números: " + e.getMessage());
        }
    }
}
package software.aoc.day02.b;

import software.aoc.day02.a.model.NumberPredicate;
import software.aoc.day02.a.model.NumericRange;
import software.aoc.day02.a.parser.RangeParser;
import software.aoc.day02.a.service.RangeSumService;
import software.aoc.day02.b.model.PatternRepetitivePredicate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            // 1. Inyección de dependencias
            // Aquí inyectamos la implementación 'PatternRepetitivePredicate' (Lógica Parte B)
            NumberPredicate predicate = new PatternRepetitivePredicate();

            RangeParser parser = new RangeParser();
            RangeSumService service = new RangeSumService(predicate);

            // 2. Lectura
            Path path = Path.of("src/main/resources/Day02/Ranges.txt");
            String content = Files.readString(path);

            // 3. Proceso
            List<NumericRange> ranges = parser.parse(content);
            long result = service.calculateUniqueSum(ranges);

            // 4. Salida
            System.out.println(result);

        } catch (IOException e) {
            System.err.println("Error IO: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error formato: " + e.getMessage());
        }
    }
}
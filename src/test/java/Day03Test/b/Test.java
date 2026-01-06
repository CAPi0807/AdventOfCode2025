package Day03Test.b;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.aoc.day03.a.Battery;
import software.aoc.day03.a.BatteryParser;
import software.aoc.day03.a.JoltageService;
import software.aoc.day03.a.JoltageStrategy;
import software.aoc.day03.b.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class JoltageBTest {

    @Test
    void testShortStringReturnsZero() {
        JoltageStrategy strategy = new GreedySelectionStrategy();
        // Menos de 12 caracteres
        Battery battery = new Battery("12345678901");
        Assertions.assertEquals(0L, strategy.calculate(battery));
    }

    @Test
    void testExactLengthReturnsSameNumber() {
        JoltageStrategy strategy = new GreedySelectionStrategy();
        // Exactamente 12 caracteres
        String input = "123456789012";
        Battery battery = new Battery(input);

        Assertions.assertEquals(123456789012L, strategy.calculate(battery));
    }

    @Test
    void testGreedySelectionLogicPrioritizesHighNumbers() {
        JoltageStrategy strategy = new GreedySelectionStrategy();

        // Input: "5656..." (Suficientes caracteres de sobra)
        // Debería coger los 6 antes que los 5 si hay espacio.
        // String largo con muchos números
        String input = "565656565656565656565656";
        // Debería formar "666666666666"

        Assertions.assertEquals(666666666666L, strategy.calculate(new Battery(input)));
    }

    @Test
    void testIntegrationFromFile() throws IOException {
        BatteryParser parser = new BatteryParser();
        JoltageStrategy strategy = new GreedySelectionStrategy();
        JoltageService service = new JoltageService(strategy);

        // Archivo generado en resources
        Path path = Path.of("src/test/resources/Day03/Batteries.txt");
        List<String> rawLines = Files.readAllLines(path);
        List<Battery> batteries = parser.parse(rawLines);

        long result = service.calculateTotalLoad(batteries);

        // Basado en el archivo Batteries.txt que definiremos abajo
        System.out.println("Integration Result Day03 B: " + result);
        Assertions.assertEquals(3121910778619L, result);
    }
}
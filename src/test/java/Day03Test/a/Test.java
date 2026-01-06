package Day03Test.a;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.aoc.day03.a.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class JoltageTest {

    @Test
    void testHighestOrderedPairLogic() {
        JoltageStrategy strategy = new HighestOrderedPairStrategy();

        // Caso simple: 1, 2, 3 -> Pares: 12, 13, 23. Max 23.
        Assertions.assertEquals(23, strategy.calculate(new Battery("123")));

        // Caso inverso: 3, 2, 1 -> Pares: 32, 31, 21. Max 32.
        Assertions.assertEquals(32, strategy.calculate(new Battery("321")));

        // Caso máximo inmediato: "99" al principio
        Assertions.assertEquals(99, strategy.calculate(new Battery("99123")));

        // Caso ceros: "05" -> 5. "50" -> 50.
        Assertions.assertEquals(50, strategy.calculate(new Battery("50")));
        Assertions.assertEquals(5, strategy.calculate(new Battery("05")));

        // Caso un solo dígito (no hay pares)
        Assertions.assertEquals(0, strategy.calculate(new Battery("5")));
    }

    @Test
    void testIntegrationFromFile() throws IOException {
        // Wiring
        BatteryParser parser = new BatteryParser();
        JoltageStrategy strategy = new HighestOrderedPairStrategy();
        JoltageService service = new JoltageService(strategy);

        // Lectura del archivo de test generado
        Path path = Path.of("src/test/resources/Day03/Batteries.txt");
        List<String> rawLines = Files.readAllLines(path);

        // Ejecución
        List<Battery> batteries = parser.parse(rawLines);
        long result = service.calculateTotalLoad(batteries);

        // Verificación
        System.out.println("Integration Result Day03: " + result);
        Assertions.assertEquals(357, result);
    }
}
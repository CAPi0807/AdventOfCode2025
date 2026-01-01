package Day01Test.a;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.aoc.day01.a.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class DialTest {

    @Test
    void testRotationLogicLeft() {
        // Dado un dial en 50
        Dial dial = new Dial(50);
        Instruction instruction = new Instruction(Direction.LEFT, 50);

        // Cuando aplicamos L50
        Dial nextDial = dial.move(instruction);

        // Entonces la posición debe ser 0 y el score 1
        Assertions.assertEquals(0, nextDial.position());
        Assertions.assertEquals(1, nextDial.calculateScore());
    }

    @Test
    void testRotationLogicRight() {
        // Dado un dial en 90
        Dial dial = new Dial(90);
        Instruction instruction = new Instruction(Direction.RIGHT, 20);

        // Cuando aplicamos R20 -> (90 + 20) % 100 = 10
        Dial nextDial = dial.move(instruction);

        // Entonces
        Assertions.assertEquals(10, nextDial.position());
        Assertions.assertEquals(0, nextDial.calculateScore());
    }

    @Test
    void testIntegrationWithFile() throws IOException {
        // Setup
        InstructionParser parser = new InstructionParser();
        PasswordService service = new PasswordService();

        // Lectura del archivo de test generado
        Path path = Path.of("src/test/resources/Day01/Orders.txt");
        List<String> rawLines = Files.readAllLines(path);

        // Parseo
        List<Instruction> instructions = parser.parseAll(rawLines);

        // Ejecución
        int result = service.calculatePassword(50, instructions);

        // Verificación básica: El resultado debe ser un número positivo (sanity check)
        // Nota: Si conociéramos el resultado exacto ("Golden Master"), lo pondríamos aquí.
        System.out.println("Test Integration Result: " + result);
        Assertions.assertTrue(result >= 0, "El resultado no puede ser negativo");
    }
}
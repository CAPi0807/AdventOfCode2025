package Day01Test.b;

import org.junit.jupiter.api.Assertions;
import software.aoc.day01.a.model.Direction;
import software.aoc.day01.a.model.Instruction;
import software.aoc.day01.a.parser.InstructionParser;
import software.aoc.day01.b.model.Dial;
import software.aoc.day01.b.model.TurnResult;
import software.aoc.day01.b.service.PasswordServiceB;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class Test {

    @org.junit.jupiter.api.Test
    void testRightTurnHits() {
        // Caso: Estamos en 90, sumamos 20. Total recorrido absoluto = 110.
        // 110 / 100 = 1 hit. Nueva pos: 10.
        Dial dial = new Dial(90);
        Instruction instruction = new Instruction(Direction.RIGHT, 20);

        TurnResult result = dial.turn(instruction);

        Assertions.assertEquals(10, result.newDial().position());
        Assertions.assertEquals(1, result.hitsGenerated());
    }

    @org.junit.jupiter.api.Test
    void testLeftTurnNoHit() {
        // Caso: Estamos en 10, restamos 5. No llegamos a 0.
        Dial dial = new Dial(10);
        Instruction instruction = new Instruction(Direction.LEFT, 5);

        TurnResult result = dial.turn(instruction);

        Assertions.assertEquals(5, result.newDial().position());
        Assertions.assertEquals(0, result.hitsGenerated());
    }

    @org.junit.jupiter.api.Test
    void testLeftTurnExactHit() {
        // Caso: Estamos en 10, restamos 10. Llegamos a 0 exacto.
        // distToZero = 10. value = 10. hits = 1 + (10-10)/100 = 1.
        Dial dial = new Dial(10);
        Instruction instruction = new Instruction(Direction.LEFT, 10);

        TurnResult result = dial.turn(instruction);

        Assertions.assertEquals(0, result.newDial().position());
        Assertions.assertEquals(1, result.hitsGenerated());
    }

    @org.junit.jupiter.api.Test
    void testLeftTurnMultipleHits() {
        // Caso: Estamos en 10, restamos 110.
        // Toca 0 (gasta 10). Quedan 100. Da una vuelta completa extra. Total 2 hits.
        // Pos final: 0.
        Dial dial = new Dial(10);
        Instruction instruction = new Instruction(Direction.LEFT, 110);

        TurnResult result = dial.turn(instruction);

        Assertions.assertEquals(0, result.newDial().position());
        Assertions.assertEquals(2, result.hitsGenerated());
    }

    @org.junit.jupiter.api.Test
    void testIntegrationFromFile() throws IOException {
        InstructionParser parser = new InstructionParser();
        PasswordServiceB service = new PasswordServiceB();

        // Usamos el mismo archivo de recursos que generamos en el paso anterior
        Path path = Path.of("src/test/resources/Day01/Orders.txt");
        List<String> rawLines = Files.readAllLines(path);
        List<Instruction> instructions = parser.parseAll(rawLines);

        int result = service.calculatePassword(50, instructions);

        System.out.println("Integration Test Result B: " + result);
        Assertions.assertEquals(6, result, "El resultado debe ser 6");
    }
}
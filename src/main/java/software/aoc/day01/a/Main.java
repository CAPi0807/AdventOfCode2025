package software.aoc.day01.a;

import software.aoc.day01.a.model.Instruction;
import software.aoc.day01.a.parser.InstructionParser;
import software.aoc.day01.a.service.PasswordService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        InstructionParser parser = new InstructionParser();
        PasswordService service = new PasswordService();

        Path path = Path.of("src/main/resources/Day01/Orders.txt");
        List<String> rawLines = Files.readAllLines(path);

        List<Instruction> instructions = parser.parseAll(rawLines);

        int password = service.calculatePassword(50, instructions);

        // 5. Salida
        System.out.println("Contraseña = " + password);
    }
}
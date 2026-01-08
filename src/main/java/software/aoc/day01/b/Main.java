package software.aoc.day01.b;

import software.aoc.day01.a.model.Instruction;
import software.aoc.day01.a.parser.InstructionParser;
import software.aoc.day01.b.service.PasswordServiceB;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        InstructionParser parser = new InstructionParser();
        PasswordServiceB service = new PasswordServiceB();

        Path path = Path.of("src/main/resources/Day01/Orders.txt");
        List<String> rawLines = Files.readAllLines(path);

        // Ahora esto devuelve Instructions con Direction de A
        List<Instruction> instructions = parser.parseAll(rawLines);

        // Y service/Dial saben manejar Direction de A
        int password = service.calculatePassword(50, instructions);

        System.out.println("Contraseña = " + password);
    }
}
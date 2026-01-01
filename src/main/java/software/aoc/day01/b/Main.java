package software.aoc.day01.b;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        // 1. Dependencias
        InstructionParser parser = new InstructionParser();
        PasswordServiceB service = new PasswordServiceB();

        // 2. IO
        Path path = Path.of("src/main/resources/Day01/Orders.txt");
        List<String> rawLines = Files.readAllLines(path);

        // 3. Dominio
        List<Instruction> instructions = parser.parseAll(rawLines);

        // 4. Lógica
        int password = service.calculatePassword(50, instructions);

        // 5. Salida
        System.out.println("Contraseña = " + password);
    }
}
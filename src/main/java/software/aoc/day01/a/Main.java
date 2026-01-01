package software.aoc.day01.a;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        // 1. Configuración de dependencias
        InstructionParser parser = new InstructionParser();
        PasswordService service = new PasswordService();

        // 2. Lectura (IO)
        Path path = Path.of("src/main/resources/Day01/Orders.txt");
        List<String> rawLines = Files.readAllLines(path);

        // 3. Conversión a Dominio
        List<Instruction> instructions = parser.parseAll(rawLines);

        // 4. Ejecución de lógica
        int password = service.calculatePassword(50, instructions);

        // 5. Salida
        System.out.println("Contraseña = " + password);
    }
}
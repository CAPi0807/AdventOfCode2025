package software.aoc.day07.a;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        // 1. Lectura de Recursos (I/O separado de la lógica)
        Path inputPath = Path.of("src/main/resources/Day07/Input.txt");
        List<String> lines = Files.readAllLines(inputPath);

        // 2. Instanciación del Modelo y Solucionador (Inyección de dependencias simple)
        GridMap map = new GridMap(lines);
        RayTracer tracer = new RayTracer(map);

        // 3. Ejecución
        long totalSplits = tracer.countSplits();

        System.out.println("El rayo se dividió un total de: " + totalSplits + " veces.");
    }
}

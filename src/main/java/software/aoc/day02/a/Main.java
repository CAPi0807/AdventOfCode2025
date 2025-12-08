package software.aoc.day02.a;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.LongStream;

public class Main {

    private static final String FILE_PATH = "src/main/resources/Day02/Ranges.txt";

    public static void main(String[] args) {
        try {
            String content = Files.readString(Path.of(FILE_PATH)).trim();

            long suma = Arrays.stream(content.split(","))
                    .map(range -> range.split("-"))
                    .flatMapToLong(parts -> { // Usamos flatMapToLong
                        // Lo tratamos como Long en lugar de Integer, porque los números son muy grandes
                        long start = Long.parseLong(parts[0]);
                        long end = Long.parseLong(parts[1]);

                        // Generamos un LongStream
                        return LongStream.rangeClosed(Math.min(start, end), Math.max(start, end));
                    })
                    .filter(ID_detector::isRepetitive)
                    .distinct()
                    .sorted()
                    .sum();
            System.out.println(suma);
        } catch (IOException e) {
            System.err.println("Error leyendo el archivo: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error: El número es demasiado grande o tiene formato inválido: " + e.getMessage());
        }
    }
}

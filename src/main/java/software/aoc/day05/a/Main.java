package software.aoc.day05.a;

import software.aoc.day05.a.model.ValidationPolicy;
import software.aoc.day05.a.parser.InputParser;
import software.aoc.day05.a.policies.AllowedRangesPolicy;
import software.aoc.day05.a.service.ProductService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        // 1. IO
        Path path = Path.of("src/main/resources/Day05/IDs.txt");
        List<String> lines = Files.readAllLines(path);

        // 2. Parsing y Construcción
        InputParser parser = new InputParser();
        InputData data = parser.parse(lines);

        // 3. Configuración de Estrategia
        ValidationPolicy policy = new AllowedRangesPolicy(data.ranges());
        ProductService service = new ProductService();

        // 4. Ejecución
        long totalValid = service.countValidProducts(data.ids(), policy);

        // 5. Salida
        System.out.println("Total valid products: " + totalValid);
    }
}
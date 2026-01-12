package software.aoc.day05.a;

import software.aoc.day05.a.model.InputData;
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

        Path path = Path.of("src/main/resources/Day05/IDs.txt");
        List<String> lines = Files.readAllLines(path);

        InputParser parser = new InputParser();
        InputData data = parser.parse(lines);

        ValidationPolicy policy = new AllowedRangesPolicy(data.ranges());
        ProductService service = new ProductService();

        long totalValid = service.countValidProducts(data.ids(), policy);

        System.out.println("Total valid products: " + totalValid);
    }
}
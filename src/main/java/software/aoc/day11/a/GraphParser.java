package software.aoc.day11.a;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GraphParser {

    public Graph parse(List<String> lines) {
        Map<String, List<String>> adjacencyList = lines.stream()
                .filter(line -> !line.isBlank())
                .map(this::parseLine)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));

        return new Graph(adjacencyList);
    }

    private Map.Entry<String, List<String>> parseLine(String line) {
        // Formato: "nodo: vecino1 vecino2"
        String[] parts = line.split(":");
        String source = parts[0].trim();

        List<String> destinations = Arrays.stream(parts[1].trim().split("\\s+"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();

        return Map.entry(source, destinations);
    }
}
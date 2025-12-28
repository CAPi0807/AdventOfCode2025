package software.aoc.day09.b;

import java.util.List;
import java.util.stream.Collectors;

public class InputParser {

    public List<Point> parse(List<String> lines) {
        return lines.stream()
                .filter(line -> !line.isBlank())
                .map(this::parseLine)
                .collect(Collectors.toList());
    }

    private Point parseLine(String line) {
        String[] parts = line.split(",");
        return new Point(
                Long.parseLong(parts[0].trim()),
                Long.parseLong(parts[1].trim())
        );
    }
}

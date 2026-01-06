package software.aoc.day09.a;

import java.util.List;

public class PointParser {

    public List<Point> parse(List<String> lines) {
        return lines.stream()
                .filter(line -> !line.isBlank())
                .map(Point::parse)
                .toList();
    }
}
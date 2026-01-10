package software.aoc.day09.a.parser;

import software.aoc.day09.a.model.Point;

import java.util.List;

public class PointParser {

    public List<Point> parse(List<String> lines) {
        return lines.stream()
                .filter(line -> !line.isBlank())
                .map(Point::parse)
                .toList();
    }
}
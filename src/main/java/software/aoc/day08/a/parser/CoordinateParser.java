package software.aoc.day08.a.parser;

import software.aoc.day08.a.model.Point3D;

import java.util.List;

public class CoordinateParser {
    public List<Point3D> parse(List<String> lines) {
        return lines.stream()
                .map(Point3D::parse)
                .toList();
    }
}
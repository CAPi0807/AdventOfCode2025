package software.aoc.day09.a.service;

import software.aoc.day09.a.model.AreaStrategy;
import software.aoc.day09.a.model.Point;

import java.util.List;

public class GeometryService {

    private final AreaStrategy strategy;

    public GeometryService(AreaStrategy strategy) {
        this.strategy = strategy;
    }

    public long calculateLargestRectangle(List<Point> points) {
        if (points == null || points.size() < 2) {
            return 0;
        }
        return strategy.findMaxArea(points);
    }
}
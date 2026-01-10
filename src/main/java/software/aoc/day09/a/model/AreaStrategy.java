package software.aoc.day09.a.model;

import java.util.List;

@FunctionalInterface
public interface AreaStrategy {
    long findMaxArea(List<Point> points);
}
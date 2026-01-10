package software.aoc.day08.a.model;

@FunctionalInterface
public interface DistanceStrategy {
    double calculate(Point3D a, Point3D b);
}
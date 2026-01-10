package software.aoc.day08.a.strategies;

import software.aoc.day08.a.model.DistanceStrategy;
import software.aoc.day08.a.model.Point3D;

public class EuclideanStrategy implements DistanceStrategy {
    @Override
    public double calculate(Point3D a, Point3D b) {
        return Math.pow(a.x() - b.x(), 2) +
                Math.pow(a.y() - b.y(), 2) +
                Math.pow(a.z() - b.z(), 2);
    }
}
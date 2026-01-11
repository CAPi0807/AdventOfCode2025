package software.aoc.day03.a.model;

@FunctionalInterface
public interface JoltageStrategy {
    long calculate(Battery battery);
}
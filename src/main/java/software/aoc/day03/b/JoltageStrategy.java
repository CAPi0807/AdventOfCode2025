package software.aoc.day03.b;

@FunctionalInterface
public interface JoltageStrategy {
    long calculate(Battery battery);
}
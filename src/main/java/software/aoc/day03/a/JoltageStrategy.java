package software.aoc.day03.a;

@FunctionalInterface
public interface JoltageStrategy {
    long calculate(Battery battery);
}
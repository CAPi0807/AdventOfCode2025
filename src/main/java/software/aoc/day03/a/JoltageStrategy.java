package software.aoc.day03.a;

@FunctionalInterface
public interface JoltageStrategy {
    int calculate(Battery battery);
}
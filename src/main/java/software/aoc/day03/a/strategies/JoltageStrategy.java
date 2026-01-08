package software.aoc.day03.a.strategies;

import software.aoc.day03.a.model.Battery;

@FunctionalInterface
public interface JoltageStrategy {
    long calculate(Battery battery);
}
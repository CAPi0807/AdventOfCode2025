package software.aoc.day04.a.model;

@FunctionalInterface
public interface SelectionRule {
    boolean matches(Grid grid, Position position);
}
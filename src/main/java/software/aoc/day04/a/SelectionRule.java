package software.aoc.day04.a;

@FunctionalInterface
public interface SelectionRule {
    boolean matches(Grid grid, Position position);
}
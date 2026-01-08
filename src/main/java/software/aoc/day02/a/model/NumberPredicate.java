package software.aoc.day02.a.model;

@FunctionalInterface
public interface NumberPredicate {
    boolean test(long number);
}
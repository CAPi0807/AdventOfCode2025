package software.aoc.day02.b;

@FunctionalInterface
public interface NumberPredicate {
    boolean test(long number);
}
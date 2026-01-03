package software.aoc.day02.a;

@FunctionalInterface
public interface NumberPredicate {
    boolean test(long number);
}
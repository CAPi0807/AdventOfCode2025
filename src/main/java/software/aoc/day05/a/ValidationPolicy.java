package software.aoc.day05.a;

@FunctionalInterface
public interface ValidationPolicy {
    boolean isValid(long id);
}
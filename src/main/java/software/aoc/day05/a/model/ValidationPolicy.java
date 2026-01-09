package software.aoc.day05.a.model;

@FunctionalInterface
public interface ValidationPolicy {
    boolean isValid(long id);
}
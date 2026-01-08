package software.aoc.day03.a.model;

public record Battery(String sequence) {
    public Battery {
        if (sequence == null) {
            throw new IllegalArgumentException("Sequence cannot be null");
        }
    }
}
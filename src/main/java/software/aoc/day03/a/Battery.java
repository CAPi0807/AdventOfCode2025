package software.aoc.day03.a;

public record Battery(String sequence) {
    public Battery {
        if (sequence == null) {
            throw new IllegalArgumentException("Battery sequence cannot be null");
        }
    }
}
package software.aoc.day05.a;

public record NumericRange(long start, long end) {

    public NumericRange {
        if (start > end) {
            throw new IllegalArgumentException("Start cannot be greater than end");
        }
    }

    public boolean contains(long value) {
        return value >= start && value <= end;
    }

    public static NumericRange parse(String line) {
        String[] parts = line.split("-");
        return new NumericRange(
                Long.parseLong(parts[0].trim()),
                Long.parseLong(parts[1].trim())
        );
    }
}
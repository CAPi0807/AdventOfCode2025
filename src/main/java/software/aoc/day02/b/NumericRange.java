package software.aoc.day02.b;

import java.util.stream.LongStream;

public record NumericRange(long start, long end) {

    public NumericRange {
        if (start > end) {
            long temp = start;
            start = end;
            end = temp;
        }
    }

    public static NumericRange fromParts(String[] parts) {
        return new NumericRange(
                Long.parseLong(parts[0]),
                Long.parseLong(parts[1])
        );
    }

    public LongStream stream() {
        return LongStream.rangeClosed(start, end);
    }
}
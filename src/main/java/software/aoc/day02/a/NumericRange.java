package software.aoc.day02.a;

import java.util.stream.LongStream;

public record NumericRange(long start, long end) {

    public NumericRange {
        // Aseguramos que start siempre sea el menor y end el mayor
        if (start > end) {
            long temp = start;
            start = end;
            end = temp;
        }
    }

    // Factory method para crear desde array de strings (ej: ["10", "20"])
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
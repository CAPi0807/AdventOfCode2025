package software.aoc.day05.b;

import java.util.List;

public class RangeParser {
    public List<NumericRange> parse(List<String> lines) {
        return lines.stream()
                .filter(l -> !l.isBlank())
                .map(NumericRange::parse)
                .toList();
    }
}
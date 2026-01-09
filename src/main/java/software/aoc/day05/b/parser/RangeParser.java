package software.aoc.day05.b.parser;

import software.aoc.day05.b.model.NumericRange;

import java.util.List;

public class RangeParser {
    public List<NumericRange> parse(List<String> lines) {
        return lines.stream()
                .filter(l -> !l.isBlank())
                .map(NumericRange::parse)
                .toList();
    }
}
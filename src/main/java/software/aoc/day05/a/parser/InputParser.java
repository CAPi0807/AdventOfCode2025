package software.aoc.day05.a.parser;

import software.aoc.day05.a.model.InputData;
import software.aoc.day05.a.model.NumericRange;

import java.util.List;

public class InputParser {

    public InputData parse(List<String> lines) {
        int separatorIndex = lines.indexOf("");

        if (separatorIndex == -1) {
            throw new IllegalArgumentException("Input file format invalid: Missing empty separator line");
        }

        // Rangos
        List<NumericRange> ranges = lines.subList(0, separatorIndex).stream()
                .filter(s -> !s.isBlank())
                .map(NumericRange::parse)
                .toList();

        // IDs
        List<Long> ids = lines.subList(separatorIndex + 1, lines.size()).stream()
                .filter(s -> !s.isBlank())
                .map(Long::parseLong)
                .toList();

        return new InputData(ranges, ids);
    }
}
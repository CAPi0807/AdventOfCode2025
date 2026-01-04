package software.aoc.day05.a;

import java.util.List;

public class InputParser {

    public InputData parse(List<String> lines) {
        int separatorIndex = lines.indexOf("");

        if (separatorIndex == -1) {
            throw new IllegalArgumentException("Input file format invalid: Missing empty separator line");
        }

        // Sección 1: Rangos
        List<NumericRange> ranges = lines.subList(0, separatorIndex).stream()
                .filter(s -> !s.isBlank())
                .map(NumericRange::parse)
                .toList();

        // Sección 2: IDs
        List<Long> ids = lines.subList(separatorIndex + 1, lines.size()).stream()
                .filter(s -> !s.isBlank())
                .map(Long::parseLong)
                .toList();

        return new InputData(ranges, ids);
    }
}
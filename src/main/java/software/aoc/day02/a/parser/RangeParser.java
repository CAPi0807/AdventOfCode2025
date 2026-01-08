package software.aoc.day02.a.parser;

import software.aoc.day02.a.model.NumericRange;

import java.util.Arrays;
import java.util.List;

public class RangeParser {

    public List<NumericRange> parse(String content) {
        if (content == null || content.isBlank()) {
            return List.of();
        }

        return Arrays.stream(content.trim().split(","))
                .map(rangeStr -> rangeStr.split("-"))
                .map(NumericRange::fromParts)
                .toList();
    }
}
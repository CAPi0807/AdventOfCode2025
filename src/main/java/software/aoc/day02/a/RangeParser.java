package software.aoc.day02.a;

import java.util.Arrays;
import java.util.List;

public class RangeParser {

    private static final String RANGE_DELIMITER = "-";
    private static final String ITEM_DELIMITER = ",";

    public List<NumericRange> parse(String content) {
        if (content == null || content.isBlank()) {
            return List.of();
        }

        return Arrays.stream(content.trim().split(ITEM_DELIMITER))
                .map(rangeStr -> rangeStr.split(RANGE_DELIMITER))
                .map(NumericRange::fromParts)
                .toList();
    }
}
package software.aoc.day05.a;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class IDChecker {

    private final List<Range> validRanges = new ArrayList<>();

    public void loadRanges(List<String> lines) {
        validRanges.clear();
        validRanges.addAll(
                lines.stream()
                        .filter(l -> !l.isBlank())
                        .map(this::parseRange)
                        .collect(Collectors.toList())
        );
    }

    public long countValidIDs(List<String> idLines) {
        return idLines.stream()
                .filter(l -> !l.isBlank())
                .mapToLong(Long::parseLong)
                .filter(this::isValidID)
                .count();
    }

    private boolean isValidID(long id) {
        return validRanges.stream().anyMatch(r -> r.contains(id));
    }

    private Range parseRange(String line) {
        String[] parts = line.split("-");
        long start = Long.parseLong(parts[0].trim());
        long end = Long.parseLong(parts[1].trim());
        return new Range(start, end);
    }

    private record Range(long start, long end) {
        boolean contains(long id) {
            return id >= start && id <= end;
        }
    }
}

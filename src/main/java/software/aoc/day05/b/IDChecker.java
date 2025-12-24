package software.aoc.day05.b;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class IDChecker {

    private record Range(long start, long end) {}

    public long countValidIDsInRanges(List<String> rangeLines) {
        // Convertir líneas → rangos
        List<Range> ranges = rangeLines.stream()
                .filter(l -> !l.isBlank())
                .map(this::parseRange)
                .sorted(Comparator.comparingLong(r -> r.start))
                .toList();

        // Fusionar rangos solapados
        List<Range> merged = mergeRanges(ranges);

        // Sumar tamaño total
        return merged.stream()
                .mapToLong(r -> r.end - r.start + 1)
                .sum();
    }

    private Range parseRange(String line) {
        String[] p = line.split("-");
        long s = Long.parseLong(p[0].trim());
        long e = Long.parseLong(p[1].trim());
        return new Range(s, e);
    }

    private List<Range> mergeRanges(List<Range> sorted) {
        List<Range> merged = new ArrayList<>();

        for (Range r : sorted) {
            if (merged.isEmpty()) {
                merged.add(r);
                continue;
            }

            Range last = merged.getLast();

            if (r.start <= last.end + 1) {
                // Solapan → fusionar
                merged.set(merged.size() - 1,
                        new Range(last.start, Math.max(last.end, r.end)));
            } else {
                // No solapan → nuevo intervalo
                merged.add(r);
            }
        }

        return merged;
    }
}

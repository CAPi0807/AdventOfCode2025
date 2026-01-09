package software.aoc.day05.a.policies;

import software.aoc.day05.a.model.NumericRange;
import software.aoc.day05.a.model.ValidationPolicy;

import java.util.List;

public class AllowedRangesPolicy implements ValidationPolicy {

    private final List<NumericRange> allowedRanges;

    // Copia defensiva o uso de List.copyOf para garantizar inmutabilidad
    public AllowedRangesPolicy(List<NumericRange> ranges) {
        this.allowedRanges = List.copyOf(ranges);
    }

    @Override
    public boolean isValid(long id) {
        // Un ID es válido si está contenido en AL MENOS uno de los rangos
        return allowedRanges.stream()
                .anyMatch(range -> range.contains(id));
    }
}
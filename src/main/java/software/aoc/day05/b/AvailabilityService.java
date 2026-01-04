package software.aoc.day05.b;

import java.util.List;

public class AvailabilityService {

    private final RangeMerger merger;

    public AvailabilityService(RangeMerger merger) {
        this.merger = merger;
    }

    public long calculateTotalAvailableIDs(List<NumericRange> rawRanges) {
        // 1. Simplificar los rangos (lógica compleja)
        List<NumericRange> consolidatedRanges = merger.merge(rawRanges);

        // 2. Calcular total (reducción simple)
        return consolidatedRanges.stream()
                .mapToLong(NumericRange::size)
                .sum();
    }
}
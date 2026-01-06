package software.aoc.day02.a;

import java.util.List;

public class RangeSumService {

    private final NumberPredicate validator;

    // Inyección de dependencias (DIP)
    public RangeSumService(NumberPredicate validator) {
        this.validator = validator;
    }

    public long calculateUniqueSum(List<NumericRange> ranges) {
        return ranges.stream()
                .flatMapToLong(NumericRange::stream)
                .filter(validator::test)
                .distinct()
                .sorted()
                .sum();
    }
}
package software.aoc.day02.b;

import software.aoc.day02.a.NumberPredicate;
import software.aoc.day02.a.NumericRange;

import java.util.List;

public class RangeSumService {

    private final NumberPredicate validator;

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
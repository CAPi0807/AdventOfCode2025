package software.aoc.day02.a.service;

import software.aoc.day02.a.model.NumberPredicate;
import software.aoc.day02.a.model.NumericRange;

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
                .distinct()     //De nuevo, por si acaso
                .sum();
    }
}
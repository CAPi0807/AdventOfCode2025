package software.aoc.day06.b;

import java.util.List;

public record NumberBlock(List<Long> numbers, Operation operation) {

    public NumberBlock {
        // Copia defensiva para inmutabilidad
        numbers = List.copyOf(numbers);
    }

    public long calculateResult() {
        if (numbers.isEmpty()) return 0;

        return numbers.stream()
                .reduce(operation::apply)
                .orElse(0L);
    }
}
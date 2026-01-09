package software.aoc.day04.a.model;

import java.util.List;
import java.util.stream.IntStream;

public record Position(int row, int col) {
    private static final int[] DELTAS = {-1, 0, 1};

    public List<Position> neighbors() {
        return IntStream.of(DELTAS).boxed()
                .flatMap(dr -> IntStream.of(DELTAS)
                        .filter(dc -> !(dr == 0 && dc == 0))
                        .mapToObj(dc -> new Position(row + dr, col + dc)))
                .toList();
    }
}
package software.aoc.day04.a.service;

import software.aoc.day04.a.model.Grid;
import software.aoc.day04.a.model.Position;
import software.aoc.day04.a.model.SelectionRule;

import java.util.stream.IntStream;

public record WarehouseService(SelectionRule rule) {

    public long countSafeRolls(Grid grid) {
        return IntStream.range(0, grid.rows())
                .boxed()
                .flatMap(r -> IntStream.range(0, grid.cols())
                        .mapToObj(c -> new Position(r, c)))
                .filter(pos -> rule.matches(grid, pos))
                .count();
    }
}
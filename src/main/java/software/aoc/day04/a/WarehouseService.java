package software.aoc.day04.a;

import java.util.stream.IntStream;

public class WarehouseService {

    private final SelectionRule rule;

    public WarehouseService(SelectionRule rule) {
        this.rule = rule;
    }

    public long countSafeRolls(Grid grid) {
        return IntStream.range(0, grid.rows())
                .boxed()
                .flatMap(r -> IntStream.range(0, grid.cols())
                        .mapToObj(c -> new Position(r, c)))
                .filter(pos -> rule.matches(grid, pos))
                .count();
    }
}
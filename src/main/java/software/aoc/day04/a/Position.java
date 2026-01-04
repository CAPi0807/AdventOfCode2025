package software.aoc.day04.a;

import java.util.List;
import java.util.stream.IntStream;

public record Position(int row, int col) {

    private static final int[] DELTAS = {-1, 0, 1};

    /**
     * Genera las 8 coordenadas adyacentes.
     */
    public List<Position> neighbors() {
        return IntStream.of(DELTAS).boxed()
                .flatMap(dr -> IntStream.of(DELTAS)
                        .filter(dc -> !(dr == 0 && dc == 0)) // Excluirse a sí mismo (0,0)
                        .mapToObj(dc -> new Position(row + dr, col + dc)))
                .toList();
    }
}
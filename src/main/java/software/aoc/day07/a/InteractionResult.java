package software.aoc.day07.a;

import java.util.List;

public record InteractionResult(List<Integer> nextColumnOffsets, boolean isSplit) {
    // Constructor estático para facilitar la lectura
    public static InteractionResult passThrough() {
        return new InteractionResult(List.of(0), false);
    }

    public static InteractionResult split() {
        return new InteractionResult(List.of(-1, 1), true);
    }
}
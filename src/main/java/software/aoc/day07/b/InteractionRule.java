package software.aoc.day07.b;

import java.util.List;

public class InteractionRule {

    public List<Integer> getNextOffsets(char cellContent) {
        if (cellContent == '^') {
            // División: Izquierda (-1) y Derecha (+1)
            return List.of(-1, 1);
        }
        // Paso directo (S, ., etc): Centro (0)
        return List.of(0);
    }
}
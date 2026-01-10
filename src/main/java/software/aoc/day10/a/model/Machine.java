package software.aoc.day10.a.model;

import java.util.List;


public record Machine(
        List<Integer> targetState,
        List<List<Integer>> buttons,
        List<Integer> voltageTargets
) {
    public int lightCount() {
        return targetState.size();
    }

    public int buttonCount() {
        return buttons.size();
    }

    public int voltageCount() {
        return voltageTargets.size();
    }
}

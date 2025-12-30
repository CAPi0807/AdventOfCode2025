package software.aoc.day10.a;

import java.util.List;

/**
 * Modelo inmutable de una máquina.
 * @param targetState Lista de 0s (apagado) y 1s (encendido) representando el estado final deseado.
 * @param buttons Lista de botones, donde cada botón es una lista de los índices que afecta.
 */
public record Machine(List<Integer> targetState, List<List<Integer>> buttons) {
    public int lightCount() {
        return targetState.size();
    }

    public int buttonCount() {
        return buttons.size();
    }
}

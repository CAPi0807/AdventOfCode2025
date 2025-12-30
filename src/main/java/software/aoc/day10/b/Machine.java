package software.aoc.day10.b;

import java.util.List;

public record Machine(
        List<Integer> lightsTarget,   // Parte A (Ignorado en Parte B)
        List<List<Integer>> buttons,  // Qué índices afecta cada botón
        List<Integer> voltageTargets  // Parte B: Voltaje requerido
) {
    public int buttonCount() {
        return buttons.size();
    }

    public int voltageCount() {
        return voltageTargets.size();
    }
}

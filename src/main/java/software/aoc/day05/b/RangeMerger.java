package software.aoc.day05.b;

import java.util.ArrayList;
import java.util.List;

public class RangeMerger {

    public List<NumericRange> merge(List<NumericRange> inputs) {
        if (inputs.isEmpty()) return List.of();

        // 1. Ordenar (Stream sorted crea una nueva lista, preservando inmutabilidad de la entrada)
        List<NumericRange> sorted = inputs.stream().sorted().toList();

        List<NumericRange> merged = new ArrayList<>();
        NumericRange current = sorted.get(0);

        // 2. Algoritmo de barrido
        for (int i = 1; i < sorted.size(); i++) {
            NumericRange next = sorted.get(i);

            if (current.overlapsOrTouches(next)) {
                // Fusión: Actualizamos 'current' (variable local)
                current = current.merge(next);
            } else {
                // No hay solape: Guardamos el anterior y empezamos uno nuevo
                merged.add(current);
                current = next;
            }
        }

        // Añadir el último remanente
        merged.add(current);

        return merged;
    }
}
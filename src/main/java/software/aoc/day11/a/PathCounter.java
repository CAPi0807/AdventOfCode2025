package software.aoc.day11.a;

import java.util.HashMap;
import java.util.Map;

public class PathCounter {

    private final Graph graph;
    private final Map<String, Long> memo; // Caché para almacenar resultados parciales

    public PathCounter(Graph graph) {
        this.graph = graph;
        this.memo = new HashMap<>();
    }

    public long countPaths(String startNode, String endNode) {
        // Limpiamos caché por si se reusa la instancia
        memo.clear();
        return countPathsRecursive(startNode, endNode);
    }

    private long countPathsRecursive(String current, String target) {
        // 1. Caso base: Hemos llegado al destino
        if (current.equals(target)) {
            return 1;
        }

        // 2. Verificar caché (Memoización)
        if (memo.containsKey(current)) {
            return memo.get(current);
        }

        // 3. Recurrencia: Sumar caminos de todos los vecinos
        long totalPaths = 0;
        for (String neighbor : graph.getNeighbors(current)) {
            totalPaths += countPathsRecursive(neighbor, target);
        }

        // 4. Guardar en caché y retornar
        memo.put(current, totalPaths);
        return totalPaths;
    }
}
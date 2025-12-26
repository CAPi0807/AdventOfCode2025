package software.aoc.day07.b;

import java.util.HashMap;
import java.util.Map;

public class PathCounter {

    private final GridMap map;

    public PathCounter(GridMap map) {
        this.map = map;
    }

    public long calculateTotalPaths() {
        int startCol = map.findStartIndex();
        if (startCol == -1) throw new IllegalStateException("No start found");

        // Estado actual: Mapa de <Columna, CantidadDeCaminos>
        // Inicialmente, tenemos 1 camino en la posición de salida (S)
        Map<Integer, Long> currentPaths = new HashMap<>();
        currentPaths.put(startCol, 1L);

        // Iteramos fila por fila
        for (int row = 0; row < map.getHeight(); row++) {

            // Preparamos el mapa para la siguiente fila
            Map<Integer, Long> nextRowPaths = new HashMap<>();

            // Procesamos cada columna activa de la fila actual
            for (Map.Entry<Integer, Long> entry : currentPaths.entrySet()) {
                int col = entry.getKey();
                long pathsReachingHere = entry.getValue();

                char cell = map.getCell(row, col);

                if (cell == '^') {
                    // DIVISIÓN: Los caminos se duplican y divergen.
                    // Si llegaban 5 caminos aquí, ahora van 5 a la izq y 5 a la der.
                    addPaths(nextRowPaths, col - 1, pathsReachingHere);
                    addPaths(nextRowPaths, col + 1, pathsReachingHere);
                } else {
                    // PASO DIRECTO: Los caminos bajan recto.
                    addPaths(nextRowPaths, col, pathsReachingHere);
                }
            }

            // Avanzamos a la siguiente fila
            currentPaths = nextRowPaths;

            if (currentPaths.isEmpty()) break;
        }

        // El resultado total es la suma de todos los caminos que llegaron al final (o se salieron del mapa)
        return currentPaths.values().stream()
                .mapToLong(Long::longValue)
                .sum();
    }

    /**
     * Helper para sumar caminos de manera segura.
     * Si dos rayos convergen en la misma celda, sus contadores de caminos se SUMAN.
     */
    private void addPaths(Map<Integer, Long> pathMap, int col, long count) {
        if (map.isInsideBounds(col)) {
            pathMap.merge(col, count, Long::sum);
        }
        // Nota: Si el rayo sale de los límites (map.isInsideBounds false),
        // esos caminos se "pierden" o "terminan". Según la lógica del problema,
        // si queremos contar caminos que llegan al fondo del grid, esto es correcto.
        // Si queremos contar caminos aunque salgan por los lados, habría que ajustar esto.
        // Asumo que el "tablero" contiene todo el recorrido.
    }
}
package software.aoc.day07.a;

import java.util.HashSet;
import java.util.Set;

public class RayTracer {

    private final GridMap map;

    public RayTracer(GridMap map) {
        this.map = map;
    }

    /**
     * Simula la caída del rayo fila por fila.
     * @return Número total de veces que un rayo golpeó un divisor '^'.
     */
    public long countSplits() {
        // Estado inicial: Encontramos la columna de la 'S'
        int startCol = map.findStartIndex();
        if (startCol == -1) {
            throw new IllegalStateException("No se encontró el punto de inicio 'S'");
        }

        // Usamos un Set para rastrear las columnas activas (evita duplicados si los rayos convergen)
        Set<Integer> activeBeams = new HashSet<>();
        activeBeams.add(startCol);

        long splitCounter = 0;

        // Iteramos fila por fila hacia abajo (simulando la gravedad)
        // Empezamos en la fila siguiente a la S
        for (int row = 0; row < map.getHeight(); row++) {

            Set<Integer> nextBeams = new HashSet<>();

            for (int col : activeBeams) {
                char currentCell = map.getCell(row, col);

                if (currentCell == '^') {
                    // Lógica de División
                    splitCounter++;
                    // El rayo se divide en dos: izquierda (col-1) y derecha (col+1)
                    // Asumimos expansión triangular típica en grid de texto
                    addIfValid(nextBeams, col - 1);
                    addIfValid(nextBeams, col + 1);
                } else {
                    // Lógica de Paso (S, ., o cualquier otro caracter de fondo)
                    // El rayo continúa recto hacia abajo
                    addIfValid(nextBeams, col);
                }
            }

            // Actualizamos los rayos para la siguiente iteración (fila)
            activeBeams = nextBeams;

            // Optimización: Si no quedan rayos, terminamos antes
            if (activeBeams.isEmpty()) {
                break;
            }
        }

        return splitCounter;
    }

    private void addIfValid(Set<Integer> beams, int col) {
        if (map.isInsideBounds(col)) {
            beams.add(col);
        }
    }
}

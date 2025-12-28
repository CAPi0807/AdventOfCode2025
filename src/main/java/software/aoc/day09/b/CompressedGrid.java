package software.aoc.day09.b;

import java.util.Arrays;
import java.util.List;

public class CompressedGrid {
    private final long[] xCoords;
    private final long[] yCoords;
    private final boolean[][] solidCells; // true si la celda es parte del polígono

    public CompressedGrid(List<Point> vertices) {
        // 1. Extraer y ordenar coordenadas únicas
        this.xCoords = vertices.stream().mapToLong(Point::x).distinct().sorted().toArray();
        this.yCoords = vertices.stream().mapToLong(Point::y).distinct().sorted().toArray();

        int width = xCoords.length - 1;
        int height = yCoords.length - 1;

        // 2. Crear grid lógico
        this.solidCells = new boolean[height][width];

        // 3. Rellenar grid (Pre-cálculo)
        // Comprobamos el punto central de cada celda lógica
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double midX = (xCoords[x] + xCoords[x+1]) / 2.0;
                double midY = (yCoords[y] + yCoords[y+1]) / 2.0;

                if (isPointInsidePolygon(midX, midY, vertices)) {
                    solidCells[y][x] = true;
                }
            }
        }
    }

    /**
     * Verifica si un rectángulo cubre SOLO celdas sólidas.
     */
    public boolean covers(Rectangle rect) {
        // Encontrar los índices en el array comprimido
        int xStartIndex = Arrays.binarySearch(xCoords, rect.minX());
        int xEndIndex = Arrays.binarySearch(xCoords, rect.maxX());
        int yStartIndex = Arrays.binarySearch(yCoords, rect.minY());
        int yEndIndex = Arrays.binarySearch(yCoords, rect.maxY());

        // Si las coordenadas del rectángulo no coinciden con vértices del polígono,
        // binarySearch devuelve negativo. Pero en este problema, los rectángulos
        // se forman con vértices existentes, así que siempre deberían existir.
        if (xStartIndex < 0 || xEndIndex < 0 || yStartIndex < 0 || yEndIndex < 0) return false;

        // Verificar todas las celdas lógicas dentro del rango
        // Nota: iteramos hasta index - 1 porque las celdas están ENTRE las líneas
        for (int y = yStartIndex; y < yEndIndex; y++) {
            for (int x = xStartIndex; x < xEndIndex; x++) {
                if (!solidCells[y][x]) {
                    return false; // Encontramos un hueco -> Rectángulo inválido
                }
            }
        }
        return true;
    }

    // Algoritmo Ray Casting estándar (adaptado para double)
    private boolean isPointInsidePolygon(double x, double y, List<Point> vertices) {
        boolean inside = false;
        int n = vertices.size();
        for (int i = 0, j = n - 1; i < n; j = i++) {
            Point vi = vertices.get(i);
            Point vj = vertices.get(j);
            if (((vi.y() > y) != (vj.y() > y)) &&
                    (x < (vj.x() - vi.x()) * (y - vi.y()) / (double)(vj.y() - vi.y()) + vi.x())) {
                inside = !inside;
            }
        }
        return inside;
    }
}
package software.aoc.day09.b.model;

import software.aoc.day09.a.model.BoundingBox;
import software.aoc.day09.a.model.Point;

import java.util.Arrays;
import java.util.List;

public class CompressedGrid {

    private final int[] xCoords;
    private final int[] yCoords;
    private final boolean[][] solidCells;

    public CompressedGrid(List<Point> vertices, RayCaster rayCaster) {
        // 1. Compresión de coordenadas (Distinct + Sorted)
        // Nota: Usamos int[] asumiendo que caben, si el dominio A usara long, adaptaríamos aquí.
        this.xCoords = vertices.stream().mapToInt(Point::x).distinct().sorted().toArray();
        this.yCoords = vertices.stream().mapToInt(Point::y).distinct().sorted().toArray();

        int width = xCoords.length - 1;
        int height = yCoords.length - 1;

        // 2. Construcción de la matriz lógica
        this.solidCells = new boolean[height][width];

        // 3. Relleno (Pre-cálculo)
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Probamos el punto central de la celda lógica
                double midX = (xCoords[x] + xCoords[x+1]) / 2.0;
                double midY = (yCoords[y] + yCoords[y+1]) / 2.0;

                if (rayCaster.isPointInside(midX, midY, vertices)) {
                    solidCells[y][x] = true;
                }
            }
        }
    }

    public boolean fullyCovers(BoundingBox box) {
        // Helpers locales para obtener límites del BoundingBox (reutilizado de A)
        int minX = Math.min(box.p1().x(), box.p2().x());
        int maxX = Math.max(box.p1().x(), box.p2().x());
        int minY = Math.min(box.p1().y(), box.p2().y());
        int maxY = Math.max(box.p1().y(), box.p2().y());

        // Búsqueda binaria sobre el eje comprimido
        int xStart = Arrays.binarySearch(xCoords, minX);
        int xEnd = Arrays.binarySearch(xCoords, maxX);
        int yStart = Arrays.binarySearch(yCoords, minY);
        int yEnd = Arrays.binarySearch(yCoords, maxY);

        if (xStart < 0 || xEnd < 0 || yStart < 0 || yEnd < 0) return false;

        // Verificar que todas las celdas lógicas en el rango sean sólidas
        for (int y = yStart; y < yEnd; y++) {
            for (int x = xStart; x < xEnd; x++) {
                if (!solidCells[y][x]) {
                    return false; // Hueco encontrado
                }
            }
        }
        return true;
    }
}
package software.aoc.day09.a;

import java.util.ArrayList;
import java.util.List;

public class RectangleSolver {

    public long findLargestRectangleArea(List<String> lines) {
        // 1. Parseo de datos (Single Responsibility)
        List<Point> points = parsePoints(lines);

        // 2. Cálculo de fuerza bruta sobre pares
        return calculateMaxAreaFromPairs(points);
    }

    private List<Point> parsePoints(List<String> lines) {
        List<Point> points = new ArrayList<>();
        for (String line : lines) {
            if (line.isBlank()) continue;

            String[] parts = line.split(",");
            int x = Integer.parseInt(parts[0].trim());
            int y = Integer.parseInt(parts[1].trim());

            points.add(new Point(x, y));
        }
        return points;
    }

    private long calculateMaxAreaFromPairs(List<Point> points) {
        long maxArea = 0;
        int n = points.size();

        // Comparamos cada punto con todos los demás para formar diagonales
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                Point p1 = points.get(i);
                Point p2 = points.get(j);

                long currentArea = calculateInclusiveArea(p1, p2);

                if (currentArea > maxArea) {
                    maxArea = currentArea;
                }
            }
        }
        return maxArea;
    }

    /**
     * Calcula el área inclusiva (contando nodos, no solo distancia).
     * Ejemplo: de 2 a 11 hay distancia 9, pero 10 puntos (2,3,4,5,6,7,8,9,10,11).
     */
    private long calculateInclusiveArea(Point p1, Point p2) {
        // Math.abs para obtener la distancia positiva
        long width = Math.abs(p1.x() - p2.x()) + 1;
        long height = Math.abs(p1.y() - p2.y()) + 1;

        return width * height;
    }
}
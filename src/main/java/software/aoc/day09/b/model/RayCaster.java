package software.aoc.day09.b.model;

import software.aoc.day09.a.model.Point;
import java.util.List;

public class RayCaster {


     // Determina si un punto (x, y) está dentro del polígono definido por los vértices.
     // Utiliza el algoritmo de Ray Casting (Even-Odd rule).

    public boolean isPointInside(double x, double y, List<Point> vertices) {
        boolean inside = false;
        int n = vertices.size();
        for (int i = 0, j = n - 1; i < n; j = i++) {
            Point vi = vertices.get(i);
            Point vj = vertices.get(j);

            // Lógica geométrica proyectiva
            if (((vi.y() > y) != (vj.y() > y)) &&
                    (x < (vj.x() - vi.x()) * (y - vi.y()) / (double)(vj.y() - vi.y()) + vi.x())) {
                inside = !inside;
            }
        }
        return inside;
    }
}
package software.aoc.day09.b;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class RectangleSolver {

    public Rectangle solve(List<Point> vertices) {
        if (vertices.size() < 2) {
            return new Rectangle(new Point(0,0), new Point(0,0));
        }

        // 1. Construir Grid Comprimido
        CompressedGrid grid = new CompressedGrid(vertices);

        // 2. Buscar el mejor rectángulo devolviendo el objeto entero
        return generateRectangleCandidates(vertices)
                .parallel()
                .filter(grid::covers) // Filtramos solo los válidos
                .max(Comparator.comparingLong(Rectangle::area)) // Comparamos por área
                .orElse(new Rectangle(new Point(0,0), new Point(0,0))); // Retorno por defecto si no hay solución
    }

    private java.util.stream.Stream<Rectangle> generateRectangleCandidates(List<Point> points) {
        int n = points.size();
        return IntStream.range(0, n)
                .boxed()
                .flatMap(i -> IntStream.range(i + 1, n)
                        .mapToObj(j -> new Rectangle(points.get(i), points.get(j)))
                );
    }
}
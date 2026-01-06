package software.aoc.day09.b;

import software.aoc.day09.a.BoundingBox;
import software.aoc.day09.a.Point;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ConstrainedSolver {

    public BoundingBox solve(List<Point> vertices) {
        if (vertices.size() < 2) {
            return new BoundingBox(new Point(0,0), new Point(0,0));
        }

        // 1. Preparar infraestructura
        RayCaster rayCaster = new RayCaster();
        CompressedGrid grid = new CompressedGrid(vertices, rayCaster);

        // 2. Generar y filtrar (Paralelizable por rendimiento)
        return generateCandidates(vertices)
                .parallel()
                .filter(grid::fullyCovers)
                .max(Comparator.comparingLong(BoundingBox::calculateInclusiveArea))
                .orElse(new BoundingBox(new Point(0,0), new Point(0,0)));
    }

    private Stream<BoundingBox> generateCandidates(List<Point> points) {
        int n = points.size();
        // Genera todos los pares posibles de vértices
        return IntStream.range(0, n)
                .boxed()
                .flatMap(i -> IntStream.range(i + 1, n)
                        .mapToObj(j -> new BoundingBox(points.get(i), points.get(j)))
                );
    }
}
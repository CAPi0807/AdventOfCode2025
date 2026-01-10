package software.aoc.day09.a.strategy;

import software.aoc.day09.a.model.AreaStrategy;
import software.aoc.day09.a.model.BoundingBox;
import software.aoc.day09.a.model.Point;

import java.util.List;

public class BruteForcePairsStrategy implements AreaStrategy {

    @Override
    public long findMaxArea(List<Point> points) {
        long maxArea = 0;
        int n = points.size();

        // Comparación O(N^2)
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                Point p1 = points.get(i);
                Point p2 = points.get(j);

                // Delegamos el cálculo matemático al objeto de dominio
                BoundingBox box = new BoundingBox(p1, p2);
                long currentArea = box.calculateInclusiveArea();

                if (currentArea > maxArea) {
                    maxArea = currentArea;
                }
            }
        }
        return maxArea;
    }
}
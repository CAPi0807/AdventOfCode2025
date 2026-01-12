package software.aoc.day08.a.service;

import software.aoc.day08.a.model.DisjointSet;
import software.aoc.day08.a.model.DistanceStrategy;
import software.aoc.day08.a.model.Edge;
import software.aoc.day08.a.model.Point3D;

import java.util.ArrayList;
import java.util.List;

public class NetworkService {

    private final DistanceStrategy distanceStrategy;

    public NetworkService(DistanceStrategy distanceStrategy) {
        this.distanceStrategy = distanceStrategy;
    }

    public long calculateCircuitScore(List<Point3D> points, int maxConnections) {

        List<Edge> edges = generateAllEdges(points);

        edges.sort(Edge::compareTo);

        // Conectar componentes
        DisjointSet dsu = new DisjointSet(points.size());
        int limit = Math.min(edges.size(), maxConnections);

        for (int i = 0; i < limit; i++) {
            Edge edge = edges.get(i);
            dsu.union(edge.nodeIndexA(), edge.nodeIndexB());
        }

        return calculateScore(dsu.getComponentSizes());
    }

    private List<Edge> generateAllEdges(List<Point3D> points) {
        List<Edge> edges = new ArrayList<>();
        int n = points.size();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double dist = distanceStrategy.calculate(points.get(i), points.get(j));
                edges.add(new Edge(i, j, dist));
            }
        }
        return edges;
    }

    private long calculateScore(List<Integer> sizes) {
        return sizes.stream()
                .sorted((a, b) -> b - a) // Descendente
                .limit(3)
                .mapToLong(Integer::longValue)
                .reduce(1, (a, b) -> a * b);
    }
}
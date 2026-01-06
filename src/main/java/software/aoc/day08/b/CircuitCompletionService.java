package software.aoc.day08.b;

import software.aoc.day08.a.DistanceStrategy;
import software.aoc.day08.a.Edge;
import software.aoc.day08.a.EuclideanStrategy;
import software.aoc.day08.a.Point3D;

import java.util.ArrayList;
import java.util.List;

public class CircuitCompletionService {

    private final DistanceStrategy distanceStrategy;

    public CircuitCompletionService(EuclideanStrategy distanceStrategy) {
        this.distanceStrategy = distanceStrategy;
    }

    /**
     * Ejecuta Kruskal hasta que todo está conectado.
     * @return El producto X1 * X2 de la arista que cerró el circuito.
     */
    public long findCriticalConnectionValue(List<Point3D> points) {
        if (points.size() < 2) throw new IllegalArgumentException("Need at least 2 points");

        // 1. Generar todas las aristas
        List<Edge> edges = generateAllEdges(points);

        // 2. Ordenar por peso (distancia)
        edges.sort(Edge::compareTo);

        // 3. Inicializar DSU
        DisjointSet dsu = new DisjointSet(points.size());

        // 4. Bucle principal de Kruskal
        for (Edge edge : edges) {
            boolean merged = dsu.union(edge.nodeIndexA(), edge.nodeIndexB());

            if (merged) {
                // Condición de parada: Todo está conectado
                if (dsu.getComponentCount() == 1) {
                    Point3D pA = points.get(edge.nodeIndexA());
                    Point3D pB = points.get(edge.nodeIndexB());

                    // Cálculo específico del problema: Multiplicación de coordenadas X
                    return (long) pA.x() * pB.x();
                }
            }
        }

        throw new IllegalStateException("Graph is not fully connected (Islands remain)");
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
}
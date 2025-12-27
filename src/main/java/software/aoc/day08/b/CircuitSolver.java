package software.aoc.day08.b;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CircuitSolver {

    public long solveFullCircuit(List<String> lines) {
        List<Box> boxes = parseBoxes(lines);

        // 1. Generar todas las posibles conexiones
        List<Connection> connections = generateAllConnections(boxes);

        // 2. Ordenar por distancia (menor a mayor) - Esencia del algoritmo de Kruskal
        connections.sort(Comparator.comparingDouble(Connection::distanceSquared));

        // 3. Inicializar Union-Find con el total de cajas
        DisjointSet dsu = new DisjointSet(boxes.size());

        // 4. Iterar conexiones hasta unificar todo
        for (Connection conn : connections) {
            // Intentamos unir las cajas A y B
            boolean wasMerged = dsu.union(conn.boxIndexA(), conn.boxIndexB());

            if (wasMerged) {
                // Si la unión fue exitosa, verificamos si ya terminamos
                // (es decir, si todas las cajas forman un solo circuito)
                if (dsu.getComponentCount() == 1) {
                    Box boxA = boxes.get(conn.boxIndexA());
                    Box boxB = boxes.get(conn.boxIndexB());

                    // Retornamos la multiplicación de sus coordenadas X
                    return (long) boxA.x() * boxB.x();
                }
            }
        }

        throw new IllegalStateException("No se pudo conectar todas las cajas en un solo circuito.");
    }

    private List<Box> parseBoxes(List<String> lines) {
        List<Box> boxes = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split(",");
            boxes.add(new Box(
                    Integer.parseInt(parts[0].trim()),
                    Integer.parseInt(parts[1].trim()),
                    Integer.parseInt(parts[2].trim())
            ));
        }
        return boxes;
    }

    private List<Connection> generateAllConnections(List<Box> boxes) {
        List<Connection> connections = new ArrayList<>();
        int n = boxes.size();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                Box b1 = boxes.get(i);
                Box b2 = boxes.get(j);

                double distSq = Math.pow(b1.x() - b2.x(), 2) +
                        Math.pow(b1.y() - b2.y(), 2) +
                        Math.pow(b1.z() - b2.z(), 2);

                connections.add(new Connection(i, j, distSq));
            }
        }
        return connections;
    }

    // Records para DTOs
    private record Box(int x, int y, int z) {}
    private record Connection(int boxIndexA, int boxIndexB, double distanceSquared) {}
}
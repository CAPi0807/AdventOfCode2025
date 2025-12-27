package software.aoc.day08.a;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CircuitSolver {

    public long solve(List<String> lines, int maxConnections) {
        // 1. Parsear el input a objetos Box
        List<Box> boxes = parseBoxes(lines);

        // 2. Generar todas las conexiones posibles (aristas) con sus distancias
        List<Connection> connections = generateAllConnections(boxes);

        // 3. Ordenar por distancia (de menor a mayor)
        connections.sort(Comparator.comparingDouble(Connection::distanceSquared));

        // 4. Aplicar lógica de Union-Find
        DisjointSet dsu = new DisjointSet(boxes.size());

        int attempts = Math.min(connections.size(), maxConnections);

        for (int i = 0; i < attempts; i++) {
            Connection conn = connections.get(i);

            // Intentamos unir. La clase DisjointSet maneja internamente
            // si ya están conectados ("no hacemos nada" si find(a) == find(b))
            dsu.union(conn.boxIndexA(), conn.boxIndexB());
        }

        // 5. Obtener tamaños de los circuitos
        List<Integer> circuitSizes = dsu.getComponentSizes();

        // 6. Calcular el resultado (producto de los top 3)
        return calculateTop3Product(circuitSizes);
    }

    private List<Box> parseBoxes(List<String> lines) {
        List<Box> boxes = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split(",");
            int x = Integer.parseInt(parts[0].trim());
            int y = Integer.parseInt(parts[1].trim());
            int z = Integer.parseInt(parts[2].trim());
            boxes.add(new Box(x, y, z));
        }
        return boxes;
    }

    private List<Connection> generateAllConnections(List<Box> boxes) {
        List<Connection> connections = new ArrayList<>();
        // Doble bucle para conectar todos con todos (n * (n-1) / 2 pares)
        for (int i = 0; i < boxes.size(); i++) {
            for (int j = i + 1; j < boxes.size(); j++) {
                Box b1 = boxes.get(i);
                Box b2 = boxes.get(j);
                // Usamos distancia euclidiana al cuadrado para evitar Math.sqrt (más eficiente y preciso)
                double distSq = Math.pow(b1.x() - b2.x(), 2) +
                        Math.pow(b1.y() - b2.y(), 2) +
                        Math.pow(b1.z() - b2.z(), 2);

                connections.add(new Connection(i, j, distSq));
            }
        }
        return connections;
    }

    private long calculateTop3Product(List<Integer> sizes) {
        // Ordenar descendente
        sizes.sort((a, b) -> b - a);

        long result = 1;
        // Tomamos hasta 3, o los que haya si son menos
        int limit = Math.min(sizes.size(), 3);

        for (int i = 0; i < limit; i++) {
            result *= sizes.get(i);
        }

        return result;
    }

    // --- Clases Internas/Records para encapsulación de datos (DTOs) ---

    // Usamos Records de Java (Java 14+) por ser inmutables y concisos
    private record Box(int x, int y, int z) {}

    private record Connection(int boxIndexA, int boxIndexB, double distanceSquared) {}
}
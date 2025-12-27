package software.aoc.day08.b;

public class DisjointSet {

    private final int[] parent;
    private final int[] size;
    private int componentCount; // Nuevo: Rastrea cuántos grupos aislados quedan

    public DisjointSet(int n) {
        parent = new int[n];
        size = new int[n];
        componentCount = n; // Al inicio, hay N componentes (cada caja es su propia isla)

        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    public int find(int i) {
        if (parent[i] == i) {
            return i;
        }
        parent[i] = find(parent[i]); // Path compression
        return parent[i];
    }

    /**
     * Intenta unir dos conjuntos.
     * @return true si se fusionaron dos conjuntos diferentes.
     * false si ya pertenecían al mismo conjunto.
     */
    public boolean union(int i, int j) {
        int rootA = find(i);
        int rootB = find(j);

        if (rootA != rootB) {
            // Unimos el más pequeño al más grande
            if (size[rootA] < size[rootB]) {
                parent[rootA] = rootB;
                size[rootB] += size[rootA];
            } else {
                parent[rootB] = rootA;
                size[rootA] += size[rootB];
            }

            componentCount--; // Importante: Reducimos el contador de grupos
            return true;
        }
        return false;
    }

    public int getComponentCount() {
        return componentCount;
    }
}
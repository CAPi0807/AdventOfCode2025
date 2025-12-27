package software.aoc.day08.a;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DisjointSet {

    private final int[] parent;
    private final int[] size;

    public DisjointSet(int n) {
        parent = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i; // Cada nodo es su propio padre al inicio
            size[i] = 1;   // Cada circuito tiene tamaño 1 al inicio
        }
    }

    public int find(int i) {
        if (parent[i] == i) {
            return i;
        }
        // Path compression: apunta directamente al nodo raíz para aplanar el árbol
        parent[i] = find(parent[i]);
        return parent[i];
    }

    public void union(int i, int j) {
        int rootA = find(i);
        int rootB = find(j);

        if (rootA != rootB) {
            // Unimos el conjunto más pequeño al más grande (optimización por tamaño)
            if (size[rootA] < size[rootB]) {
                parent[rootA] = rootB;
                size[rootB] += size[rootA];
            } else {
                parent[rootB] = rootA;
                size[rootA] += size[rootB];
            }
        }
        // Si rootA == rootB, ya están conectados ("no hacemos nada"),
        // tal como pide el problema.
    }

    public List<Integer> getComponentSizes() {
        Map<Integer, Integer> rootSizes = new HashMap<>();

        for (int i = 0; i < parent.length; i++) {
            int root = find(i);
            // Solo nos interesa el tamaño almacenado en la raíz
            // Ojo: size[] es correcto solo en la raíz del conjunto
            rootSizes.put(root, size[root]);
        }

        return new ArrayList<>(rootSizes.values());
    }
}
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
            parent[i] = i;
            size[i] = 1;
        }
    }

    public int find(int i) {
        if (parent[i] == i) return i;
        parent[i] = find(parent[i]); // Path compression
        return parent[i];
    }

    public void union(int i, int j) {
        int rootA = find(i);
        int rootB = find(j);

        if (rootA != rootB) {
            // Optimización por tamaño
            if (size[rootA] < size[rootB]) {
                parent[rootA] = rootB;
                size[rootB] += size[rootA];
            } else {
                parent[rootB] = rootA;
                size[rootA] += size[rootB];
            }
        }
    }

    public List<Integer> getComponentSizes() {
        Map<Integer, Integer> rootSizes = new HashMap<>();
        for (int i = 0; i < parent.length; i++) {
            int root = find(i);
            rootSizes.put(root, size[root]);
        }
        return new ArrayList<>(rootSizes.values());
    }
}
package software.aoc.day08.b.model;

public class DisjointSet {
    private final int[] parent;
    private final int[] size;
    private int componentCount; // ¿Hemos conectado todo?

    public DisjointSet(int n) {
        this.componentCount = n;
        this.parent = new int[n];
        this.size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    public int find(int i) {
        if (parent[i] == i) return i;
        parent[i] = find(parent[i]);
        return parent[i];
    }

    public boolean union(int i, int j) {
        int rootA = find(i);
        int rootB = find(j);

        if (rootA != rootB) {
            if (size[rootA] < size[rootB]) {
                parent[rootA] = rootB;
                size[rootB] += size[rootA];
            } else {
                parent[rootB] = rootA;
                size[rootA] += size[rootB];
            }
            componentCount--; // Reducción de componentes
            return true;
        }
        return false;
    }

    public int getComponentCount() {
        return componentCount;
    }
}
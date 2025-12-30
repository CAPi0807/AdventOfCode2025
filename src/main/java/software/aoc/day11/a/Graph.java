package software.aoc.day11.a;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Graph {
    private final Map<String, List<String>> adjacencyList;

    public Graph(Map<String, List<String>> adjacencyList) {
        this.adjacencyList = Map.copyOf(adjacencyList);
    }

    public List<String> getNeighbors(String node) {
        return adjacencyList.getOrDefault(node, Collections.emptyList());
    }
}
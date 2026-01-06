package software.aoc.day08.a;

public record Edge(int nodeIndexA, int nodeIndexB, double weight) implements Comparable<Edge> {
    @Override
    public int compareTo(Edge other) {
        return Double.compare(this.weight, other.weight);
    }
}
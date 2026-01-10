package software.aoc.day12.model;

public record Coordinate(int r, int c) {
    public Coordinate add(Coordinate other) {
        return new Coordinate(this.r + other.r, this.c + other.c);
    }
}
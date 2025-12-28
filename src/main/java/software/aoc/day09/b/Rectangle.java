package software.aoc.day09.b;

public record Rectangle(Point p1, Point p2) {

    public long area() {
        long width = Math.abs(p1.x() - p2.x()) + 1;
        long height = Math.abs(p1.y() - p2.y()) + 1;
        return width * height;
    }

    public long minX() { return Math.min(p1.x(), p2.x()); }
    public long maxX() { return Math.max(p1.x(), p2.x()); }
    public long minY() { return Math.min(p1.y(), p2.y()); }
    public long maxY() { return Math.max(p1.y(), p2.y()); }
}
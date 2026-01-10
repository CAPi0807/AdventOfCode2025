package software.aoc.day09.a.model;

public record BoundingBox(Point p1, Point p2) {

    public long calculateInclusiveArea() {
        // Lógica de dominio: Distancia + 1 (Geometría discreta/pixelada)
        long width = Math.abs(p1.x() - p2.x()) + 1;
        long height = Math.abs(p1.y() - p2.y()) + 1;

        return width * height;
    }
}
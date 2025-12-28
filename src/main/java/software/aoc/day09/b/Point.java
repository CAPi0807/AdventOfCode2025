package software.aoc.day09.b;

public record Point(long x, long y) {

    public long distanceX(Point other) {
        return Math.abs(this.x - other.x);
    }

    public long distanceY(Point other) {
        return Math.abs(this.y - other.y);
    }

    // Usamos double para el centro preciso
    public Point2D centerWith(Point other) {
        return new Point2D((this.x + other.x) / 2.0, (this.y + other.y) / 2.0);
    }

    // Registro interno auxiliar para puntos con decimales
    public record Point2D(double x, double y) {}
}
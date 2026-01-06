package software.aoc.day09.a;

public record Point(int x, int y) {
    public Point {}

    public static Point parse(String line) {
        String[] parts = line.split(",");
        return new Point(
                Integer.parseInt(parts[0].trim()),
                Integer.parseInt(parts[1].trim())
        );
    }
}
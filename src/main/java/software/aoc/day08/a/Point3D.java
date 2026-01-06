package software.aoc.day08.a;

public record Point3D(int x, int y, int z) {
    public static Point3D parse(String line) {
        String[] parts = line.split(",");
        return new Point3D(
                Integer.parseInt(parts[0].trim()),
                Integer.parseInt(parts[1].trim()),
                Integer.parseInt(parts[2].trim())
        );
    }
}
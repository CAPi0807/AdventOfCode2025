package software.aoc.day05.b;

public record NumericRange(long start, long end) implements Comparable<NumericRange> {

    public NumericRange {
        if (start > end) {
            throw new IllegalArgumentException("Start (" + start + ") cannot be greater than end (" + end + ")");
        }
    }

    public long size() {
        return end - start + 1;
    }

    /**
     * Verifica si este rango se solapa o es adyacente (toca) al otro.
     * Ej: [1-5] y [6-10] se tocan.
     */
    public boolean overlapsOrTouches(NumericRange other) {
        // Asumiendo que 'this' empieza antes o igual que 'other' (gracias al ordenamiento previo)
        return other.start <= this.end + 1;
    }

    /**
     * Crea un nuevo rango que engloba a ambos.
     */
    public NumericRange merge(NumericRange other) {
        long newStart = Math.min(this.start, other.start);
        long newEnd = Math.max(this.end, other.end);
        return new NumericRange(newStart, newEnd);
    }

    public static NumericRange parse(String line) {
        String[] parts = line.split("-");
        return new NumericRange(
                Long.parseLong(parts[0].trim()),
                Long.parseLong(parts[1].trim())
        );
    }

    @Override
    public int compareTo(NumericRange o) {
        return Long.compare(this.start, o.start);
    }
}
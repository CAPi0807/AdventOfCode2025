package software.aoc.day06.a;

import java.util.List;

public record Matrix(List<List<Long>> rows) {

    public Matrix {
        if (rows == null) throw new IllegalArgumentException("Rows cannot be null");
    }

    public int height() {
        return rows.size();
    }

    public int width() {
        return rows.isEmpty() ? 0 : rows.getFirst().size();
    }

    public long get(int row, int col) {
        return rows.get(row).get(col);
    }
}
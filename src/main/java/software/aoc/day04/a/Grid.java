package software.aoc.day04.a;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class Grid {
    private final char[][] data;
    private final int rows;
    private final int cols;

    // Constructor público (Parsing)
    public Grid(List<String> lines) {
        this.rows = lines.size();
        this.cols = lines.isEmpty() ? 0 : lines.getFirst().length();
        this.data = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            this.data[i] = lines.get(i).toCharArray();
        }
    }

    // Constructor privado (Copia interna para inmutabilidad)
    private Grid(char[][] data) {
        this.rows = data.length;
        this.cols = data.length > 0 ? data[0].length : 0;
        this.data = data;
    }

    public boolean isValid(Position p) {
        return p.row() >= 0 && p.row() < rows &&
                p.col() >= 0 && p.col() < cols;
    }

    public char get(Position p) {
        if (!isValid(p)) {
            throw new IndexOutOfBoundsException("Invalid position: " + p);
        }
        return data[p.row()][p.col()];
    }

    public int rows() { return rows; }
    public int cols() { return cols; }

    // --- Métodos añadidos para soportar la Parte B ---

    public Stream<Position> streamAllPositions() {
        return IntStream.range(0, rows).boxed()
                .flatMap(r -> IntStream.range(0, cols)
                        .mapToObj(c -> new Position(r, c)));
    }

    public Grid without(List<Position> positionsToRemove) {
        char[][] newData = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            newData[i] = Arrays.copyOf(data[i], cols);
        }

        for (Position p : positionsToRemove) {
            if (isValid(p)) {
                newData[p.row()][p.col()] = '.';
            }
        }
        return new Grid(newData);
    }

    public List<String> print() {
        List<String> lines = new ArrayList<>();
        for (char[] row : data) {
            lines.add(new String(row));
        }
        return lines;
    }
}
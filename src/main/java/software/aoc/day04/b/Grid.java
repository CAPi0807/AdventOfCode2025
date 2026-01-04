package software.aoc.day04.b;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class Grid {
    private final char[][] data;
    private final int rows;
    private final int cols;

    public Grid(List<String> lines) {
        this.rows = lines.size();
        this.cols = lines.isEmpty() ? 0 : lines.getFirst().length();
        this.data = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            this.data[i] = lines.get(i).toCharArray();
        }
    }

    // Constructor privado para uso interno (copia defensiva)
    private Grid(char[][] data) {
        this.rows = data.length;
        this.cols = data.length > 0 ? data[0].length : 0;
        this.data = data;
    }

    public boolean isValid(Position p) {
        return p.row() >= 0 && p.row() < rows && p.col() >= 0 && p.col() < cols;
    }

    public char get(Position p) {
        if (!isValid(p)) throw new IndexOutOfBoundsException("Invalid pos: " + p);
        return data[p.row()][p.col()];
    }

    public Stream<Position> streamAllPositions() {
        return IntStream.range(0, rows).boxed()
                .flatMap(r -> IntStream.range(0, cols)
                        .mapToObj(c -> new Position(r, c)));
    }

    /**
     * Devuelve una NUEVA instancia de Grid con las posiciones indicadas vaciadas ('.').
     * Cumple con el principio de inmutabilidad.
     */
    public Grid without(List<Position> positionsToRemove) {
        // 1. Deep copy del array actual
        char[][] newData = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            newData[i] = Arrays.copyOf(data[i], cols);
        }

        // 2. Aplicar cambios en la copia
        for (Position p : positionsToRemove) {
            if (isValid(p)) {
                newData[p.row()][p.col()] = '.';
            }
        }

        // 3. Retornar nuevo objeto
        return new Grid(newData);
    }

    // Para visualizar el resultado final
    public List<String> print() {
        List<String> lines = new ArrayList<>();
        for (char[] row : data) {
            lines.add(new String(row));
        }
        return lines;
    }
}
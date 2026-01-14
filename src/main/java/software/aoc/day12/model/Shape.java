package software.aoc.day12.model;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class Shape {
    private final int id;
    private final Set<Coordinate> cells;
    private final int height;
    private final int width;
    private final List<Shape> uniqueRotations;

    // Constructor privado para uso interno (rotaciones)
    private Shape(int id, Set<Coordinate> cells, boolean calculateRotations) {
        this.id = id;
        this.cells = cells;
        this.height = this.cells.stream().mapToInt(Coordinate::r).max().orElse(0) + 1;
        this.width = this.cells.stream().mapToInt(Coordinate::c).max().orElse(0) + 1;

        // Pre-cálculo de rotaciones solo para la figura base
        if (calculateRotations) {
            this.uniqueRotations = generateUniqueRotations();
        } else {
            this.uniqueRotations = List.of();
        }
    }

    public static Shape create(int id, Set<Coordinate> cells) {
        return new Shape(id, cells, true);
    }

    public Set<Coordinate> getCells() {
        return cells;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public List<Shape> getUniqueRotations() {
        return uniqueRotations;
    }

    public int size() {
        return cells.size();
    }

    private List<Shape> generateUniqueRotations() {
        // Generar las 4 rotaciones posibles y eliminar duplicados
        // Set para evitar duplicados geométricos
        return IntStream.range(0, 4)
                .mapToObj(this::rotate)
                .distinct()
                .toList();
    }

    private Shape rotate(int times) {
        if (times == 0)
            return this;
        Set<Coordinate> current = this.cells;
        for (int i = 0; i < times; i++) {
            // Rotación 90 grados: (r, c) -> (c, -r)
            current = current.stream()
                    .map(c -> new Coordinate(c.c(), -c.r()))
                    .collect(Collectors.toSet());
        }
        return new Shape(this.id, current, false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Shape shape = (Shape) o;
        return cells.equals(shape.cells);
    }

    @Override
    public int hashCode() {
        return cells.hashCode();
    }
}
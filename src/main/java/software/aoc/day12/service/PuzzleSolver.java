package software.aoc.day12.service;

import software.aoc.day12.model.Shape;
import software.aoc.day12.model.Grid;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PuzzleSolver {

    public boolean canFitAll(int rows, int cols, List<Shape> shapesToFit) {
        // Optimización 1: Comprobar área total antes de empezar
        int totalArea = shapesToFit.stream().mapToInt(Shape::size).sum();
        if (totalArea > rows * cols) return false;

        Grid grid = new Grid(rows, cols);

        // Optimización 2: Ordenar figuras de mayor a menor tamaño o complejidad.
        // Es más difícil encajar las grandes, así que mejor fallar rápido si no caben.
        List<Shape> sortedShapes = new ArrayList<>(shapesToFit);
        sortedShapes.sort(Comparator.comparingInt(Shape::size).reversed());

        return backtrack(grid, sortedShapes, 0);
    }

    private boolean backtrack(Grid grid, List<Shape> shapes, int index) {
        // Caso base: todas las figuras han sido colocadas
        if (index == shapes.size()) {
            return true;
        }

        Shape currentShape = shapes.get(index);

        // Probar todas las rotaciones únicas de la figura actual
        for (Shape rotatedShape : currentShape.getUniqueRotations()) {
            // Intentar colocar en todas las coordenadas posibles
            // Nota: grid.rows - rotatedShape.getHeight() es una pequeña optimización de límites
            for (int r = 0; r <= 50 - rotatedShape.getHeight(); r++) { // Usamos límite seguro, Grid tiene check interno
                for (int c = 0; c <= 50 - rotatedShape.getWidth(); c++) {

                    if (grid.canPlace(rotatedShape, r, c)) {
                        grid.place(rotatedShape, r, c);

                        // Paso recursivo
                        if (backtrack(grid, shapes, index + 1)) {
                            return true;
                        }

                        // Backtrack: deshacer el movimiento si no llevó a una solución
                        grid.remove(rotatedShape, r, c);
                    }
                }
            }
        }

        return false;
    }
}
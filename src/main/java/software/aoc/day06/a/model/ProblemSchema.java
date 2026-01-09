package software.aoc.day06.a.model;

import java.util.List;

public record ProblemSchema(Matrix matrix, List<Operator> operators) {

    public ProblemSchema {
        // Validación de consistencia dimensional
        int expectedCols = operators.size();
        for (List<Long> row : matrix.rows()) {
            if (row.size() != expectedCols) {
                throw new IllegalArgumentException(
                        "Dimension mismatch: Matrix width " + row.size() +
                                " does not match operator count " + expectedCols
                );
            }
        }
    }
}
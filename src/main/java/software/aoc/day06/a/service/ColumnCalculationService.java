package software.aoc.day06.a.service;

import software.aoc.day06.a.model.Matrix;
import software.aoc.day06.a.model.Operator;
import software.aoc.day06.a.model.ProblemSchema;

import java.util.List;

public class ColumnCalculationService {

    public long solve(ProblemSchema schema) {
        Matrix matrix = schema.matrix();
        List<Operator> operators = schema.operators();
        long totalSum = 0;

        for (int col = 0; col < matrix.width(); col++) {
            Operator op = operators.get(col);
            totalSum += calculateColumn(matrix, col, op);
        }

        return totalSum;
    }

    private long calculateColumn(Matrix matrix, int col, Operator op) {
        // Iniciamos con el valor identidad (0 para suma, 1 para multi)
        long accumulator = op.identity();

        for (int row = 0; row < matrix.height(); row++) {
            long value = matrix.get(row, col);
            accumulator = op.apply(accumulator, value);
        }
        return accumulator;
    }
}
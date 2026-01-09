package software.aoc.day06.a.parser;

import software.aoc.day06.a.model.Matrix;
import software.aoc.day06.a.model.Operator;
import software.aoc.day06.a.model.ProblemSchema;

import java.util.ArrayList;
import java.util.List;

public class ProblemParser {

    public ProblemSchema parse(List<String> lines) {
        int operatorLineIndex = findOperatorLineIndex(lines);

        // 1. Parsear Matriz (líneas anteriores a los operadores)
        List<String> matrixLines = lines.subList(0, operatorLineIndex);
        Matrix matrix = parseMatrix(matrixLines);

        // 2. Parsear Operadores
        String operatorString = lines.get(operatorLineIndex);
        List<Operator> operators = parseOperators(operatorString);

        return new ProblemSchema(matrix, operators);
    }

    private int findOperatorLineIndex(List<String> lines) {
        for (int i = 0; i < lines.size(); i++) {
            // Usamos el método estático del Enum para detectar la línea
            if (lines.get(i).chars().anyMatch(Operator::isOperatorChar)) {
                return i;
            }
        }
        throw new IllegalArgumentException("Invalid format: No operator line found (+ or *)");
    }

    private Matrix parseMatrix(List<String> lines) {
        List<List<Long>> data = new ArrayList<>();
        for (String line : lines) {
            if (line.isBlank()) continue;

            List<Long> row = List.of(line.trim().split("\\s+")).stream()
                    .map(Long::parseLong)
                    .toList();
            data.add(row);
        }
        return new Matrix(data);
    }

    private List<Operator> parseOperators(String line) {
        return line.chars()
                .filter(Operator::isOperatorChar)
                .mapToObj(Operator::fromChar)
                .toList();
    }
}
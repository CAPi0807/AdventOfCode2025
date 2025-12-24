package software.aoc.day06.a;

import java.util.ArrayList;
import java.util.List;

public class ProblemSolver {

    public long solve(List<String> lines) {

        int operatorLineIndex = findOperatorLine(lines);

        List<String> numberLines = lines.subList(0, operatorLineIndex);
        String operatorLine = lines.get(operatorLineIndex);

        List<List<Long>> matrix = parseMatrix(numberLines);
        List<Character> operators = parseOperators(operatorLine);

        validateMatrix(matrix, operators);

        return solveColumns(matrix, operators);
    }

    private int findOperatorLine(List<String> lines) {
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).chars().anyMatch(c -> c == '+' || c == '*')) {
                return i;
            }
        }
        throw new IllegalArgumentException("Formato inválido: no se encontró línea de operadores");
    }

    private List<List<Long>> parseMatrix(List<String> lines) {
        List<List<Long>> matrix = new ArrayList<>();

        for (String line : lines) {
            matrix.add(
                    List.of(line.trim().split("\\s+"))
                            .stream()
                            .map(Long::parseLong)
                            .toList()
            );
        }
        return matrix;
    }

    private List<Character> parseOperators(String line) {
        return line.chars()
                .filter(c -> c == '+' || c == '*')
                .mapToObj(c -> (char) c)
                .toList();
    }

    private void validateMatrix(List<List<Long>> matrix, List<Character> operators) {
        int expectedColumns = operators.size();

        for (List<Long> row : matrix) {
            if (row.size() != expectedColumns) {
                throw new IllegalArgumentException(
                        "Formato inválido: número de columnas no coincide con operadores"
                );
            }
        }
    }

    private long solveColumns(List<List<Long>> matrix, List<Character> operators) {
        long total = 0;

        for (int col = 0; col < operators.size(); col++) {
            char op = operators.get(col);
            long acc = (op == '*') ? 1 : 0;

            for (List<Long> row : matrix) {
                acc = (op == '*')
                        ? acc * row.get(col)
                        : acc + row.get(col);
            }
            total += acc;
        }
        return total;
    }
}

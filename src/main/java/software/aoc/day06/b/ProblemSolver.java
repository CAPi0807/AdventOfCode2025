package software.aoc.day06.b;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ProblemSolver {

    /**
     * Método principal que orquesta la solución.
     * Sigue el principio de Responsabilidad Única (SRP) delegando tareas.
     */
    public long solve(List<String> lines) {
        if (lines == null || lines.size() < 2) {
            return 0;
        }

        // Separar datos numéricos de la línea de operadores
        List<String> dataLines = lines.subList(0, lines.size() - 1);
        String operatorLine = lines.get(lines.size() - 1);

        // Parsear la entrada en bloques lógicos (patrón de columnas contiguas)
        List<NumberBlock> blocks = parseBlocks(dataLines, operatorLine);

        // Calcular y sumar resultados (Uso de Streams y reducción)
        return blocks.stream()
                .mapToLong(NumberBlock::calculate)
                .sum();
    }

    /**
     * Identifica grupos de columnas numéricas separadas por espacios vacíos.
     */
    private List<NumberBlock> parseBlocks(List<String> dataRows, String operatorRow) {
        List<NumberBlock> blocks = new ArrayList<>();
        int maxWidth = dataRows.stream().mapToInt(String::length).max().orElse(0);
        maxWidth = Math.max(maxWidth, operatorRow.length());

        List<Long> currentNumbers = new ArrayList<>();
        StringBuilder currentOperatorSig = new StringBuilder();

        for (int colIndex = 0; colIndex < maxWidth; colIndex++) {
            // Extraer números verticalmente para esta columna
            Optional<Long> number = extractVerticalNumber(dataRows, colIndex);

            // Extraer operador en esta columna (si existe)
            char opChar = (colIndex < operatorRow.length()) ? operatorRow.charAt(colIndex) : ' ';

            if (number.isPresent()) {
                currentNumbers.add(number.get());
                if (opChar != ' ') {
                    currentOperatorSig.append(opChar);
                }
            } else {
                // Si encontramos una columna vacía y tenemos números acumulados, cerramos el bloque
                if (!currentNumbers.isEmpty()) {
                    blocks.add(createBlock(currentNumbers, currentOperatorSig.toString()));
                    currentNumbers = new ArrayList<>();
                    currentOperatorSig.setLength(0);
                }
            }
        }

        // Agregar el último bloque si existe
        if (!currentNumbers.isEmpty()) {
            blocks.add(createBlock(currentNumbers, currentOperatorSig.toString()));
        }

        return blocks;
    }

    /**
     * Lee una columna verticalmente, ignora espacios y concatena dígitos.
     * Devuelve Empty si la columna no contiene dígitos.
     */
    private Optional<Long> extractVerticalNumber(List<String> rows, int colIndex) {
        StringBuilder sb = new StringBuilder();
        boolean hasDigit = false;

        for (String row : rows) {
            if (colIndex < row.length()) {
                char c = row.charAt(colIndex);
                if (Character.isDigit(c)) {
                    sb.append(c);
                    hasDigit = true;
                }
            }
        }

        return hasDigit ? Optional.of(Long.parseLong(sb.toString())) : Optional.empty();
    }

    private NumberBlock createBlock(List<Long> numbers, String operatorSig) {
        // Determinamos la operación basada en el caracter encontrado (por defecto SUMA si no hay nada claro)
        Operation op = operatorSig.contains("*") ? Operation.MULTIPLY : Operation.SUM;
        return new NumberBlock(new ArrayList<>(numbers), op);
    }

    // --- Inner Classes for Domain Modeling (Clean Code & Encapsulation) ---

    /**
     * Representa un grupo de números (columnas contiguas) y la operación que los rige.
     */
    private static class NumberBlock {
        private final List<Long> numbers;
        private final Operation operation;

        public NumberBlock(List<Long> numbers, Operation operation) {
            this.numbers = numbers;
            this.operation = operation;
        }

        public long calculate() {
            if (numbers.isEmpty()) return 0;

            // Uso de Stream reduce para aplicar la operación acumulativa (ej: 1 * 24 * 356)
            return numbers.stream()
                    .reduce(operation::apply)
                    .orElse(0L);
        }
    }

    /**
     * Strategy Pattern mediante Enum para definir operaciones.
     * Open/Closed Principle: Fácil añadir nuevas operaciones (RESTA, DIV) sin tocar la lógica de parseo.
     */
    private enum Operation {
        SUM {
            @Override
            long apply(long a, long b) { return a + b; }
        },
        MULTIPLY {
            @Override
            long apply(long a, long b) { return a * b; }
        };

        abstract long apply(long a, long b);
    }
}
package software.aoc.day06.b.parser;

import software.aoc.day06.b.model.NumberBlock;
import software.aoc.day06.b.model.Operation;
import software.aoc.day06.b.model.ParsedColumn;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class GridScanner {

    public List<NumberBlock> scanBlocks(List<String> dataRows, String operatorRow) {
        List<NumberBlock> blocks = new ArrayList<>();

        // Calcular el ancho máximo de la "parrilla"
        int maxWidth = Math.max(
                operatorRow.length(),
                dataRows.stream().mapToInt(String::length).max().orElse(0));

        // Estado temporal para el bloque actual
        List<Long> currentNumbers = new ArrayList<>();
        StringBuilder currentOpSignature = new StringBuilder();

        // Usar Iterator para barrido vertical
        Iterator<ParsedColumn> columnIterator = new ColumnIterator(dataRows, operatorRow, maxWidth);

        while (columnIterator.hasNext()) {
            ParsedColumn column = columnIterator.next();

            if (column.hasNumber()) {
                // Acumular en el bloque actual
                currentNumbers.add(column.number().get());
                if (column.operatorChar() != ' ') {
                    currentOpSignature.append(column.operatorChar());
                }
            } else {
                // Columna vacía -> Cortar bloque (si existe)
                if (!currentNumbers.isEmpty()) {
                    blocks.add(createBlock(currentNumbers, currentOpSignature));

                    // Resetear estado
                    currentNumbers = new ArrayList<>();
                    currentOpSignature.setLength(0);
                }
            }
        }

        // Añadir remanente final si existe
        if (!currentNumbers.isEmpty()) {
            blocks.add(createBlock(currentNumbers, currentOpSignature));
        }

        return blocks;
    }

    private NumberBlock createBlock(List<Long> numbers, StringBuilder signature) {
        Operation op = Operation.determineFromSignature(signature.toString());
        return new NumberBlock(numbers, op);
    }

    // Implementa el patrón Iterator para encapsular la lógica de travesía vertical.

    private static class ColumnIterator implements Iterator<ParsedColumn> {
        private final List<String> rows;
        private final String opRow;
        private final int maxWidth;
        private int currentColumn = 0;

        public ColumnIterator(List<String> rows, String opRow, int maxWidth) {
            this.rows = rows;
            this.opRow = opRow;
            this.maxWidth = maxWidth;
        }

        @Override
        public boolean hasNext() {
            return currentColumn < maxWidth;
        }

        @Override
        public ParsedColumn next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }

            ParsedColumn column = extractColumn(currentColumn);
            currentColumn++;
            return column;
        }

        private ParsedColumn extractColumn(int colIndex) {
            // 1. Extraer dígito vertical (concatenando filas)
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

            Optional<Long> number = hasDigit
                    ? Optional.of(Long.parseLong(sb.toString()))
                    : Optional.empty();

            // 2. Extraer operador
            char opChar = (colIndex < opRow.length()) ? opRow.charAt(colIndex) : ' ';

            return new ParsedColumn(number, opChar);
        }
    }
}
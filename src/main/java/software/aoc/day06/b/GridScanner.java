package software.aoc.day06.b;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GridScanner {

    public List<NumberBlock> scanBlocks(List<String> dataRows, String operatorRow) {
        List<NumberBlock> blocks = new ArrayList<>();

        // Calcular el ancho máximo de la "parrilla"
        int maxWidth = Math.max(
                operatorRow.length(),
                dataRows.stream().mapToInt(String::length).max().orElse(0)
        );

        // Estado temporal para el bloque actual
        List<Long> currentNumbers = new ArrayList<>();
        StringBuilder currentOpSignature = new StringBuilder();

        // Barrido vertical columna a columna
        for (int col = 0; col < maxWidth; col++) {
            ParsedColumn column = extractColumn(dataRows, operatorRow, col);

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

    private ParsedColumn extractColumn(List<String> rows, String opRow, int colIndex) {
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

    private NumberBlock createBlock(List<Long> numbers, StringBuilder signature) {
        Operation op = Operation.determineFromSignature(signature.toString());
        return new NumberBlock(numbers, op);
    }
}
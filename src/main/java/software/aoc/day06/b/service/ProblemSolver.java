package software.aoc.day06.b.service;

import software.aoc.day06.b.model.NumberBlock;
import software.aoc.day06.b.parser.GridScanner;

import java.util.List;

public class ProblemSolver {

    private final GridScanner scanner;

    public ProblemSolver() {
        this.scanner = new GridScanner();
    }

    public long solve(List<String> lines) {
        if (lines == null || lines.size() < 2) {
            return 0;
        }

        // 1. Separación de inputs (Data vs Operators)
        int lastIndex = lines.size() - 1;
        List<String> dataLines = lines.subList(0, lastIndex);
        String operatorLine = lines.get(lastIndex);

        // 2. Parsing inteligente (Scanner)
        List<NumberBlock> blocks = scanner.scanBlocks(dataLines, operatorLine);

        // 3. Cálculo y Reducción
        return blocks.stream()
                .mapToLong(NumberBlock::calculateResult)
                .sum();
    }
}
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

        // Separación de inputs
        int lastIndex = lines.size() - 1;
        List<String> dataLines = lines.subList(0, lastIndex);
        String operatorLine = lines.get(lastIndex);

        // Parsing
        List<NumberBlock> blocks = scanner.scanBlocks(dataLines, operatorLine);

        return blocks.stream()
                .mapToLong(NumberBlock::calculateResult)
                .sum();
    }
}
package software.aoc.day01.a;

import java.util.List;

public class InstructionParser {

    public List<Instruction> parseAll(List<String> lines) {
        return lines.stream()
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .map(this::parseSingle)
                .toList();
    }

    private Instruction parseSingle(String line) {
        char dirChar = line.charAt(0);
        int amount = Integer.parseInt(line.substring(1));
        return new Instruction(Direction.fromChar(dirChar), amount);
    }
}
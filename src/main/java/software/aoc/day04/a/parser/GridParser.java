package software.aoc.day04.a.parser;

import software.aoc.day04.a.model.Grid;

import java.util.List;

public class GridParser {

    public static Grid parse(List<String> lines) {
        if (lines == null || lines.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null or empty");
        }

        int cols = lines.get(0).length();
        char[][] data = new char[lines.size()][cols];

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.length() != cols) {
                throw new IllegalArgumentException("All lines must have the same length");
            }
            data[i] = line.toCharArray();
        }

        return new Grid(data);
    }
}

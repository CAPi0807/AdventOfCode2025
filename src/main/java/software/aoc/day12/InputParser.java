package software.aoc.day12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputParser {

    public record InputData(Map<Integer, Shape> shapeDefinitions, List<TestCase> testCases) {}
    public record TestCase(int rows, int cols, List<Shape> shapesToFit) {}

    public InputData parse(String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(filePath));
        Map<Integer, Shape> shapeDefs = new HashMap<>();
        List<TestCase> testCases = new ArrayList<>();

        int i = 0;
        // 1. Parsear Figuras (hasta encontrar una línea vacía o que empiece con dígitos de dimensiones)
        // El formato parece ser bloques separados por ids.

        while (i < lines.size()) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) {
                i++;
                continue;
            }

            // Si detectamos formato "WxH:", terminamos la sección de figuras
            if (line.matches("\\d+x\\d+:.*")) {
                break;
            }

            if (line.matches("\\d+:")) {
                int id = Integer.parseInt(line.replace(":", ""));
                i++;
                Set<Coordinate> coords = new HashSet<>();
                int r = 0;
                while (i < lines.size()) {
                    String rowStr = lines.get(i);
                    // Si encontramos una línea vacía o una nueva definición, paramos la figura actual
                    if (rowStr.isEmpty() || rowStr.matches("\\d+:.*") || rowStr.matches("\\d+x\\d+:.*")) {
                        break;
                    }
                    for (int c = 0; c < rowStr.length(); c++) {
                        if (rowStr.charAt(c) == '#') {
                            coords.add(new Coordinate(r, c));
                        }
                    }
                    r++;
                    i++;
                }
                shapeDefs.put(id, Shape.create(id, coords));
            } else {
                i++;
            }
        }

        // 2. Parsear Casos de Prueba (líneas tipo 4x4: 0 0 0 0 2 0)
        // Estas líneas pueden estar mezcladas o al final.
        // Re-iteramos o continuamos desde donde quedamos.
        // Dado que el bucle anterior para en el primer match de test case, podemos seguir.

        for (; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) continue;

            // Regex para "12x5: 1 0 1 0 2 2"
            Pattern pattern = Pattern.compile("(\\d+)x(\\d+):\\s*(.*)");
            Matcher matcher = pattern.matcher(line);

            if (matcher.matches()) {
                int rows = Integer.parseInt(matcher.group(1));
                int cols = Integer.parseInt(matcher.group(2));
                String countsStr = matcher.group(3);

                int[] counts = Arrays.stream(countsStr.split("\\s+"))
                        .mapToInt(Integer::parseInt)
                        .toArray();

                List<Shape> shapeList = new ArrayList<>();
                for (int shapeId = 0; shapeId < counts.length; shapeId++) {
                    int count = counts[shapeId];
                    Shape def = shapeDefs.get(shapeId);
                    if (def != null) {
                        for (int k = 0; k < count; k++) {
                            shapeList.add(def);
                        }
                    }
                }
                testCases.add(new TestCase(rows, cols, shapeList));
            }
        }

        return new InputData(shapeDefs, testCases);
    }
}
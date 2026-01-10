package software.aoc.day12;

import software.aoc.day12.parser.InputParser;
import software.aoc.day12.service.PuzzleSolver;

public class Main {
    public static void main(String[] args) {
        try {
            String path = "src/main/resources/Day12/Presents.txt"; // Asegúrate de la ruta
            if (args.length > 0) path = args[0];

            InputParser parser = new InputParser();
            InputParser.InputData data = parser.parse(path);
            PuzzleSolver solver = new PuzzleSolver();

            long validCount = data.testCases().stream()
                    .filter(tc -> {
                        System.out.printf("Procesando caso %dx%d con %d figuras... ",
                                tc.rows(), tc.cols(), tc.shapesToFit().size());
                        boolean fits = solver.canFitAll(tc.rows(), tc.cols(), tc.shapesToFit());
                        System.out.println(fits ? "OK" : "FALLO");
                        return fits;
                    })
                    .count();

            System.out.println("------------------------------------------------");
            System.out.println("Número total de configuraciones válidas: " + validCount);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
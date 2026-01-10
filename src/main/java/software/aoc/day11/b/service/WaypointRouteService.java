package software.aoc.day11.b.service;

import software.aoc.day11.a.model.Graph;
import software.aoc.day11.a.parser.GraphParser;
import software.aoc.day11.a.service.PathCounter;

import java.util.List;

public class WaypointRouteService {

    private static final String START_NODE = "svr";
    private static final String END_NODE = "out";
    private static final String WAYPOINT_1 = "fft";
    private static final String WAYPOINT_2 = "dac";

    public long solve(List<String> inputLines) {
        // 1. Reutilizamos el parser y grafo del paquete 'a'
        GraphParser parser = new GraphParser();
        Graph graph = parser.parse(inputLines);
        PathCounter counter = new PathCounter(graph);

        // 2. Calculamos la variante 1: srv -> fft -> dac -> out
        // Si no existe conexión entre algún tramo, countPaths devuelve 0, anulando esta variante.
        long pathsViaFftThenDac = counter.countPaths(START_NODE, WAYPOINT_1)
                * counter.countPaths(WAYPOINT_1, WAYPOINT_2)
                * counter.countPaths(WAYPOINT_2, END_NODE);

        // 3. Calculamos la variante 2: srv -> dac -> fft -> out
        long pathsViaDacThenFft = counter.countPaths(START_NODE, WAYPOINT_2)
                * counter.countPaths(WAYPOINT_2, WAYPOINT_1)
                * counter.countPaths(WAYPOINT_1, END_NODE);

        // 4. El total es la suma de ambas posibilidades (mutuamente excluyentes por orden)
        return pathsViaFftThenDac + pathsViaDacThenFft;
    }
}
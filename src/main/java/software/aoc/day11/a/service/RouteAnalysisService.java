package software.aoc.day11.a.service;

import software.aoc.day11.a.model.Graph;
import software.aoc.day11.a.parser.GraphParser;

import java.util.List;

public class RouteAnalysisService {

    public long solve(List<String> inputLines) {
        // 1. Parsear
        GraphParser parser = new GraphParser();
        Graph graph = parser.parse(inputLines);

        // 2. Ejecutar algoritmo
        PathCounter counter = new PathCounter(graph);

        // El problema especifica ir de "you" a "out"
        return counter.countPaths("you", "out");
    }
}
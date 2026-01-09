package software.aoc.day04.b.model;

import software.aoc.day04.a.model.Grid;
import software.aoc.day04.a.model.Position;
import java.util.List;

public interface SelectionRule {
    List<Position> findMatches(Grid grid);
}
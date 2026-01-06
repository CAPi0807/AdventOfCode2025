package software.aoc.day04.b;

import software.aoc.day04.a.Grid;
import software.aoc.day04.a.Position;
import java.util.List;

public interface SelectionRule {
    List<Position> findMatches(Grid grid);
}
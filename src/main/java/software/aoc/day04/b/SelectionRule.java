package software.aoc.day04.b;

import java.util.List;

public interface SelectionRule {
    List<Position> findMatches(Grid grid);
}
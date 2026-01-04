package software.aoc.day03.a;

import java.util.List;

public class BatteryParser {

    public List<Battery> parse(List<String> lines) {
        return lines.stream()
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Battery::new)
                .toList();
    }
}

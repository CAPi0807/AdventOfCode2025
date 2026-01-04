package software.aoc.day03.b;

import java.util.List;

public class JoltageService {

    private final JoltageStrategy strategy;

    public JoltageService(JoltageStrategy strategy) {
        this.strategy = strategy;
    }

    public long calculateTotalLoad(List<Battery> batteries) {
        return batteries.stream()
                .mapToLong(strategy::calculate)
                .sum();
    }
}
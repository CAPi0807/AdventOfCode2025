package software.aoc.day03.a;

import java.util.List;

public class JoltageService {

    private final JoltageStrategy strategy;

    public JoltageService(JoltageStrategy strategy) {
        this.strategy = strategy;
    }

    public Long calculateTotalLoad(List<Battery> batteries) {
        return batteries.stream()
                .mapToLong(strategy::calculate)
                .sum();
    }
}
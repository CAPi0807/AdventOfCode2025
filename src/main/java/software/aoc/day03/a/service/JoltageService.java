package software.aoc.day03.a.service;

import software.aoc.day03.a.model.Battery;
import software.aoc.day03.a.strategies.JoltageStrategy;

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
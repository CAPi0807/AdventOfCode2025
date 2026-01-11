package software.aoc.day03.a.service;

import software.aoc.day03.a.model.Battery;
import software.aoc.day03.a.model.JoltageStrategy;

import java.util.List;

public record JoltageService(JoltageStrategy strategy) {

    public Long calculateTotalLoad(List<Battery> batteries) {
        return batteries.stream()
                .mapToLong(strategy::calculate)
                .sum();
    }
}
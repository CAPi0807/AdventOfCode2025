package software.aoc.day06.b.model;

import java.util.Optional;

public record ParsedColumn(Optional<Long> number, char operatorChar) {
    public boolean hasNumber() {
        return number.isPresent();
    }
}
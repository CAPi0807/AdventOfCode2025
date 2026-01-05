package software.aoc.day06.b;

import java.util.Optional;

public record ParsedColumn(Optional<Long> number, char operatorChar) {
    public boolean hasNumber() {
        return number.isPresent();
    }
}
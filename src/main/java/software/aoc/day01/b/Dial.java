package software.aoc.day01.b;

import software.aoc.day01.a.Direction; // IMPORTANTE: Usamos el Enum de A
import software.aoc.day01.a.Instruction;

public record Dial(int position) {

    private static final int SIZE = 100;

    public Dial {
        if (position < 0 || position >= SIZE) {
            throw new IllegalArgumentException("Position must be between 0 and 99");
        }
    }

    public TurnResult turn(Instruction instruction) {
        // Extraemos la lógica del Enum y la traemos al dominio de B
        // usando un switch sobre el Enum de A.
        int hits = calculateHits(instruction.direction(), instruction.amount());
        int newPos = calculateNewPosition(instruction.direction(), instruction.amount());

        return new TurnResult(new Dial(newPos), hits);
    }

    // Lógica específica de la Parte B encapsulada aquí
    private int calculateHits(Direction dir, int amount) {
        return switch (dir) {
            case RIGHT -> (position + amount) / SIZE;
            case LEFT -> {
                int distToZero = (position == 0) ? SIZE : position;
                if (amount >= distToZero) {
                    yield 1 + (amount - distToZero) / SIZE;
                }
                yield 0;
            }
        };
    }

    private int calculateNewPosition(Direction dir, int amount) {
        return switch (dir) {
            case RIGHT -> (position + amount) % SIZE;
            // Math.floorMod es necesario para manejar vueltas negativas grandes
            case LEFT -> Math.floorMod(position - amount, SIZE);
        };
    }
}